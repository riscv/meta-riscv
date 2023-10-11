# meta-riscv
RISC-V Architecture Layer for OpenEmbedded/Yocto

[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/riscv/meta-riscv/blob/kraj/master/COPYRIGHT)
[![Build Status](https://travis-ci.org/riscv/meta-riscv.svg?branch=master)](https://travis-ci.org/riscv/meta-riscv)

## Description

This is the general hardware-specific BSP overlay for the RISC-V based devices.

More information can be found at: <https://riscv.org/> (Official Site)

The core BSP part of meta-riscv should work with different
OpenEmbedded/Yocto distributions and layer stacks, such as:

* Distro-less (only with OE-Core).
* Yoe Distro.
* Yocto/Poky.

## Dependencies

This layer depends on:

* URI: https://github.com/openembedded/openembedded-core
  * branch: master
  * revision: HEAD
* URI: https://github.com/openembedded/bitbake
  * branch: master
  * revision: HEAD

## Quick Start

**Note: You only need this if you do not have an existing Yocto Project build environment.**

Make sure to [install the `repo` command by Google](https://source.android.com/setup/downloading#installing-repo) first. 

### Create workspace
```text
mkdir riscv-yocto && cd riscv-yocto
repo init -u https://github.com/riscv/meta-riscv  -b master -m tools/manifests/riscv-yocto.xml
repo sync
repo start work --all
```
### Update existing workspace

In order to bring all the layers up to date with upstream

```text
cd riscv-yocto
repo sync
repo rebase
```

### Setup Build Environment
```text
. ./meta-riscv/setup.sh
```

### Kas Support

Kas build is supported, you can run the following commands:

```text
git clone https://github.com/riscv/meta-riscv.git -b kirkstone
cd meta-riscv
```

* For basic `qemuriscv64` build run:

```text
kas build kas/base-riscv.yml
```

**base-riscv.yml** will build `core-image-minimal`, you can then boot it with:

```text
runqemu core-image-minimal nographic
```

**NOTE** `nographic` is needed for this image, because it has no graphical support for graphical Qemu run.

* For `nezha` build:

```text
kas build kas/nezha.yml
```

* For `beaglev` build:

```text
kas build kas/beaglev.yml
```

* For more machines check `kas` folder.


## Custom Project

If you have your own layer that depends on this layer, you can create a kas `yml` file in your layer with the following content (`nezha` build as an example):

```yml
head:
  version: 8
  includes:
    - repo: meta-riscv
      file: kas/nezha.yml

repos:
  meta-riscv:
    url: https://github.com/riscv/meta-riscv.git
    path: layers
    refspec: kirkstone

target: custom-image # Or nezha default image: riscv-nezha-image
```

For more details on `nezha`, `beaglev` and other boards steps check `doc` folder.

## Available Machines

The different machines you can build for are:

* freedom-u540: The SiFive HiFive Unleashed board
* beaglev-starlight-jh7100: BeagleV - Based on Starlight JH7100 SOC
* mangopi-mq-pro: MangoPi MQ Pro - Based on Allwinner D1 SOC

Note that this layer also provides improvements and features for the
upstream qemuriscv32 and qemuriscv64 machines.

Additional beagleV notes on bringup are [here](https://github.com/riscv/meta-riscv/blob/master/docs/BeagleV.md)
## Build Images

A console-only image for the 64-bit QEMU machine
```text
MACHINE=qemuriscv64 bitbake core-image-full-cmdline
MACHINE=beaglev-starlight-jh7100 bitbake core-image-full-cmdline
```

To build an image to run on the HiFive Unleashed using Wayland run the following

```text
MACHINE=freedom-u540 bitbake core-image-weston
```

To build an image to run on the BeagleV using Wayland run the following
```text
MACHINE=beaglev-starlight-jh7100 bitbake core-image-weston
```

To build an image to run on the MangoPi MQ Pro (console only has been tested so far) run the following:
```text
MACHINE=mangopi-mq-pro bitbake core-image-base
```

To build a full GUI equipped image running Plasma Mobile see the in-tree documentation [here](https://github.com/riscv/meta-riscv/blob/master/docs/Plasma-Mobile-on-Unleashed.md).

## Running in QEMU

Run the 64-bit machine in QEMU using the following command:

```text
MACHINE=qemuriscv64 runqemu nographic
```

Run the 32-bit machine in QEMU using the following command:

```text
MACHINE=qemuriscv32 runqemu nographic
```

## Running on hardware

### Setting up a TFTP server

If you would like to boot the images from a TFTP server (optional) you should set your TFTP server address in your local.conf with the following line. Change ```127.0.0.1``` to the IP address of your TFTP server and copy the uImage to the server.

```text
TFTP_SERVER_IP = "127.0.0.1"
```

### Running with the Microsemi Expansion board

To use the Microsemi expansion board with your HiFive Unleased add the following line to your local.conf. This tells the Unleashed to use a device tree with the PCIe device described:

```text
RISCV_SBI_FDT:freedom-u540 = "hifive-unleashed-a00-microsemi.dtb"
```

### Sparse Image Creation

The output of the build can also be written to an SD card using bmaptool, the steps to do this are below:

```text
$ MACHINE=freedom-u540 wic create freedom-u540-opensbi -e core-image-minimal
$ bmaptool create ./freedom-u540-opensbi-201812181337-mmcblk.direct > image.bmap
$ sudo bmaptool copy --bmap image.bmap ./freedom-u540-opensbi-201812181337-mmcblk.direct /dev/sdX
```

### dding wic.gz

The output of a ```freedom-u540```, ```beaglev-starlight-jh7100``` or ```mangopi-mq-pro```  build will be a ```<image>.wic.gz``` file. You can write this file to an sd card using:

```text
$ zcat <image>-<machine>.wic.gz | sudo dd of=/dev/sdX bs=4M iflag=fullblock oflag=direct conv=fsync status=progress
```

### Using bmaptoop to write the image

Instead of dding wic.gz image ```bmaptool``` (available in most Linux distributions and/or pip)  can be used for more reliable and faster flashing. You can write this file to an sd card using:
```text
$ sudo bmaptool copy <image>-<machine>.wic.gz /dev/sdX
```

## Maintainer(s)

* Khem Raj `<raj dot khem at gmail.com>`
