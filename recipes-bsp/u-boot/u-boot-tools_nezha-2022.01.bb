require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot-tools.inc

# Use U-Boot patched for Allwinner D1 due to necessary of usage of the mkimage
# tool with option of build 'TOC1' images
SRC_URI = " \
    git://github.com/tekkamanninja/u-boot.git;protocol=git;branch=allwinner_d1 \
"
SRCREV = "6db9960b2443ef84b88a573cb5817f8e0ef3712e"
