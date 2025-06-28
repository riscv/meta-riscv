SUMMARY = "Kernel module for JPU"
DESCRIPTION = "CODAJ12 performs JPEG encoding/decoding and is a chipset from Chip&Media"

LICENSE = "ChipsMedia-VisionFive2"
LIC_FILES_CHKSUM = "file://../../../LICENSE.txt;md5=16bead7cc56b053f5da0061ce0637ad2"

COMPATIBLE_MACHINE = "jh7110"

inherit module
require recipes-bsp/common/visionfive2-firmware.inc

SRC_URI += " \
    file://Makefile;subdir=${BB_GIT_DEFAULT_DESTSUFFIX}/codaj12/jdi/linux/driver \
"

S = "${UNPACKDIR}/${BP}/codaj12/jdi/linux/driver"

RPROVIDES:${PN} += "kernel-module-jpu"
