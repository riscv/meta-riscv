COMPATIBLE_MACHINE_append = "|qemuriscv32|qemuriscv64"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_qemuriscv32 = " file://0001-riscv-Partially-revert-Remove-stat64-family-from-def.patch"
