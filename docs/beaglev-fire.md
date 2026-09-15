BeagleV-Fire
============

BeagleV-Fire is a RISC-V 64-bit single board computer built around the
Microchip PolarFire SoC (MPFS025T), a quad core SiFive U54 cluster with an
E51 monitor hart and an FPGA fabric.

How to Build
============

Clone and enable these repositories and enable the below layers:

* bitbake
* openembedded-core
  * meta
* meta-riscv

See [the Yocto Project](https://docs.yoctoproject.org/brief-yoctoprojectqs/index.html) manual for details.

Set these variables in a configuration file:

* `MACHINE = "beaglev-fire"`
* `EXTRA_IMAGE_FEATURES = "allow-empty-password empty-root-password allow-root-login"`

Build your image:

```
$ bitbake core-image-minimal
```

How to Build - Using Kas
========================

```
$ kas build /path/to/meta-riscv/kas/beaglev-fire.yml
```

The `kas` tool can be installed as a package on your distribution or
be obtained from the [kas repository](https://github.com/siemens/kas/).

How the Image Boots
===================

The board as supported here boots through the below stages:

```
HSS (eNVM) -> Mainline OpenSBI -> Mainline U-Boot -> Mainline Linux
```

The Hart Software Services (HSS) is stored in eNVM and comes with the board,
so it is not built by this layer. PolarFire SoC normally runs the OpenSBI that
is built into HSS and starts U-Boot on top of it. Here the HSS payload tells
HSS to skip its own OpenSBI and start a mainline OpenSBI in M-mode on all four
U54 harts, and that OpenSBI carries U-Boot.

U-Boot then runs `boot.scr` from the boot partition. It loads `Image` and
`mpfs-beaglev-fire.dtb` and boots Linux. The device tree overlays that the
gateware, the design programmed into the FPGA fabric, keeps in its SPI flash
are written for the vendor device tree and are not applied. The peripherals
that only they describe, such as UART2, UART4 and the PWMs of the cape header,
are not available.

Flashing the Image
==================

Connect to the serial console first. HSS, OpenSBI, U-Boot and Linux all use
MMUART0 at 115200 8N1:

```
$ sudo apt install tio
$ tio /dev/ttyUSB0
```

The stock HSS only boots from the eMMC, so the image goes there rather than on
a uSD card. HSS can expose the eMMC to your PC as a USB mass storage device.

Hold the USER button, connect the board to your PC, wait about five seconds
until the LEDs change state, then release the button. The board is powered over
the same USB connection. The serial console shows:

```
Waiting for USB Host to connect... (CTRL-C to quit)
USB Host connected. Waiting for disconnect... (CTRL-C to quit)
```

The eMMC now shows up as a disk on your PC, and `lsusb` lists it:

```
Bus 001 Device 032: ID 1514:0001 Actel PolarFireSoc-FlashDrive
```

Write the image to it (assuming it is `/dev/sdx`):

```
$ sudo bmaptool copy build/tmp/deploy/images/beaglev-fire/core-image-minimal-beaglev-fire.rootfs.wic.gz /dev/sdx
```

When the write completes, press Ctrl-C on the HSS console to end the session.

Boot the Board
==============

Power-cycle the board. The serial console shows HSS, OpenSBI and U-Boot, and
then Linux boots to a command line shell.

Resources
=========

* [BeagleV-Fire documentation](https://docs.beagleboard.org/boards/beaglev/fire/)
* [PolarFire SoC documentation](https://github.com/polarfire-soc/polarfire-soc-documentation)
* [PolarFire SoC Hart Software Services](https://github.com/polarfire-soc/hart-software-services)
* [U-Boot PolarFire SoC boot flows](https://docs.u-boot-project.org/en/latest/board/microchip/mpfs_icicle.html)
