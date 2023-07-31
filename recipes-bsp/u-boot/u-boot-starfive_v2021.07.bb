require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

BRANCH:visionfive = "JH7100_VisionFive_OH_dev"
BRANCH:beaglev-starlight-jh7100 = "Fedora_JH7100_2021.07"

SRC_URI = "git://github.com/starfive-tech/u-boot.git;protocol=https;branch=${BRANCH} \
           file://tftp-mmc-boot.txt \
          "

#SRC_URI:append:beaglev-starlight-jh7100 = " \
#           file://0001-starfive-beaglev-enable-RISCV_TIMER-in-SMODE.patch \
#"

SRC_URI:append:visionfive = " \
           file://fix-riscv-isa.patch \
           file://uEnv-visionfive.txt \
"

SRCREV = "386e78f4a791c06cb34e805e4095a8d4600d5551"
SRCREV:visionfive = "ccecef294d355e9d05edf0bb6058002a0fe08908"

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

COMPATIBLE_MACHINE = "(beaglev-starlight-jh7100|visionfive)"

TOOLCHAIN = "gcc"

# U-boot sets O=... which needs it to build outside of S
B = "${WORKDIR}/build"
