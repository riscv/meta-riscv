FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:jh7110 = "\
    file://0001-add-starfive-support.patch \
    file://0002-Fix-gst-omx-Enable-the-gst-omx-VPU-decoding-and-enco.patch \
    file://0003-add-video-scale-support.patch \
    file://0004-add-encoder-support.patch \
    file://0005-rank-257-for-sf-codecs.patch \
    file://0006-dont-invoke-USE_BUFFER-if-no-dmabuffer.patch \
    file://0007-add-omxmjpegdec-support.patch \
    file://0008-support-nv21-i422-y444-for-omxmjpegdec.patch \
    file://0009-suport-usebuffer-mode-for-encoding.patch \
    file://0010-add-property-framerate.patch \
    file://0011-hanle-some-extra-profile-for-avc.patch \
    file://0012-combine-sps-pps-header-to-idr.patch \
    file://0013-Modify-sf-component-name-to-in-std-format.patch \
    file://0014-support-nv21-for-omxh264_5dec.patch \
    file://0015_Add_NV21_for_gstomxvideoenc_class.patch \
    file://0016-Modify-gstomxmjpegdec-format.patch \
    file://0017-support-mirror-rotation-scale-for-gstomxmjpegdec.patch \
    file://0018-support-cut-for-gstomxmjpegdec.patch \
    file://0019-Add-Interlaced-mode-judgment.patch \
"
GSTREAMER_1_0_OMX_TARGET:jh7110 = "stf"
GSTREAMER_1_0_OMX_CORE_NAME:jh7110 = "${libdir}/libsf-omx-il.so"
EXTRA_OEMESON:append:jh7110 = " -Dheader_path=${STAGING_DIR_TARGET}/usr/include/khronos"
VIRTUAL-RUNTIME_libomxil:jh7110 = "libsf-omxil"
