SUMMARY = "Kernel module for WAVE511 video decoder"
DESCRIPTION = "WAVE511 is a 4K multi-format decoder IP from Chip&Media to support both HEVC/H.265 and AVC/H.264 video \
formats which provide high-performance decode capability. WAVE511 was also called VPU Dec."

LICENSE = "ChipsMedia_VisionFive2"
LIC_FILES_CHKSUM = "file://../../../LICENSE.txt;md5=16bead7cc56b053f5da0061ce0637ad2"

COMPATIBLE_MACHINE = "visionfive2"

WAVE511_MODULE_SRC = "git/wave511/code/vdi/linux/driver"

inherit module
require recipes-bsp/common/visionfive2-firmware.inc

SRC_URI += " \
    file://Makefile;subdir=${WAVE511_MODULE_SRC} \
"

S = "${WORKDIR}/${WAVE511_MODULE_SRC}"

RPROVIDES:${PN} += "kernel-module-vdec"
RDEPENDS:${PN} += "linux-firmware-visionfive2-wave511"
