HOMEPAGE = "https://gitee.com/bianbu-linux/uboot-2022.10"
DESCRIPTION = "U-Boot SPL for SpacemiT K1 SoCs, derived from U-Boot"
SECTION = "bootloaders"
DEPENDS += "flex-native bison-native python3-setuptools-native"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

inherit deploy

SRC_URI = "git://gitee.com/bianbu-linux/uboot-2022.10;protocol=https;branch=k1-bl-v2.2.y"
SRCREV = "c6f2746cb7993a6fb6c9f51b2bff318921e13f98"

UBOOT_SPL_MACHINE ??= "k1_defconfig"

EXTRA_OEMAKE = "\
    CROSS_COMPILE=${TARGET_PREFIX} \
    ARCH=riscv \
    HOSTCC='${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}' \
    HOSTCXX='${BUILD_CXX}' \
"

do_configure() {
	oe_runmake ${UBOOT_SPL_MACHINE}
}

do_compile() {
    LIBGCC_PATH=$(${CC} ${CFLAGS} ${LDFLAGS} -print-libgcc-file-name)
    oe_runmake PLATFORM_LIBGCC="${LIBGCC_PATH}" spl/u-boot-spl.bin
}

do_deploy() {
	install -d ${DEPLOYDIR}
	install -m 644 ${B}/FSBL.bin ${DEPLOYDIR}/
	install -m 644 ${B}/spl/u-boot-spl.bin ${DEPLOYDIR}/
	install -m 644 ${B}/bootinfo_emmc.bin ${DEPLOYDIR}/
	install -m 644 ${B}/bootinfo_sd.bin ${DEPLOYDIR}/
}

addtask deploy before do_build after do_compile

COMPATIBLE_MACHINE = "(k1)"
