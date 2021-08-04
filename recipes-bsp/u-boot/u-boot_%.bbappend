FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:freedom-u540 = " \
            file://tftp-mmc-boot.txt \
           "
SRC_URI:append:freedom-u540_sota = " file://uEnv.txt"

DEPENDS:append:freedom-u540 = " u-boot-tools-native"

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

FILES:${PN}:append:freedom-u540 = " /boot/boot.scr.uimg"
