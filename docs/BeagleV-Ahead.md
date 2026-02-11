BeagleV-Ahead
=======

BeagleV-Ahead is a RISC-V platform with an Alibaba T-Head TH1520 SoC (2GHz quad-core 64-bit Xuantee C910).
The board provides both a eMMC based startup and booting from U-Boot (the current configuration only allows startup via eMMC and flashing via fastboot).

How to Build
============

Create an image with `MACHINE=beaglev-ahead` setting in `conf/local.conf` and obtain the following build artifacts (the following assumes a core-image-minimal):

- u-boot-with-spl.bin : the SPL boot loader
- boot.ext4 : a generated ext4 partition containing `fw_dynamic.bin`, `Image`, and `th1520-beaglev-ahead.dtb`
- core-image-minimal-beaglev-ahead.rootfs.ext4 : the root file system

Flashing Linux
==============
Currently, there is only working support for flashing to eMMC via fastboot.

0. Install `fastboot` via distro packages.
1. Enter flash mode (see Quickstart guide for details): press USB-button and while pressing, click `RESET`-button; then release `USB`-button only after device started
2. Enter deploy folder `tmp/deploy/images/beaglev-ahead/` and flash artifacts with sudo rights:

```shell
fastboot flash ram ./u-boot-with-spl.bin
fastboot reboot
sleep 10
fastboot oem format
fastboot flash uboot ./u-boot-with-spl.bin
fastboot flash boot ./boot.ext4.sparse
fastboot flash root ./core-image-minimal-beaglev-ahead.rootfs.ext4.sparse
fastboot reboot
```

> **_NOTE:_**  You can also use the helper script:
> ```shell
> ./flash.sh
> ```

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
