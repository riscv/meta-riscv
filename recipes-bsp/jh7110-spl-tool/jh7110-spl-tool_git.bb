DESCRIPTION = "spl_tool is a jh7110 signature tool used to generate spl header information and generate u-boot-spl.bin.normal.out."
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e6dc25dc2418b8831c906d43809d8336"
SECTION = "bootloaders"

SRCREV = "0747c0510e090f69bf7d2884f44903b77b3db5c5"
SRC_URI = "git://github.com/starfive-tech/Tools;protocol=https;branch=master"

S = "${WORKDIR}/git/spl_tool"

do_compile () {
    cd ${S}
    oe_runmake clean
    oe_runmake
}

do_install () {
    install -Dm 0755 ${S}/spl_tool ${D}${bindir}/spl_tool
}

BBCLASSEXTEND = "native"
