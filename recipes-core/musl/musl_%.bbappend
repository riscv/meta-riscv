FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:riscv32 = "\
    file://0001-Emulate-wait4-using-waitid.patch \
    file://0002-riscv-Fall-back-to-syscall-__riscv_flush_icache.patch \
    file://0003-riscv32-Target-and-subtarget-detection.patch \
    file://0004-riscv32-add-arch-headers.patch \
    file://0005-riscv32-Add-fenv-and-math.patch \
    file://0006-riscv32-Add-dlsym.patch \
    file://0007-riscv32-Add-jmp_buf-and-sigreturn.patch \
    file://0008-riscv32-Add-thread-support.patch \
    file://0009-Change-definitions-of-F_GETLK-F_SETLK-F_SETLKW.patch \
    file://0010-Add-bits-reg.h-for-riscv32.patch \
    file://0011-riscv32-fix-inconsistent-ucontext_t-struct-tag.patch \
    file://0012-riscv32-Wire-new-syscalls.patch \
"
# RISCV-32 is supported via meta-riscv until musl port lands upstream
COMPATIBLE_HOST:riscv32 = ".*-musl.*"
