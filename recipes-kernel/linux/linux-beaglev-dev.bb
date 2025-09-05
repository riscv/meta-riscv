require linux-mainline-common.inc
KERNEL_FEATURES_RISCV = ""

SUMMARY = "Beagle-V Ahead dev kernel recipe"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP = "1"

#v6.14.2
# downstream patches are taken from Beagle's Kernel fork: https://openbeagle.org/beaglev-ahead/linux
# network stability is not good enough for Yocto checkout
SRCREV_kernel = "38fec10eb60d687e30c8c6b5420d86e8149f7557"
SRCREV_dts = "4396d439d680221bd3e7c3135535bd2cd7d41958"

BRANCH_kernel = "master"
BRANCH_dts = "v6.14.x"
LINUX_VERSION ?= "6.14.2"

SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;protocol=https;branch=${BRANCH_kernel};name=kernel \
    git://git.beagleboard.org/beagleboard/BeagleBoard-DeviceTrees.git;protocol=https;subdir=beaglev_devicetrees;branch=${BRANCH_dts};name=dts \
    file://defconfig \
    file://extlinux.conf \
    file://0001-dt-binding-riscv-add-T-HEAD-CPU-reset.patch \
    file://0002-th1520-add-cpu-reset-node.patch \
    file://0003-dt-bindings-reset-Add-T-HEAD-TH1520-SoC-Reset-Contro.patch \
    file://0004-reset-thead-Add-TH1520-reset-controller-driver.patch \
    "

COMPATIBLE_MACHINE = "(beaglev-ahead)"

do_configure:prepend() {
    mkdir -p ${S}/arch/riscv/boot/dts/thead/
    cp ${UNPACKDIR}/beaglev_devicetrees/src/riscv/thead/* ${S}/arch/riscv/boot/dts/thead/
}

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
    cp -f ${DEPLOYDIR}/th1520-beaglev-ahead.dtb ${DEPLOY_DIR_IMAGE}/.boot/
    cp -f ${DEPLOYDIR}/Image ${DEPLOY_DIR_IMAGE}/.boot/
    cp -f ${UNPACKDIR}/extlinux.conf ${DEPLOY_DIR_IMAGE}/.boot/extlinux/
    
    cp -f ${UNPACKDIR}/extlinux.conf ${DEPLOY_DIR_IMAGE}/extlinux_sd.conf
    sed -i 's/\/dev\/mmcblk0p3/\/dev\/mmcblk1p3/g' ${DEPLOY_DIR_IMAGE}/extlinux_sd.conf

    dd if=/dev/zero of=${DEPLOY_DIR_IMAGE}/boot.ext4 bs=1 count=0 seek=190M
    mkfs.ext4 -F ${DEPLOY_DIR_IMAGE}/boot.ext4 -d ${DEPLOY_DIR_IMAGE}/.boot
}

do_deploy[depends] += "opensbi:do_deploy"
