# meta-riscv
RISC-V Architecture Layer for OpenEmbedded/Yocto


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

1. source openembedded-core/oe-init-build-env riscv-build
2. Add this layer to bblayers.conf and the dependencies above
3. Set MACHINE in local.conf to one of the supported boards
4. bitbake core-image-minimal
6. Boot in qemu.

## Maintainers

* Khem Raj `<raj dot khem at gmail.com>`
