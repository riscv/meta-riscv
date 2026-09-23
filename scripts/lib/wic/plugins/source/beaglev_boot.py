import logging
import os
import shutil

from wic import WicError
from wic.pluginbase import SourcePlugin
from wic.misc import exec_native_cmd

logger = logging.getLogger('wic')


class BeagleVBootPlugin(SourcePlugin):
    name = 'beaglev_boot'

    @classmethod
    def do_install_disk(cls, disk, disk_name, creator, workdir,
                        oe_builddir, bootimg_dir, kernel_dir,
                        native_sysroot):

        image = disk.path
        uboot = os.path.join(kernel_dir, "u-boot-with-spl.bin")

        logger.debug(
            "=== BEAGLEV BOOT PLUGIN: do_install_disk() WAS CALLED ==="
        )
        logger.debug(
            "=== BEAGLEV BOOT PLUGIN: disk image = %s ===",
            image
        )
        logger.debug(
            "=== BEAGLEV BOOT PLUGIN: U-Boot image = %s ===",
            uboot
        )

        if not os.path.isfile(uboot):
            raise WicError(
                "BeagleV boot plugin: U-Boot image not found: %s" % uboot
            )

        if disk.sector_size != 512:
            raise WicError(
                "BeagleV boot plugin requires a 512-byte sector size"
            )

        uboot_size = os.path.getsize(uboot)

        if uboot_size <= 604:
            raise WicError(
                "BeagleV boot plugin: U-Boot image is too small"
            )

        if uboot_size > 4 * 1024 * 1024:
            raise WicError(
                "BeagleV boot plugin: U-Boot image exceeds the 4 MiB boot area"
            )

        logger.debug(
            "=== Moving GPT entry array to LBA 8192 ==="
        )

        exec_native_cmd(
            "sgdisk -j 8192 %s" % image,
            native_sysroot
        )

        with open(image, "rb") as img:
            img.seek(440)
            protected_metadata = img.read(164)

        logger.debug(
            "=== Clearing disk area from 1 MiB to 4 MiB ==="
        )

        with open(image, "r+b") as img:
            img.seek(1024 * 1024)
            img.write(b"\x00" * (3 * 1024 * 1024))

        logger.debug(
            "=== Writing U-Boot bytes 0..439 ==="
        )

        with open(uboot, "rb") as src, open(image, "r+b") as dst:
            first_part = src.read(440)

            if len(first_part) != 440:
                raise WicError(
                    "BeagleV boot plugin: failed to read first 440 U-Boot bytes"
                )

            dst.seek(0)
            dst.write(first_part)

            logger.debug(
                "=== Writing U-Boot bytes 604..end at disk byte 604 ==="
            )

            src.seek(604)
            dst.seek(604)
            shutil.copyfileobj(src, dst)

        with open(image, "rb") as img:
            img.seek(440)
            metadata_after = img.read(164)

        if metadata_after != protected_metadata:
            raise WicError(
                "BeagleV boot plugin: protected PMBR/GPT metadata was modified"
            )

        logger.debug(
            "=== BEAGLEV BOOT PLUGIN: disk layout completed successfully ==="
        )
