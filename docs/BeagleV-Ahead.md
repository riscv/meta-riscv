BeagleV-Ahead
=======

BeagleV-Ahead is a RISC-V platform with an Alibaba T-Head TH1520 SoC (2GHz quad-core 64-bit Xuantee C910).

The board supports booting Linux from both eMMC and SD card. The boot ROM starts U-Boot from the eMMC boot area, not from the SD card, so U-Boot has to be installed to eMMC once (see "Installing U-Boot to eMMC" below). U-Boot looks for a bootable SD card first and falls back to eMMC, so the same image boots from the medium it is written to.

How to Build
============

Clone and enable these repositories and enable the below layers:

* bitbake
* openembedded-core
  * meta
* meta-riscv

See [the Yocto Project](https://docs.yoctoproject.org/brief-yoctoprojectqs/index.html) manual for details.

Set these variables in a configuration file:

* `MACHINE = "beaglev-ahead"`
* `DISTRO = "poky-altcfg"`
* `EXTRA_IMAGE_FEATURES = "allow-empty-password empty-root-password allow-root-login post-install-logging"`
* `IMAGE_INSTALL:append = " bmaptool"` (used to flash eMMC from the board)

Build your image:

```
$ bitbake core-image-minimal
```

How to Build - Using Kas
========================

```
$ kas build /path/to/meta-riscv/kas/beaglev-ahead.yml
```

The `kas` tool can be installed as a package on your distribution or
be obtained from the [kas repository](https://github.com/siemens/kas/).

Build artifacts
===============

After building, you will obtain the following artifacts: 

- u-boot-with-spl.bin : the SPL boot loader
- core-image-minimal-beaglev-ahead.rootfs.ext4 : the root file system
- core-image-minimal-beaglev-ahead.rootfs.wic.gz : the complete SD card image
- core-image-minimal-beaglev-ahead.rootfs.wic.bmap : bmap description for the wic image

Flashing Linux
==============

Flashing to SD card
-------------------

Enter the deploy folder:

```shell
cd tmp/deploy/images/beaglev-ahead/
```

Flash the generated WIC image to the SD card using `bmaptool`, assuming the SD card is `/dev/sdX`:

```shell
sudo bmaptool copy core-image-minimal-beaglev-ahead.rootfs.wic.gz /dev/sdX
```

Replace `/dev/sdX` with the block device corresponding to the SD card.

Insert the SD card into the BeagleV-Ahead and power on the board. This needs U-Boot in eMMC, see the next section if it is not installed yet.

Installing U-Boot to eMMC
-------------------------

This is needed once and again when you want to update U-Boot.

Mainline U-Boot does not support USB on the TH1520 yet, so it cannot flash eMMC
over fastboot. Instead, the boot ROM loads U-Boot to RAM, this U-Boot boots
Linux from the SD card and Linux writes U-Boot to the eMMC boot area.

Insert the SD card flashed as described above. Then enter the boot ROM USB mode
(see the Quickstart guide for details): press the USB button and while
pressing, click the RESET button, then release the USB button after the board
has started.

From the deploy folder, load U-Boot to RAM and start it:

```shell
fastboot flash ram u-boot-with-spl.bin
fastboot reboot
```

U-Boot starts from RAM and boots Linux from the SD card. On the serial console
(see "Check Functionality" below), write the copy of U-Boot from the SD card
boot partition to the eMMC boot area:

```shell
mount /dev/mmcblk1p2 /mnt
echo 0 > /sys/block/mmcblk0boot0/force_ro
dd if=/mnt/u-boot-with-spl.bin of=/dev/mmcblk0boot0
sync
umount /mnt
```

After a reboot, U-Boot starts from eMMC.

Flashing to eMMC
----------------

The easiest way to flash eMMC is by first flashing an SD card and booting it, with U-Boot installed to eMMC (see above).

Then, copy the `core-image-minimal-beaglev-ahead.rootfs.wic.gz` and
`core-image-minimal-beaglev-ahead.rootfs.wic.bmap` files from the deploy folder
to an ext4 formatted USB flash drive.

Power your board with a 5V power supply (using the 5.5 mm barrel connector),
and use the USB type-B to USB-A F cable provided with your board to connect
the USB flash drive to the board.

Once booted, mount the USB drive on the command line shell
(see the "Check Functionality" section below to access the serial console):

```shell
mkdir /mnt/sda1
mount /dev/sda1 /mnt/sda1
```

You can then flash eMMC:

```shell
cd /mnt/sda1
bmaptool copy core-image-minimal-beaglev-ahead.rootfs.wic.gz /dev/mmcblk0
```

You can then remove the SD card and reboot.

Check Functionality
===================
Follow the Quickstart-Guide and connect an RS232-to-USB device, then connect to the UART console and login

```shell
picocom -b 115200 /dev/ttyUSB0
```

Resources
=========

* [BeagleV_Ahead landing page](https://www.beagleboard.org/boards/beaglev-ahead)
* [BeagleV-Ahead Getting Started](https://docs.beagle.cc/latest/boards/beaglev/ahead/02-quick-start.html)
