DESCRIPTION = "Linux Kernel for Zhihe A210 RISC-V SoC"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

BRANCH = "osl/a210-mainline-v7.2.11"
SRC_URI = "git://git.osuosl.org/osuosl/zhihe-a210-kernel.git;protocol=https;branch=${BRANCH}"

SRCREV = "918fb6c25eae90d0e310b717a1fb2038d045e798"

KCONFIG_MODE = "alldefconfig"
KBUILD_DEFCONFIG = "a210_evb_defconfig"

KERNEL_FEATURES:remove:riscv64 = " ${KERNEL_FEATURES_RISCV}"

LINUX_VERSION ?= "7.2.11"
LINUX_VERSION_EXTENSION = "-zhihe-a210"
PV = "${LINUX_VERSION}+git"

COMPATIBLE_MACHINE = "zhihe-a210"
