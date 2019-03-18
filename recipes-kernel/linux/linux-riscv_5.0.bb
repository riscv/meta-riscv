DESCRIPTION = "RISC-V Linux Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

DEPENDS_append = " libgcc"

LINUX_VERSION ?= "5.0"
LINUX_VERSION_EXTENSION = "-riscv"

PV = "${LINUX_VERSION}+git${SRCPV}"

BRANCH = "hifive-unleashed-5.0"
SRCREV = "1fc509f5f47f0ef5b723666ab3670898ac3208a1"

SRC_URI = " \
    git://github.com/alistair23/linux.git;branch=${BRANCH} \
"

# qemu uses in-tree defconfig
# freedom-u540 uses out-of-tree defconfig
SRC_URI_append_freedom-u540 = " file://defconfig"

SRC_URI_append_qemuriscv32 = " file://32bit.cfg"

KBUILD_DEFCONFIG_qemuriscv32 = "defconfig"
KBUILD_DEFCONFIG_qemuriscv64 = "defconfig"
KCONFIG_MODE="--alldefconfig"

KERNEL_CC_append = " ${TOOLCHAIN_OPTIONS} ${SECURITY_NOPIE_CFLAGS}"
KERNEL_LD_append = " -no-pie"

COMPATIBLE_MACHINE = "(qemuriscv32|qemuriscv64|freedom-u540)"

KERNEL_FEATURES_remove = "features/debug/printk.scc"
KERNEL_FEATURES_remove = "features/kernel-sample/kernel-sample.scc"
