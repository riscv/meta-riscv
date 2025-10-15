SUMMARY = "Orange Pi Mainline Linux Kernel"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

DEPENDS:append:orangepi-rv2-mainline = " u-boot-tools-native"

LIC_FILES_CHKSUM:orangepi-rv2-mainline = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

BRANCH:orangepi-rv2-mainline = "master"
SRCREV:orangepi-rv2-mainline = "9b332cece987ee1790b2ed4c989e28162fa47860"

LINUX_VERSION:orangepi-rv2-mainline = "6.18-rc1"
LINUX_VERSION_EXTENSION:append:orangepi-rv2-mainline = "-orangepi-rv2"

# Disable do_kernel_configcheck, failing on 6.18+ (not fixed yet on Oct. 15, 2025)
KMETA_AUDIT = ""

PV = "${LINUX_VERSION}+git${SRCPV}"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;protocol=https;branch=${BRANCH}"

INITRAMFS_IMAGE:orangepi-rv2-mainline = "core-image-minimal-initramfs"

KCONFIG_MODE = "alldefconfig"
KBUILD_DEFCONFIG:orangepi-rv2-mainline = "defconfig"

do_deploy:append:orangepi-rv2-mainline() {
	cd ${DEPLOY_DIR_IMAGE}
	mkimage -A riscv -O linux -T ramdisk -n "Initial Ram Disk" \
		-d ${INITRAMFS_IMAGE}-${MACHINE}.cpio.gz initramfs.img
}

COMPATIBLE_MACHINE = "(orangepi-rv2-mainline)"
