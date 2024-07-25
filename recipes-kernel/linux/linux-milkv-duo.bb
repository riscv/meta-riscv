require linux-mainline-common.inc

SUMMARY = "Milk-V Duo mainline kernel recipe"

LINUX_VERSION ?= "6.8+"
KERNEL_VERSION_SANITY_SKIP="1"
PV = "${LINUX_VERSION}+git${SRCPV}"

BRANCH = "linux-6.8.y"
SRCREV = "v6.8.5"
SRCPV = "${@bb.fetch2.get_srcrev(d)}"
SRC_URI = " \
	git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;branch=${BRANCH} \
	file://017199c2849c3d6d417791ec1cf5521005d663a1.patch \
	file://riscv-dts-sophgo-add-sdcard-support-for-milkv-duo.patch \
	file://sophgo-add-reboot-shutdown-driver.patch \
	file://sophgo-add-ethernet-driver.patch \
	file://dts-exclude-memory-occupied-by-opensbi.patch \
	file://milkv-duo_defconfig \
	file://multi.its \
"

KERNEL_DEVICETREE:milkv-duo ?= "sophgo/cv1800b-milkv-duo.dtb"

DEPENDS = "u-boot-mkimage-native dtc-native"

do_deploy[depends] = "milkv-duo-fsbl:do_deploy"

do_deploy:append:milkv-duo() {
	cp ${B}/arch/riscv/boot/Image.gz ${B}
	cp ${WORKDIR}/multi.its ${B}
	mkimage -f ${B}/multi.its ${B}/uImage.fit
	install -m 744 ${B}/uImage.fit ${DEPLOYDIR}
	install -m 744 ${B}/arch/riscv/boot/dts/${KERNEL_DEVICETREE} ${DEPLOYDIR}/default.dtb
}

COMPATIBLE_MACHINE = "milkv-duo"
