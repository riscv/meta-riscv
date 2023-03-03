FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# Support fdt drivers for AE350
SRCREV:ae350-ax45mp = "22f38ee6c658a660083aa45c4ec6c72f66a17260"

SRC_URI:append:visionfive2 = "\
	file://visionfive2-uboot-fit-image.its \
	"

DEPENDS:visionfive2:append = " u-boot-tools-native dtc-native"

do_deploy:append:visionfive2() {
	install -m 0644 ${WORKDIR}/visionfive2-uboot-fit-image.its ${DEPLOYDIR}/visionfive2-uboot-fit-image.its
	cd ${DEPLOYDIR}
	mkimage -f visionfive2-uboot-fit-image.its -A riscv -O u-boot -T firmware visionfive2_fw_payload.img
}
