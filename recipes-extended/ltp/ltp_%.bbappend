FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_riscv32 = " file://0001-Define-__NR_futex-to-be-__NR_futex_time64-on-riscv32.patch"
