require linux-mainline-common.inc
SUMMARY = "Beagle-V Ahead dev kernel recipe"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP = "1"

# LPI4A (latest work and should support beaglev-ahead but looks broken ATM)
#SRCREV_kernel = "b9cf70c75d2b7482195a94e754d59f8cfc9dda2c"
#BRANCH_kernel = "LPI4A"

# MERGED_AHEAD
SRCREV_kernel = "75644d241ab7f78288f9591dbc1efafbbec29761"
BRANCH_kernel = "merged-ahead"

LINUX_VERSION ?= "5.10"
KERNEL_DEVICETREE = "thead/light-beagle.dtb"

SRC_URI = " \
    git://github.com/revyos/thead-kernel.git;protocol=https;branch=${BRANCH_kernel};name=kernel \
    file://defconfig \
    file://extlinux.conf \
    "

COMPATIBLE_MACHINE = "(beaglev-ahead)"

DEPENDS += "e2fsprogs-native"

# package a separate partition boot.ext4 that can be flashed via fastboot to partition boot
do_deploy:append() {
    [ -d ${DEPLOY_DIR_IMAGE}/.boot ] && rm -rf ${DEPLOY_DIR_IMAGE}/.boot

    if [ ! -d ${DEPLOY_DIR_IMAGE}/.boot ]; then
        mkdir -p ${DEPLOY_DIR_IMAGE}/.boot
    fi
    if [ ! -d ${DEPLOY_DIR_IMAGE}/.boot/overlays ]; then
        mkdir -p ${DEPLOY_DIR_IMAGE}/.boot/overlays
    fi
    if [ ! -d ${DEPLOY_DIR_IMAGE}/.boot/extlinux ]; then
        mkdir -p ${DEPLOY_DIR_IMAGE}/.boot/extlinux
    fi

    sleep 1

    cp ${DEPLOY_DIR_IMAGE}/fw_dynamic.bin ${DEPLOY_DIR_IMAGE}/.boot/fw_dynamic.bin
    cp -f ${DEPLOYDIR}/light-beagle.dtb ${DEPLOY_DIR_IMAGE}/.boot/
    cp -f ${DEPLOYDIR}/Image ${DEPLOY_DIR_IMAGE}/.boot/
    cp -f ${WORKDIR}/extlinux.conf ${DEPLOY_DIR_IMAGE}/.boot/extlinux/

    dd if=/dev/zero of=${DEPLOY_DIR_IMAGE}/boot.ext4 bs=1 count=0 seek=190M
    mkfs.ext4 -F ${DEPLOY_DIR_IMAGE}/boot.ext4 -d ${DEPLOY_DIR_IMAGE}/.boot
}

do_deploy[depends] += "opensbi:do_deploy"

# CF
#INSANE_SKIP:${PN} += "pathtest"
INSANE_SKIP:${PN}-src += "buildpaths"