import logging
import os
import shutil

from wic import WicError
from wic.pluginbase import SourcePlugin
from wic.misc import exec_native_cmd

logger = logging.getLogger('wic')


class Thead1520BootPlugin(SourcePlugin):
    name = 'th1520_boot'

    @classmethod
    def do_install_disk(cls, disk, disk_name, creator, workdir,
                        oe_builddir, bootimg_dir, kernel_dir,
                        native_sysroot):

        image = disk.path
        uboot = os.path.join(kernel_dir, "u-boot-with-spl.bin")

        logger.debug(
            "=== TH1520 BOOT PLUGIN: do_install_disk() WAS CALLED ==="
        )
        logger.debug(
            "=== TH1520 BOOT PLUGIN: disk image = %s ===",
            image
        )
        logger.debug(
            "=== TH1520 BOOT PLUGIN: U-Boot image = %s ===",
            uboot
        )

        if not os.path.isfile(uboot):
            raise WicError(
                "TH1520 boot plugin: U-Boot image not found: %s" % uboot
            )

        if disk.sector_size != 512:
            raise WicError(
                "TH1520 boot plugin requires a 512-byte sector size"
            )

        uboot_size = os.path.getsize(uboot)

        if uboot_size <= 604:
            raise WicError(
                "TH1520 boot plugin: U-Boot image is too small"
            )

        if uboot_size > 4 * 1024 * 1024:
            raise WicError(
                "TH1520 boot plugin: U-Boot image exceeds the 4 MiB boot area"
            )

        with open(uboot, "rb") as src:
            src.seek(440)
            if any(src.read(164)):
                raise WicError(
                    "TH1520 boot plugin: U-Boot has data in bytes 440..603, "
                    "which the partition table overwrites"
                )

        logger.debug(
            "=== Moving GPT entry array to LBA 8192 ==="
        )

        exec_native_cmd(
            "sgdisk -j 8192 %s" % image,
            native_sysroot
        )

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
                    "TH1520 boot plugin: failed to read first 440 U-Boot bytes"
                )

            dst.seek(0)
            dst.write(first_part)

            logger.debug(
                "=== Writing U-Boot bytes 604..end at disk byte 604 ==="
            )

            src.seek(604)
            dst.seek(604)
            shutil.copyfileobj(src, dst)

        logger.debug(
            "=== TH1520 BOOT PLUGIN: disk layout completed successfully ==="
        )
