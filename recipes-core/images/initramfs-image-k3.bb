SUMMARY = "K3 Board Custom Initramfs"
DESCRIPTION = "Custom initramfs with init script for mounting rootfs and switching root"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

export IMAGE_BASENAME = "initramfs-image"

PACKAGE_INSTALL = " \
    busybox \
    base-passwd \
    udev \
    util-linux \
    util-linux-switch-root \
    e2fsprogs \
    e2fsprogs-e2fsck \
    e2fsprogs-resize2fs \
    mtd-utils \
    kmod \
    esos-k3 \
    init-k3 \
"

inherit core-image

IMAGE_FSTYPES = "cpio.gz"
IMAGE_ROOTFS_SIZE = "8192"
IMAGE_ROOTFS_EXTRA_SPACE = "0"

COMPATIBLE_MACHINE = "(k3)"
