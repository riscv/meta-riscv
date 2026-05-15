# K3 Titan Image Packaging
# Generates a tar.gz archive compatible with the Titan flashing tool.
# The archive contains: fastboot.yaml, partition_*.json, factory/, firmware,
# bootfs.ext4, rootfs.ext4, esp.vfat, env.bin, etc.
#
# Include this file from image bbappend files (after image-k3.inc).

DEPENDS += "mtools-native dosfstools-native"

# Titan archive output
TITAN_IMAGE_NAME ?= "${IMAGE_BASENAME}-${MACHINE}-titan"

# ESP partition size in MB (empty FAT32 partition required by titan layout)
TITAN_ESP_SIZE_MB ?= "256"

# Run titan packaging after ext4 rootfs is done
do_image_ext4[postfuncs] += "do_create_titan_archive"

python do_create_titan_archive() {
    import subprocess, os, shutil, json, time

    deploydir = d.getVar('DEPLOY_DIR_IMAGE')
    imgdeploydir = d.getVar('IMGDEPLOYDIR')
    image_name = d.getVar('IMAGE_NAME')
    image_basename = d.getVar('IMAGE_BASENAME')
    machine = d.getVar('MACHINE')
    esp_size_mb = int(d.getVar('TITAN_ESP_SIZE_MB') or '256')

    # Titan staging directory
    titan_dir = os.path.join(imgdeploydir, 'titan-staging')
    bb.utils.remove(titan_dir, recurse=True)
    bb.utils.mkdirhier(titan_dir)

    # --- 1. Create factory/ directory ---
    factory_dir = os.path.join(titan_dir, 'factory')
    bb.utils.mkdirhier(factory_dir)

    # Copy bootinfo variants and FSBL
    for f in ['bootinfo_block.bin', 'bootinfo_spinand.bin', 'bootinfo_spinor.bin', 'FSBL.bin']:
        src = os.path.join(deploydir, f)
        if os.path.exists(src):
            shutil.copy2(src, os.path.join(factory_dir, f))
            bb.note(f"[Titan] Copied {f} to factory/")
        else:
            bb.warn(f"[Titan] {f} not found in deploy dir")

    # --- 2. Copy firmware files ---
    firmware_files = ['fw_dynamic.itb', 'u-boot.itb', 'esos.itb']
    for f in firmware_files:
        src = os.path.join(deploydir, f)
        if os.path.exists(src):
            shutil.copy2(src, os.path.join(titan_dir, f))
        else:
            bb.warn(f"[Titan] {f} not found")

    # --- 3. env.bin (from u-boot-env-default.bin) ---
    env_src = os.path.join(deploydir, 'u-boot-env-default.bin')
    if os.path.exists(env_src):
        shutil.copy2(env_src, os.path.join(titan_dir, 'env.bin'))
    else:
        bb.warn("[Titan] u-boot-env-default.bin not found, env.bin skipped")

    # --- 4. bootfs.ext4 ---
    bootfs_src = os.path.join(deploydir, 'bootfs.ext4')
    if os.path.exists(bootfs_src):
        shutil.copy2(bootfs_src, os.path.join(titan_dir, 'bootfs.ext4'))
    else:
        bb.warn("[Titan] bootfs.ext4 not found")

    # --- 5. rootfs.ext4 ---
    # IMAGE_NAME is like "core-image-weston-k3.rootfs-20260511033206"
    # The ext4 file is IMAGE_NAME + '.ext4' in IMGDEPLOYDIR
    rootfs_src = os.path.join(imgdeploydir, image_name + '.ext4')
    if not os.path.exists(rootfs_src):
        # Try the symlink form: IMAGE_BASENAME-MACHINE.rootfs.ext4
        rootfs_src = os.path.join(imgdeploydir, f'{image_basename}-{machine}.rootfs.ext4')
    if not os.path.exists(rootfs_src):
        # Try deploy dir
        rootfs_src = os.path.join(deploydir, f'{image_basename}-{machine}.rootfs.ext4')
    if os.path.exists(rootfs_src):
        shutil.copy2(rootfs_src, os.path.join(titan_dir, 'rootfs.ext4'))
        bb.note(f"[Titan] Copied rootfs.ext4 from {rootfs_src}")
    else:
        bb.warn(f"[Titan] rootfs.ext4 not found (tried {imgdeploydir} and {deploydir})")

    # --- 6. Create empty ESP (FAT16, 4096-byte sector) partition ---
    esp_path = os.path.join(titan_dir, 'esp.vfat')
    esp_size_bytes = esp_size_mb * 1024 * 1024
    try:
        # Create empty file
        with open(esp_path, 'wb') as f:
            f.truncate(esp_size_bytes)
        # Format as FAT16 with 4096-byte sector size (matches reference image)
        cmd = ['mkfs.vfat', '-S', '4096', '-F', '16', '-n', 'ESP', esp_path]
        subprocess.run(cmd, check=True, capture_output=True, text=True)
        bb.note(f"[Titan] Created esp.vfat ({esp_size_mb}MB, FAT16 sector=4096)")
    except subprocess.CalledProcessError as e:
        bb.warn(f"[Titan] mkfs.vfat failed: {e.stderr}")

    # --- 7. Copy titan config files (fastboot.yaml, partition_*.json) ---
    # FILE_DIRNAME points to the .bb file directory, not the .inc file.
    # Use LAYERDIR_meta-riscv or search through BBPATH for titan-cfg.
    # The titan-cfg dir is in meta-riscv/recipes-core/images/titan-cfg/
    titan_cfg_dir = None
    # Try via TITAN_CFG_DIR variable (set in bbappend/inc)
    cfg_var = d.getVar('TITAN_CFG_DIR')
    if cfg_var and os.path.isdir(cfg_var):
        titan_cfg_dir = cfg_var
    else:
        # Search in all layers for recipes-core/images/titan-cfg
        for layerdir in (d.getVar('BBLAYERS') or '').split():
            candidate = os.path.join(layerdir, 'recipes-core', 'images', 'titan-cfg')
            if os.path.isdir(candidate):
                titan_cfg_dir = candidate
                break

    if titan_cfg_dir and os.path.isdir(titan_cfg_dir):
        for f in os.listdir(titan_cfg_dir):
            src = os.path.join(titan_cfg_dir, f)
            if os.path.isfile(src):
                shutil.copy2(src, os.path.join(titan_dir, f))
                bb.note(f"[Titan] Copied config: {f}")
    else:
        bb.warn(f"[Titan] titan-cfg directory not found (FILE_DIRNAME={d.getVar('FILE_DIRNAME')})")

    # --- 8. Create tar.gz archive ---
    timestamp = time.strftime('%Y%m%d%H%M%S')
    archive_name = f"{image_basename}-{machine}-titan-{timestamp}"
    archive_path = os.path.join(imgdeploydir, archive_name + '.tar.gz')

    try:
        cmd = ['tar', '-czf', archive_path, '-C', titan_dir, '.']
        subprocess.run(cmd, check=True, capture_output=True, text=True)
        bb.note(f"[Titan] Archive created: {archive_name}.tar.gz")

        # Create symlink without timestamp
        link_name = f"{image_basename}-{machine}-titan.tar.gz"
        link_path = os.path.join(imgdeploydir, link_name)
        if os.path.exists(link_path) or os.path.islink(link_path):
            os.remove(link_path)
        os.symlink(archive_name + '.tar.gz', link_path)
    except subprocess.CalledProcessError as e:
        bb.error(f"[Titan] tar failed: {e.stderr}")

    # Cleanup staging
    bb.utils.remove(titan_dir, recurse=True)
    bb.note("[Titan] Packaging complete")
}
