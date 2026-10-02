# light_aon_fpga.bin required since mainline kernel 6.15
SUMMARY = "th1520 firmware binary"
HOMEPAGE = "https://github.com/revyos/th1520-boot-firmware"

# The firmware itself does not provide a license file.
LICENSE = "GPL-2.0-only AND LicenseRef-Proprietary"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Proprietary;md5=0557f9d92cf58f2ccdd50f62f8ac0b28 \
    file://../aon-helper/LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
"
inherit deploy

SRC_URI = " \
    git://github.com/revyos/th1520-boot-firmware.git;branch=master;protocol=https;name=bootfw;destsuffix=boot-firmware \
    git://github.com/ziyao233/th1520-firmware.git;branch=main;protocol=https;name=aonhelper;destsuffix=aon-helper \
    file://0001-aon-generate-drop-O_DIRECT-for-small-writes.patch;patchdir=${UNPACKDIR}/aon-helper \
"

SRCREV_bootfw = "725756411ecc20f2c2dbc5ea6b8e5aacc6f83aad"
SRCREV_aonhelper = "672c4441a8487bd1fcc8a8ae36824586c007625a"
SRCREV_FORMAT = "bootfw_aonhelper"

S = "${UNPACKDIR}/boot-firmware"

DEPENDS += "bc-native"

do_compile:append:beaglev-ahead() {
    # Start with the LPi4A AON configuration from th1520-firmware.
    cp ${UNPACKDIR}/aon-helper/bin/lpi4a-aon.patch.bin \
        ${B}/beaglev-ahead-aon.patch.bin

    # DA9063: enable the watchdog flag instead of pre-setting AUTO_REBOOT.
    # The driver reads AUTO_BOOT from CONTROL_C and sets AUTO_REBOOT at runtime.
    printf '\001' | dd \
        of=${B}/beaglev-ahead-aon.patch.bin \
        bs=1 seek=103 conv=notrunc status=none

    # Clear the initial DA9063 slew rate (10 mV/us) and watchdog timeout
    # (32 seconds). The driver and AON watchdog code initialize them later.
    dd if=/dev/zero \
        of=${B}/beaglev-ahead-aon.patch.bin \
        bs=1 seek=104 count=2 conv=notrunc status=none

    # Clear the initial DA9121 slew rate (20 mV/us);
    # da9121_init() initializes it later.
    dd if=/dev/zero \
        of=${B}/beaglev-ahead-aon.patch.bin \
        bs=1 seek=163 count=1 conv=notrunc status=none

    # aon-generate.sh uses Bash-specific $'...' syntax despite its /bin/sh
    # shebang, so invoke it explicitly with Bash.
    bash ${UNPACKDIR}/aon-helper/aon-generate.sh \
        ${S}/addons/boot/light_aon_fpga.bin \
        ${B}/beaglev-ahead-aon.patch.bin \
        ${B}/beaglev-ahead-aon.elf
}

do_deploy() {
    install -Dm 644 \
        ${S}/addons/boot/light_aon_fpga.bin \
        ${DEPLOYDIR}/light_aon_fpga.bin
}

do_deploy:append:beaglev-ahead() {
    install -m 0644 \
        ${B}/beaglev-ahead-aon.elf \
        ${DEPLOYDIR}/beaglev-ahead-aon.elf
}

addtask deploy before do_build after do_compile

COMPATIBLE_MACHINE = "(th1520)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
