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

For details regarding basic usage for some platforms supported by this layer,
see the [Quick Start Guide](docs/QUICK_START.md).

## Dependencies

This layer depends on:

* URI: https://github.com/openembedded/openembedded-core
  * branch: master
  * revision: HEAD
* URI: https://github.com/openembedded/bitbake
  * branch: master
  * revision: HEAD

## Available Machines

| MACHINE                  | Platform                                                                                                     | Notes                                                                            |
| ------------------------ | ------------------------------------------------------------------------------------------------------------ | -------------------------------------------------------------------------------- |
| bananapi-f3              | [BPI-F3](https://docs.banana-pi.org/en/BPI-F3/BananaPi_BPI-F3)                                               | Mainline Kernel and U-Boot (uses vendor FSBL)                                    |
| beaglev-ahead            | [BeagleV-Ahead](https://www.beagleboard.org/boards/beaglev-ahead)                                            |                                                                                  |
| beaglev-starlight-jh7100 | [BeagleV](https://old.beagleboard.org/static/beagleV/beagleV.html)                                           | No longer produced                                                               |
| eswin-ebc77              | ESWIN EBC77                                                                                                  | Vendor kernel. Use `eswin-ebc77-mainline` for upstream version                   |
| eswin-ebc77-mainline     | ESWIN EBC77                                                                                                  |                                                                                  |
| freedom-u540             | [HiFive Unleashed](https://www.sifive.com/boards/hifive-unleashed)                                           |                                                                                  |
| mangopi-mq-pro           | [MangoPi MQ Pro](https://mangopi.org/mangopi_mqpro)                                                          |                                                                                  |
| milkv-duo                | [Milk-V Duo](https://milkv.io/duo)                                                                           |                                                                                  |
| milkv-megrez             | [Milk-V Megrez](https://milkv.io/megrez)                                                                     | Build broken due to downstream kernel commit since being branchless              |
| nezha-allwinner-d1       | [Nezha D1-H](https://docs.aw-ol.com/d1/en/d1_dev/)                                                           |                                                                                  |
| orangepi-r2s             | [OrangePi R2S](http://www.orangepi.org/html/hardWare/computerAndMicrocontrollers/details/Orange-Pi-R2S.html) | Vendor Kernel and U-Boot                                                         |
| orangepi-rv2             | [OrangePi RV2](http://www.orangepi.org/html/hardWare/computerAndMicrocontrollers/details/Orange-Pi-RV2.html) | Vendor Kernel and U-Boot                                                         |
| orangepi-rv2-mainline    | [OrangePi RV2](http://www.orangepi.org/html/hardWare/computerAndMicrocontrollers/details/Orange-Pi-RV2.html) | Mainline Kernel and U-Boot (uses vendor FSBL)                                    |
| star64                   | [PINE64 STAR64](https://pine64.org/devices/star64/)                                                          | 5.15 Kernel [fork](https://github.com/fishwaldo/Star64_linux)                    |
| visionfive               | [StarFive VisionFive](https://www.starfivetech.com/en/site/boards)                                           |                                                                                  |
| visionfive2              | [StarFive VisionFive 2](https://www.starfivetech.com/en/site/boards)                                         |                                                                                  |

Note that this layer also provides improvements and features for the
upstream qemuriscv32 and qemuriscv64 machines.

## Contributing

Submit patches via GitHub pull requests, Use GitHub issues to report problems or to send comments.

## Maintainer(s)

* Khem Raj `<raj.khem@gmail.com>`
