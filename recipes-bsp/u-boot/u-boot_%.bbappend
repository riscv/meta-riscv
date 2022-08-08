FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend:ae350-ax45mp := "${THISDIR}/files/ae350-ax45mp:"

SRC_URI:append:freedom-u540 = " \
            file://tftp-mmc-boot.txt \
           "
SRC_URI:append:freedom-u540_sota = " file://uEnv.txt"

DEPENDS:append:freedom-u540 = " u-boot-tools-native"

SRC_URI:append:ae350-ax45mp = " \
            file://0001-Fix-SD-detect-failure.patch \
            file://0002-Prevent-fw_dynamic-relocation.patch \
            file://0003-riscv-ae350-Fix-XIP-config-boot-failure.patch \
            file://0004-spl_opensbi.c-enable-printing-OpenSBI-boot-log.patch \
            file://0005-mmc-ftsdc010_mci-Support-DTS-of-ftsdc010-driver-for-.patch \
            file://u-boot.cfg \
            file://tftp-mmc-boot.txt \
            file://uEnv-ae350.txt \
            "

DEPENDS:append = " u-boot-tools-native"
DEPENDS:append:ae350-ax45mp = " opensbi"

# Overwrite this for your server
TFTP_SERVER_IP ?= "127.0.0.1"

do_configure:prepend:freedom-u540() {
    sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${WORKDIR}/tftp-mmc-boot.txt

    if [ -f "${WORKDIR}/${UBOOT_ENV}.txt" ]; then
        mkimage -O linux -T script -C none -n "U-Boot boot script" \
            -d ${WORKDIR}/${UBOOT_ENV}.txt ${WORKDIR}/boot.scr.uimg
    fi
}

do_deploy:append:freedom-u540() {
    if [ -f "${WORKDIR}/boot.scr.uimg" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 755 ${WORKDIR}/boot.scr.uimg ${DEPLOY_DIR_IMAGE}
    fi

    if [ -f "${WORKDIR}/uEnv.txt" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 755 ${WORKDIR}/uEnv.txt ${DEPLOY_DIR_IMAGE}
    fi
}

do_configure[depends] += "opensbi:do_deploy"

do_compile:prepend:ae350-ax45mp() {
    export OPENSBI=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin
}

do_configure:prepend:ae350-ax45mp() {
    if [ -f "${WORKDIR}/tftp-mmc-boot.txt" ]; then
        sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${WORKDIR}/tftp-mmc-boot.txt
        mkimage -A riscv -O linux -T script -C none -n "U-Boot boot script" \
            -d ${WORKDIR}/tftp-mmc-boot.txt ${WORKDIR}/boot.scr.uimg
    fi
}

do_deploy:append:ae350-ax45mp() {
    if [ -f "${WORKDIR}/boot.scr.uimg" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 755 ${WORKDIR}/boot.scr.uimg ${DEPLOYDIR}
    fi

    if [ -f "${WORKDIR}/uEnv-ae350.txt" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 644 ${WORKDIR}/uEnv-ae350.txt ${DEPLOYDIR}/uEnv.txt
    fi
}

FILES:${PN}:append:freedom-u540 = " /boot/boot.scr.uimg"
