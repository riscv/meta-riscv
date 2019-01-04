FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_riscv = " file://pci.cfg"

SRC_URI_append_qemuriscv32 = " file://32bit.cfg"

KBUILD_DEFCONFIG_qemuriscv32 = "defconfig"
KCONFIG_MODE_qemuriscv32 = "--alldefconfig"

KBUILD_DEFCONFIG_qemuriscv64 = "defconfig"
KCONFIG_MODE_qemuriscv64 = "--alldefconfig"

KERNEL_CC_append_riscv = " ${TOOLCHAIN_OPTIONS} ${SECURITY_NOPIE_CFLAGS}"
KERNEL_LD_append_riscv = " -no-pie"

COMPATIBLE_MACHINE = "(qemuriscv32|qemuriscv64)"
