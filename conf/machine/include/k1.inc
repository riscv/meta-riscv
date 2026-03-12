#@TYPE: SOC
#@NAME: SpacemiT K1
#@SOC: SpacemiT K1
#@DESCRIPTION: SOC configuration for the SpacemiT K1 SoC

require conf/machine/include/riscv/tune-riscv.inc
require conf/machine/include/soc-family.inc

SOC_FAMILY = "k1"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-mainline-k1"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot"

KERNEL_DANGLING_FEATURES_WARN_ONLY = "1"

# Try to bring up a selection of physical or virtual serial consoles (copied
# from genericarm64)
SERIAL_CONSOLES ?= "115200;ttyAMA0 115200;hvc0 115200;ttyS0 115200;ttyS1 115200;ttyS2 115200;ttyPS1"

SPL_BINARY = ""

RISCV_SBI_PLAT = "generic"

KERNEL_CLASSES = "kernel"
KERNEL_IMAGETYPE = "Image"

EXTRA_IMAGEDEPENDS += "u-boot-spl-k1 boot-bundle"

IMAGE_FSTYPES += "wic.gz wic.bmap ext4 tar.zst"
IMAGE_CLASSES += " k1-image"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "kernel-modules"

WKS_FILE ?= "spacemit-k1.wks"
