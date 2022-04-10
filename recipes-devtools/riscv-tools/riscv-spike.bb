SUMMARY = "RISC-V Spike ISA Simulator"
DESCRIPTION = "RISC-V Spike ISA Simulator"
LICENSE = "GPL-2.0-or-later"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"

SRCREV = "65c8ac48af16235097084b413c10c7bff576b331"
SRC_URI = "git://github.com/riscv-software-src/riscv-isa-sim;protocol=https;branch=master \
           file://spike-makefile.patch"

DEPENDS = "riscv-fesvr dtc-native"
RDEPENDS:nativesdk-riscv-spike = "nativesdk-riscv-fesvr"

inherit autotools cross-canadian

S = "${WORKDIR}/git"

do_configure:prepend () {
	touch ${S}/softfloat/softfloat.ac
        if [ ! -e ${S}/acinclude.m4 ]; then
                cp ${S}/aclocal.m4 ${S}/acinclude.m4
        fi
}
BBCLASSEXTEND = "native nativesdk"
COMPATIBLE_HOST:class-target = "(riscv64|riscv32).*-linux"
