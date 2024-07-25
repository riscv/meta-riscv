SUMMARY = "Milk-V Duo dev kernel recipe"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

DEPENDS = "u-boot-mkimage-native dtc-native"

BRANCH ?= "main"
FORK ?= "xyq1113723547"

SRC_URI = "git://github.com/${FORK}/cvitek-linux-5.10.git;protocol=https;branch=${BRANCH} \
           "

SRCREV ?= "15ea08f842174c4eff8dcc0943de6e70b0b7aa2f"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://cvitek_cv1800b_milkv_duo_sd_defconfig"
SRC_URI:append:milkv-duo = " file://multi.its"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

LINUX_VERSION ?= "5.10.4"
LINUX_VERSION_EXTENSION:append:milkv-duo = "-milkv-duo"
KERNEL_VERSION_SANITY_SKIP = "1"

KERNEL_DEVICETREE ?= "cvitek/cv1800b_milkv_duo_sd.dtb"
SDIR = "${B}/arch/riscv/boot"

do_deploy[depends] = "milkv-duo-fsbl:do_deploy"

do_deploy:append() {
	cp ${SDIR}/Image.gz ${B}
	cp ${WORKDIR}/multi.its ${B}
	mkimage -f ${B}/multi.its ${B}/uImage.fit
	install -m 744 ${B}/uImage.fit ${DEPLOYDIR}
	install -m 744 ${SDIR}/dts/${KERNEL_DEVICETREE} ${DEPLOYDIR}/default.dtb
}

COMPATIBLE_MACHINE = "(milkv-duo)"
