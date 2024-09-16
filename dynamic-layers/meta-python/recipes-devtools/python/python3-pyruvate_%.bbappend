FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:riscv32 = "\
    file://0001-statfs-Exclude-riscv32.patch;patchdir=${UNPACKDIR}/cargo_home/bitbake/nix-0.23.2/ \
"
