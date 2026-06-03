DC-ROMA RISC-V Mainboard (FML13V01)
===================================

The [DC-ROMA RISC-V Mainboard](https://deepcomputing.io/product/dc-roma-risc-v-mainboard/)
is a Framework Laptop 13 compatible mainboard built by DeepComputing,
based on the StarFive JH7110 64-bit quad-core RISC-V SoC.

Flashing the Image
==================

Write the image onto an SD card:

```
$ sudo bmaptool copy build/tmp/deploy/images/dc-roma-fml13v01/core-image-full-cmdline-dc-roma-fml13v01.rootfs.wic.gz /dev/sdx
```

Insert the SD card into the [DC-ROMA Mainboard or Framework Laptop
13 chassis](https://github.com/DC-DeepComputing/Framework/blob/main/FML13V01/Cooler%20Master%20Mainboard%20Case%20Quick%20Start%20Guide.pdf) and power on.

See https://github.com/DC-DeepComputing/Framework/blob/main/FML13V01/Framework%20Serial%20Port%20Connection%20Guide.pdf for details on getting UART output.

Resources
=========

* [DC-ROMA RISC-V Mainboard product page](https://deepcomputing.io/product/dc-roma-risc-v-mainboard/)
* [User Guides](https://github.com/DC-DeepComputing/Framework/tree/main/FML13V01)
* [Linux kernel fork (fml13v01-linux)](https://github.com/DC-DeepComputing/fml13v01-linux)
