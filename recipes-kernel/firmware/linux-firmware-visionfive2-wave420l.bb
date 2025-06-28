SUMMARY = "WAVE420l Firmware Binary Blob"

LICENSE = "ChipsMedia-VisionFive2"
LIC_FILES_CHKSUM = " \
    file://code/LICENSE.txt;md5=16bead7cc56b053f5da0061ce0637ad2 \
    file://firmware/LICENSE.txt;md5=16bead7cc56b053f5da0061ce0637ad2 \
"

require recipes-bsp/common/visionfive2-firmware.inc
inherit allarch

S = "${UNPACKDIR}/${BP}/wave420l"

CLEANBROKEN = "1"

do_install () {
    install -d 0644 ${D}${nonarch_base_libdir}/firmware/
    install -m 0644 ${S}/firmware/monet.bin ${D}${nonarch_base_libdir}/firmware/
    install -m 0644 ${S}/code/cfg/encoder_defconfig.cfg ${D}${nonarch_base_libdir}/firmware/
}

FILES:${PN} = "${nonarch_base_libdir}/firmware/"

INSANE_SKIP = "arch"
