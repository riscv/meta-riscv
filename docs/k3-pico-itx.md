K3 Pico-ITX
===========

The K3 Pico-ITX is a 64-bit, RVA23-compliant RISC-V platform based on the
SpacemiT K3 SoC.

How to Build
============

Clone and enable these repositories and enable the below layers:

* bitbake
* openembedded-core
  * meta
* meta-riscv

See [the Yocto Project](https://docs.yoctoproject.org/brief-yoctoprojectqs/index.html) manual for details.

Set these variables in a configuration file:

* `MACHINE = "k3-pico-itx"`
* `DISTRO = "poky-altcfg"`
* `EXTRA_IMAGE_FEATURES = "allow-empty-password empty-root-password allow-root-login post-install-logging"`

Build your image:

```
$ bitbake core-image-minimal
```

Flashing the Image
==================

The K3 Pico-ITX does not have a microSD card slot, but it does support NVMe
storage. You can flash your image to an NVMe drive (assuming that it is
connected to a machine using an USB-to-NVMe adapter, or a spare NVMe slot) using `bmaptool` 

```
$ sudo bmaptool copy build/tmp/deploy/images/k3-pico-itx/core-image-minimal-k3-pico-itx.rootfs.wic.gz /dev/sdx
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

* [K3 Pico-ITX User
  Guide](https://www.spacemit.com/community/document/info?nodepath=hardware/eco/k3_pico/pico_user_guide.md)
* [Sipeed K3](https://sipeed.com/k3)
