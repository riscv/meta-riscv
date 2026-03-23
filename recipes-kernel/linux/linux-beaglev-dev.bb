require linux-mainline-common.inc
KERNEL_FEATURES_RISCV = ""

SUMMARY = "Beagle-V Ahead dev kernel recipe"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP = "1"

inherit deploy

# Linux 6.19.9
SRCREV = "4a2b0ed2ac7abe9743e1559d212075a0ebac96b3"

BRANCH = "linux-6.19.y"
LINUX_VERSION ?= "6.19"

SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;protocol=https;branch=${BRANCH} \
    file://extlinux.conf \
    file://0001-dt-binding-riscv-add-T-HEAD-CPU-reset.patch \
    file://0002-th1520-add-cpu-reset-node.patch \
    file://0001-dt-bindings-usb-Add-T-HEAD-TH1520-USB-controller.patch \
    file://0002-usb-dwc3-add-T-HEAD-TH1520-usb-driver.patch \
    file://0003-riscv-dts-thead-Add-TH1520-USB-nodes.patch \
   "

COMPATIBLE_MACHINE = "(beaglev-ahead)"

DEPENDS += "e2fsprogs-native firmware-th1520"

# package a separate partition boot.ext4 that can be flashed via fastboot to partition boot
do_deploy:append() {
    [ -d ${DEPLOYDIR}/.boot ] && rm -rf ${DEPLOYDIR}/.boot

    if [ ! -d ${DEPLOYDIR}/.boot ]; then
        mkdir -p ${DEPLOYDIR}/.boot
    fi
    if [ ! -d ${DEPLOYDIR}/.boot/overlays ]; then
        mkdir -p ${DEPLOYDIR}/.boot/overlays
    fi
    if [ ! -d ${DEPLOYDIR}/.boot/extlinux ]; then
        mkdir -p ${DEPLOYDIR}/.boot/extlinux
    fi

    sleep 1

    cp ${DEPLOY_DIR_IMAGE}/fw_dynamic.bin ${DEPLOYDIR}/.boot/fw_dynamic.bin
    cp ${DEPLOY_DIR_IMAGE}/light_aon_fpga.bin ${DEPLOYDIR}/.boot/
    cp -f ${DEPLOYDIR}/th1520-beaglev-ahead.dtb ${DEPLOYDIR}/.boot/
    cp -f ${DEPLOYDIR}/Image ${DEPLOYDIR}/.boot/
    cp -f ${UNPACKDIR}/extlinux.conf ${DEPLOYDIR}/.boot/extlinux/
    
    cp -f ${UNPACKDIR}/extlinux.conf ${DEPLOYDIR}/extlinux_sd.conf
    sed -i 's/\/dev\/mmcblk0p3/\/dev\/mmcblk1p3/g' ${DEPLOYDIR}/extlinux_sd.conf

    dd if=/dev/zero of=${DEPLOYDIR}/boot.ext4 bs=1 count=0 seek=190M
    mkfs.ext4 -F ${DEPLOYDIR}/boot.ext4 -d ${DEPLOYDIR}/.boot
}

addtask deploy after do_compile before do_build
do_deploy[depends] += "opensbi:do_deploy"
