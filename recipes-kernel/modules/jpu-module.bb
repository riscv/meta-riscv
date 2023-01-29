SUMMARY = "Kernel module for JPU"
DESCRIPTION = "CODAJ12 performs JPEG encoding/decoding and is a chipset from Chip&Media"

LICENSE = "ChipsMedia_VisionFive2"
LIC_FILES_CHKSUM = "file://../../../LICENSE.txt;md5=16bead7cc56b053f5da0061ce0637ad2"

COMPATIBLE_MACHINE = "visionfive2"

JPU_MODULE_SRC = "git/codaj12/jdi/linux/driver"

inherit module
require recipes-bsp/common/visionfive2-firmware.inc

SRC_URI += " \
    file://Makefile;subdir=${JPU_MODULE_SRC} \
"

S = "${WORKDIR}/${JPU_MODULE_SRC}"

RPROVIDES:${PN} += "kernel-module-jpu"
