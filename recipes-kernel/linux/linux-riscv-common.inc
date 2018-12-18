DESCRIPTION = "RISC-V Linux Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

SRC_URI = "\
    git://git.kernel.org/pub/scm/linux/kernel/git/palmer/riscv-linux.git;branch=${BRANCH} \
    file://pci.cfg \
"

# qemu uses in-tree defconfig
# freedom-u540 uses out-of-tree defconfig
SRC_URI_append_freedom-u540 = " file://defconfig \
                                file://0001-spi-sifive-support-SiFive-SPI-controller-in-Quad-Mod.patch \
                                file://0002-spi-nor-add-support-for-is25wp-32-64-128-256.patch \
                                file://0003-serial-sifive-initial-driver-from-Paul-Walmsley.patch \
                                file://0005-u54-prci-driver-for-core-U54-clocks.patch \
                                file://0006-u54-prci-driver-for-core-U54-clocks.patch \
                                file://0008-pwm-sifive-add-a-driver-for-SiFive-SoC-PWM.patch \
                                file://0009-RISC-V-Networking-fix-Hack.patch \
                                file://0010-pcie-microsemi-added-support-for-the-Vera-board-root.patch \
                              "

SRC_URI_append_qemuriscv32 = " file://32bit.cfg \
                            "

LINUX_VERSION_EXTENSION = "-riscv"

PV = "${LINUX_VERSION}+git${SRCPV}"

DEPENDS_append = " libgcc"

KBUILD_DEFCONFIG_qemuriscv32 = "defconfig"
KBUILD_DEFCONFIG_qemuriscv64 = "defconfig"
KCONFIG_MODE="--alldefconfig"

KERNEL_CC_append = " ${TOOLCHAIN_OPTIONS} ${SECURITY_NOPIE_CFLAGS}"
KERNEL_LD_append = " -no-pie"

COMPATIBLE_MACHINE = "(qemuriscv32|qemuriscv64|freedom-u540)"

KERNEL_FEATURES_remove = "features/debug/printk.scc"
KERNEL_FEATURES_remove = "features/kernel-sample/kernel-sample.scc"
