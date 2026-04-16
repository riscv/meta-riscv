#@TYPE: SOC
#@NAME: Sophgo CV18XX/SG200X
#@DESCRIPTION: Common config for Milk-V boards based on Sophgo CV18XX/SG200X SoCs

require conf/machine/include/riscv/tune-riscv.inc

MACHINE_FEATURES = "screen keyboard ext2 ext3 serial"

KERNEL_CLASSES = "kernel"
KERNEL_IMAGETYPE = "Image.gz"

PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot"

SERIAL_CONSOLES = "115200;ttyS0"

IMAGE_FSTYPES += "wic.gz wic.bmap ext4"

## Do not update fstab file when using wic images
WIC_CREATE_EXTRA_ARGS ?= "--no-fstab-update"

# OpenSBI generic platform for T-Head C906
RISCV_SBI_PLAT = "generic"

# Boot chain: FSBL builds BL2 + composes fip.bin (OpenSBI + U-Boot)
EXTRA_IMAGEDEPENDS += "milkv-duo-fsbl"

# Boot partition contents
# fip.bin is also placed raw by WKS rawcopy but must be listed here
# for WIC to find it in the deploy directory
IMAGE_BOOT_FILES ?= " \
                    default.dtb \
                    fip.bin \
                    uEnv.txt \
                    uImage.fit \
                    "


