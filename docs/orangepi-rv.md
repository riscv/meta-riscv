Xunlong Orange Pi RV
====================

The [Orange Pi RV](http://www.orangepi.org/html/hardWare/computerAndMicrocontrollers/details/Orange-Pi-RV.html)
is a single-board computer based on the StarFive JH7110 quad-core RISC-V SoC.

The `orangepi-rv` machine uses mainline Linux with the in-tree
`jh7110-orangepi-rv.dts`, mainline U-Boot and mainline OpenSBI. U-Boot uses the
same binaries as the VisionFive 2 (`starfive_visionfive2_defconfig`), see
[U-Boot: Xunlong OrangePi RV](https://docs.u-boot.org/en/latest/board/starfive/orangepi_rv.html).

Boot flow
=========

    boot ROM -> U-Boot SPL -> OpenSBI -> U-Boot  (QSPI flash)
             -> GRUB -> Linux                     (SD card)

U-Boot standard boot starts GRUB (`EFI/BOOT/bootriscv64.efi`) from the EFI
System Partition on the SD card and passes it the device tree
`/dtb/${fdtfile}` from the same partition, with U-Boot's fixups (such as the
Ethernet MAC address) applied. The SD card image contains no SPL or U-Boot:
they are loaded from the on-board QSPI flash.

Building
========

```
$ kas build kas/orangepi-rv.yml
```

Writing the SD card image
=========================

```
$ sudo bmaptool copy build/tmp/deploy/images/orangepi-rv/core-image-full-cmdline-orangepi-rv.rootfs.wic.gz /dev/sdX
```

The serial console is UART0 (`ttyS0`), 115200 8N1.

Installing U-Boot to QSPI flash
===============================

The board ships with a vendor U-Boot in its QSPI flash. This image is booted by
the U-Boot built by this layer: install it once. The flash layout is
described in
[U-Boot: JH7110 common description](https://docs.u-boot.org/en/latest/board/starfive/jh7110_common.html):

| Offset   | File (from `tmp/deploy/images/orangepi-rv/`) | MTD partition |
|----------|----------------------------------------------|---------------|
| 0x0      | `u-boot-spl.bin.normal.out`                  | `/dev/mtd0`   |
| 0xf0000  | U-Boot environment                           | `/dev/mtd1`   |
| 0x100000 | `u-boot.itb`                                 | `/dev/mtd2`   |

First installation
------------------

Start the U-Boot built by this layer over the serial console (see
[Recovery](#recovery)). It still reads the vendor U-Boot environment from the
QSPI flash, so stop autoboot (press a key at `Hit any key to stop autoboot`)
and boot the SD card with the default environment, without saving it:

```
StarFive # env default -a
StarFive # env set ethaddr <Ethernet MAC0 address>
StarFive # env set eth1addr <Ethernet MAC1 address>
StarFive # env set fdtfile starfive/jh7110-orangepi-rv.dtb
StarFive # run bootcmd
```

Take the MAC addresses from the `Ethernet MAC0 address` and
`Ethernet MAC1 address` lines that U-Boot printed at start-up.

Then write the flash from Linux on the board with `flashcp` from `mtd-utils`,
which `core-image-full-cmdline` does not contain (add
`IMAGE_INSTALL:append = " mtd-utils"` to `local.conf` for this step), with
both files copied to the board:

```
# flashcp -v u-boot-spl.bin.normal.out /dev/mtd0
# flashcp -v u-boot.itb /dev/mtd2
# flash_erase /dev/mtd1 0 0
```

The last command erases the vendor U-Boot environment, so U-Boot starts from
its defaults. Reboot, stop autoboot again and set the device tree for Linux
once:

```
StarFive # env set fdtfile starfive/jh7110-orangepi-rv.dtb
StarFive # env save
```

This is needed because the factory EEPROM of these boards identifies them as a
VisionFive 2 v1.3b, so U-Boot would otherwise select the VisionFive 2 device
tree.

Updating
--------

Copy both files to the EFI System Partition (partition 1) of the SD card, then
in the U-Boot console:

```
StarFive # sf probe
StarFive # load mmc 1:1 ${kernel_addr_r} u-boot-spl.bin.normal.out
StarFive # sf update ${kernel_addr_r} 0 ${filesize}
StarFive # load mmc 1:1 ${kernel_addr_r} u-boot.itb
StarFive # sf update ${kernel_addr_r} 0x100000 ${filesize}
StarFive # reset
```

The environment, including `fdtfile`, is kept.

Recovery
========

If the QSPI flash does not contain a working U-Boot, the boot ROM can load SPL
and U-Boot over the serial console ("Recovery U-Boot console" in the U-Boot
JH7110 documentation). On the Orange Pi RV the "UART BOOT" button selects this
mode. Keep it pressed while powering on until SPL prints
`Trying to boot from UART`: SPL checks the boot mode again when it starts.
