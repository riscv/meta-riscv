DESCRIPTION = "Mainline Linux Kernel"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

LINUX_VERSION_EXTENSION = ""

PV = "${LINUX_VERSION}+git${SRCPV}"

KCONFIG_MODE = "--alldefconfig"

KBUILD_DEFCONFIG ?= "defconfig"
KBUILD_DEFCONFIG:qemuriscv32 = "rv32_defconfig"

COMPATIBLE_MACHINE = "(qemuriscv32|qemuriscv64|freedom-u540)"

KERNEL_FEATURES:remove = "features/debug/printk.scc"
KERNEL_FEATURES:remove = "features/kernel-sample/kernel-sample.scc"
KERNEL_FEATURES:remove = "features/taskstats/taskstats.scc"
KERNEL_FEATURES:remove = "cfg/fs/vfat.scc"
KERNEL_FEATURES:remove:riscv32 = " ${KERNEL_FEATURES_RISCV}"
KERNEL_FEATURES:remove:riscv64 = " ${KERNEL_FEATURES_RISCV}"
