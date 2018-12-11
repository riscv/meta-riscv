# Upstream Glibc does not yet have RISC-V 32-bit support

# This is the most recent port sent to the glibc mailing list
GLIBC_GIT_URI_qemuriscv32 = "git://github.com/zongbox/riscv-glibc.git"
SRCBRANCH_qemuriscv32 = "riscv32-submission-v4-rebase-v5"
SRCREV_qemuriscv32 = "858a04037082f99ac74766df696f6a7c9c986ada"

SRC_URI_remove_qemuriscv32 = " \
    file://0011-eglibc-run-libm-err-tab.pl-with-specific-dirs-in-S.patch \
    file://0028-bits-siginfo-consts.h-enum-definition-for-TRAP_HWBKP.patch \
    file://0031-sysdeps-ieee754-prevent-maybe-uninitialized-errors-w.patch \
    file://0032-sysdeps-ieee754-soft-fp-ignore-maybe-uninitialized-w.patch \
    file://0032-sysdeps-ieee754-soft-fp-ignore-maybe-uninitialized-w.patch \
    file://0033-locale-prevent-maybe-uninitialized-errors-with-Os-BZ.patch \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_qemuriscv32 = "\
    file://0001-riscv-Fix-dl-cache-array-bounds.patch \
"
