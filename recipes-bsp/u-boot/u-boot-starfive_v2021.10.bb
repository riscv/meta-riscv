require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

BRANCH:visionfive2 = "JH7110_VisionFive2_devel"

SRC_URI = "git://github.com/starfive-tech/u-boot.git;protocol=https;branch=${BRANCH} \
           file://tftp-mmc-boot.txt \
          "

SRC_URI:append:visionfive2 = " \
        file://uEnv-visionfive2.txt \
"

# tag VF2_v2.11.5
SRCREV:visionfive2 = "688befadf1d337dee3593e6cc0fe1c737cc150bd"

DEPENDS:append = " u-boot-tools-native"

DEPENDS:append:visionfive2 = " jh7110-spl-tool-native"

# Overwrite this for your server
TFTP_SERVER_IP ?= "127.0.0.1"

do_configure:prepend() {
    sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${WORKDIR}/tftp-mmc-boot.txt
    mkimage -O linux -T script -C none -n "U-Boot boot script" \
        -d ${WORKDIR}/tftp-mmc-boot.txt ${WORKDIR}/${UBOOT_ENV_BINARY}
}

do_deploy:append:visionfive2() {
    install -m 644 ${WORKDIR}/uEnv-visionfive2.txt ${DEPLOYDIR}/vf2_uEnv.txt
    spl_tool -c -f ${DEPLOYDIR}/${SPL_IMAGE}
    ln -sf ${SPL_IMAGE}.normal.out ${DEPLOYDIR}/${SPL_BINARYNAME}.normal.out
    ln -sf ${SPL_IMAGE}.normal.out ${DEPLOYDIR}/${SPL_SYMLINK}.normal.out
}

COMPATIBLE_MACHINE = "(visionfive2)"

TOOLCHAIN = "gcc"

# U-boot sets O=... which needs it to build outside of S
B = "${WORKDIR}/build"
