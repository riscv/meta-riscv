#@TYPE: SOC
#@NAME: StarFive JH7110
#@SOC: StarFive JH7110
#@DESCRIPTION: SOC configuration for the StarFive JH7110

require conf/machine/include/riscv/tune-riscv.inc
require conf/machine/include/soc-family.inc

MACHINE_FEATURES = "screen keyboard ext2 ext3 serial"
SOC_FAMILY = "jh7110"

UBOOT_ENV ?= "boot"
UBOOT_ENV_SUFFIX = "scr.uimg"
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

PREFERRED_VERSION_openocd-native = "riscv"
PREFERRED_VERSION_openocd = "riscv"

RISCV_SBI_PLAT = "generic"

## This sets u-boot as the default OpenSBI payload
RISCV_SBI_PAYLOAD ?= "u-boot.bin"

SERIAL_CONSOLES = "115200;ttyS0"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += " \
    kernel-modules \
    linux-firmware-visionfive2-imggpu \
    vdec-module \
    venc-module \
    jpu-module \
    visionfive2-pvr-graphics-tools \
"

KERNEL_MODULE_AUTOLOAD += "pvrsrvkm"

IMAGE_FSTYPES += "wic.gz wic.bmap ext4"

## Do not update fstab file when using wic images
WIC_CREATE_EXTRA_ARGS ?=  "--no-fstab-update"

EXTRA_IMAGEDEPENDS += "u-boot-starfive"

KERNEL_IMAGETYPE ?= "fitImage"
KERNEL_CLASSES = "kernel-fitimage"
UBOOT_ENTRYPOINT = "0x40200000"
UBOOT_DTB_LOADADDRESS = "0x46000000"
UBOOT_RD_LOADADDRESS = "0x46100000"
UBOOT_DTB = "1"

SPL_BINARY = "spl/u-boot-spl.bin"
SPL_NORMAL_BINARY = "u-boot-spl.bin.normal.out"

## wic default support
WKS_FILE_DEPENDS ?= " \
    opensbi \
    e2fsprogs-native \
    bmap-tools-native \
"

IMAGE_BOOT_FILES ?= " \
    fw_payload.bin \
    ${KERNEL_IMAGETYPE} \
    ${RISCV_SBI_FDT}  \
    boot.scr.uimg \
    vf2_uEnv.txt \
"

WKS_FILE ?= "visionfive2.wks"
