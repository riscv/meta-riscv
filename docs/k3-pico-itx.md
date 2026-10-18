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

By default, the Pico-ITX EEPROM is configured to always boot from the onboard
UFS storage. To modify this setting, use the `tlv_eeprom` command from the
U-Boot environment like so:

```
=> [   3.433] tlv_eeprom
TLV: 0
[   5.183] TlvInfo Header:
[   5.183]    Id String:    TlvInfo
[   5.186]    Version:      1
[   5.189]    Total Length: 83
[   5.192] TLV Name             Code Len Value
[   5.196] -------------------- ---- --- -----
[   5.200] Product Name         0x21  11 k3-pico-itx
[   5.205] Serial Number        0x23  16 HW3MPK3321280188
[   5.210] Base MAC Address     0x24   6 50:0A:52:0B:E7:17
[   5.215] MAC Addresses        0x2A   2 1
[   5.219] DDR Part Number      0x45  13 MT62F4G32D8DV
[   5.224] Part Number          0x22   4 MPK3
[   5.228] PMIC Type            0x80   6 au4562
[   5.232] Second Boot Device   0x83   3 UFS
[   5.236] CRC-32               0xFE   4 0x65193AB5
[   5.241] Checksum is valid.
=> [   9.988] tlv_eeprom set 0x83 SSD
=> tlv_eeprom write
Programming passed.
=> [  52.393] reset
```

Following the change, the board should boot from the NVMe storage, and any
further calls to tlv_eeprom should indicate `SSD` on the `Second Boot Device`
line:

```
=> [  52.393] tlv_eeprom
TLV: 0
[ 115.959] TlvInfo Header:
[ 115.960]    Id String:    TlvInfo
[ 115.963]    Version:      1
[ 115.966]    Total Length: 83
[ 115.968] TLV Name             Code Len Value
[ 115.973] -------------------- ---- --- -----
[ 115.977] Product Name         0x21  11 k3-pico-itx
[ 115.981] Serial Number        0x23  16 HW3MPK3321280188
[ 115.987] Base MAC Address     0x24   6 50:0A:52:0B:E7:17
[ 115.992] MAC Addresses        0x2A   2 1
[ 115.996] DDR Part Number      0x45  13 MT62F4G32D8DV
[ 116.000] Part Number          0x22   4 MPK3
[ 116.004] PMIC Type            0x80   6 au4562
[ 116.009] Second Boot Device   0x83   3 SSD
[ 116.013] CRC-32               0xFE   4 0x94F7DD4D
[ 116.017] Checksum is valid.
```

Cycle the board power (or run `reset` from within U-Boot)  and you will see it
boot to a Linux command line shell.

Resources
=========

* [K3 Pico-ITX User
  Guide](https://www.spacemit.com/community/document/info?lang=en&nodepath=ecohardware/k3_series/k3_pico/pico_user_guide.md)
* [Sipeed K3](https://sipeed.com/k3)
