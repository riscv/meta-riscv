DESCRIPTION = "Linux Kernel for Zhihe A210 RISC-V SoC"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

SRC_URI = "git://git.osuosl.org/osuosl/zhihe-a210-kernel.git;protocol=https;nobranch=1;tag=v7.2.12-osl-a210"

SRCREV = "5e59b3a72cd424dc1ea8aeef89ebbcf9b64c81c4"

KCONFIG_MODE = "alldefconfig"
KBUILD_DEFCONFIG = "a210_evb_defconfig"

KERNEL_FEATURES:remove:riscv64 = " ${KERNEL_FEATURES_RISCV}"

LINUX_VERSION ?= "7.2.12"
LINUX_VERSION_EXTENSION = "-zhihe-a210"
PV = "${LINUX_VERSION}+git"

COMPATIBLE_MACHINE = "zhihe-a210"
