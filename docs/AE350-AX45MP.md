Andes AE350 AX45MP Platform
=======

AE350 is an AXI/AHB-based platform with level-one memories,
interrupt controller, debug module, AXI and AHB Bus Matrix
Controller, AXI-to-AHB Bridge and a collection of fundamental
AHB/APB bus IP components pre-integrated together as a system
design.

The `ae350-ax45mp` machine configuration helps developers build
a disk image based on mainline Linux kernel 5.18 with MMC, DMA,
Ethernet and RTC drivers support, hosted on Andestech Github.

How to build
=============

```
$ SHELL=/bin/bash kas-container build ./meta-riscv/ae350-ax45mp.yml
```

The `kas-container` script can be obtained from
[kas repository](https://github.com/siemens/kas/blob/3.0.2/kas-container).

Build results
=============

- core-image-minimal-ae350-ax45mp.wic.gz
- fitImage
- ae350_ax45mp.dtb
- boot.scr.uimg
- uEnv.txt
- u-boot-spl.bin
- u-boot.itb

Flashing SD card
==============

```
gunzip -c core-image-minimal-ae350-ax45mp.wic.gz | sudo dd of=/dev/sdX bs=4M iflag=fullblock oflag=direct conv=fsync status=progress && sync
```

(Optional.) Flashing U-boot SPL
==============

If you need to update the SPL, the first partition holds XIP mode SPL
(u-boot-spl.bin) that can be burned on flash by using the `sf` command
shown below in U-boot prompt or [SPI_Burn tool](https://github.com/andestech/Andes-Development-Kit).

```
RISC-V # fatload mmc 0:1 0x600000 u-boot-spl.bin
RISC-V # sf probe 0:0 50000000 0
RISC-V # sf erase 0x0 0x10000
RISC-V # sf write 0x600000 0x0 0x10000
```

Connect to the UART console and reset the board, it should boot
from MMC and loads fw_dynamic.bin and u-boot.bin from u-boot.itb
located at first partition.

Resources
==============

* [Buildroot AE350 README](https://github.com/buildroot/buildroot/blob/master/board/andes/ae350/readme.txt)
* [Andestech AndesCore™ AX45MP Multicore](http://www.andestech.com/en/products-solutions/andescore-processors/riscv-ax45mp/)
