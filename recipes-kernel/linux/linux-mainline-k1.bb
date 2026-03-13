SUMMARY = "Mainline Linux Kernel for SpacemiT K1 boards"

require recipes-kernel/linux/linux-mainline-common.inc

DEPENDS += "u-boot-tools-native"

BRANCH = "master"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;protocol=https;branch=${BRANCH} \
           file://nfs.cfg \
           file://misc.cfg \
          "
SRCREV = "f338e77383789c0cae23ca3d48adcc5e9e137e3c"
KBUILD_DEFCONFIG ?= "defconfig"
LINUX_VERSION = "7.0-rc4"

COMPATIBLE_MACHINE = "(k1)"
