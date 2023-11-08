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
LINUXDEPLOYDIR = "${WORKDIR}/deploy-${PN}"

do_deploy:append() {
	# Generate and deploy kernel.itb (boot.sd)
	# linux.dtb
	cp ${LINUXDEPLOYDIR}/cv1800b_milkv_duo_sd.dtb ${B}
	# kernel.its
	cp ${WORKDIR}/multi.its ${B}
	# Image
	cp ${LINUXDEPLOYDIR}/Image ${B}
	# Compress Image to lzma format
	lzma -c -5 -f -k Image > Image.lzma
	# Generate kernel.itb
	mkimage -f ${B}/multi.its ${B}/kernel.itb
	# Deploy kernel.itb and Image.lzma
	install -m 744 ${B}/kernel.itb ${DEPLOYDIR}
	install -m 744 ${B}/Image.lzma ${DEPLOYDIR}
}

COMPATIBLE_MACHINE = "(milkv-duo)"
