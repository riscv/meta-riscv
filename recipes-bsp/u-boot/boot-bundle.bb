DESCRIPTION = "Create FIT image bundle with opensbi, u-boot, u-boot dtb, kernel and dtb"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PROVIDES = "boot-bundle"

COMPATIBLE_MACHINE = "(k1)"

# Useful to use mainline U-Boot when it doesn't support MMC yet
# This way, everything can be loaded from the vendors U-Boot SPL
# when it loads mainline U-Boot.

DEPENDS:k1 = "u-boot-tools-native"

inherit deploy

SRC_URI = "file://boot-bundle.its"

S = "${UNPACKDIR}"

do_install[depends] += "virtual/kernel:do_deploy virtual/bootloader:do_deploy opensbi:do_deploy"

B = "${WORKDIR}/build"

do_install:k1() {
	install -d ${B}
	cd ${B}
	DTBFILE=$(basename ${KERNEL_DEVICETREE})
	install -m 0644 ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE} ${B}/
	install -m 0644 ${DEPLOY_DIR_IMAGE}/$DTBFILE ${B}/
	install -m 0644 ${DEPLOY_DIR_IMAGE}/u-boot-nodtb.bin ${B}/
	install -m 0644 ${DEPLOY_DIR_IMAGE}/u-boot.dtb ${B}/
	install -m 0644 ${DEPLOY_DIR_IMAGE}/fw_dynamic.bin ${B}/
	sed "s/KERNEL_DEVICETREE/$DTBFILE/" ${S}/boot-bundle.its > boot-bundle.its
        mkimage -f boot-bundle.its boot-bundle.itb
}

addtask deploy after do_install before do_build

do_deploy() {
	install -Dm 0644 ${B}/boot-bundle.itb ${DEPLOYDIR}/boot-bundle.itb
}
