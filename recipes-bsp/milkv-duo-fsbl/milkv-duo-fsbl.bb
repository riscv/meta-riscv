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

DEFINES  = " \
            -DBOARD_${@'${MACHINE}'.upper().replace('-', '_')} \
            -DRTOS_DUMP_PRINT_ENABLE=1 \
            -DRTOS_DUMP_PRINT_SZ_IDX=17 \
            -DRTOS_ENABLE_FREERTOS=y \
            -DRTOS_FAST_IMAGE_TYPE=0 \
           "

do_compile () {
    cp ${DEPLOY_DIR_IMAGE}/cvi_board_memmap.h ${S}/include/cvi_board_memmap.h

    # this is a risc-v bin that contains a busy loop instruction
    # using wfi instruction, this is needed to initialize the
    # secondary core.

    printf '\163\000\120\020\157\360\337\377' > ${B}/blank.bin

    unset LDFLAGS

    export DEFINES='${DEFINES}'
    export ARCH=riscv
    export BOOT_CPU=riscv
    export CHIP_ARCH=${CHIP_ARCH}
    export DDR_CFG=${DDR_CFG}

    oe_runmake -C ${S} \
        CROSS_COMPILE=${HOST_PREFIX} \
        BLCP_2ND_PATH=${B}/blank.bin \
        LOADER_2ND_PATH=${DEPLOY_DIR_IMAGE}/u-boot.bin \
        MONITOR_PATH=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin
}

do_deploy () {
    install -m 0644 ${B}/${CHIP_ARCH}/fip.bin ${DEPLOYDIR}
}

addtask deploy after do_compile
