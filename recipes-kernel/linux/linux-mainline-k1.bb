SUMMARY = "Mainline Linux Kernel for SpacemiT K1 boards"

require recipes-kernel/linux/linux-mainline-common.inc

DEPENDS += "u-boot-tools-native"

BRANCH = "master"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;protocol=https;branch=${BRANCH} \
           file://nfs.cfg \
           file://misc.cfg \
          "
SRCREV = "1f318b96cc84d7c2ab792fcc0bfd42a7ca890681"
KBUILD_DEFCONFIG ?= "defconfig"
LINUX_VERSION = "7.0-rc3"

COMPATIBLE_MACHINE = "(k1)"
