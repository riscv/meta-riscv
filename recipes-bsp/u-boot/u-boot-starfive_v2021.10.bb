require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

inherit uboot-extlinux-config

LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

BRANCH = "Star64"

SRC_URI = "git://github.com/Fishwaldo/u-boot.git;protocol=https;branch=${BRANCH} \
           file://tftp-mmc-boot.txt \
           file://uEnv-star64.txt \
          "

SRCREV = "c71fa7376f4eaf29e2dc20e5a68418d79201290a"

DEPENDS:append = " u-boot-tools-native"

DEPENDS:append = " jh7110-spl-tool-native"

# Overwrite this for your server
TFTP_SERVER_IP ?= "127.0.0.1"

do_configure:prepend() {
    sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${UNPACKDIR}/tftp-mmc-boot.txt
    mkimage -O linux -T script -C none -n "U-Boot boot script" \
        -d ${UNPACKDIR}/tftp-mmc-boot.txt ${B}/${UBOOT_ENV_BINARY}
}

do_deploy:append() {
    install -m 644 ${UNPACKDIR}/uEnv-star64.txt ${DEPLOYDIR}/vf2_uEnv.txt
    spl_tool -c -f ${DEPLOYDIR}/${SPL_IMAGE}
    ln -sf ${SPL_IMAGE}.normal.out ${DEPLOYDIR}/${SPL_BINARYNAME}.normal.out
    ln -sf ${SPL_IMAGE}.normal.out ${DEPLOYDIR}/${SPL_SYMLINK}.normal.out
}

COMPATIBLE_MACHINE = "star64"

TOOLCHAIN = "gcc"
