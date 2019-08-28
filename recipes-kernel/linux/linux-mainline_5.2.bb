require recipes-kernel/linux/linux-mainline-common.inc

LINUX_VERSION ?= "5.2.x"
KERNEL_VERSION_SANITY_SKIP="1"

BRANCH = "linux-5.2.y"
SRCREV = "${AUTOREV}"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;branch=${BRANCH} \
"

SRC_URI_append_freedom-u540 = " \
    file://0001-macb-bindings-doc-add-sifive-fu540-c000-binding.patch \
    file://0002-macb-Add-support-for-SiFive-FU540-C000.patch \
    file://0003-riscv-dts-Add-DT-node-for-SiFive-FU540-Ethernet-cont.patch \
    file://0004-PCI-microsemi-Add-host-driver-for-Microsemi-PCIe-con.patch \
    file://0005-Microsemi-PCIe-expansion-board-DT-entry.patch \
    file://0006-HACK-Revert-of-device-Really-only-set-bus-DMA-mask-w.patch \
    file://0007-HACK-radeon-Don-t-set-PCI-DMA-mask.patch \
"

KERNEL_DEVICETREE_freedom-u540 = "sifive/${RISCV_SBI_FDT}"

# freedom-u540 uses out-of-tree defconfig
SRC_URI_append_freedom-u540 = " file://defconfig"

# qemu uses in-tree defconfig
KBUILD_DEFCONFIG_qemuriscv32 = "rv32_defconfig"
KBUILD_DEFCONFIG_qemuriscv64 = "defconfig"
