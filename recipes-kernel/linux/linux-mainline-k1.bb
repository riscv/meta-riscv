SUMMARY = "Mainline Linux Kernel for SpacemiT K1 boards"

require recipes-kernel/linux/linux-mainline-common.inc

DEPENDS += "u-boot-tools-native"

BRANCH = "master"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;protocol=https;branch=${BRANCH} \
           file://misc.cfg \
           file://k1-i2c.cfg \
           file://no-vector-unaligned-probe.cfg \
          "

SRC_URI:append:bananapi-cm6-io = " \
           file://0001-dt-bindings-riscv-spacemit-Add-Banana-Pi-BPI-CM6-com.patch \
           file://0002-riscv-dts-spacemit-k1-Split-gmac_clk_ref-into-indepe.patch \
           file://0003-riscv-dts-spacemit-k1-Add-Banana-Pi-BPI-CM6-IO-board.patch \
          "

SRCREV = "f5098b6bae761e346ebcd9da7f95622c04733cff"
LINUX_VERSION = "7.2-rc5"

COMPATIBLE_MACHINE = "(k1)"
