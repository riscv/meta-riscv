SUMMARY = "Orange Pi Mainline Linux Kernel"

require recipes-kernel/linux/linux-mainline-common.inc

DEPENDS:append = " u-boot-tools-native"

BRANCH = "master"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;protocol=https;branch=${BRANCH}"
SRCREV = "ac3fd01e4c1efce8f2c054cdeb2ddd2fc0fb150d"
LINUX_VERSION = "6.18-rc7"
KERNEL_DANGLING_FEATURES_WARN_ONLY = "1"

INITRAMFS_IMAGE = "core-image-minimal-initramfs"
KBUILD_DEFCONFIG = "defconfig"

do_deploy:append() {
	cd ${DEPLOY_DIR_IMAGE}
	mkimage -A riscv -O linux -T ramdisk -n "Initial Ram Disk" \
		-d ${INITRAMFS_IMAGE}-${MACHINE}.cpio.gz initramfs.img
}

COMPATIBLE_MACHINE = "(orangepi-rv2-mainline)"
