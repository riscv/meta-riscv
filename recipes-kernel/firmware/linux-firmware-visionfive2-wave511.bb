SUMMARY = "WAVE511 Firmware Binary Blob"

LICENSE = "ChipsMedia-VisionFive2"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=16bead7cc56b053f5da0061ce0637ad2"

require recipes-bsp/common/visionfive2-firmware.inc
inherit allarch

SRC_URI += " \
    https://github.com/starfive-tech/buildroot/raw/JH7110_VisionFive2_devel/package/starfive/wave511/wave511_dec_fw.bin;name=wave511_dec_fw \
    "
SRC_URI[wave511_dec_fw.sha256sum] = "398eb201cf4d6c5050856de5660764a743b8879a69b8684d12aadb556409b79c"

S = "${WORKDIR}/git/wave511/firmware"

CLEANBROKEN = "1"

do_install () {
    install -d 0644 ${D}${nonarch_base_libdir}/firmware/
    install -m 0644 ${S}/chagall.bin ${D}${nonarch_base_libdir}/firmware/
    install -m 0644 ${UNPACKDIR}/wave511_dec_fw.bin ${D}${nonarch_base_libdir}/firmware/
}

FILES:${PN} = "${nonarch_base_libdir}/firmware/"

INSANE_SKIP = "arch"
