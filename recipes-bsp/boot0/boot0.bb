DESCRIPTION = "Mainline friendly Secondary Program Loader for Allwinner D1"
LICENSE = "GPL-2.0+"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-or-later;md5=fed54355545ffd980b814dab4a3b312c"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI = " \
	git://github.com/smaeul/sun20i_d1_spl;protocol=https;branch=mainline \
	file://0001-config.mk-provide-path-to-linux-headers.patch \
"

PV = "1.0-dev"
SRCREV = "0ad88bfdb723b1ac74cca96122918f885a4781ac"

S = "${WORKDIR}/git"

CFLAGS += " --sysroot=${RECIPE_SYSROOT}"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX} p=sun20iw1p1 mmc"
EXTRA_OEMAKE += "STAGING_INCDIR=${STAGING_INCDIR_NATIVE}"

GCC_INCLUDE = "${RECIPE_SYSROOT_NATIVE}${libdir}/${TARGET_SYS}/gcc/${TARGET_SYS}/*/include"

inherit deploy

do_configure () {
	cp -r ${GCC_INCLUDE}/* ${STAGING_INCDIR_NATIVE}
}

do_compile () {
	unset LDFLAGS
	oe_runmake
}

do_deploy() {
    install -m 644 ${S}/nboot/boot0_sdcard_sun20iw1p1.bin ${DEPLOYDIR}
}

addtask deploy before do_build after do_compile
