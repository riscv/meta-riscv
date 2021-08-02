DESCRIPTION = "initramfs image to be used together with SiFive Unleashed board \
as the board currently only supports booting from a ramdisk image"

# Do not pollute the initrd image with rootfs features
IMAGE_FEATURES = ""

export IMAGE_BASENAME = "${MLPREFIX}riscv-initramfs-image"
IMAGE_LINGUAS = ""

LICENSE = "MIT"

IMAGE_FSTYPES += "${INITRAMFS_FSTYPES}"
inherit core-image

IMAGE_ROOTFS_SIZE = "8192"
IMAGE_ROOTFS_EXTRA_SPACE = "0"

BAD_RECOMMENDATIONS += "busybox-syslog"

# WIC is not compatible with an initramfs image, also enabling WIC would cause
# an circular dependency.
IMAGE_FSTYPES:remove = " wic wic.gz"
