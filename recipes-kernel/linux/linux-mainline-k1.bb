SUMMARY = "Mainline Linux Kernel for SpacemiT K1 boards"

require recipes-kernel/linux/linux-mainline-common.inc

DEPENDS += "u-boot-tools-native"

BRANCH = "master"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;protocol=https;branch=${BRANCH} \
           file://nfs.cfg \
           file://misc.cfg \
          "
SRCREV = "05f7e89ab9731565d8a62e3b5d1ec206485eeb0b"
KBUILD_DEFCONFIG ?= "defconfig"
LINUX_VERSION = "6.19"

COMPATIBLE_MACHINE = "(k1)"
