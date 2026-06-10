#@TYPE: Include
#@NAME: visionfive2-common
#@DESCRIPTION: Common configuration for VisionFive 2 boards

require jh7110-common.inc

#============================================
# Common Machine Configuration
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += " \
    kernel-modules \
		"
#============================================

MACHINE_FEATURES += "efi"

#============================================
# SBI Configuration
RISCV_SBI_FDT ?= "jh7110-starfive-visionfive-2-v1.3b.dtb"
unset RISCV_SBI_PAYLOAD
#============================================

#============================================
# Kernel Configuration
KERNEL_DEVICETREE ?= "starfive/jh7110-starfive-visionfive-2-v1.3b.dtb"
KERNEL_CLASSES = "kernel"
KERNEL_IMAGETYPE = "Image"
#============================================

#============================================
# Uboot Configuration
UBOOT_DTB = ""
UBOOT_SUFFIX = "itb"
UBOOT_MACHINE = "starfive_visionfive2_defconfig"
UBOOT_DTB_BINARY = "jh7110-starfive-visionfive-2.dtb"
SPL_BINARY = "spl/u-boot-spl.bin.normal.out"
UBOOT_ENV = ""
#============================================

PREFERRED_PROVIDER_virtual/bootloader = "u-boot"
