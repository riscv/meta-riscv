SUMMARY = "RISC-V Proxy Kernel"
DESCRIPTION = "RISC-V Proxy Kernel"
LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"

SRCREV = "2bbd8e1a1bccae13ec87882baf423abfc6ef76fd"
SRC_URI = "git://github.com/riscv/riscv-pk.git \
           file://0001-add-acinclude.m4.patch \
          "

LDFLAGS_append = " -Wl,--build-id=none"

inherit autotools

EXTRA_OECONF += "--enable-logo --with-payload=${DEPLOY_DIR_IMAGE}/${RISCV_BBL_PAYLOAD}"

INHIBIT_PACKAGE_STRIP = "1"

S = "${WORKDIR}/git"

do_configure[depends] = "virtual/kernel:do_deploy"

do_install_prepend () {
        install -d ${D}${datadir}/riscv-pk
        install -m 755 ${WORKDIR}/build/bbl ${D}${datadir}/riscv-pk
}

do_install_append() {
        rm -rf ${D}${exec_prefix}/riscv64-unknown-elf
}

do_deploy () {
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 755 ${WORKDIR}/build/bbl ${DEPLOY_DIR_IMAGE}/bbl
}

addtask deploy before do_build after do_compile
do_deploy[depends] = "${PN}:do_compile"

SECURITY_CFLAGS = "${SECURITY_NOPIE_CFLAGS}"
SECURITY_LDFLAGS = ""

do_configure[nostamp] = "1"
do_compile[nostamp] = "1"
do_install[nostamp] = "1"
do_deploy[nostamp] = "1"
