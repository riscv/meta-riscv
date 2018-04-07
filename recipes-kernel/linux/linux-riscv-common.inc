DESCRIPTION = "RISC-V Linux Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

SRC_URI = "\
    git://github.com/riscv/riscv-linux.git;branch=${BRANCH} \
    file://earlyprintk.cfg \
"

# qemu uses in-tree defconfig
# freedom-u540 uses out-of-tree defconfig
SRC_URI_append_freedom-u540 = " file://defconfig \
                                file://0001-risc-v-Fix-issue-ignoring-CONFIG_CMDLINE_OVERRIDE.patch \
                              "

LINUX_VERSION_EXTENSION = "-riscv"

PV = "${LINUX_VERSION}+git${SRCPV}"

DEPENDS_append = " libgcc"

KBUILD_DEFCONFIG_qemuriscv64 = "defconfig"
KCONFIG_MODE="--alldefconfig"

KERNEL_CC_append = " ${TOOLCHAIN_OPTIONS} ${SECURITY_NOPIE_CFLAGS}"
KERNEL_LD_append = " -no-pie"

COMPATIBLE_MACHINE = "(qemuriscv64|freedom-u540)"

KERNEL_FEATURES_remove = "features/debug/printk.scc"
