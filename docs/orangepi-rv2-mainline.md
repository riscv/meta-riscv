Orange Pi RV2
=============

Orange Pi RV2 is a low-cost RISC-V 64-bit platform based on the SpacemiT K1 SoC.

How to Build
============

Clone and enable these repositories and enable the below layers:

* bitbake
* openembedded-core
  * meta
* meta-riscv

See [the Yocto Project](https://docs.yoctoproject.org/brief-yoctoprojectqs/index.html) manual for details.

Set these variables in a configuration file:

* `MACHINE = "orangepi-rv2-mainline"`
* `DISTRO = "poky-altcfg"`
* `EXTRA_IMAGE_FEATURES = "allow-empty-password empty-root-password allow-root-login post-install-logging"`

Build your image:

```
$ bitbake core-image-full-cmdline
```

How to Build - Using Kas
========================

```
$ kas build /path/to/meta-riscv/kas/orangepi-rv2-mainline.yml
```

The `kas` tool can be installed as a package on your distribution or
be obtained from the [kas repository](https://github.com/siemens/kas/).

How the Image Boots
===================

The board as supported here boots through the below stages:

```
FSBL.bin -> Mainline U-Boot -> Mainline Linux
            -> Mainline OpenSBI
```

`FSBL.bin` is built from SpacemiT's U-Boot tree, see `recipes-bsp/u-boot/u-boot-spl-k1.bb`

It then loads `boot-bundle.itb` to RAM (instead of just `u-boot.itb`), containing:

* `u-boot-nodtb.bin`: mainline U-Boot
* `fw_dynamic.bin`: mainline OpenSBI
* `u-boot.dtb`: U-Boot device tree
* `Image`: mainline Linux kernel
* `k1-orangepi-rv2.dtb`: device tree for Linux

This way, when mainline U-Boot starts, the kernel and its device tree are
already loaded in RAM.

Flashing the Image
==================

Flash `core-image-full-cmdline-orangepi-rv2-mainline.rootfs.wic.gz` onto a uSD card (assuming it's accessed through `/dev/sdx`):

```
$ sudo bmaptool copy build/tmp/deploy/images/orangepi-rv2-mainline/core-image-full-cmdline-orangepi-rv2-mainline.rootfs.wic.gz /dev/sdx
```

Then, you also need to flash the `bootinfo_sd.bin` file at the very beginning of the SD card (not part of the WIC image yet):

```
$ sudo dd if=build/tmp/deploy/images/orangepi-rv2-mainline/bootinfo_sd.bin of=/dev/sdx
```

Set up NFS
==========

As there is no support for MMC or USB in the mainline Linux kernel yet, you will have to set up an NFS
server to boot your board through a directory shared through NFS.

On Debian-based systems, you can install the server with:

```
$ sudo apt install nfs-kernel-server
```

Then extract the Yocto-generated image:

```
sudo mkdir -p /srv/nfs/yocto-k1
sudo tar xf build/tmp/deploy/images/orangepi-rv2-mainline/core-image-full-cmdline-orangepi-rv2-mainline.rootfs.tar.zst -C /srv/nfs/yocto-k1
```

Add this directory to `/etc/exports`:
```
/srv/nfs/yocto-k1 172.24.0.2(rw,no_root_squash,no_subtree_check)
```

And then run:

```
$ sudo exportfs -r
```

Note: the NFS directory can be modified through `recipes-bsp/u-boot/files/k1/bootcommand.cfg`.

Then, connect the "LANX2" Ethernet port of your board to your NFS server through for example
a USB/Ethernet dongle. Then assign the `172.24.0.1` IP address to your server:

```
$ nmcli con add type ethernet ifname enx<mac> ip4 172.24.0.1/24
```

Boot the Board
==============

Connect a USB to serial dongle to your board and to your PC, and
start your favorite terminal emulator:

```
$ sudo apt install tio
$ tio /dev/ttyUSB0
```

Power the board and you will see it boot to a Linux command line shell.

Resources
=========

* [Orange Pi RV2 product page](http://www.orangepi.org/html/hardWare/computerAndMicrocontrollers/details/Orange-Pi-RV2.html)
