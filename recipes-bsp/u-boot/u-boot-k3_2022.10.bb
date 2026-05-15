DESCRIPTION = "U-Boot bootloader for K3 Board (Spacemit K3 RISC-V Processor)"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

require recipes-bsp/u-boot/u-boot.inc

DEPENDS += "bc-native dtc-native bison-native flex-native u-boot-tools-native"

# Force version number
PV = "2022.10"

SRC_URI = "git://github.com/spacemit-com/uboot-2022.10.git;protocol=https;tag=k3-br-v1.0.0;nobranch=1 \
           file://0001-Disable-source-tree-clean-check.patch \
           file://env_k3.txt \
           file://spacemit.bmp"


COMPATIBLE_MACHINE = "k3"
UBOOT_MAKE_TARGET = "all"

# Do not try to install boot.scr.uimg if it doesn't exist
# K3 U-Boot may not generate this file
SPL_BINARY = ""
UBOOT_ENV = ""

do_deploy:append() {
    # Deploy all bootinfo variants for different storage types
    for target in sd emmc spinand spinor block; do
        if [ -f "${S}/bootinfo_${target}.bin" ]; then
            install -m 644 "${S}/bootinfo_${target}.bin" "${DEPLOYDIR}/bootinfo_${target}.bin"
        fi
    done
    
    install -m 644 "${S}/FSBL.bin" "${DEPLOYDIR}/FSBL.bin"
    install -m 644 "${B}/u-boot.itb" "${DEPLOYDIR}/u-boot.itb"
    install -m 644 "${S}/u-boot-env-default.bin" "${DEPLOYDIR}/u-boot-env-default.bin"
        
    # Deploy custom environment file for K3
    install -m 644 "${WORKDIR}/sources/env_k3.txt" "${DEPLOYDIR}/env_k3.txt"

    # Deploy boot logo for bootfs
    install -m 644 "${WORKDIR}/sources/spacemit.bmp" "${DEPLOYDIR}/spacemit.bmp"
}
