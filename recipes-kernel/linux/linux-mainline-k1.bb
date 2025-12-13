SUMMARY = "Mainline Linux Kernel for SpacemiT K1 boards"

require recipes-kernel/linux/linux-mainline-common.inc

DEPENDS += "u-boot-tools-native"

BRANCH = "master"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;protocol=https;branch=${BRANCH} \
           file://nfs.cfg \
           file://misc.cfg \
          "
SRCREV = "8f0b4cce4481fb22653697cced8d0d04027cb1e8"
KBUILD_DEFCONFIG ?= "defconfig"
LINUX_VERSION = "6.19-rc1"

COMPATIBLE_MACHINE = "(k1)"
