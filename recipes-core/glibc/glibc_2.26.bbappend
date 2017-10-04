FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
GLIBC_GIT_URI = "git://github.com/riscv/riscv-glibc"
SRCBRANCH = "riscv-glibc-${PV}"
SRCREV = "8259af85ae3988d1c1f5f3d7c9499f0a18153ec9"

SRC_URI += "file://0001-sysdeps-riscv-Execute-preconfiure-only-for-risv-targ.patch \
           "
# see https://github.com/riscv/riscv-glibc/issues/20
EXTRA_OECONF += "--disable-werror"

