FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# Support fdt drivers for AE350
SRCREV:ae350-ax45mp = "22f38ee6c658a660083aa45c4ec6c72f66a17260"
SRCREV:jh7100 = "568ea49490d935e01b9a41ef8bb5daf9cb0ef201"

SRC_URI:append:jh7110 = "\
	file://visionfive2-uboot-fit-image.its \
	"

DEPENDS:append:jh7110 = " u-boot-tools-native dtc-native"
EXTRA_OEMAKE:jh7110:append = " FW_TEXT_START=0x40000000"

do_deploy:append:jh7110() {
	install -m 0644 ${WORKDIR}/visionfive2-uboot-fit-image.its ${DEPLOYDIR}/visionfive2-uboot-fit-image.its
	cd ${DEPLOYDIR}
	mkimage -f visionfive2-uboot-fit-image.its -A riscv -O u-boot -T firmware visionfive2_fw_payload.img
}
