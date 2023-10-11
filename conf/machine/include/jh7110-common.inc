#@TYPE: SOC
#@NAME: StarFive JH7110
#@SOC: StarFive JH7110
#@DESCRIPTION: Common config for boards based on the StarFive JH7110

require jh7110.inc

#============================================
# Common Preferred Providers
PREFERRED_PROVIDER_virtual/kernel ?= "linux-starfive-dev"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-starfive"
PREFERRED_PROVIDER_virtual/libgl ?= "mesa-pvr"
PREFERRED_PROVIDER_virtual/libglx ?= "mesa-pvr"
PREFERRED_PROVIDER_virtual/mesa ?= "mesa-pvr"
PREFERRED_PROVIDER_virtual/libgbm ?= "mesa-pvr"
PREFERRED_PROVIDER_virtual/egl ?= "mesa-pvr"
PREFERRED_PROVIDER_virtual/libgles3 ?= "visionfive2-pvr-graphics"
PREFERRED_PROVIDER_virtual/libgles1 ?= "visionfive2-pvr-graphics"
PREFERRED_PROVIDER_virtual/libgles2 ?= "visionfive2-pvr-graphics"
PREFERRED_PROVIDER_virtual/libomxil ?= "libsf-omxil"
#============================================

#============================================
# Common Machine Configuration
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += " \
    kernel-modules \
    linux-firmware-visionfive2-imggpu \
    vdec-module \
    venc-module \
    jpu-module \
    visionfive2-pvr-graphics-tools \
"
#============================================

#============================================
# Common Kernel Configuration
KERNEL_MODULE_AUTOLOAD += "pvrsrvkm"
#============================================

#============================================
# Common Uboot Configuration
EXTRA_IMAGEDEPENDS += "u-boot-starfive"
UBOOT_ENTRYPOINT = "0x40200000"
UBOOT_DTB_LOADADDRESS = "0x46000000"
UBOOT_RD_LOADADDRESS = "0x46100000"
UBOOT_DTB = "1"
SPL_BINARY = "spl/u-boot-spl.bin"
SPL_NORMAL_BINARY = "u-boot-spl.bin.normal.out"
#============================================

#============================================
# Common Image Configuration
IMAGE_BOOT_FILES = " \
    fw_payload.bin \
    ${KERNEL_IMAGETYPE} \
    ${RISCV_SBI_FDT}  \
    boot.scr.uimg \
    vf2_uEnv.txt \
"
WKS_FILE ?= "visionfive2.wks"
#============================================