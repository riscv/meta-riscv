require linux-mainline-common.inc
SUMMARY = "Beagle-V Ahead dev kernel recipe"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP = "1"

SRCREV = "${AUTOREV}"

#v6.9-rc2
# downstream patches are taken from Beagle's Kernel fork: https://openbeagle.org/beaglev-ahead/linux
# network stability is not good enough for Yocto checkout
SRCREV[kernel] = "39cd87c4eb2b893354f3b850f916353f2658ae6f"
SRCREV[dts] = "aacd2722f029c588c8ce0c758e296caa56f8b947"

BRANCH_kernel = "master"
BRANCH_dts = "v6.9.x"
LINUX_VERSION ?= "6.9.0"

SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;protocol=https;branch=${BRANCH_kernel};name=kernel \
    git://git.beagleboard.org/beagleboard/BeagleBoard-DeviceTrees.git;protocol=https;subdir=beaglev_devicetrees;branch=${BRANCH_dts};name=dts \
    file://defconfig \
    file://extlinux.conf \
    file://0001-dt-bindings-reset-Document-th1520-reset-control.patch \
    file://0002-reset-Add-th1520-reset-driver-support.patch \
    file://0003-WIP-on-th_net-d3e1832c7d2d-riscv-mm-update-T-Head-me.patch \
    file://0004-dt-binding-riscv-add-T-HEAD-CPU-reset.patch \
    file://0005-th1520-add-cpu-reset-node.patch \
    file://0006-th1520-beaglev-ahead.dts-enable-ethernet0.patch \
    file://0007-v6.8-rc3-fix-reset-th1520.c-build.patch \
    "

COMPATIBLE_MACHINE = "(beaglev-ahead)"

do_configure:prepend() {
    mkdir -p ${S}/arch/riscv/boot/dts/thead/
    cp ${UNPACKDIR}/beaglev_devicetrees/src/riscv/thead/* ${S}/arch/riscv/boot/dts/thead/
}

DEPENDS += "e2fsprogs-native"

# package a separate partition boot.ext4 that can be flashed via fastboot to partition boot
do_beaglev_img_deploy() {
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

    cp ${DEPLOY_DIR_IMAGE}/fw_dynamic.bin ${DEPLOY_DIR_IMAGE}/.boot/fw_dynamic.bin
    cp -f ${DEPLOY_DIR_IMAGE}/th1520-beaglev-ahead.dtb ${DEPLOY_DIR_IMAGE}/.boot/
    cp -f ${DEPLOY_DIR_IMAGE}/Image ${DEPLOY_DIR_IMAGE}/.boot/
    cp -f ${UNPACKDIR}/extlinux.conf ${DEPLOY_DIR_IMAGE}/.boot/extlinux/

    dd if=/dev/zero of=${DEPLOY_DIR_IMAGE}/boot.ext4 bs=1 count=0 seek=190M
    mkfs.ext4 -F ${DEPLOY_DIR_IMAGE}/boot.ext4 -d ${DEPLOY_DIR_IMAGE}/.boot
}

do_beaglev_img_deploy[depends] += "opensbi:do_deploy"
addtask beaglev_img_deploy after do_populate_sysroot do_packagedata do_bundle_initramfs do_install before do_build
