FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

COMPATIBLE_HOST_riscv64 = "null"
COMPATIBLE_HOST_riscv32 = "null"

SRC_URI_append_riscv64 = " file://0001-Recongnise-riscv64-target-arch.patch"
