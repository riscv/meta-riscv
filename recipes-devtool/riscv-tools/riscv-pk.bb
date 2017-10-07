SUMMARY = "RISC-V Proxy Kernel"
DESCRIPTION = "RISC-V Proxy Kernel"
LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"

SRCREV = "fb4e31229fb072297dad9fcdf2c67b053c5d0322"
SRC_URI = "git://github.com/riscv/riscv-pk.git"

LDFLAGS += "-Wl,--build-id=none"

inherit autotools

DEPENDS = "riscv-fesvr-native riscv-spike-native"

INHIBIT_PACKAGE_STRIP = "1"

S = "${WORKDIR}/git"

do_install_prepend () {
        install -d ${D}${datadir}/riscv-pk
        install -m 755 ${WORKDIR}/build/bbl ${D}${datadir}/riscv-pk
}

do_install_append() {
        rm -rf ${D}${exec_prefix}/riscv64-unknown-elf
}
SECURITY_CFLAGS = "${SECURITY_NOPIE_CFLAGS}"
SECURITY_LDFLAGS = ""

PROVIDES_${PN}_class += "${PN}-bbl"
PACKAGES_class += " ${PN}-bbl"
