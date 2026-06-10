#@TYPE: SOC
#@NAME: StarFive JH7110
#@SOC: StarFive JH7110
#@DESCRIPTION: Common config for boards based on the StarFive JH7110 - vendor repo version

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
