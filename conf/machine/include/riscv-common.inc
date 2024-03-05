#@DESCRIPTION: Machine configuration for the beta BeagleV Starlight board

require conf/machine/include/riscv/tune-riscv.inc

#============================================
# Common Linux Kernel & Uboot Configuration
KERNEL_CLASSES = "kernel-fitimage"
KERNEL_IMAGETYPE = "fitImage"
UBOOT_ENV ?= "boot"
UBOOT_ENV_SUFFIX = "scr.uimg"
#============================================

#============================================
# Common Preferred Versions
PREFERRED_VERSION_openocd-native = "riscv"
PREFERRED_VERSION_openocd = "riscv"
#============================================

#============================================
# Common Image Configuration
EXTRA_IMAGEDEPENDS += "opensbi"
WIC_CREATE_EXTRA_ARGS ?= "--no-fstab-update"
WKS_FILE_DEPENDS ?= " \
    opensbi \
    e2fsprogs-native \
    bmaptool-native \
"
IMAGE_FSTYPES += "wic.gz wic.bmap ext4"
IMAGE_BOOT_FILES ??= " \
    ${KERNEL_IMAGETYPE} \
    boot.scr.uimg \
    uEnv.txt \
"
#============================================

#============================================
# Common Hardware Configuration
SERIAL_CONSOLES = "115200;ttyS0"
MACHINE_FEATURES = "screen keyboard ext2 ext3 serial"
MACHINE_EXTRA_RRECOMMENDS += " kernel-modules"
#============================================

#============================================
# Common SBI Configuration
RISCV_SBI_PLAT = "generic"
RISCV_SBI_PAYLOAD ?= "u-boot.bin"
#============================================
