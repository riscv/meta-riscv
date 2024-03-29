DESCRIPTION = "FSBL contains OpenSBI and u-boot binaries for Milk-V Duo"
LICENSE = "CLOSED"

inherit nopackages deploy

SRC_URI = "git://github.com/milkv-duo/milkv-duo-buildroot-libraries;protocol=https;branch=main"
SRCREV = "f359994bd497f942bb67734280d81f6640c7c168"

COMPATIBLE_MACHINE = "milkv-duo"

S = "${WORKDIR}/git/firmware"
B = "${S}/build"

TARGET_LDFLAGS = ""
SECURITY_LDFLAGS = ""

do_compile[depends] += "opensbi:do_deploy"

do_compile () {
	unset LDFLAGS
	oe_runmake -C ${S} CROSS_COMPILE=${HOST_PREFIX} ARCH=riscv BOOT_CPU=riscv CHIP_ARCH=cv180x PROJECT_FULLNAME=cv1800b_milkv_duo_sd FREE_RAM_SIZE=64mb bl2

	# this is a risc-v bin that contains a busy loop instruction
	# using wfi instruction, this is needed to initialize the
	# secondary core.

	printf '\163\000\120\020\157\360\337\377' > ${B}/blank.bin

	# generate fip.bin
	python3 ${S}/plat/cv180x/fiptool.py genfip ${B}/fip.bin \
		--MONITOR_RUNADDR=0x80000000 \
		--CHIP_CONF=${S}/plat/cv180x/chip_conf.bin \
		--NOR_INFO=FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF \
		--NAND_INFO=00000000 \
		--BL2=${B}/cv180x/bl2.bin \
		--BLCP_IMG_RUNADDR=0x05200200 \
		--BLCP_PARAM_LOADADDR=0 \
		--BLCP_2ND=${B}/blank.bin \
		--BLCP_2ND_RUNADDR=0x83f40000 \
		--DDR_PARAM=${S}/test/cv181x/ddr_param.bin \
		--MONITOR=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin \
		--LOADER_2ND=${DEPLOY_DIR_IMAGE}/u-boot.bin
}

do_deploy () {
	install -m 0644 ${B}/fip.bin ${DEPLOYDIR}
}

addtask deploy after do_compile
