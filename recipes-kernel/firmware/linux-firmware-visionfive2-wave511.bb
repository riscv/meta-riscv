SUMMARY = "WAVE511 Firmware Binary Blob"

LICENSE = "ChipsMedia_VisionFive2"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=16bead7cc56b053f5da0061ce0637ad2"

require recipes-bsp/common/visionfive2-firmware.inc
inherit allarch

S = "${WORKDIR}/git/wave511/firmware"

CLEANBROKEN = "1"

do_install () {
    install -d 0644 ${D}${nonarch_base_libdir}/firmware/
    install -m 0644 ${S}/chagall.bin ${D}${nonarch_base_libdir}/firmware/
}

FILES:${PN} = "${nonarch_base_libdir}/firmware/"

INSANE_SKIP = "arch"
