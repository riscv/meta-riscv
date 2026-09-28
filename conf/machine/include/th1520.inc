#@TYPE: SOC
#@NAME: T-Head 1520 SoC
#@SOC: T-Head 1520
#@DESCRIPTION: SOC configuration for the Alibaba T-Head 1520 SoC with 4 Xuantie C910 cores

require riscv-common.inc
require conf/machine/include/soc-family.inc

SOC_FAMILY = "th1520"

WKS_FILE ?= "th1520.wks"
WKS_FILE_DEPENDS:append = " gptfdisk-native u-boot"

# SBI Configuration
unset RISCV_SBI_PAYLOAD

# U-Boot configuration
UBOOT_ENV = ""
UBOOT_EXTLINUX = "1"
UBOOT_EXTLINUX_CONSOLE ?= ""
UBOOT_EXTLINUX_KERNEL_ARGS ?= "ro rootfstype=ext4 rootwait console=ttyS0,115200 earlycon clk_ignore_unused"
UBOOT_EXTLINUX_ROOT ?= "root=/dev/${rootdev}p2"
UBOOT_EXTLINUX_KERNEL_IMAGE ?= "/Image"

# Kernel configuration
KERNEL_IMAGETYPE = "Image"
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS:append = " kernel-modules"

# Common Preferred Providers
PREFERRED_PROVIDER_virtual/kernel ?= "linux-mainline"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot"
EXTRA_IMAGEDEPENDS += "u-boot firmware-th1520"


