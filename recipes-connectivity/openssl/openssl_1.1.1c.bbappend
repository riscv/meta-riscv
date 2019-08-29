FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_riscv32 = " \
        file://0001-Add-support-for-io_pgetevents_time64-syscall.patch \
    "
