FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_riscv64 = " file://0001-Update-sys-module-to-latest.patch;patchdir=src/github.com/golang/dep"
