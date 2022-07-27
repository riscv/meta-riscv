require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

FILESEXTRAPATHS:prepend := "${THISDIR}/files/ae350-ax45mp:"
DEPENDS:append = " opensbi u-boot-tools-native python3-setuptools-native"

# tag: v2022.01
SRCREV="d637294e264adfeb29f390dfc393106fd4d41b17"

SRC_URI = " \
    git://git.denx.de/u-boot.git;branch=master \
    file://0001-Fix-mmc-no-partition-table-error.patch \
    file://0002-Prevent-fw_dynamic-from-relocation.patch \
    file://0003-Fix-u-boot-proper-booting-issue.patch \
    file://0004-Enable-printing-OpenSBI-boot-log.patch \
    file://u-boot.cfg \
    "

SRC_URI:append:ae350-ax45mp = " \
    file://tftp-mmc-boot.txt \
    file://uEnv-ae350.txt \
"

do_compile:prepend:ae350-ax45mp() {
    export OPENSBI=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin
}

do_configure:prepend:ae350-ax45mp() {
    if [ -f "${WORKDIR}/${UBOOT_ENV}.txt" ]; then
        sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${WORKDIR}/tftp-mmc-boot.txt
        mkimage -A riscv -O linux -T script -C none -n "U-Boot boot script" \
            -d ${WORKDIR}/${UBOOT_ENV}.txt ${WORKDIR}/boot.scr.uimg
    fi
}

do_deploy:append:ae350-ax45mp() {
    if [ -f "${WORKDIR}/boot.scr.uimg" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 755 ${WORKDIR}/boot.scr.uimg ${DEPLOY_DIR_IMAGE}
    fi

    if [ -f "${WORKDIR}/uEnv-ae350.txt" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 644 ${WORKDIR}/uEnv-ae350.txt ${DEPLOYDIR}/uEnv.txt
    fi
}

# Overwrite this for your server
TFTP_SERVER_IP ?= "127.0.0.1"

do_configure[depends] += "opensbi:do_deploy"

COMPATIBLE_MACHINE = "(ae350-ax45mp)"
