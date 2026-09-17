DESCRIPTION = "FSBL contains OpenSBI and u-boot binaries for Milk-V Duo"
LICENSE = "LicenseRef-Proprietary"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Proprietary;md5=0557f9d92cf58f2ccdd50f62f8ac0b28"

inherit nopackages deploy

SRC_URI = " \
    git://github.com/milkv-duo/duo-buildroot-sdk-v2;protocol=https;branch=main \
    file://0001-milkv-duo-fsbl-fix-build-with-newer-binutils.patch \
    file://0002-cpu-riscv-do-not-use-vendor-specific-extension.patch \
"
SRCREV = "6f8962c394dd0a05729abb089f0feb7d5cc4aa5e"

COMPATIBLE_MACHINE = "milkv-(duo|duo256m|duos)"

S = "${UNPACKDIR}/${BP}/fsbl"
B = "${S}/build"

EXTRA_OEMAKE = " \
  CFLAGS=-Wno-error \
  LDFLAGS=--no-fatal-warnings \
"

TARGET_LDFLAGS = ""
SECURITY_LDFLAGS = ""

do_compile[depends] += "opensbi:do_deploy virtual/bootloader:do_deploy"

CHIP_ARCH:milkv-duo = "cv180x"
CHIP_ARCH:milkv-duo256m = "cv181x"
CHIP_ARCH:milkv-duos = "cv181x"

DDR_CFG:milkv-duo = "ddr2_1333_x16"
DDR_CFG:milkv-duo256m = "ddr3_1866_x16"
DDR_CFG:milkv-duos = "ddr3_1866_x16"

do_compile () {
    cp ${DEPLOY_DIR_IMAGE}/cvi_board_memmap.h ${S}/include/cvi_board_memmap.h

    unset LDFLAGS

    export ARCH=riscv
    export BOOT_CPU=riscv
    export CHIP_ARCH=${CHIP_ARCH}
    export DDR_CFG=${DDR_CFG}

    oe_runmake -C ${S} \
        CROSS_COMPILE=${HOST_PREFIX} \
        fip-dep

    MONITOR_RUNADDR="$(sed -n 's/^MONITOR_RUNADDR=//p' ${B}/${CHIP_ARCH}/blmacros.env)"

    ${S}/plat/${CHIP_ARCH}/fiptool.py genfip \
            --CHIP_CONF ${B}/${CHIP_ARCH}/chip_conf.bin \
            --NOR_INFO=FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF \
            --NAND_INFO=00000000 \
            --MONITOR=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin \
            --MONITOR_RUNADDR=${MONITOR_RUNADDR} \
            --LOADER_2ND=${DEPLOY_DIR_IMAGE}/u-boot.bin \
            --BL2=${B}/${CHIP_ARCH}/bl2.bin \
            --compress='lzma' \
            ${B}/${CHIP_ARCH}/fip.bin
}

do_deploy () {
    install -m 0644 ${B}/${CHIP_ARCH}/fip.bin ${DEPLOYDIR}
}

addtask deploy after do_compile
