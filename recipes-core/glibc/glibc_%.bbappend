# Upstream Glibc does not yet have RISC-V 32-bit support

# This is the most recent port sent to the glibc mailing list
GLIBC_GIT_URI_qemuriscv32 = "git://github.com/riscv/riscv-glibc.git"
SRCBRANCH_qemuriscv32 = "riscv-glibc-2.29"
SRCREV_qemuriscv32 = "04fdd476160a55792a75375ba2bf56c761f811c2"

SRC_URI_remove_qemuriscv32 = " \
    file://0001-x86-64-memcmp-Use-unsigned-Jcc-instructions-on-size-.patch \
"
