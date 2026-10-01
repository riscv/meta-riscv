SUMMARY = "U-Boot for Milk-V Duo S (vendor fork)"
DESCRIPTION = "Downstream U-Boot fork used by the Milk-V Duo S (SG2000), which \
has no upstream U-Boot support. Other Milk-V Duo boards use upstream U-Boot \
through u-boot_%.bbappend."

require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

# Set after the requires: u-boot-common.inc defines these for current U-Boot.
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

DEPENDS += "bc-native \
            dtc-native \
            gnutls-native \
            python3-pyelftools-native \
            u-boot-tools-native \
            xxd-native \
            "

SRC_URI = "git://github.com/milkv-duo/milkv-duo-u-boot;protocol=https;branch=duo-64mb \
           file://uboot-milkv-duo.env \
           file://uEnv-milkv-duo.txt \
           file://mmap_conv.py \
           file://memmap.py \
           file://milkv-duo-support-files.patch \
           file://0001-skip-cvitek-board-init.patch \
           file://0002-Add-milkv-boards-dtbs.patch \
           file://milkv-duos.cfg \
           "

SRC_URI_RISCV = "file://u-boot-riscv-isa_clear.cfg \
                 ${@bb.utils.contains    ("TUNE_FEATURES", "a",      "file://u-boot-riscv-isa_a.cfg", "", d)} \
                 ${@bb.utils.contains    ("TUNE_FEATURES", "f",      "file://u-boot-riscv-isa_f.cfg", "", d)} \
                 ${@bb.utils.contains    ("TUNE_FEATURES", "d",      "file://u-boot-riscv-isa_d.cfg", "", d)} \
                 ${@bb.utils.contains    ("TUNE_FEATURES", "c",      "file://u-boot-riscv-isa_c.cfg", "", d)} \
                 ${@bb.utils.contains_any("TUNE_FEATURES", "b zbb",  "file://u-boot-riscv-isa_zbb.cfg", "", d)} \
                 ${@bb.utils.contains    ("TUNE_FEATURES", "zicbom", "file://u-boot-riscv-isa_zicbom.cfg", "", d)} \
                 "
SRC_URI:append:riscv32 = " ${SRC_URI_RISCV}"
SRC_URI:append:riscv64 = " ${SRC_URI_RISCV}"

SRCREV = "4345a29c08e67044021f74139b4ff307019e9932"

# OpenSBI is built after U-Boot for this board: it uses the dtb that U-Boot
# generates (FW_FDT_PATH), so there is no opensbi dependency here.
do_configure:prepend() {
    python3 ${UNPACKDIR}/mmap_conv.py --type h \
        ${UNPACKDIR}/memmap.py \
        ${S}/include/configs/cvi_board_memmap.h

    if [ -f "${UNPACKDIR}/uboot-milkv-duo.env" ]; then
        cp ${UNPACKDIR}/uboot-milkv-duo.env ${S}/include/milkv-duo.env
    fi
}

do_deploy:append() {
    install -m 0644 ${B}/u-boot.dtb ${DEPLOYDIR}
    if [ -f "${UNPACKDIR}/uEnv-milkv-duo.txt" ]; then
        cp ${UNPACKDIR}/uEnv-milkv-duo.txt ${DEPLOYDIR}/uEnv.txt
    fi
    # Consumed by milkv-duo-fsbl
    install -m 0644 ${S}/include/configs/cvi_board_memmap.h ${DEPLOYDIR}
}

COMPATIBLE_MACHINE = "milkv-duos"
