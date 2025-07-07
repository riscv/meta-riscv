require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"


SRC_URI = " \
    git://github.com/beagleboard/beaglev-ahead-u-boot.git;protocol=https;branch=beaglev-v2020.01-1.1.2-ubuntu \
    file://0001-Set-sec_library-path-to-source-location.patch \
    file://0002-Fix-errors-for-GCC-14-compatibility.patch \
    file://tftp-mmc-boot.txt \
    file://uEnv-beaglev-ahead.txt \
"

SRCREV = "85565d543633e5532d727039e1f880117c74f0a8"

DEPENDS:append = " \
    u-boot-tools-native \
    python3-setuptools-native \
"

# Overwrite this for your server
TFTP_SERVER_IP ?= "127.0.0.1"

do_configure:prepend() {
    sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${WORKDIR}/tftp-mmc-boot.txt
    mkimage -O linux -T script -C none -n "U-Boot boot script" \
        -d ${WORKDIR}/tftp-mmc-boot.txt ${WORKDIR}/${UBOOT_ENV_BINARY}
}

do_install() {
    echo "nothing to do"
}

do_deploy() {
    install -m 644 ${B}/u-boot-with-spl.bin ${DEPLOYDIR}
    install -m 644 ${WORKDIR}/uEnv-beaglev-ahead.txt ${DEPLOYDIR}/uEnv.txt
}

TOOLCHAIN = "gcc"
COMPATIBLE_MACHINE = "(beaglev-ahead)"