FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_riscv32 = " file://rv32_sys_futex.patch"

