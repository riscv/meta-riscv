HOMEPAGE = "https://gitee.com/bianbu-linux/uboot-2022.10"
DESCRIPTION = "U-Boot SPL for SpacemiT SoCs, derived from U-Boot"
SECTION = "bootloaders"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

inherit deploy

DEPENDS += "flex-native bison-native python3-setuptools-native"

COMPATIBLE_MACHINE = "(k1|k3)"

SRC_URI    = "git://github.com/spacemit-com/uboot-2022.10.git;protocol=https;branch=${BRANCH}"
BRANCH:k1  ?= "k1-bl-v2.2.y"
BRANCH:k3  ?= "k3-br-v1.0.y"
SRCREV:k1  ?= "c6f2746cb7993a6fb6c9f51b2bff318921e13f98"
SRCREV:k3  ?= "6747f87ae4cd359ff6e22daa38b06c3ecc2fecb4"

UBOOT_SPL_MACHINE:k1 ?= "k1_defconfig"
UBOOT_SPL_MACHINE:k3 ?= "k3_defconfig"

EXTRA_OEMAKE = "\
    CROSS_COMPILE=${TARGET_PREFIX} \
    ARCH=riscv \
    HOSTCC='${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}' \
    HOSTCXX='${BUILD_CXX}' \
"
EXTRA_OEMAKE:append = " HOSTCFLAGS_rsa-sign.o=-DOPENSSL_ENGINE_STUBS -Wno-deprecated-declarations"

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
}

do_deploy:append:k1() {
        install -m 644 ${B}/bootinfo_emmc.bin ${DEPLOYDIR}/
        install -m 644 ${B}/bootinfo_sd.bin ${DEPLOYDIR}/
}

do_deploy:append:k3() {
	install -m 644 ${B}/bootinfo_block.bin ${DEPLOYDIR}/
}

addtask deploy before do_build after do_compile
