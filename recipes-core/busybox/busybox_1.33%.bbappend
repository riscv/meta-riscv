FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_riscv32 = " file://0001-hwclock-Check-for-SYS_settimeofday-before-calling-sy.patch"
