FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_riscv32 = "\
    file://0001-syscfg-Add-a-riscv32-architecture.patch \
"
