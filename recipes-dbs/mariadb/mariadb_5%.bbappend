FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI_append_riscv64 = " file://0001-libmysql-Do-not-export-_fini-_init-on-risc-v.patch"
SRC_URI_append_riscv = " file://0001-libmysql-Do-not-export-_fini-_init-on-risc-v.patch"
