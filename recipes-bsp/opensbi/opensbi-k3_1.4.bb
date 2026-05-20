SUMMARY = "RISC-V Open Source Supervisor Binary Interface (OpenSBI) for Spacemit K3"
DESCRIPTION = "OpenSBI implementation for Spacemit K3 RISC-V processor"
HOMEPAGE = "https://github.com/riscv/opensbi"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING.BSD;md5=42dd9555eb177f35150cf9aa240b61e5"

require recipes-bsp/opensbi/opensbi-payloads.inc

inherit deploy

COMPATIBLE_MACHINE = "k3"
INHIBIT_PACKAGE_STRIP = "1"

DEPENDS += "u-boot-tools-native dtc-native"

PROVIDES += "opensbi"

SRCREV = "e5fc30394ac18263fa045dcaef52f86f180ed512"
SRC_URI = "git://github.com/spacemit-com/opensbi.git;branch=k3-br-v1.0.y;protocol=https"

RISCV_SBI_PLAT = "generic"
OPENSBI_DEFCONFIG = "k3_defconfig"

EXTRA_OEMAKE += "PLATFORM=${RISCV_SBI_PLAT} I=${D} FW_PIC=y"
EXTRA_OEMAKE += "PLATFORM_DEFCONFIG=${OPENSBI_DEFCONFIG}"
EXTRA_OEMAKE += "CROSS_COMPILE=${TARGET_PREFIX}"

# OpenSBI uses Makefile, not autotools - skip configure
do_configure[noexec] = "1"

# We don't need do_install either, since we're deploying specific files
# ourselves
do_install[noexec] = "1"

do_deploy() {
	install -d ${DEPLOYDIR}

	BUILD_DIR="${S}/build/platform/${RISCV_SBI_PLAT}/firmware"

	for file in fw_dynamic.bin fw_dynamic.elf fw_dynamic.itb; do
		if [ -f "${BUILD_DIR}/${file}" ]; then
			install -m 0644 "${BUILD_DIR}/${file}" "${DEPLOYDIR}/${file}"
		fi
	done
}

addtask deploy before do_build after do_install
