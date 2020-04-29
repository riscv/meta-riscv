FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_riscv32 = " file://riscv32__NR_futex.patch"
