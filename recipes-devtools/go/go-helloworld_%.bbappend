FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

GOBUILDFLAGS_remove_riscv64 = "-buildmode=pie"
