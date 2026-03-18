# light_aon_fpga.bin required since mainline kernel 6.15
SUMMARY = "th1520 firmware binary"
HOMEPAGE = "https://github.com/revyos/th1520-boot-firmware"

# no license file provided, but the firmware appears to be protected by an apache license 2.0
LICENSE = "CLOSED"

inherit deploy

SRC_URI = "git://github.com/revyos/th1520-boot-firmware.git;branch=master;protocol=https"
SRCREV = "725756411ecc20f2c2dbc5ea6b8e5aacc6f83aad"

do_deploy() {
        install -Dm 644 ${S}/addons/boot/light_aon_fpga.bin ${DEPLOYDIR}/light_aon_fpga.bin
}

addtask deploy before do_build after do_compile

COMPATIBLE_MACHINE = "(beaglev-ahead)"

