SUMMARY = "PolarFire SoC Hart Software Services (HSS) payload generator"
DESCRIPTION = "Wraps a bootloader or firmware image into the boot image format \
that the PolarFire SoC Hart Software Services boots from."
HOMEPAGE = "https://github.com/polarfire-soc/hart-software-services"
SECTION = "bootloaders"

LICENSE = "MIT OR GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://tools/hss-payload-generator/LICENSE.md;md5=aafc87210eccb6c2c2afe5dc2b7d9ecb"

inherit native

DEPENDS = "elfutils-native libyaml-native openssl-native zlib-native"

SRC_URI = "git://github.com/polarfire-soc/hart-software-services.git;protocol=https;branch=master;tag=v${PV}"
SRCREV = "711a983527cef51b8d1dbb92f91d1674a02f2270"

B = "${S}/tools/hss-payload-generator"

do_compile() {
    oe_runmake -e
}

do_install() {
    install -Dm 0755 ${B}/hss-payload-generator ${D}${bindir}/hss-payload-generator
}
