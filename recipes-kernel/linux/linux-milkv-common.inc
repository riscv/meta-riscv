# Common infrastructure shared by mainline Milk-V kernel recipes.
# Each board recipe sets LINUX_VERSION, BRANCH, SRCREV, SRC_URI (patches +
# defconfig), and COMPATIBLE_MACHINE, then requires this file.

require linux-mainline-common.inc

KERNEL_FEATURES_RISCV = ""

DEPENDS = "u-boot-mkimage-native dtc-native"

do_deploy[depends] = "milkv-duo-fsbl:do_deploy"

do_deploy:append() {
	cp ${B}/arch/riscv/boot/Image.gz ${B}
	cp ${UNPACKDIR}/multi.its ${B}
	mkimage -f ${B}/multi.its ${B}/uImage.fit
	install -m 744 ${B}/uImage.fit ${DEPLOYDIR}
	install -m 744 ${B}/arch/riscv/boot/dts/${KERNEL_DEVICETREE} ${DEPLOYDIR}/default.dtb
}
