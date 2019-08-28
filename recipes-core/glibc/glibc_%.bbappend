# Upstream Glibc does not yet have RISC-V 32-bit support

GLIBC_GIT_URI_qemuriscv32 = "git://github.com/alistair23/glibc.git"
SRCBRANCH_qemuriscv32 = "alistair/rv32.rfc5"
SRCREV_glibc_qemuriscv32 = "1fe4adcab0084df29f4c18c2d5f1154a1eeb119d"

SRC_URI_remove_qemuriscv32 = " \
    file://0001-x86-64-memcmp-Use-unsigned-Jcc-instructions-on-size-.patch \
    file://CVE-2019-9169.patch \
"
