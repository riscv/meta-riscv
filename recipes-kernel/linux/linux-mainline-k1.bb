SUMMARY = "Mainline Linux Kernel for SpacemiT K1 boards"

require recipes-kernel/linux/linux-mainline-common.inc

DEPENDS += "u-boot-tools-native"

BRANCH = "master"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;protocol=https;branch=${BRANCH} \
           file://nfs.cfg \
           file://misc.cfg \
           file://k1-i2c.cfg \
           file://0001-mmc-sdhci-of-k1-enable-essential-clock-infrastructur.patch \
           file://0002-mmc-sdhci-of-k1-add-regulator-and-pinctrl-voltage-sw.patch \
           file://0003-mmc-sdhci-of-k1-add-SDR-tuning-infrastructure.patch \
           file://0004-mmc-sdhci-of-k1-add-comprehensive-SDR-tuning-support.patch \
           file://0005-riscv-dts-spacemit-k1-add-SD-card-controller-and-pin.patch \
           file://0006-riscv-dts-spacemit-k1-orangepi-rv2-add-PMIC-and-powe.patch \
           file://0007-riscv-dts-spacemit-k1-orangepi-rv2-add-SD-card-suppo.patch \
           file://0001-k1-musepi-pro-add-SD-card-support.patch \
          "
SRCREV = "1f318b96cc84d7c2ab792fcc0bfd42a7ca890681"
KBUILD_DEFCONFIG ?= "defconfig"
LINUX_VERSION = "7.0-rc3"

COMPATIBLE_MACHINE = "(k1)"
