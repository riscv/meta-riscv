FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

LDFLAGS_append_riscv64 = " -pthread"
