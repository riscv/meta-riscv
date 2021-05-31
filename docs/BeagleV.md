BeagleV
=======

BeagleV is a low-cost RISC-V 64-bit based platform. The beta revision of the
board uses the Starfive JH7100 SoC.

How to build
============

```
$ SHELL=/bin/bash kas-container build ./meta-riscv/beaglev.yml
```

The `kas-container` script can be obtained from
[kas repository](https://github.com/siemens/kas/blob/2.4/kas-container).

Build results
=============

- core-image-minimal-beaglev-starlight-jh7100.wic.gz
- core-image-minimal-beaglev-starlight-jh7100.wic.bmap
- fw_payload.bin
- fw_payload.elf
- extlinux.conf
- Image
- starfive_vic7100_beagle_v.dtb
- u-boot.bin

Flashing Linux
==============

Flash `core-image-minimal-beaglev-starlight-jh7100.wic.gz` onto uSD card:

```
$ sudo bmaptool copy --bmap core-image-minimal-beaglev-starlight-jh7100.wic.bmap core-image-minimal-beaglev-starlight-jh7100.wic.gz /dev/sdx
```

Flashing U-Boot
===============

Generally you shouldn't need to change u-boot or OpenSBI, but if you do need
to you can update it with the Xmodem protocol. Below are steps to do this with
Linux and screen.

First prepare the image with

```
$ perl -e 'print pack("l", (stat @ARGV[0])[7])' fw_payload.bin > fw_payload.bin.out
$ cat fw_payload.bin >> fw_payload.bin.out
```

and install the `lrzsz` package.

Then connect to the UART console

```shell
screen /dev/ttyUSB0 115200
```

Press the `RST` button on the board and then press a key to interrupt the
boot process. You should then see

```text
*************** FLASH PROGRAMMING *****************
```

After that press `0` to update the bootloader. Then press `Ctrl` + `A` then `:`.
In screen run:

```shell
exec !! sx fw_payload.bin.out
```
You can now watch the bootloader update. After it is complete you can reboot the board and watch it boot.

More details can be found [here](https://github.com/tpetazzoni/buildroot/blob/beaglev/board/beaglev/readme.txt#L60).

Resources
=========

* [BeagleV wiki](https://wiki.seeedstudio.com/BeagleV-Getting-Started/)
* [Buildroot BeagleV README](https://github.com/tpetazzoni/buildroot/blob/beaglev/board/beaglev/readme.txt)
