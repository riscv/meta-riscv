#@TYPE: SOC
#@NAME: Spacemit K3
#@SOC: Spacemit K3
#@DESCRIPTION: Common config for boards based on the Spacemit K3

require conf/machine/include/riscv-common.inc

# Preferred versions
PREFERRED_VERSION_linux-k3 ?= "6.18"

PREFERRED_VERSION_u-boot-k3 ?= "2022.10"
UBOOT_MACHINE = "k3_defconfig"

PREFERRED_VERSION_opensbi-k3 ?= "1.4"
#PREFERRED_VERSION_mesa ?= "24.0%"

#============================================
# Common Preferred Providers
PREFERRED_PROVIDER_virtual/kernel ?= "linux-k3-dev"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-k3"
PREFERRED_PROVIDER_opensbi ?= "opensbi-k3"
PREFERRED_PROVIDER_virtual/libgl ?= "mesa-k3"
PREFERRED_PROVIDER_virtual/libglx ?= "mesa-k3"
PREFERRED_PROVIDER_virtual/mesa ?= "mesa-k3"
PREFERRED_PROVIDER_virtual/libgbm ?= "mesa-k3"
PREFERRED_PROVIDER_virtual/egl ?= "mesa-k3"
PREFERRED_PROVIDER_virtual/libgles1 ?= "mesa-k3"
PREFERRED_PROVIDER_virtual/libgles2 ?= "mesa-k3"
PREFERRED_PROVIDER_virtual/libgles3 ?= "mesa-k3"
#============================================

#============================================
# Common Machine Configuration
# Add esos-k3 firmware as image dependency
EXTRA_IMAGEDEPENDS += "esos-k3"
#============================================

#============================================
# Common Kernel Configuration
KERNEL_IMAGETYPE = "Image"
KERNEL_CLASSES = "kernel"
#============================================

#============================================
# Common Image Configuration
WKS_FILE ?= "k3.wks"
IMAGE_FSTYPES = "ext4 wic"
# K3 uses custom bootinfo and bootfs creation (see image-k3.inc)
#============================================

#============================================
# Machine Features
MACHINE_FEATURES:append = "usbhost usbgadget"
#============================================

#============================================
# Serial Console
SERIAL_CONSOLES = "115200;ttyS0"
#============================================
