SUMMARY = "Mainline Linux Kernel for StarFive JH7110 boards"

require recipes-kernel/linux/linux-mainline-common.inc

DEPENDS += "u-boot-tools-native"

BRANCH = "master"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;protocol=https;branch=${BRANCH} \
          "
SRCREV = "028ef9c96e96197026887c0f092424679298aae8"
LINUX_VERSION = "7.0"

COMPATIBLE_MACHINE = "(visionfive2-mainline)"
