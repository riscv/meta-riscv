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

DEPENDS = "virtual/kernel"

do_install_prepend () {
        install -d ${D}${datadir}/riscv-pk
        install -m 755 ${WORKDIR}/build/bbl ${D}${datadir}/riscv-pk
}

do_install_append() {
        rm -rf ${D}${exec_prefix}/riscv64-unknown-elf
}

do_install_append_freedom-u540() {
        ${OBJCOPY} -S -O binary --change-addresses -0x80000000 \
               ${WORKDIR}/build/bbl ${WORKDIR}/build/bbl.bin
}

do_deploy () {
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 755 ${WORKDIR}/build/bbl ${DEPLOY_DIR_IMAGE}/bbl
}

do_deploy_append_freedom-u540() {
        install -m 755 ${WORKDIR}/build/bbl.bin ${DEPLOY_DIR_IMAGE}/bbl.bin
}
addtask deploy before do_build after do_install

SECURITY_CFLAGS = "${SECURITY_NOPIE_CFLAGS}"
SECURITY_LDFLAGS = ""
