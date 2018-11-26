# Upstream Glibc does not yet have RISC-V 32-bit support.
# Force it to use the RISC-V fork

GLIBC_GIT_URI_qemuriscv32 = "git://github.com/riscv/riscv-glibc.git"
SRCBRANCH_qemuriscv32 = "32bit"
SRCREV_qemuriscv32 = "83d5eaa75361ba0cb7969669452c9ff233ac710b"

SRC_URI_remove_qemuriscv32 = " \
    file://0031-sysdeps-ieee754-prevent-maybe-uninitialized-errors-w.patch \
    file://0032-sysdeps-ieee754-soft-fp-ignore-maybe-uninitialized-w.patch \
    file://0033-locale-prevent-maybe-uninitialized-errors-with-Os-BZ.patch \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_qemuriscv32 = "\
    file://0001-riscv-Fix-dl-cache-array-bounds.patch \
"
