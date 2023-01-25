SUMMARY = "WAVE511 decoding library for VisionFive2"
DESCRIPTION = "Library provides API to vdec Kernel module for using the WAVE511 chip for HEVC/H.265 and AVC/H.264 decoding."

LICENSE = "ChipsMedia_VisionFive2"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=16bead7cc56b053f5da0061ce0637ad2"

COMPATIBLE_MACHINE = "visionfive2"

require recipes-bsp/common/visionfive2-firmware.inc
inherit autotools

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

SRC_URI += " \
    file://WaveDecode_yocto.mak;subdir=git/wave511/code \
    file://20_vdec.rules \
"

S = "${WORKDIR}/git/wave511/code"

do_configure:prepend() {
    # workaround wrong build system/include path assumptions
    ln -sf ${S} ${S}/wave511
}

do_compile() {
    oe_runmake -C ${S} -f WaveDecode_yocto.mak
}

do_install() {
    install -D -m 0644 ${S}/sample_v2/component_list_all.h ${D}/usr/include/wave511/sample_v2/component_list_all.h
    install -D -m 0644 ${S}/sample_v2/helper/misc/bw_monitor.h ${D}/usr/include/wave511/sample_v2/helper/misc/bw_monitor.h
    install -D -m 0644 ${S}/sample_v2/helper/misc/pbu.h ${D}/usr/include/wave511/sample_v2/helper/misc/pbu.h
    install -D -m 0644 ${S}/sample_v2/helper/misc/json_output.h ${D}/usr/include/wave511/sample_v2/helper/misc/json_output.h
    install -D -m 0644 ${S}/sample_v2/helper/misc/header_struct.h ${D}/usr/include/wave511/sample_v2/helper/misc/header_struct.h
    install -D -m 0644 ${S}/sample_v2/helper/misc/debug.h ${D}/usr/include/wave511/sample_v2/helper/misc/debug.h
    install -D -m 0644 ${S}/sample_v2/helper/main_helper.h ${D}/usr/include/wave511/sample_v2/helper/main_helper.h
    install -D -m 0644 ${S}/sample_v2/component_decoder/decoder_listener.h ${D}/usr/include/wave511/sample_v2/component_decoder/decoder_listener.h
    install -D -m 0644 ${S}/sample_v2/component_encoder/encoder_listener.h ${D}/usr/include/wave511/sample_v2/component_encoder/encoder_listener.h
    install -D -m 0644 ${S}/sample_v2/component/component.h ${D}/usr/include/wave511/sample_v2/component/component.h
    install -D -m 0644 ${S}/sample_v2/component/component_list.h ${D}/usr/include/wave511/sample_v2/component/component_list.h
    install -D -m 0644 ${S}/sample_v2/component/cnm_app_internal.h ${D}/usr/include/wave511/sample_v2/component/cnm_app_internal.h
    install -D -m 0644 ${S}/sample_v2/component/cnm_app.h ${D}/usr/include/wave511/sample_v2/component/cnm_app.h
    install -D -m 0644 ${S}/sample_v2/component_list_decoder.h ${D}/usr/include/wave511/sample_v2/component_list_decoder.h
    install -D -m 0644 ${S}/sample_v2/component_list_encoder.h ${D}/usr/include/wave511/sample_v2/component_list_encoder.h
    install -D -m 0644 ${S}/config.h ${D}/usr/include/wave511/config.h
    install -D -m 0644 ${S}/vpuapi/wave/wave5_regdefine.h ${D}/usr/include/wave511/vpuapi/wave/wave5_regdefine.h
    install -D -m 0644 ${S}/vpuapi/wave/wave5.h ${D}/usr/include/wave511/vpuapi/wave/wave5.h
    install -D -m 0644 ${S}/vpuapi/vpuerror.h ${D}/usr/include/wave511/vpuapi/vpuerror.h
    install -D -m 0644 ${S}/vpuapi/vputypes.h ${D}/usr/include/wave511/vpuapi/vputypes.h
    install -D -m 0644 ${S}/vpuapi/vpuconfig.h ${D}/usr/include/wave511/vpuapi/vpuconfig.h
    install -D -m 0644 ${S}/vpuapi/coda9/coda9.h ${D}/usr/include/wave511/vpuapi/coda9/coda9.h
    install -D -m 0644 ${S}/vpuapi/coda9/coda9_vpuconfig.h ${D}/usr/include/wave511/vpuapi/coda9/coda9_vpuconfig.h
    install -D -m 0644 ${S}/vpuapi/coda9/coda9_regdefine.h ${D}/usr/include/wave511/vpuapi/coda9/coda9_regdefine.h
    install -D -m 0644 ${S}/vpuapi/vpuapifunc.h ${D}/usr/include/wave511/vpuapi/vpuapifunc.h
    install -D -m 0644 ${S}/vpuapi/vpuapi.h ${D}/usr/include/wave511/vpuapi/vpuapi.h
    install -D -m 0644 ${S}/vpuapi/product.h ${D}/usr/include/wave511/vpuapi/product.h
    install -D -m 0644 ${S}/vdi/mm.h ${D}/usr/include/wave511/vdi/mm.h
    install -D -m 0644 ${S}/vdi/linux/driver/vdec-starfive.h ${D}/usr/include/wave511/vdi/linux/driver/vdec-starfive.h
    install -D -m 0644 ${S}/vdi/linux/driver/vpu.h ${D}/usr/include/wave511/vdi/linux/driver/vpu.h
    install -D -m 0644 ${S}/vdi/linux/driver/vmm.h ${D}/usr/include/wave511/vdi/linux/driver/vmm.h
    install -D -m 0644 ${S}/vdi/vdi.h ${D}/usr/include/wave511/vdi/vdi.h
    install -D -m 0644 ${S}/vdi/vdi_osal.h ${D}/usr/include/wave511/vdi/vdi_osal.h
    install -d ${D}/usr/lib
    install -m 0644 ${S}/libsfdec.so ${D}/usr/lib/

    install -d ${D}/${base_libdir}/udev/rules.d/
    install -m 0644 ${WORKDIR}/20_vdec.rules ${D}/${base_libdir}/udev/rules.d/
}

FILES:${PN} += " \
    ${base_libdir}/* \
    ${libdir}/* \
"
