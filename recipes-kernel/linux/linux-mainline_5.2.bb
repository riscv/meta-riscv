require recipes-kernel/linux/linux-mainline-common.inc

LINUX_VERSION ?= "5.2.x"
KERNEL_VERSION_SANITY_SKIP="1"

BRANCH = "master"
SRCREV = "${AUTOREV}"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;branch=${BRANCH} \
"

# Out of tree patches required for HiFive Unleashed
SRC_URI_append_freedom-u540 = " \
    file://0003-u54-prci-driver-for-core-U54-clocks.patch \
    file://0004-u54-prci-driver-for-core-U54-clocks.patch \
    file://0005-gpio-sifive-support-GPIO-on-SiFive-SoCs.patch \
    file://0006-pwm-sifive-add-a-driver-for-SiFive-SoC-PWM.patch \
    file://0008-pcie-microsemi-Add-support-for-the-Vera-board-root-c.patch \
    file://0009-HACK-Revert-of-device-Really-only-set-bus-DMA-mask-w.patch \
    file://0010-HACK-radeon-Don-t-set-PCI-DMA-mask.patch \
"

# use in-tree defconfig

KBUILD_DEFCONFIG_qemuriscv32 = "rv32_defconfig"
KBUILD_DEFCONFIG_qemuriscv64 = "defconfig"
KBUILD_DEFCONFIG_freedom-u540 = "defconfig"
