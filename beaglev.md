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
- fw_payload.bin
- fw_payload.elf
- extlinux.conf
- Image
- starfive_vic7100_beagle_v.dtb
- u-boot.bin

Flashing
========

Flash `core-image-minimal-beaglev-starlight-jh7100.wic.gz` onto uSD card.

Testing
=======

TBD
