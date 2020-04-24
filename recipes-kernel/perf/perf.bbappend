FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PACKAGECONFIG_remove_riscv32 = "libunwind"
