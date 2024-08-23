DESCRIPTION = "FSBL contains OpenSBI and u-boot binaries for Milk-V Duo"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM ?= "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

inherit nopackages deploy

SRC_URI = " \
           git://github.com/milkv-duo/milkv-duo-buildroot-libraries;protocol=https;branch=main \
           file://0001-updates.patch;patchdir=.. \
           file://0002-compile-fixes.patch;patchdir=.. \
          "
SRCREV = "f359994bd497f942bb67734280d81f6640c7c168"

COMPATIBLE_MACHINE = "milkv-(duo|duo256m|duos)"

S = "${WORKDIR}/git/firmware"
B = "${S}/build"

TARGET_LDFLAGS = ""
SECURITY_LDFLAGS = ""

python () {
    if d.getVar('MACHINE') == 'milkv-duo':
        d.setVar('CHIP_ARCH', 'cv180x')
        d.setVar('DDR_CFG', 'ddr2_1333_x16')
    else:
        d.setVar('CHIP_ARCH', 'cv181x')
        d.setVar('DDR_CFG', 'ddr3_1866_x16')
}

DEFINES  = " \
            -DBOARD_${@'${MACHINE}'.upper().replace('-', '_')} \
            -DRTOS_DUMP_PRINT_ENABLE=1 \
            -DRTOS_DUMP_PRINT_SZ_IDX=17 \
            -DRTOS_ENABLE_FREERTOS=y \
            -DRTOS_FAST_IMAGE_TYPE=0 \
           "

do_compile[depends] += "opensbi:do_deploy"

do_compile () {
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
    export MONITOR_PATH=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin

    oe_runmake -C ${S} \
        CROSS_COMPILE=${HOST_PREFIX} \
        BLCP_2ND_PATH=${B}/blank.bin \
        LOADER_2ND_PATH=${DEPLOY_DIR_IMAGE}/u-boot.bin
}

do_deploy () {
    install -m 0644 ${B}/${CHIP_ARCH}/fip.bin ${DEPLOYDIR}
}

addtask deploy after do_compile
