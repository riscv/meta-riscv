require recipes-kernel/linux/linux-riscv-common.inc

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

LINUX_VERSION ?= "4.19"

BRANCH = "riscv-linux-4.19"
SRCREV = "998246b5d00db61bb6449aa81a1df145e5aadf6b"

SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/palmer/riscv-linux.git;branch=${BRANCH} \
    file://pci.cfg \
"

# qemu uses in-tree defconfig
# freedom-u540 uses out-of-tree defconfig
SRC_URI_append_freedom-u540 = " file://defconfig"

SRC_URI_append_qemuriscv32 = " file://32bit.cfg"

SRC_URI_append_freedom-u540 = " \
                                file://0001-risc-v-Fix-issue-ignoring-CONFIG_CMDLINE_OVERRIDE.patch \
                                file://0001-spi-sifive-support-SiFive-SPI-controller-in-Quad-Mod.patch \
                                file://0002-spi-nor-add-support-for-is25wp-32-64-128-256.patch \
                                file://0003-serial-sifive-initial-driver-from-Paul-Walmsley.patch \
                                file://0004-gemgxl-mgmt-implement-clock-switch-for-GEM-tx_clk.patch \
                                file://0005-u54-prci-driver-for-core-U54-clocks.patch \
                                file://0006-u54-prci-driver-for-core-U54-clocks.patch \
                                file://0008-pwm-sifive-add-a-driver-for-SiFive-SoC-PWM.patch \
                                file://0007-gpio-sifive-support-GPIO-on-SiFive-SoCs.patch \
                                file://0009-RISC-V-Networking-fix-Hack.patch \
                                file://0010-pcie-microsemi-added-support-for-the-Vera-board-root.patch \
                              "
