SUMMARY = "Kernel module for WAVE420l video encoder"
DESCRIPTION = " WAVE420L is a low-cost HEVC/H.265 HW encoder IP from Chip&Media and is capable of \
encoding FHD/UHD HEVC/H.265 main profile L4.1. WAVE420L was also call *VPU Enc*"

LICENSE = "ChipsMedia-VisionFive2"
LIC_FILES_CHKSUM = "file://../../../LICENSE.txt;md5=16bead7cc56b053f5da0061ce0637ad2"

COMPATIBLE_MACHINE = "jh7110"

inherit module
require recipes-bsp/common/visionfive2-firmware.inc

SRC_URI += " \
    file://Makefile;subdir=${BB_GIT_DEFAULT_DESTSUFFIX}/wave420l/code/vdi/linux/driver \
"

S = "${UNPACKDIR}/${BP}/wave420l/code/vdi/linux/driver"

RPROVIDES:${PN} += "kernel-module-venc"
RDEPENDS:${PN} += "linux-firmware-visionfive2-wave420l"
