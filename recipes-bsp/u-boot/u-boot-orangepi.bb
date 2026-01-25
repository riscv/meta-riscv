require recipes-bsp/u-boot/u-boot.inc
require recipes-bsp/u-boot/u-boot-common.inc

DEPENDS += "u-boot-tools-native"
DEPENDS:append:k1 = " opensbi"

LIC_FILES_CHKSUM:k1 = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

BRANCH:k1 = "v2022.10-ky"
SRCREV:k1 = "89bff4a7e4cadfb5f130edb1ec44c39bff20a427"

SRC_URI = "git://github.com/orangepi-xunlong/u-boot-orangepi.git;protocol=https;branch=${BRANCH}"
SRC_URI:append:k1 = " file://drop-intree-opensbi-build.patch"

SRC_URI:append:orangepi-rv2 = " file://boot.cmd"
SRC_URI:append:orangepi-r2s = " file://boot.cmd"

SRC_URI:remove:riscv32:k1 = " ${SRC_URI_RISCV}"
SRC_URI:remove:riscv64:k1 = " ${SRC_URI_RISCV}"

do_configure:prepend:k1() {
	mkimage -A riscv -O linux -T script -C none -n "U-Boot boot script" \
		-d ${UNPACKDIR}/${UBOOT_ENV}.${UBOOT_ENV_SRC_SUFFIX} ${UNPACKDIR}/${UBOOT_ENV_BINARY}
}

do_deploy:append:orangepi-rv2() {
	install -m 644 ${S}/bootinfo_sd.bin ${DEPLOYDIR}/
	cp ${DEPLOY_DIR_IMAGE}/fw_dynamic.bin ${B}/
	cp ${B}/arch/riscv/dts/x1_orangepi-rv2.dtb ${B}/
	# u-boot-nodtb.bin also needed for .itb but already in ${B}
	cp ${S}/uboot-opensbi.its ${B}/
	cd ${B}
	mkimage -f uboot-opensbi.its -r u-boot-opensbi.itb
	install -m 644 u-boot-opensbi.itb ${DEPLOYDIR}/
}

do_deploy:append:orangepi-r2s() {
	install -m 644 ${S}/bootinfo_sd.bin ${DEPLOYDIR}/
	cp ${DEPLOY_DIR_IMAGE}/fw_dynamic.bin ${B}/
	cp ${B}/arch/riscv/dts/x1_orangepi-rv2.dtb ${B}/
	# u-boot-nodtb.bin also needed for .itb but already in ${B}
	cp ${S}/uboot-opensbi.its ${B}/
	cd ${B}
	mkimage -f uboot-opensbi.its -r u-boot-opensbi.itb
	install -m 644 u-boot-opensbi.itb ${DEPLOYDIR}/
}
do_deploy[depends] += "opensbi:do_deploy"

COMPATIBLE_MACHINE = "(orangepi-rv2|orangepi-r2s)"
