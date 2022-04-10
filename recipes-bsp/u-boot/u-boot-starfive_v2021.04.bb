require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "git://github.com/starfive-tech/u-boot.git;protocol=https;branch=Fedora_VIC_7100_2021.04 \
           file://tftp-mmc-boot.txt \
           file://977abc529f98c1c90a80ad280fe9e58ddd43c87a.patch \
           file://2feaab2bd04ed736c637518b3b553615f0c97890.patch \
          "

SRCREV = "7b70e1d44ba9702a519ca936cabf19070309123a"

DEPENDS:append = " u-boot-tools-native"

# Overwrite this for your server
TFTP_SERVER_IP ?= "127.0.0.1"

do_configure:prepend() {
    sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${WORKDIR}/tftp-mmc-boot.txt
    mkimage -O linux -T script -C none -n "U-Boot boot script" \
        -d ${WORKDIR}/tftp-mmc-boot.txt ${WORKDIR}/${UBOOT_ENV_BINARY}
}

COMPATIBLE_MACHINE = "(beaglev-starlight-jh7100)"

TOOLCHAIN = "gcc"
