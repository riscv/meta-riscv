Zhihe A210
==========

The Zhihe A210 is a 64-bit, 8-core RISC-V development board based on the
Zhihe Computing Archimedes A210 SoC.

How to Build
============

Clone and enable these repositories and layers:

* bitbake
* openembedded-core
  * meta
* meta-riscv

Set these variables in `conf/local.conf`:

* `MACHINE = "zhihe-a210"`
* `EXTRA_IMAGE_FEATURES = "allow-empty-password empty-root-password allow-root-login"`

Build the image:

```
$ bitbake core-image-minimal
```

How to Build - Using Kas
========================

```
$ kas build /path/to/meta-riscv/kas/zhihe-a210.yml --target core-image-minimal
```

The `kas` tool can be installed as a package on your distribution or
be obtained from the [kas repository](https://github.com/siemens/kas/).

Booting the Board (TFTP Netboot)
================================

No bootloader or firmware recipe is provided in `meta-riscv` yet; the board
boots using its factory eMMC OpenSBI and U-Boot firmware and loads the built
kernel (`Image`), device tree (`a210-dev.dtb`), and initramfs
(`core-image-minimal-zhihe-a210.rootfs.cpio.gz`) over TFTP into RAM:

1. Copy the built artifacts from `tmp/deploy/images/zhihe-a210/` (`Image`,
   `a210-dev.dtb`, and `core-image-minimal-zhihe-a210.rootfs.cpio.gz`) to your
   TFTP server directory.
2. Connect to the serial console (`/dev/ttyS4` on target, 115200 8N1) and
   interrupt U-Boot autoboot.
3. Run the following commands at the U-Boot prompt (replacing `<server_ip>` with
   your TFTP server IP):

```
=> setenv autoload no
=> dhcp
=> setenv serverip <server_ip>
=> tftpboot ${kernel_addr} Image
=> tftpboot ${dtb_addr} a210-dev.dtb
=> tftpboot ${initrd_addr} core-image-minimal-zhihe-a210.rootfs.cpio.gz
=> setenv bootargs 'console=ttyS4,115200 earlycon clk_ignore_unused panic=30'
=> booti ${kernel_addr} ${initrd_addr}:${filesize} ${dtb_addr}
```

Resources
=========

* [OSU-OSL Zhihe A210 Kernel Repository](https://git.osuosl.org/osuosl/zhihe-a210-kernel)
