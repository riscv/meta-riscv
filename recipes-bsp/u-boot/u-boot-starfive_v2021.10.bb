require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

inherit uboot-extlinux-config

LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

BRANCH:visionfive2 = "JH7110_VisionFive2_devel"
BRANCH:star64 = "Star64"

SRC_URI = "git://github.com/starfive-tech/u-boot.git;protocol=https;nobranch=1 \
           file://0001-cdns3-starfive-Include-dm-lists.h-for-device_bind_dr.patch \
           file://0002-net-rtl8169-Fix-compile-warning-in-rtl8169.patch \
           file://0003-video-dw_hdmi-Fix-compiler-warnings-with-gcc-14.patch \
           file://tftp-mmc-boot.txt \
          "

SRC_URI:star64 = "git://github.com/Fishwaldo/u-boot.git;protocol=https;branch=${BRANCH} \
           file://tftp-mmc-boot.txt \
          "

SRC_URI:append:visionfive2 = " \
        file://uEnv-visionfive2.txt \
"

SRC_URI:append:star64 = " \
        file://uEnv-star64.txt \
"


# tag JH7110_VF2_6.1_v5.11.3
SRCREV:visionfive2 = "223ac8b1e907924d3891b3be1b2f6620b56bff31"
SRCREV:star64 = "c71fa7376f4eaf29e2dc20e5a68418d79201290a"


DEPENDS:append = " u-boot-tools-native"

DEPENDS:append:jh7110 = " jh7110-spl-tool-native"

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

do_deploy:append:star64() {
    install -m 644 ${WORKDIR}/uEnv-star64.txt ${DEPLOYDIR}/vf2_uEnv.txt
    spl_tool -c -f ${DEPLOYDIR}/${SPL_IMAGE}
    ln -sf ${SPL_IMAGE}.normal.out ${DEPLOYDIR}/${SPL_BINARYNAME}.normal.out
    ln -sf ${SPL_IMAGE}.normal.out ${DEPLOYDIR}/${SPL_SYMLINK}.normal.out
}


COMPATIBLE_MACHINE = "jh7110"

TOOLCHAIN = "gcc"


# U-boot sets O=... which needs it to build outside of S
B = "${WORKDIR}/build"
