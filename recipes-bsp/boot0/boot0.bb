DESCRIPTION = "Mainline friendly Secondary Program Loader for Allwinner D1"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-or-later;md5=fed54355545ffd980b814dab4a3b312c"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = " \
	git://github.com/smaeul/sun20i_d1_spl;protocol=https;branch=mainline \
	file://0001-config.mk-Allow-overriding-CC-and-other-tools.patch \
	file://0002-config.mk-Remove-nostdinc.patch \
	file://0003-riscv-fix-build-with-binutils-2.38.patch \
"

PV = "1.0+git${SRCPV}"
SRCREV = "0ad88bfdb723b1ac74cca96122918f885a4781ac"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "p=sun20iw1p1 mmc"

inherit deploy

do_compile () {
	unset LDFLAGS
	oe_runmake
}

do_deploy() {
    install -m 644 ${S}/nboot/boot0_sdcard_sun20iw1p1.bin ${DEPLOYDIR}
}

addtask deploy before do_build after do_compile

COMPATIBLE_MACHINE = "nezha-allwinner-d1"
