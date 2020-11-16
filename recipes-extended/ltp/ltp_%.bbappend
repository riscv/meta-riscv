FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_riscv32 = " file://0001-Define-SYS_futex-on-32bit-arches-using-64-bit-time_t.patch"
