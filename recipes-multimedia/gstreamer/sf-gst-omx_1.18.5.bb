SUMMARY = "OpenMAX IL plugins for GStreamer"
DESCRIPTION = "Wraps available OpenMAX IL components and makes them available as standard GStreamer elements."
HOMEPAGE = "http://gstreamer.freedesktop.org/"
SECTION = "multimedia"

LICENSE = "LGPL-2.1-or-later"
LICENSE_FLAGS = "commercial"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c \
                    file://omx/gstomx.h;beginline=1;endline=21;md5=5c8e1fca32704488e76d2ba9ddfa935f"

SRC_URI = " \
    https://gstreamer.freedesktop.org/src/gst-omx/gst-omx-${PV}.tar.xz \
"

SRC_URI[sha256sum] = "2cd457c1e8deb1a9b39608048fb36a44f6c9a864a6b6115b1453a32e7be93b42"

S = "${WORKDIR}/gst-omx-${PV}"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad virtual/libomxil"

inherit meson pkgconfig upstream-version-is-even

GSTREAMER_1_0_OMX_TARGET ?= "bellagio"
GSTREAMER_1_0_OMX_CORE_NAME ?= "${libdir}/libomxil-bellagio.so.0"

EXTRA_OEMESON += "-Dtarget=${GSTREAMER_1_0_OMX_TARGET}"

python __anonymous () {
    omx_target = d.getVar("GSTREAMER_1_0_OMX_TARGET")
    if omx_target in ['generic', 'bellagio']:
        # Bellagio headers are incomplete (they are missing the OMX_VERSION_MAJOR,#
        # OMX_VERSION_MINOR, OMX_VERSION_REVISION, and OMX_VERSION_STEP macros);
        # appending a directory path to gst-omx' internal OpenMAX IL headers fixes this
        d.appendVar("CFLAGS", " -I${S}/omx/openmax")
    elif omx_target == "rpi":
        # Dedicated Raspberry Pi OpenMAX IL support makes this package machine specific
        d.setVar("PACKAGE_ARCH", d.getVar("MACHINE_ARCH"))
}

set_omx_core_name() {
	sed -i -e "s;^core-name=.*;core-name=${GSTREAMER_1_0_OMX_CORE_NAME};" "${D}${sysconfdir}/xdg/gstomx.conf"
}
do_install[postfuncs] += " set_omx_core_name "

FILES:${PN} += "${libdir}/gstreamer-1.0/*.so"
FILES:${PN}-staticdev += "${libdir}/gstreamer-1.0/*.a"

VIRTUAL-RUNTIME_libomxil ?= "libomxil"
RDEPENDS:${PN} = "${VIRTUAL-RUNTIME_libomxil}"

# TODO this recipe is currently a WIP patch set
# in order to make life easier, the above is a verbatim copy of the original recipe, all VisionFive2 specific parts go below:
# from the above, only the recipe version and checksum is different to poky to ease patch applying

SRC_URI += " \
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
GSTREAMER_1_0_OMX_TARGET:visionfive2 = "stf"
GSTREAMER_1_0_OMX_CORE_NAME:visionfive2 = "${libdir}/libsf-omx-il.so"
EXTRA_OEMESON:append:visionfive2 = " -Dheader_path=${STAGING_DIR_TARGET}/usr/include/khronos"
VIRTUAL-RUNTIME_libomxil:visionfive2 = "libsf-omxil"
