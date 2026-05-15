# K3 Image Common Functions
# This file contains shared functions for K3 image recipes

# Ensure dependencies are built before image generation
DEPENDS += "u-boot-k3 opensbi-k3 linux-k3-dev esos-k3 initramfs-image-k3"

# Kernel version used for file naming in bootfs
K3_KERNEL_VERSION ?= "6.18.3"

# Create bootfs partition with kernel, env, Initramfs and dtbs
# Layout matches the reference titan image (Bianbu-GNOME-K3)
# Triggered before ext4 rootfs generation and WIC image generation
do_image_ext4[prefuncs] += "do_create_bootfs"
do_image_wic[prefuncs] += "do_create_bootfs"

python do_create_bootfs() {
    import subprocess, os, shutil

    deploydir = d.getVar('DEPLOY_DIR_IMAGE')
    bootfs_dir = os.path.join(deploydir, 'bootfs')
    bootfs_img = os.path.join(deploydir, 'bootfs.ext4')
    machine = d.getVar('MACHINE')
    kver = d.getVar('K3_KERNEL_VERSION') or '6.18.3'
    kver_generic = f"{kver}-generic"

    # 1. Reset and prepare bootfs directory
    bb.utils.remove(bootfs_dir, recurse=True)
    bb.utils.mkdirhier(bootfs_dir)

    # DTB subdirectory: spacemit/<version>/
    dtb_subdir = os.path.join(bootfs_dir, 'spacemit', kver_generic)
    bb.utils.mkdirhier(dtb_subdir)

    # Define helper function for copy logic
    def copy_file(src_name, dst_path):
        src = os.path.join(deploydir, src_name)
        if os.path.exists(src):
            shutil.copy2(src, dst_path)
            bb.note(f"Copied {src_name} to bootfs")
            return True
        else:
            bb.warn(f"File not found: {src_name}")
            return False

    # 2. Copy kernel (compressed vmlinuz)
    vmlinuz_name = f"vmlinuz-{kver_generic}"
    if not copy_file(vmlinuz_name, os.path.join(bootfs_dir, vmlinuz_name)):
        # Fallback: compress Image on the fly
        image_path = os.path.join(deploydir, 'Image')
        if os.path.exists(image_path):
            import gzip
            dst = os.path.join(bootfs_dir, vmlinuz_name)
            with open(image_path, 'rb') as f_in:
                with gzip.open(dst, 'wb', compresslevel=9) as f_out:
                    shutil.copyfileobj(f_in, f_out)
            bb.note(f"Compressed Image -> {vmlinuz_name}")
        else:
            bb.fatal("Neither vmlinuz nor Image found")

    # 3. Initramfs (rename to initrd.img-<version>)
    initrd_name = f"initrd.img-{kver_generic}"
    initramfs = next((f for f in os.listdir(deploydir)
                      if f.startswith('initramfs-image-') and f.endswith('.cpio.gz')), None)
    if initramfs:
        copy_file(initramfs, os.path.join(bootfs_dir, initrd_name))
    else:
        bb.fatal("initramfs not found")

    # 4. System.map and config
    copy_file(f"System.map-{kver_generic}", os.path.join(bootfs_dir, f"System.map-{kver_generic}"))
    copy_file(f"config-{kver_generic}", os.path.join(bootfs_dir, f"config-{kver_generic}"))

    # 5. DTBs -> spacemit/<version>/
    dtbs = [f for f in os.listdir(deploydir)
            if f.endswith('.dtb') and not os.path.islink(os.path.join(deploydir, f))]
    if not dtbs:
        bb.warn("No DTB files found")
    for dtb in dtbs:
        copy_file(dtb, os.path.join(dtb_subdir, dtb))

    # 6. Boot logo (spacemit.bmp from u-boot recipe)
    copy_file('spacemit.bmp', os.path.join(bootfs_dir, 'bianbu.bmp'))

    # 7. env_k3.txt (generate to match reference image format)
    env_content = f"""knl_name={vmlinuz_name}
ramdisk_name={initrd_name}
dtb_dir=spacemit/{kver_generic}
ramdisk_addr=0x130000000
loglevel=8
commonargs=setenv bootargs plymouth.prefer-fbcon plymouth.ignore-serial-consoles splash
"""
    env_path = os.path.join(bootfs_dir, 'env_k3.txt')
    with open(env_path, 'w') as f:
        f.write(env_content)
    bb.note("Generated env_k3.txt for titan bootfs")

    # 8. Calculate size and generate image (add 15% margin)
    import math
    total_size_kb = sum(os.path.getsize(os.path.join(root, f))
                        for root, dirs, files in os.walk(bootfs_dir)
                        for f in files) / 1024
    calc_mb = math.ceil((total_size_kb * 1.15) / 1024) + 1
    final_mb = max(int(d.getVar('SDIMG_BOOTFS_SIZE') or 256), calc_mb)

    # 9. Call mke2fs to create ext4 image
    # Disable orphan_file and metadata_csum_seed: U-Boot 2022.10 ext4 driver
    # does not recognize these features and fails to mount the filesystem.
    try:
        cmd = ['mke2fs', '-F', '-L', 'bootfs', '-t', 'ext4',
               '-b', '4096',
               '-O', '^orphan_file,^metadata_csum_seed',
               '-d', bootfs_dir, bootfs_img, f'{final_mb}M']
        subprocess.run(cmd, check=True, capture_output=True, text=True)
        bb.note(f"bootfs.ext4 ({final_mb}MB) created successfully")
    except subprocess.CalledProcessError as e:
        bb.fatal(f"mke2fs failed: {e.stderr}")
}

# Inject bootinfo into WIC image after WIC generation
do_image_wic[postfuncs] += "write_bootinfo_to_wic"

python write_bootinfo_to_wic() {
    import subprocess
    import os

    # 1. Retrieve Yocto path variables
    imgdeploydir = d.getVar('IMGDEPLOYDIR')
    image_name = d.getVar('IMAGE_NAME')
    deploy_dir = d.getVar('DEPLOY_DIR_IMAGE')

    # K3 uses bootinfo_block.bin (from u-boot-k3)
    bootinfo_path = os.path.join(deploy_dir, 'bootinfo_block.bin')

    # Define the physical path of the generated .wic file
    wic_path = os.path.join(imgdeploydir, image_name + '.wic')

    # 2. Safety checks
    if not os.path.exists(bootinfo_path):
        bb.warn(f"[Post-WIC] bootinfo file not found: {bootinfo_path} - skipping injection")
        return

    if not os.path.exists(wic_path):
        bb.error(f"[Post-WIC] Target WIC file not found: {wic_path}")
        return

    # 3. Perform the dd injection
    try:
        # Write bootinfo at 1M offset (128K size) - K3 specific
        cmd = ['dd', f'if={bootinfo_path}', f'of={wic_path}', 'bs=1K', 'seek=1024', 'count=128', 'conv=notrunc']
        subprocess.run(cmd, check=True, capture_output=True)
        bb.note(f"[Post-WIC] Successfully injected bootinfo (128K @ 1M) into {os.path.basename(wic_path)}")
    except subprocess.CalledProcessError as e:
        bb.error(f"[Post-WIC] dd command failed: {e.stderr.decode()}")
}
