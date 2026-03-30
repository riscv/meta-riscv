#@TYPE: SOC
#@NAME: SpacemiT K1
#@SOC: SpacemiT K1
#@DESCRIPTION: SOC configuration for the SpacemiT K1 SoC - vendor repo version

# This include is ONLY to be used for vendor-tree based builds for K1 platforms
# like the OrangePi RV2 and R2S. For mainline support, rely on k1.inc.

require conf/machine/include/riscv/tune-riscv.inc
require conf/machine/include/soc-family.inc

SOC_FAMILY = "k1"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-orangepi"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-orangepi"

UBOOT_MACHINE = "x1_defconfig"
SPL_BINARY = "spl/u-boot-spl.bin"
UBOOT_ENV = "boot"
UBOOT_ENV_SUFFIX = "scr"

RISCV_SBI_PLAT = "generic"

KERNEL_CLASSES = "kernel"
KERNEL_IMAGETYPE = "Image"

EXTRA_IMAGEDEPENDS += "u-boot-orangepi"

IMAGE_BOOT_FILES += " \
	${UBOOT_ENV}.${UBOOT_ENV_SUFFIX} \
	${KERNEL_IMAGETYPE} \
	initramfs.img"

INITRAMFS_FSTYPES = "cpio.gz"
IMAGE_FSTYPES += "wic.gz wic.bmap ext4"

WIC_CREATE_EXTRA_ARGS ?= "--no-fstab-update"

SERIAL_CONSOLES = "115200;ttyS0"
MACHINE_FEATURES = "screen keyboard ext2 ext3 serial"
