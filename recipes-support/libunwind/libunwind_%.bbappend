FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_riscv64 = " file://0001-Recongnise-riscv64-target-arch.patch"
