FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

BRANCH:orangepi-rv2 = "v2022.10-ky"
# Support fdt drivers for AE350
SRCREV:ae350-ax45mp = "22f38ee6c658a660083aa45c4ec6c72f66a17260"
SRCREV:jh7100 = "c6a092cd80112529cb2e92e180767ff5341b22a3"
SRCREV:star64 = "c6a092cd80112529cb2e92e180767ff5341b22a3"
SRCREV:milkv-duo = "v1.4"
SRCREV:orangepi-rv2 = "89bff4a7e4cadfb5f130edb1ec44c39bff20a427"

SRC_URI:star64 = "git://github.com/starfive-tech/opensbi;branch=JH7110_VisionFive2_devel;protocol=https"
SRC_URI:append:star64 = "\
	file://visionfive2-uboot-fit-image.its \
	"

SRC_URI:milkv-duo = "git://github.com/riscv-software-src/opensbi.git;branch=master;protocol=https"
SRC_URI:orangepi-rv2 = "git://github.com/orangepi-xunlong/u-boot-orangepi.git;protocol=https;branch=${BRANCH}"
SRC_URI:append:orangepi-rv2 = " file://c23-compatibility.patch"
S:orangepi-rv2 = "${UNPACKDIR}/${BP}/opensbi"

DEPENDS:append:jh7110 = " u-boot-tools-native dtc-native"
DEPENDS:append:orangepi-rv2 = " u-boot-tools-native"

EXTRA_OEMAKE:append:milkv-duo = "FW_FDT_PATH=${DEPLOY_DIR_IMAGE}/u-boot.dtb"
EXTRA_OEMAKE:append:orangepi-rv2 = " PLATFORM_DEFCONFIG=k1_defconfig"

_DEPS = ""
_DEPS:milkv-duo = "u-boot:do_deploy"

do_compile[depends] += "${_DEPS}"

do_deploy:append:star64() {
	install -m 0644 ${UNPACKDIR}/visionfive2-uboot-fit-image.its ${DEPLOYDIR}/visionfive2-uboot-fit-image.its
	cd ${DEPLOYDIR}
	mkimage -f visionfive2-uboot-fit-image.its -A riscv -O u-boot -T firmware visionfive2_fw_payload.img
}
