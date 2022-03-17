FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:riscv32 = " file://0001-Add-rv32-libc-port.patch;patchdir=../cargo_home/bitbake/libc-0.2.120/ "
