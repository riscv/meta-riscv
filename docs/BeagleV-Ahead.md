BeagleV-Ahead
=======

BeagleV-Ahead is a RISC-V platform with an Alibaba T-Head TH1520 SoC (2GHz quad-core 64-bit Xuantee C910).

The board supports booting from both eMMC and SD card. eMMC can be flashed using fastboot, while the generated WIC image can be flashed directly to an SD card. U-Boot looks for a bootable SD card first and falls back to eMMC, so the same image boots from the medium it is written to.

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
- boot.ext4 : a generated ext4 partition containing `fw_dynamic.bin`, `Image`, and `th1520-beaglev-ahead.dtb`
- core-image-minimal-beaglev-ahead.rootfs.ext4 : the root file system
- core-image-minimal-beaglev-ahead.rootfs.wic.gz : the complete SD card image

Flashing Linux
==============

Flashing to eMMC
----------------

0. Install `fastboot` via distro packages.
1. Enter flash mode (see Quickstart guide for details): press USB-button and while pressing, click `RESET`-button; then release `USB`-button only after device started
2. Enter deploy folder `tmp/deploy/images/beaglev-ahead/` and flash artifacts with sudo rights:

```shell
fastboot flash ram ./u-boot-with-spl.bin
fastboot reboot
sleep 10
fastboot oem format
fastboot flash uboot ./u-boot-with-spl.bin
fastboot flash boot ./boot.ext4
fastboot flash root ./core-image-minimal-beaglev-ahead.rootfs.ext4
fastboot reboot
```

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

Insert the SD card into the BeagleV-Ahead and power on the board.

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
