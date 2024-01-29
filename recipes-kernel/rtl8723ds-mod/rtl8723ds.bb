SUMMARY = "Linux driver for RTL8723DS"
DESCRIPTION = "${SUMMARY}"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=1f6f1c0be32491a0c8d2915607a28f36"

inherit module
export KCFLAGS="-fno-asynchronous-unwind-tables -fno-unwind-tables"

SRCREV = "ec85dc6b9f72bfe413bff464ed01a272e29c8dbe"

SRC_URI = " \
          git://github.com/lwfinger/rtl8723ds.git;protocol=https;name=rtl8723ds;branch=master \
          file://0001-Makefile-Add-modules_install-chain-and-make-KSRC-ass.patch  \
	  "
S = "${WORKDIR}/git"

RPROVIDES:${PN} += "kernel-module-rtl8723ds"

EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_DIR}"

COMPATIBLE_MACHINE = "mangopi-mq-pro"
