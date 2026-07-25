# light_aon_fpga.bin required since mainline kernel 6.15
SUMMARY = "th1520 firmware binary"
HOMEPAGE = "https://github.com/revyos/th1520-boot-firmware"

# no license file provided, but the firmware appears to be protected by an apache license 2.0
LICENSE = "LicenseRef-Proprietary"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Proprietary;md5=0557f9d92cf58f2ccdd50f62f8ac0b28"

inherit deploy

SRC_URI = "git://github.com/revyos/th1520-boot-firmware.git;branch=master;protocol=https"
SRCREV = "725756411ecc20f2c2dbc5ea6b8e5aacc6f83aad"

do_deploy() {
        install -Dm 644 ${S}/addons/boot/light_aon_fpga.bin ${DEPLOYDIR}/light_aon_fpga.bin
}

addtask deploy before do_build after do_compile

COMPATIBLE_MACHINE = "(beaglev-ahead)"

