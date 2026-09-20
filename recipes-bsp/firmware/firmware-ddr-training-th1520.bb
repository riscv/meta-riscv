# DDR training firmware for TH1520
SUMMARY = "th1520 ddr training firmware binary"
HOMEPAGE = "https://github.com/ziyao233/th1520-firmware/"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"
DEPENDS = "lua-native"

inherit deploy

SRC_URI = "git://github.com/ziyao233/th1520-firmware.git;branch=main;protocol=https"
SRCREV = "672c4441a8487bd1fcc8a8ae36824586c007625a"

do_compile() {
        lua ddr-generate.lua src/${TH1520_TRAINING_FW_SRC} th1520-ddr-firmware.bin
}

do_deploy() {
        install -Dm 644 ${B}/th1520-ddr-firmware.bin ${DEPLOYDIR}/th1520-ddr-firmware.bin
}

addtask deploy before do_build after do_compile

COMPATIBLE_MACHINE = "(beaglev-ahead)"

