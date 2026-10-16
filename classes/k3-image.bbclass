# K3 Image Common Functions
# This file contains shared functions for K3 image recipes

# Create bootfs partition with kernel, env, Initramfs and dtbs
# Layout matches the reference titan image (Bianbu-GNOME-K3)
# Triggered before ext4 rootfs generation and WIC image generation
do_image_wic[depends] += " \
    virtual/kernel:do_shared_workdir \
    core-image-minimal-initramfs:do_image_complete \
    e2fsprogs-native:do_populate_sysroot \
    bmaptool-native:do_populate_sysroot \
"
do_image_wic[prefuncs] += "do_create_bootfs"

python do_create_bootfs() {
    import subprocess, os, shutil, gzip, math

    deploydir = d.getVar('DEPLOY_DIR_IMAGE')
    bootfs_dir = os.path.join(deploydir, 'bootfs')
    bootfs_img = os.path.join(deploydir, 'bootfs.ext4')
    machine = d.getVar('MACHINE')

    # Read kernel release (e.g. "6.18.3-k3") from the kernel build's
    # kernel-abiversion file. virtual/kernel:do_shared_workdir is a
    # dependency of this task so the file is guaranteed to exist.
    kabi_path = os.path.join(d.getVar('STAGING_KERNEL_BUILDDIR'),
                             'kernel-abiversion')
    with open(kabi_path) as f:
        kver_k3 = f.read().strip()

    # 1. Reset and prepare bootfs directory
    bb.utils.remove(bootfs_dir, recurse=True)
    bb.utils.mkdirhier(bootfs_dir)

    # DTB subdirectory: spacemit/<version>/
    dtb_subdir = os.path.join(bootfs_dir, 'spacemit', kver_k3)
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
    vmlinuz_name = f"vmlinuz-{kver_k3}"
    image_path = os.path.join(deploydir, 'Image')
    if not os.path.exists(image_path):
        bb.fatal("Image not found in DEPLOYDIR")
    with open(image_path, 'rb') as f_in:
        with gzip.open(os.path.join(bootfs_dir, vmlinuz_name), 'wb',
    compresslevel=9) as f_out:
            shutil.copyfileobj(f_in, f_out)

    # 3. Initramfs (rename to initrd.img-<version>)
    initrd_name = f"initrd.img-{kver_k3}"
    initramfs = next((f for f in os.listdir(deploydir)
                      if f.startswith('core-image-minimal-initramfs-') and f.endswith('.cpio.gz')), None)
    if initramfs:
        copy_file(initramfs, os.path.join(bootfs_dir, initrd_name))
    else:
        bb.fatal("initramfs not found")

    # 4. DTBs -> spacemit/<version>/
    dtbs = [f for f in os.listdir(deploydir)
            if f.endswith('.dtb') and not os.path.islink(os.path.join(deploydir, f))]
    if not dtbs:
        bb.warn("No DTB files found")
    for dtb in dtbs:
        copy_file(dtb, os.path.join(dtb_subdir, dtb))

    # 5. Calculate size and generate image (add 15% margin)
    total_size_kb = sum(os.path.getsize(os.path.join(root, f))
                        for root, dirs, files in os.walk(bootfs_dir)
                        for f in files) / 1024
    calc_mb = math.ceil((total_size_kb * 1.15) / 1024) + 1
    final_mb = max(int(d.getVar('SDIMG_BOOTFS_SIZE') or 256), calc_mb)

    # 6. env_k3.txt (generate to match reference image format)
    env_content = f"""knl_name={vmlinuz_name}
ramdisk_name={initrd_name}
dtb_dir=spacemit/{kver_k3}
ramdisk_addr=0x130000000
loglevel=8
commonargs=setenv bootargs plymouth.prefer-fbcon plymouth.ignore-serial-consoles
"""
    env_path = os.path.join(bootfs_dir, 'env_k3.txt')
    with open(env_path, 'w') as f:
        f.write(env_content)

    # 7. Call mke2fs to create ext4 image
    # Disable orphan_file and metadata_csum_seed: U-Boot 2022.10 ext4 driver
    # does not recognize these features and fails to mount the filesystem.
    cmd = ['mke2fs', '-F', '-L', 'bootfs', '-t', 'ext4',
           '-b', '4096',
           '-O', '^orphan_file,^metadata_csum_seed',
           '-d', bootfs_dir, bootfs_img, f'{final_mb}M']
    r = subprocess.run(cmd, capture_output=True, text=True)
    if r.returncode != 0:
        bb.fatal(f"mke2fs failed: {r.stderr}")
    bb.note(f"bootfs.ext4 ({final_mb}MB) created successfully")
}

# Inject bootinfo into WIC image after WIC generation
do_image_wic[postfuncs] += "write_bootinfo_to_wic"

python write_bootinfo_to_wic() {
    import subprocess, os, gzip, shutil

    imgdeploydir = d.getVar('IMGDEPLOYDIR')
    image_name = d.getVar('IMAGE_NAME')
    deploy_dir = d.getVar('DEPLOY_DIR_IMAGE')

    bootinfo_path = os.path.join(deploy_dir, 'bootinfo_block.bin')
    wic_gz_path = os.path.join(imgdeploydir, image_name + '.wic.gz')
    wic_bmap_path = os.path.join(imgdeploydir, image_name + '.wic.bmap')

    if not os.path.exists(bootinfo_path):
        bb.warn(f"[Post-WIC] bootinfo not found: {bootinfo_path} - skipping")
        return

    if not os.path.exists(wic_gz_path):
        bb.error(f"[Post-WIC] WIC file not found: {wic_gz_path}")
        return

    tmp_wic = wic_gz_path[:-3]  # strip .gz

    try:
        with gzip.open(wic_gz_path, 'rb') as f_in, open(tmp_wic, 'wb') as f_out:
            shutil.copyfileobj(f_in, f_out)

        r = subprocess.run(
            ['dd', f'if={bootinfo_path}', f'of={tmp_wic}',
             'bs=1K', 'seek=1024', 'count=128', 'conv=notrunc'],
            capture_output=True)
        if r.returncode != 0:
            bb.error(f"[Post-WIC] dd failed: {r.stderr.decode()}")
            return
        bb.note("[Post-WIC] Injected bootinfo (128K @ 1M)")

        with open(tmp_wic, 'rb') as f_in, gzip.open(wic_gz_path, 'wb', compresslevel=9) as f_out:
            shutil.copyfileobj(f_in, f_out)

        if os.path.exists(wic_bmap_path):
            r = subprocess.run(
                ['bmaptool', 'create', tmp_wic, '-o', wic_bmap_path],
                capture_output=True)
            if r.returncode == 0:
                bb.note("[Post-WIC] Regenerated bmap")
            else:
                bb.warn(f"[Post-WIC] bmaptool failed: {r.stderr.decode()}")
    finally:
        if os.path.exists(tmp_wic):
            os.unlink(tmp_wic)
}
