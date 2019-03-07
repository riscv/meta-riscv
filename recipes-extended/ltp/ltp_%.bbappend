FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_riscv64 = " file://0001-ptrace-Do-not-include-asm-ptrace.h-on-riscv.patch" 
SRC_URI_append_riscv32 = " file://0001-ptrace-Do-not-include-asm-ptrace.h-on-riscv.patch" 
