SUMMARY = "RISC-V Open Source Supervisor Binary Interface (OpenSBI)"
DESCRIPTION = "OpenSBI aims to provide an open-source and extensible implementation of the RISC-V SBI specification for a platform specific firmware (M-mode) and a general purpose OS, hypervisor or bootloader (S-mode or HS-mode). OpenSBI implementation can be easily extended by RISC-V platform or System-on-Chip vendors to fit a particular hadware configuration."
LICENSE = "BSD-2-Clause"

LIC_FILES_CHKSUM = "file://COPYING.BSD;md5=c36118b4f615f9da37635f2a7ac8ccaf"

SRC_URI = "https://github.com/riscv/opensbi/archive/v${PV}.tar.gz"

SRC_URI[md5sum] = "60ab3ebe6fc4ff7ef155c2e4682737b7"
SRC_URI[sha256sum] = "eb34ae6691952078189982260158376619b87b56d37a24e8dc16ee67e3ca21ad"

require opensbi-payloads.inc

inherit autotools-brokensep

EXTRA_OEMAKE += "PLATFORM=${RISCV_SBI_PLAT} I=${D}"
# If RISCV_SBI_PAYLOAD is set then include it as a payload
EXTRA_OEMAKE_append = " ${@riscv_get_extra_oemake(d)}"

# Required if specifying a custom payload
do_compile[depends] += "${@riscv_get_do_compile_depends(d)}"

do_install_append() {
	# In the future these might be required as a dependency for other packages.
	# At the moment just delete them to avoid warnings
	rm -r ${D}/include
	rm -r ${D}/platform/${RISCV_SBI_PLAT}/lib
	rm -r ${D}/platform/${RISCV_SBI_PLAT}/firmware/payloads
}

do_deploy () {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 755 ${D}/platform/${RISCV_SBI_PLAT}/firmware/fw_payload.* ${DEPLOY_DIR_IMAGE}/
	install -m 755 ${D}/platform/${RISCV_SBI_PLAT}/firmware/fw_jump.* ${DEPLOY_DIR_IMAGE}/
}

addtask deploy after do_install

FILES_${PN} += "/platform/${RISCV_SBI_PLAT}/firmware/fw_jump.*"
FILES_${PN} += "/platform/${RISCV_SBI_PLAT}/firmware/fw_payload.*"

COMPATIBLE_HOST = "(riscv64|riscv32).*"
INHIBIT_PACKAGE_STRIP = "1"
