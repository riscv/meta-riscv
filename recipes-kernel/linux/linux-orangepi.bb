SUMMARY = "Orange Pi Linux Kernel"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

DEPENDS:append:orangepi-rv2 = " u-boot-tools-native"

LIC_FILES_CHKSUM:orangepi-rv2 = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

BRANCH:orangepi-rv2 = "orange-pi-6.6-ky"
SRCREV:orangepi-rv2 = "ae9e974d3e19f460b6397bfe8f0f1417a073ce05"

LINUX_VERSION:orangepi-rv2 = "6.6.63"
LINUX_VERSION_EXTENSION:append:orangepi-rv2 = "-orangepi-rv2"

PV = "${LINUX_VERSION}+git${SRCPV}"

SRC_URI = "git://github.com/orangepi-xunlong/linux-orangepi.git;protocol=https;branch=${BRANCH}"

SRC_URI:append:orangepi-rv2 = " file://disable-focaltech-touchscreen.cfg \
								file://disable-rtl8852bs.cfg \
								file://bcmdhd-fix-typedefs-header-not-found.patch"

INITRAMFS_IMAGE:orangepi-rv2 = "core-image-minimal-initramfs"

KCONFIG_MODE = "--alldefconfig"
KBUILD_DEFCONFIG:orangepi-rv2 = "x1_defconfig"

do_install:append:orangepi-rv2() {
	sed -i -e 's#${S}##g' ${B}/drivers/tty/vt/consolemap_deftbl.c
}

do_deploy:append:orangepi-rv2() {
	cd ${DEPLOY_DIR_IMAGE}
	mkimage -A riscv -O linux -T ramdisk -n "Initial Ram Disk" \
		-d ${INITRAMFS_IMAGE}-${MACHINE}.cpio.gz initramfs.img
}

COMPATIBLE_MACHINE = "(orangepi-rv2)"
