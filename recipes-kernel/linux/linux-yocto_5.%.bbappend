FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

COMPATIBLE_MACHINE_append = "|qemuriscv32"

SRC_URI_append_riscv32 = " file://0001-perf-Alias-SYS_futex-with-SYS_futex_time64-on-32-bit.patch"
