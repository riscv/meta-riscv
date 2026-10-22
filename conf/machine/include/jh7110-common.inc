#@TYPE: SOC
#@NAME: StarFive JH7110
#@SOC: StarFive JH7110
#@DESCRIPTION: Common config for boards based on the StarFive JH7110

require jh7110.inc

#============================================
# Common Preferred Providers
PREFERRED_PROVIDER_virtual/kernel ?= "linux-starfive-dev"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-starfive"

# The PowerVR & VPU stack only works with linux-starfive-dev, so it is set
# through an override that exists only while that is the kernel.
MACHINEOVERRIDES =. "${@'jh7110-vendor:' if d.getVar('PREFERRED_PROVIDER_virtual/kernel') == 'linux-starfive-dev' else ''}"

PREFERRED_PROVIDER_virtual/libgl:jh7110-vendor ?= "mesa-pvr"
PREFERRED_PROVIDER_virtual/libglx:jh7110-vendor ?= "mesa-pvr"
PREFERRED_PROVIDER_virtual/mesa:jh7110-vendor ?= "mesa-pvr"
PREFERRED_PROVIDER_virtual/libgbm:jh7110-vendor ?= "mesa-pvr"
PREFERRED_PROVIDER_virtual/egl:jh7110-vendor ?= "mesa-pvr"
PREFERRED_PROVIDER_virtual/libgles3:jh7110-vendor ?= "visionfive2-pvr-graphics"
PREFERRED_PROVIDER_virtual/libgles1:jh7110-vendor ?= "visionfive2-pvr-graphics"
PREFERRED_PROVIDER_virtual/libgles2:jh7110-vendor ?= "visionfive2-pvr-graphics"
PREFERRED_PROVIDER_virtual/libomxil:jh7110-vendor ?= "libsf-omxil"
#============================================

#============================================
# Common Machine Configuration
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += " \
    kernel-modules \
"
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS:append:jh7110-vendor = " \
    linux-firmware-visionfive2-imggpu \
    vdec-module \
    venc-module \
    jpu-module \
    visionfive2-pvr-graphics-tools \
"
#============================================

#============================================
# Common Kernel Configuration
KERNEL_MODULE_AUTOLOAD:append:jh7110-vendor = " pvrsrvkm"
#============================================

# opensbi config
RISCV_SBI_FW_TEXT_START = "0x40000000"

#============================================
# Common Uboot Configuration
EXTRA_IMAGEDEPENDS += "virtual/bootloader"
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
