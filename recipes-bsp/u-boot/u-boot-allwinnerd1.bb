require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"


SRC_URI = " \
    git://github.com/smaeul/u-boot.git;protocol=https;branch=d1-wip \
    file://tftp-mmc-boot.txt \
    file://uEnv-nezha.txt \
    file://0001-sun20i-set-CONFIG_SYS_BOOTM_LEN.patch \
"
SRCREV = "528ae9bc6c55edd3ffe642734b4132a8246ea777"

DEPENDS:append = " \
    u-boot-tools-native \
    python3-setuptools-native \
"


# Overwrite this for your server
TFTP_SERVER_IP ?= "127.0.0.1"

do_compile[depends] = "opensbi:do_deploy"

do_configure:prepend() {
    sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${WORKDIR}/tftp-mmc-boot.txt
    mkimage -O linux -T script -C none -n "U-Boot boot script" \
        -d ${WORKDIR}/tftp-mmc-boot.txt ${WORKDIR}/${UBOOT_ENV_BINARY}
}

do_compile:prepend() {
    cp ${DEPLOY_DIR_IMAGE}/fw_dynamic.bin ${B}/fw_dynamic.bin
    export OPENSBI=${B}/fw_dynamic.bin
}

do_deploy:append() {
    install -m 644 ${B}/u-boot-sunxi-with-spl.bin ${DEPLOYDIR}
    install -m 644 ${WORKDIR}/uEnv-nezha.txt ${DEPLOYDIR}/uEnv.txt
}

TOOLCHAIN = "gcc"
## Should be overwritten in machine conf
UBOOT_MACHINE ?= "allwinner_defconfig"
COMPATIBLE_MACHINE = "(nezha-allwinner-d1|mangopi-mq-pro)"
