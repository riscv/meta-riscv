require recipes-kernel/linux/linux-mainline-common.inc

LINUX_VERSION ?= "5.1.x"
KERNEL_VERSION_SANITY_SKIP="1"

BRANCH = "linux-5.1.y"
SRCREV = "${AUTOREV}"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;branch=${BRANCH} \
"

# Out of tree patches required for HiFive Unleashed
SRC_URI_append_freedom-u540 = " \
    file://0001-tty-serial-add-driver-for-the-SiFive-UART.patch \
    file://0002-gemgxl-mgmt-implement-clock-switch-for-GEM-tx_clk.patch \
    file://0003-u54-prci-driver-for-core-U54-clocks.patch \
    file://0004-u54-prci-driver-for-core-U54-clocks.patch \
    file://0005-gpio-sifive-support-GPIO-on-SiFive-SoCs.patch \
    file://0006-pwm-sifive-add-a-driver-for-SiFive-SoC-PWM.patch \
    file://0007-RISC-V-Networking-fix-Hack.patch \
    file://0008-pcie-microsemi-Add-support-for-the-Vera-board-root-c.patch \
    file://0009-HACK-Revert-of-device-Really-only-set-bus-DMA-mask-w.patch \
    file://0010-HACK-radeon-Don-t-set-PCI-DMA-mask.patch \
"

# Fix a breakage with the current 32bit glibc fork
SRC_URI_append_qemuriscv32 = " file://0001-Revert-riscv-Use-latest-system-call-ABI.patch"

# qemu uses in-tree defconfig
# freedom-u540 uses out-of-tree defconfig
SRC_URI_append_freedom-u540 = " file://defconfig"

KBUILD_DEFCONFIG_qemuriscv32 = "rv32_defconfig"
KBUILD_DEFCONFIG_qemuriscv64 = "defconfig"
