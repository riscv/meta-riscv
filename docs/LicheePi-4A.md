LicheePi 4A
===========

LicheePi 4A is a RISC-V platform with an Alibaba T-Head TH1520 SoC (2GHz quad-core 64-bit Xuantee C910).

The board supports booting from an SD card.

How to Build
============

Clone and enable these repositories and enable the below layers:

* bitbake
* openembedded-core
  * meta
* meta-riscv

See [the Yocto Project](https://docs.yoctoproject.org/brief-yoctoprojectqs/index.html) manual for details.

Set these variables in a configuration file:

* `MACHINE = "licheepi-4a"`
* `DISTRO = "poky-altcfg"`
* `EXTRA_IMAGE_FEATURES = "allow-empty-password empty-root-password allow-root-login post-install-logging"`

Build your image:

```
$ bitbake core-image-minimal
```

How to Build - Using Kas
========================

```
$ kas build /path/to/meta-riscv/kas/licheepi-4a.yml
```

The `kas` tool can be installed as a package on your distribution or
be obtained from the [kas repository](https://github.com/siemens/kas/).

Build artifacts
===============

After building, you will obtain the following artifacts: 

- u-boot-with-spl.bin : U-Boot SPL, OpenSBI and U-Boot bootloader
- core-image-minimal-licheepi-4a.rootfs.wic.gz : the complete SD card image
- core-image-minimal-licheepi-4a.rootfs.wic.bmap : bmap description for the wic image

Flashing Linux to SD card
=========================

Enter the deploy folder:

```shell
cd tmp/deploy/images/licheepi-4a/
```

Flash the generated WIC image to the SD card using `bmaptool`, assuming the SD card is `/dev/sdX`:

```shell
sudo bmaptool copy core-image-minimal-licheepi-4a.rootfs.wic.gz /dev/sdX
```

Replace `/dev/sdX` with the block device corresponding to the SD card.

Boot the Board
==============

Configure the DIP switches under the SoM for SD card boot:
- `SW1`: `ON`
- `SW2`: `OFF`

Insert the microSD card, then connect a USB-to-serial adapter between your PC and your board,
using the `U0_RX` and `U0_TX` pins, and start your favorite terminal emulator:

```
$ sudo apt install tio
$ tio /dev/ttyUSB0
```

Power the board and you will see it boot to a Linux command line shell.

Resources
=========

* [LicheePi 4A landing page](https://wiki.sipeed.com/hardware/en/lichee/th1520/lpi4a/1_intro.html)
* [U-Boot documentation for LicheePi 4A](https://docs.u-boot.org/en/latest/board/thead/lpi4a.html)
