FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_riscv32 = " \
        file://0001-date-Use-64-prefix-syscall-if-we-have-to.patch \
        file://0002-time-Use-64-prefix-syscall-if-we-have-to.patch \
        file://0003-runsv-Use-64-prefix-syscall-if-we-have-to.patch \
    "
