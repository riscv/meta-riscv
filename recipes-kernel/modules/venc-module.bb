SUMMARY = "Kernel module for WAVE420l video encoder"
DESCRIPTION = " WAVE420L is a low-cost HEVC/H.265 HW encoder IP from Chip&Media and is capable of \
encoding FHD/UHD HEVC/H.265 main profile L4.1. WAVE420L was also call *VPU Enc*"

LICENSE = "ChipsMedia_VisionFive2"
LIC_FILES_CHKSUM = "file://../../../LICENSE.txt;md5=16bead7cc56b053f5da0061ce0637ad2"

COMPATIBLE_MACHINE = "visionfive2"

WAVE420L_MODULE_SRC = "git/wave420l/code/vdi/linux/driver"

inherit module
require recipes-bsp/common/visionfive2-firmware.inc

SRC_URI += " \
    file://Makefile;subdir=${WAVE420L_MODULE_SRC} \
"

S = "${WORKDIR}/${WAVE420L_MODULE_SRC}"

RPROVIDES:${PN} += "kernel-module-venc"
RDEPENDS:${PN} += "linux-firmware-visionfive2-wave420l"
