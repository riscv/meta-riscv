require linux-mainline-common.inc

SUMMARY = "Milk-V Duo mainline kernel recipe"

LINUX_VERSION ?= "6.17.8"
PV = "${LINUX_VERSION}+git${SRCPV}"

BRANCH = "linux-6.17.y"
SRCREV = "v6.17.8"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;protocol=https;branch=${BRANCH} \
           file://dts-exclude-memory-occupied-by-opensbi.patch \
           file://0001-sophgo-add-cv1800-rtcsys-reset-handler.patch \
           file://milkv-duo_defconfig \
           file://multi.its \
           "

KERNEL_DEVICETREE:milkv-duo ?= "sophgo/cv1800b-milkv-duo.dtb"

DEPENDS = "u-boot-mkimage-native dtc-native"

do_deploy[depends] = "milkv-duo-fsbl:do_deploy"

do_deploy:append:milkv-duo() {
	cp ${B}/arch/riscv/boot/Image.gz ${B}
	cp ${UNPACKDIR}/multi.its ${B}
	mkimage -f ${B}/multi.its ${B}/uImage.fit
	install -m 744 ${B}/uImage.fit ${DEPLOYDIR}
	install -m 744 ${B}/arch/riscv/boot/dts/${KERNEL_DEVICETREE} ${DEPLOYDIR}/default.dtb
}

COMPATIBLE_MACHINE = "milkv-duo"
