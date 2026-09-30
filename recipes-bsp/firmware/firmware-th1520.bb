# light_aon_fpga.bin required since mainline kernel 6.15
SUMMARY = "th1520 firmware binary"
HOMEPAGE = "https://github.com/revyos/th1520-boot-firmware"

# The firmware itself does not provide a license file.
LICENSE = "LicenseRef-Proprietary & GPL-2.0-only"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Proprietary;md5=0557f9d92cf58f2ccdd50f62f8ac0b28 \
    file://${UNPACKDIR}/aon-helper/LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
"

inherit deploy
SRC_URI = " \
    git://github.com/revyos/th1520-boot-firmware.git;branch=master;protocol=https;name=bootfw;destsuffix=boot-firmware \
    git://github.com/ziyao233/th1520-firmware.git;branch=main;protocol=https;name=aonhelper;destsuffix=aon-helper \
"

SRCREV_bootfw = "725756411ecc20f2c2dbc5ea6b8e5aacc6f83aad"
SRCREV_aonhelper = "672c4441a8487bd1fcc8a8ae36824586c007625a"
SRCREV_FORMAT = "bootfw_aonhelper"

S = "${UNPACKDIR}/boot-firmware"

DEPENDS += "bc-native"

do_deploy() {
    install -Dm 644 \
        ${S}/addons/boot/light_aon_fpga.bin \
        ${DEPLOYDIR}/light_aon_fpga.bin
}

do_deploy:append:beaglev-ahead() {
    # Start with the known TH1520 AON configuration from th1520-firmware.
    cp \
        ${UNPACKDIR}/aon-helper/bin/lpi4a-aon.patch.bin \
        ${B}/beaglev-ahead-aon.patch.bin

    # BeagleV Ahead PMIC configuration differences.
    # offsets: 0x67, 0x68, 0x69, 0xa3
    printf '\001' | dd \
        of=${B}/beaglev-ahead-aon.patch.bin \
        bs=1 seek=103 conv=notrunc status=none

    dd if=/dev/zero \
        of=${B}/beaglev-ahead-aon.patch.bin \
        bs=1 seek=104 count=2 conv=notrunc status=none

    dd if=/dev/zero \
        of=${B}/beaglev-ahead-aon.patch.bin \
        bs=1 seek=163 count=1 conv=notrunc status=none

    # aon-generate.sh uses Bash-specific $'...' syntax despite its /bin/sh
    # shebang, so invoke it explicitly with Bash.
    #
    # It also uses O_DIRECT for tiny ELF-header writes. Make a private
    # build-time copy without oflag=direct so those writes work normally.
    cp ${UNPACKDIR}/aon-helper/aon-generate.sh ${B}/aon-generate.sh
    sed -i 's/ oflag=direct//' ${B}/aon-generate.sh

    bash ${B}/aon-generate.sh \
        ${S}/addons/boot/light_aon_fpga.bin \
        ${B}/beaglev-ahead-aon.patch.bin \
        ${B}/beaglev-ahead-aon.elf

    install -m 0644 \
        ${B}/beaglev-ahead-aon.elf \
        ${DEPLOYDIR}/beaglev-ahead-aon.elf
}

addtask deploy before do_build after do_compile

COMPATIBLE_MACHINE = "(th1520)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
