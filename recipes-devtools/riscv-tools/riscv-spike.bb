SUMMARY = "RISC-V Spike ISA Simulator"
DESCRIPTION = "RISC-V Spike ISA Simulator"
LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"

SRCREV = "65c8ac48af16235097084b413c10c7bff576b331"
SRC_URI = "git://github.com/riscv/riscv-isa-sim.git \
           file://spike-makefile.patch"

DEPENDS = "riscv-fesvr dtc-native"
RDEPENDS_nativesdk-riscv-spike = "nativesdk-riscv-fesvr"

inherit autotools cross-canadian

S = "${WORKDIR}/git"

do_configure_prepend () {
	touch ${S}/softfloat/softfloat.ac
        if [ ! -e ${S}/acinclude.m4 ]; then
                cp ${S}/aclocal.m4 ${S}/acinclude.m4
        fi
}
BBCLASSEXTEND = "native nativesdk"
COMPATIBLE_HOST_class-target = "(riscv64|riscv32).*-linux"
