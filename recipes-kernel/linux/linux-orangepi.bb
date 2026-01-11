SUMMARY = "Orange Pi Linux Kernel"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

DEPENDS:append:k1 = " u-boot-tools-native"

LIC_FILES_CHKSUM:k1 = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

BRANCH:k1 = "orange-pi-6.6-ky"
SRCREV:k1 = "ae9e974d3e19f460b6397bfe8f0f1417a073ce05"

LINUX_VERSION:k1 = "6.6.63"
LINUX_VERSION_EXTENSION:append:orangepi-rv2 = "-orangepi-rv2"
LINUX_VERSION_EXTENSION:append:orangepi-r2s = "-orangepi-r2s"

PV = "${LINUX_VERSION}+git${SRCPV}"

SRC_URI = "git://github.com/orangepi-xunlong/linux-orangepi.git;protocol=https;branch=${BRANCH}"

SRC_URI:append:k1 = " file://disable-focaltech-touchscreen.cfg \
				file://disable-rtl8852bs.cfg \
				file://bcmdhd-fix-typedefs-header-not-found.patch"
SRC_URI:append:orangepi-r2s = " file://enable-r8125.cfg"

INITRAMFS_IMAGE:k1 = "core-image-minimal-initramfs"

KCONFIG_MODE = "alldefconfig"
KBUILD_DEFCONFIG:k1 = "x1_defconfig"

KERNEL_FEATURES:remove:riscv32:k1 = " ${KERNEL_FEATURES_RISCV}"
KERNEL_FEATURES:remove:riscv64:k1 = " ${KERNEL_FEATURES_RISCV}"

do_install:append:k1() {
	sed -i -e 's#${S}##g' ${B}/drivers/tty/vt/consolemap_deftbl.c
}

do_deploy:append:k1() {
	cd ${DEPLOY_DIR_IMAGE}
	mkimage -A riscv -O linux -T ramdisk -n "Initial Ram Disk" \
		-d ${INITRAMFS_IMAGE}-${MACHINE}.cpio.gz initramfs.img
}

COMPATIBLE_MACHINE = "(k1)"
