#@TYPE: Include
#@NAME: nezha-common
#@DESCRIPTION: Common configuration for Nezhar boards

require riscv-common.inc

#============================================
# Preferred Providers
PREFERRED_PROVIDER_virtual/kernel ?= "linux-allwinnerd1-dev"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-allwinnerd1"
#============================================

#============================================
# Uboot Configuration
KBUILD_DEFCONFIG = "nezha_defconfig"
UBOOT_ENTRYPOINT = "0x40200000"
UBOOT_DTB_LOADADDRESS = "0x4FA00000"
UBOOT_DTB = "1"
#============================================

#============================================
# Image Configuration
WKS_FILE_DEPENDS:append = "u-boot-allwinnerd1"
WKS_FILE ?= "nezha.wks"
EXTRA_IMAGEDEPENDS += "u-boot-allwinnerd1"
#============================================

#============================================
# SBI Configuration
#- This sets u-boot as the default OpenSBI payload
#-- Nezha board uses TOC1 images loaded by SPL and containing U-Boot, DTB, and
#-- OpenSBI so 'RISCV_SBI_PAYLOAD' isn't used.
#-- 'RISCV_SBI_FDT' isn't used because the DTB is loaded from RAM at
#-- ${fdtcontroladdr}
unset RISCV_SBI_PAYLOAD 
#RISCV_SBI_PAYLOAD ?= "u-boot.bin"
#RISCV_SBI_FDT ?= "sun20i-d1-nezha.dtb"
#============================================

#============================================
# Kernel Configuration
#-- 'KERNEL_DEVICETREE' isn't used because the DTB is loaded from RAM
#-- at address ${fdtcontroladdr}
#KERNEL_DEVICETREE ?= "allwinner/sun20i-d1-nezha.dtb"
#============================================