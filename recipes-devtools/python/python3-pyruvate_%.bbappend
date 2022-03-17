FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:riscv32 = "\
    file://0001-Add-rv32-libc-port.patch;patchdir=../cargo_home/bitbake/libc-0.2.120/ \
    file://0001-statfs-Exclude-riscv32.patch;patchdir=../cargo_home/bitbake/nix-0.23.1/ \
"
