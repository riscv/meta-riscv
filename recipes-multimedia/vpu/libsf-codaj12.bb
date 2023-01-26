SUMMARY = "CODAJ12 is a standalone JPEG Codec (JPU)"
DESCRIPTION = "CODAJ12 performs the JPEG baseline/extended sequential and M-JPEG decoding and encoding"

LICENSE = "ChipsMedia_VisionFive2"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=16bead7cc56b053f5da0061ce0637ad2"

COMPATIBLE_MACHINE = "visionfive2"

require recipes-bsp/common/visionfive2-firmware.inc
inherit autotools

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

SRC_URI += " \
    file://codaj12_yocto.mak;subdir=git/codaj12 \
    file://20_jpu.rules \
"

S = "${WORKDIR}/git/codaj12"

do_compile() {
    oe_runmake -C ${S} -f codaj12_yocto.mak
}

do_install() {
    install -d ${D}/usr/include/codaj12
    install -D -m 0644 ${S}/jpuapi/jpuapi.h ${D}/usr/include/codaj12/jpuapi/jpuapi.h
    install -D -m 0644 ${S}/jpuapi/jpuapifunc.h ${D}/usr/include/codaj12/jpuapi/jpuapifunc.h
    install -D -m 0644 ${S}/jpuapi/regdefine.h ${D}/usr/include/codaj12/jpuapi/regdefine.h
    install -D -m 0644 ${S}/jpuapi/jpuconfig.h ${D}/usr/include/codaj12/jpuapi/jpuconfig.h
    install -D -m 0644 ${S}/jpuapi/jputypes.h ${D}/usr/include/codaj12/jpuapi/jputypes.h
    install -D -m 0644 ${S}/jpuapi/jputable.h ${D}/usr/include/codaj12/jpuapi/jputable.h
    install -D -m 0644 ${S}/sample/helper/cnm_fpga.h ${D}/usr/include/codaj12/sample/helper/cnm_fpga.h
    install -D -m 0644 ${S}/sample/helper/platform.h ${D}/usr/include/codaj12/sample/helper/platform.h
    install -D -m 0644 ${S}/sample/helper/yuv_feeder.h ${D}/usr/include/codaj12/sample/helper/yuv_feeder.h
    install -D -m 0644 ${S}/sample/helper/datastructure.h ${D}/usr/include/codaj12/sample/helper/datastructure.h
    install -D -m 0644 ${S}/sample/helper/jpulog.h ${D}/usr/include/codaj12/sample/helper/jpulog.h
    install -D -m 0644 ${S}/sample/main_helper.h ${D}/usr/include/codaj12/sample/main_helper.h
    install -D -m 0644 ${S}/jdi/linux/driver/jpu.h ${D}/usr/include/codaj12/jdi/linux/driver/jpu.h
    install -D -m 0644 ${S}/jdi/linux/driver/jmm.h ${D}/usr/include/codaj12/jdi/linux/driver/jmm.h
    install -D -m 0644 ${S}/jdi/jdi.h ${D}/usr/include/codaj12/jdi/jdi.h
    install -D -m 0644 ${S}/jdi/mm.h ${D}/usr/include/codaj12/jdi/mm.h
    install -D -m 0644 ${S}/config.h ${D}/usr/include/codaj12/

    install -d ${D}/usr/lib
    install -m 0644 ${S}/libcodadec.so ${D}/usr/lib/


    install -d ${D}/${base_libdir}/udev/rules.d/
    install -m 0644 ${WORKDIR}/20_jpu.rules ${D}/${base_libdir}/udev/rules.d/
}

FILES:${PN} += " \
    ${base_libdir}/* \
    ${libdir}/* \
"
