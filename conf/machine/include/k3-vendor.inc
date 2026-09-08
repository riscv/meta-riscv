#@TYPE: SOC
#@NAME: Spacemit K3
#@SOC: Spacemit K3
#@DESCRIPTION: SoC configuration for the Spacemit K3 - vendor repo version

require conf/machine/include/riscv-common.inc
require conf/machine/include/soc-family.inc

SOC_FAMILY = "k3"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-k3"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-k3"
PREFERRED_PROVIDER_opensbi ?= "opensbi-k3"

SERIAL_CONSOLES = "115200;ttyS0"

# Add esos-k3 firmware as image dependency
EXTRA_IMAGEDEPENDS += "esos-k3 u-boot-k3 u-boot-spl-spacemit"

UBOOT_MACHINE = "k3_defconfig"

KERNEL_IMAGETYPE = "Image"

IMAGE_FSTYPES = "ext4 wic.bmap wic.gz"
IMAGE_CLASSES += " k3-vendor-image"
INITRAMFS_FSTYPES = "cpio.gz"
WKS_FILE ?= "spacemit-k3.wks"
WKS_FILE_DEPENDS:append = " u-boot-k3"

MACHINE_FEATURES:append = " usbhost usbgadget"

MACHINE_EXTRA_RRECOMMENDS += "\
        linux-firmware-rtl8851 \
        linux-firmware-rtl8852 \
        linux-firmware-rtl8922 \
        "
