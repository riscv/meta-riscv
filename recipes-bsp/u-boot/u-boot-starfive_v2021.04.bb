require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "git://github.com/atishp04/u-boot.git;protocol=git;branch=beagleV/Fedora_VIC_7100_2021.04 \
	       file://tftp-mmc-boot.txt \
          "

SRCREV = "7b70e1d44ba9702a519ca936cabf19070309123a"

DEPENDS_append = " u-boot-tools-native"

# Overwrite this for your server
TFTP_SERVER_IP ?= "127.0.0.1"

do_configure_prepend() {
    sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${WORKDIR}/tftp-mmc-boot.txt
    mkimage -O linux -T script -C none -n "U-Boot boot script" \
        -d ${WORKDIR}/tftp-mmc-boot.txt ${WORKDIR}/${UBOOT_ENV_BINARY}
}

COMPATIBLE_MACHINE = "(beaglev-starlight-jh7100)"

TOOLCHAIN = "gcc"
