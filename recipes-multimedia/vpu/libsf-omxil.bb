SUMMARY = "OpenMAX library implementation for VisionFive2"
DESCRIPTION = "Library provides OpenMAX API for VPU access via WAVE420l, WAVE511 and CODAJ12"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

COMPATIBLE_MACHINE = "jh7110"

PROVIDES = "virtual/libomxil"

require recipes-bsp/common/visionfive2-firmware.inc
inherit cmake

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
INSANE_SKIP:${PN} += "dev-so"

SRC_URI += " \
     file://CMakeLists.txt;subdir=${BB_GIT_DEFAULT_DESTSUFFIX}/omx-il \
"

S = "${UNPACKDIR}/${BP}/omx-il"

DEPENDS += " \
    libsf-wave420l \
    libsf-wave511 \
    libsf-codaj12 \
"

RDEPENDS:${PN} += " \
    libsf-wave420l \
    libsf-wave511 \
    libsf-codaj12 \
"

do_install:append() {
    ln -s -r -f ${D}/usr/lib/libsf-omx-il.so ${D}/usr/lib/libOMX_Core.so
}

FILES:${PN} += " \
    ${libdir}/* \
"
