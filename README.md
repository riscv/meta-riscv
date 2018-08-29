
# meta-riscv
RISC-V Architecture Layer for OpenEmbedded/Yocto

[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/riscv/meta-riscv/blob/kraj/master/COPYRIGHT)
[![Build Status](https://travis-ci.org/riscv/meta-riscv.svg?branch=master)](https://travis-ci.org/riscv/meta-riscv)

## Description

This is the general hardware specific BSP overlay for the RISC-V based devices.

More information can be found at: <https://riscv.org/> (Official Site)

The core BSP part of meta-riscv should work with different
OpenEmbedded/Yocto distributions and layer stacks, such as:

* Distro-less (only with OE-Core).
* Angstrom.
* Yocto/Poky (main focus of testing).

## Dependencies

This layer depends on:

* URI: git://github.com/openembedded/openembedded-core
  * branch: master
  * revision: HEAD
* URI: git://github.com/openembedded/bitbake
  * branch: master
  * revision: HEAD

## Quick Start

Note: You only need this if you do not have an existing Yocto Project build environment.

Make sure to [install the `repo` command by Google](https://source.android.com/setup/downloading#installing-repo) first. 

## Create workspace
```text
mkdir riscv-yocto && cd riscv-yocto
repo init -u git://github.com/riscv/meta-riscv  -b master -m tools/manifests/riscv-yocto.xml
repo sync
repo start work --all
```
## Update existing workspace

In order to bring all layers uptodate with upstream

```text
cd riscv-yocto
repo sync
repo rebase
```

## Setup Build Environment
```text
. ./meta-riscv/setup.sh
```
## Build Image

A console-only image
```text
bitbake core-image-full-cmdline
```

Basic image without X support suitable for Linux Standard Base (LSB) implementations.
It includes the full meta-toolchain, plus development headers and libraries to form
a standalone (on device) SDK

```
bitbake core-image-lsb-sdk
```

## Run in QEMU
```text
runqemu nographic
```

## Running wic.gz image on hardware

The output of the build will be a ```<image>.wic.gz``` file. You can write this file to an sd card using:

```text
$ zcat <image>-<machine>.wic.gz | sudo dd of=/dev/sdX bs=4M iflag=fullblock oflag=direct conv=fsync status=progress
```

## Maintainer(s)

* Khem Raj `<raj dot khem at gmail.com>`

