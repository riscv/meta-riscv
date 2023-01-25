SUMMARY = "OpenMAX library implementation for VisionFive2"
DESCRIPTION = "Library provides OpenMAX API for VPU access via WAVE420l, WAVE511 and CODAJ12"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

COMPATIBLE_MACHINE = "visionfive2"

PROVIDES = "virtual/libomxil"

require recipes-bsp/common/visionfive2-firmware.inc
inherit autotools

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
INSANE_SKIP:${PN} += "dev-so"

SRC_URI += " \
     file://0001-Adapt-Makefile-for-usage-in-Yocto.patch;subdir=git/omx-il \
"

S = "${WORKDIR}/git/omx-il"

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

do_compile() {
    oe_runmake -C ${S} STAGING_DIR=${WORKDIR}/recipe-sysroot/
}

do_install() {
    install -d ${D}/usr/include/khronos
    for i in $(ls ${S}/include/khronos); do
        install -m 0644 ${S}/include/khronos/${i} ${D}/usr/include/khronos
    done
    install -d ${D}/usr/lib/
    install -m 0644 ${S}/libsf-omx-il.so ${D}/usr/lib/libsf-omx-il.so
    ln -s -r ${D}/libsf-omx-il.so ${D}/usr/lib/libOMX_Core.so
}

FILES:${PN} += " \
    ${libdir}/* \
"
