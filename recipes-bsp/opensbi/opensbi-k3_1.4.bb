SUMMARY = "RISC-V Open Source Supervisor Binary Interface (OpenSBI) for Spacemit K3"
DESCRIPTION = "OpenSBI implementation for Spacemit K3 RISC-V processor"
HOMEPAGE = "https://github.com/riscv/opensbi"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING.BSD;md5=42dd9555eb177f35150cf9aa240b61e5"

require recipes-bsp/opensbi/opensbi-payloads.inc

inherit autotools-brokensep deploy

DEPENDS += "u-boot-tools-native dtc-native"

SRC_URI = "git://github.com/spacemit-com/opensbi.git;protocol=https;tag=k3-br-v1.0.0;nobranch=1"

RISCV_SBI_PLAT = "generic"
OPENSBI_DEFCONFIG = "k3_defconfig"

EXTRA_OEMAKE += "PLATFORM=${RISCV_SBI_PLAT} I=${D} FW_PIC=y"
EXTRA_OEMAKE += "PLATFORM_DEFCONFIG=${OPENSBI_DEFCONFIG}"
EXTRA_OEMAKE += "CROSS_COMPILE=${TARGET_PREFIX}"

# OpenSBI uses Makefile, not autotools - skip configure
do_configure[noexec] = "1"

do_install(){
	:
}

do_deploy() {
	install -d ${DEPLOYDIR}

	BUILD_DIR="${S}/build/platform/${RISCV_SBI_PLAT}/firmware"

	for file in fw_dynamic.bin fw_dynamic.elf fw_dynamic.itb fw_jump.bin fw_jump.elf fw_payload.bin fw_payload.elf; do
		if [ -f "${BUILD_DIR}/${file}" ]; then
			install -m 0644 "${BUILD_DIR}/${file}" "${DEPLOYDIR}/${file}"
		fi
	done
}

addtask deploy before do_build after do_install

COMPATIBLE_HOST = "(riscv64|riscv32).*"
COMPATIBLE_MACHINE = "k3"
INHIBIT_PACKAGE_STRIP = "1"

PROVIDES += "opensbi"
