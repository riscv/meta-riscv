DESCRIPTION = "Linux Kernel 6.18 for K3 Board (Spacemit K3 RISC-V Processor)"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

BRANCH = "k3-br-v1.0.y"
SRC_URI = "\
        git://github.com/spacemit-com/linux-6.18.git;protocol=https;branch=${BRANCH} \
        file://0001-gcc-plugins-Always-define-CONST_CAST_GIMPLE-and-CONS.patch \
	"

SRCREV = "0ffac20d9ef93c572b649037213bbe20ef59a714"

KCONFIG_MODE = "alldefconfig"
KBUILD_DEFCONFIG = "k3_bianbu_defconfig"

KERNEL_FEATURES:remove:riscv64 = " ${KERNEL_FEATURES_RISCV}"

LINUX_VERSION ?= "6.18.3"
LINUX_VERSION_EXTENSION = "-k3"
PV = "${LINUX_VERSION}+git"

COMPATIBLE_MACHINE = "k3"
