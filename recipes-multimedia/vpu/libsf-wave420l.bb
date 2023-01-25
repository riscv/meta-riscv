SUMMARY = "WAVE420l encoding library for VisionFive2"
DESCRIPTION = "Library provides API to venc Kernel module for using the WAVE420l chip for AVC/H.264 encoding."

LICENSE = "ChipsMedia_VisionFive2"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=16bead7cc56b053f5da0061ce0637ad2"

COMPATIBLE_MACHINE = "visionfive2"

require recipes-bsp/common/visionfive2-firmware.inc
inherit autotools

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

SRC_URI += " \
    file://WaveEncoder_yocto.mak;subdir=git/wave420l/code \
    file://20_venc.rules \
"

S = "${WORKDIR}/git/wave420l/code"

do_configure:prepend() {
    # workaround wrong build system/include path assumptions
    ln -sf ${S} ${S}/wave420l
}

do_compile() {
    oe_runmake -C ${S} -f WaveEncoder_yocto.mak
}

do_install() {
    install -D -m 0644 ${S}/vpuapi/vpuconfig.h ${D}/usr/include/wave420l/vpuapi/vpuconfig.h
    install -D -m 0644 ${S}/vpuapi/product.h ${D}/usr/include/wave420l/vpuapi/product.h
    install -D -m 0644 ${S}/vpuapi/vputypes.h ${D}/usr/include/wave420l/vpuapi/vputypes.h
    install -D -m 0644 ${S}/vpuapi/vpuapi.h ${D}/usr/include/wave420l/vpuapi/vpuapi.h
    install -D -m 0644 ${S}/vpuapi/vpuapifunc.h ${D}/usr/include/wave420l/vpuapi/vpuapifunc.h
    install -D -m 0644 ${S}/vpuapi/coda9/coda9_vpuconfig.h ${D}/usr/include/wave420l/vpuapi/coda9/coda9_vpuconfig.h
    install -D -m 0644 ${S}/vpuapi/coda9/coda9.h ${D}/usr/include/wave420l/vpuapi/coda9/coda9.h
    install -D -m 0644 ${S}/vpuapi/coda9/coda9_regdefine.h  ${D}/usr/include/wave420l/vpuapi/coda9/coda9_regdefine.h
    install -D -m 0644 ${S}/vpuapi/wave/coda7q/coda7q_regdefine.h ${D}/usr/include/wave420l/vpuapi/wave/coda7q/coda7q_regdefine.h
    install -D -m 0644 ${S}/vpuapi/wave/coda7q/coda7q.h ${D}/usr/include/wave420l/vpuapi/wave/coda7q/coda7q.h
    install -D -m 0644 ${S}/vpuapi/wave/wave4/wave4.h ${D}/usr/include/wave420l/vpuapi/wave/wave4/wave4.h
    install -D -m 0644 ${S}/vpuapi/wave/wave4/wave4_regdefine.h ${D}/usr/include/wave420l/vpuapi/wave/wave4/wave4_regdefine.h
    install -D -m 0644 ${S}/vpuapi/wave/wave5/wave5.h ${D}/usr/include/wave420l/vpuapi/wave/wave5/wave5.h
    install -D -m 0644 ${S}/vpuapi/wave/wave5/wave5_regdefine.h ${D}/usr/include/wave420l/vpuapi/wave/wave5/wave5_regdefine.h
    install -D -m 0644 ${S}/vpuapi/wave/common/common.h ${D}/usr/include/wave420l/vpuapi/wave/common/common.h
    install -D -m 0644 ${S}/vpuapi/wave/common/common_vpuconfig.h ${D}/usr/include/wave420l/vpuapi/wave/common/common_vpuconfig.h
    install -D -m 0644 ${S}/vpuapi/wave/common/common_regdefine.h ${D}/usr/include/wave420l/vpuapi/wave/common/common_regdefine.h
    install -D -m 0644 ${S}/vpuapi/vpuerror.h ${D}/usr/include/wave420l/vpuapi/vpuerror.h
    install -D -m 0644 ${S}/sample/helper/misc/pbu.h ${D}/usr/include/wave420l/sample/helper/misc/pbu.h
    install -D -m 0644 ${S}/sample/helper/misc/skip.h ${D}/usr/include/wave420l/sample/helper/misc/skip.h
    install -D -m 0644 ${S}/sample/helper/misc/getopt.h ${D}/usr/include/wave420l/sample/helper/misc/getopt.h
    install -D -m 0644 ${S}/sample/helper/misc/header_struct.h ${D}/usr/include/wave420l/sample/helper/misc/header_struct.h
    install -D -m 0644 ${S}/sample/helper/misc/debug.h ${D}/usr/include/wave420l/sample/helper/misc/debug.h
    install -D -m 0644 ${S}/sample/helper/msvc/inttypes.h ${D}/usr/include/wave420l/sample/helper/msvc/inttypes.h
    install -D -m 0644 ${S}/sample/helper/msvc/stdint.h ${D}/usr/include/wave420l/sample/helper/msvc/stdint.h
    install -D -m 0644 ${S}/sample/helper/main_helper.h ${D}/usr/include/wave420l/sample/helper/main_helper.h
    install -D -m 0644 ${S}/vdi/mm.h ${D}/usr/include/wave420l/vdi/mm.h
    install -D -m 0644 ${S}/vdi/linux/driver/vmm.h ${D}/usr/include/wave420l/vdi/linux/driver/vmm.h
    install -D -m 0644 ${S}/vdi/linux/driver/vpu.h ${D}/usr/include/wave420l/vdi/linux/driver/vpu.h
    install -D -m 0644 ${S}/vdi/vdi.h ${D}/usr/include/wave420l/vdi/vdi.h
    install -D -m 0644 ${S}/vdi/vdi_osal.h ${D}/usr/include/wave420l/vdi/vdi_osal.h
    install -D -m 0644 ${S}/config.h ${D}/usr/include/wave420l/config.h

    install -d ${D}/usr/lib
    install -m 0644 ${S}/libsfenc.so ${D}/usr/lib/

    install -d ${D}/${base_libdir}/udev/rules.d/
    install -m 0644 ${WORKDIR}/20_venc.rules ${D}/${base_libdir}/udev/rules.d/
}

FILES:${PN} += " \
    ${base_libdir}/* \
    ${libdir}/* \
"
