require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

BRANCH:visionfive = "JH7100_VisionFive_OH_dev"
BRANCH:visionfive2 = "JH7110_VisionFive2_devel"
BRANCH:beaglev-starlight-jh7100 = "Fedora_JH7100_2021.04"


SRC_URI = "git://github.com/starfive-tech/u-boot.git;protocol=https;branch=${BRANCH} \
           file://tftp-mmc-boot.txt \
          "

SRC_URI:append:beaglev-starlight-jh7100 = " \
           file://977abc529f98c1c90a80ad280fe9e58ddd43c87a.patch \
           file://2feaab2bd04ed736c637518b3b553615f0c97890.patch \
           "

SRC_URI:append:visionfive = " \
           file://fix-riscv-isa.patch \
           file://uEnv-visionfive.txt \
"
SRC_URI:append:visionfive2 = " \
           file://0001-riscv-fix-build-with-binutils-2.38.patch \
           file://uEnv-visionfive2.txt \
"

SRCREV = "7b70e1d44ba9702a519ca936cabf19070309123a"
SRCREV:visionfive = "ccecef294d355e9d05edf0bb6058002a0fe08908"

# tag VF2_v2.6.0
SRCREV:visionfive2 = "66a72185a813c36b8975fd7ded9d74d6a5525db7"

DEPENDS:append = " u-boot-tools-native"

# Overwrite this for your server
TFTP_SERVER_IP ?= "127.0.0.1"

do_configure:prepend() {
    sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${WORKDIR}/tftp-mmc-boot.txt
    mkimage -O linux -T script -C none -n "U-Boot boot script" \
        -d ${WORKDIR}/tftp-mmc-boot.txt ${WORKDIR}/${UBOOT_ENV_BINARY}
}

do_deploy:append:visionfive() {
    install -m 644 ${WORKDIR}/uEnv-visionfive.txt ${DEPLOYDIR}/uEnv.txt
}

do_deploy:append:visionfive2() {
    install -m 644 ${WORKDIR}/uEnv-visionfive2.txt ${DEPLOYDIR}/uEnv.txt
}

COMPATIBLE_MACHINE = "(beaglev-starlight-jh7100|visionfive|visionfive2)"

TOOLCHAIN = "gcc"

# U-boot sets O=... which needs it to build outside of S
B = "${WORKDIR}/build"

