DESCRIPTION = "FSBL contains OpenSBI and u-boot binaries for Milk-V Duo"

LICENSE = "GPL-2.0-or-later AND BSD-3-Clause AND BSD-2-Clause AND MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://README.rst;beginline=7;endline=52;md5=4752212c904e448ed97a0337e13738f7 \
                    file://lib/BigDigits/LICENSE.txt;md5=cfd7d66d2864c38232ec1ef20b27c13a \
                    file://lib/lz4/LICENSE;md5=ebc2ea4814a64de7708f1571904b32cc \
                    "

SRC_URI = "git://github.com/sophgo/fsbl.git;protocol=https;branch=sg200x-dev \
           file://0001-milkv-duo-fsbl-fix-build-with-newer-binutils.patch \
           file://0002-cpu-riscv-do-not-use-vendor-specific-extension.patch \
           file://0002-plat-cv181x-cv180x-bl2-fix-and-enable-uart.patch \
           file://0003-plat-cv181x-support-packing-bare-U-boot-images.patch \
           file://mmap_conv.py \
           file://memmap.py \
           "
SRCREV = "952dcb6903efc7b5d266bc9d51dc3a2ab54022eb"

# milkv-duos has no upstream U-Boot support and stays on the vendor U-Boot
# fork, so it keeps the FSBL from the vendor SDK as well.
SRC_URI:milkv-duos = "git://github.com/milkv-duo/duo-buildroot-sdk-v2;protocol=https;branch=main \
                      file://0001-milkv-duo-fsbl-fix-build-with-newer-binutils.patch \
                      file://0002-cpu-riscv-do-not-use-vendor-specific-extension.patch \
                      "
SRCREV:milkv-duos = "6f8962c394dd0a05729abb089f0feb7d5cc4aa5e"
LICENSE:milkv-duos = "LicenseRef-Proprietary"
LIC_FILES_CHKSUM:milkv-duos = "file://${COMMON_LICENSE_DIR}/Proprietary;md5=0557f9d92cf58f2ccdd50f62f8ac0b28"

S:milkv-duos = "${UNPACKDIR}/${BP}/fsbl"
B:milkv-duos = "${S}/build"

inherit nopackages deploy

CHIP_ARCH:milkv-duo = "cv180x"
CHIP_ARCH:milkv-duo256m = "cv181x"
CHIP_ARCH:milkv-duos = "cv181x"

DDR_CFG:milkv-duo = "ddr2_1333_x16"
DDR_CFG:milkv-duo256m = "ddr3_1866_x16"
DDR_CFG:milkv-duos = "ddr3_1866_x16"

# Bare-metal firmware: the Makefile sets up its own flags and calls ld
# directly, so don't leak the target userspace flags into it.
CFLAGS[unexport] = "1"
CPPFLAGS[unexport] = "1"
ASFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"

# bl2.elf has a RWX LOAD segment, which ld >= 2.39 warns about, and the
# FSBL links with --fatal-warnings.
EXTRA_OEMAKE_COMMON = "ARCH=riscv \
                       BOOT_CPU=riscv \
                       CHIP_ARCH=${CHIP_ARCH} \
                       CROSS_COMPILE=${HOST_PREFIX} \
                       DDR_CFG=${DDR_CFG} \
                       LDFLAGS=--no-warn-rwx-segments \
                       "

EXTRA_OEMAKE = "${EXTRA_OEMAKE_COMMON} \
                MONITOR_PATH=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin \
                LOADER_2ND_PATH=${DEPLOY_DIR_IMAGE}/u-boot.bin \
                "

EXTRA_OEMAKE:milkv-duos = "${EXTRA_OEMAKE_COMMON}"

do_compile[depends] += "opensbi:do_deploy virtual/bootloader:do_deploy"

# The FSBL needs the board memory map, which the vendor SDK generates from a
# per-board memmap.py. milkv-duos gets it from the vendor U-Boot instead.
do_generate_memmap () {
    python3 ${UNPACKDIR}/mmap_conv.py --type h \
        ${UNPACKDIR}/memmap.py \
        ${S}/plat/${CHIP_ARCH}/include/cvi_board_memmap.h
}

do_generate_memmap:milkv-duos () {
    :
}

addtask generate_memmap after do_patch before do_configure

# fiptool.py prepends a 32-byte header to u-boot.bin, so the image is loaded
# that many bytes below U-Boot's CONFIG_TEXT_BASE.
do_compile () {
    text_base="$(sed -n 's/^CONFIG_TEXT_BASE=//p' ${DEPLOY_DIR_IMAGE}/u-boot.config)"
    [ -n "$text_base" ] || bbfatal "CONFIG_TEXT_BASE not found in u-boot.config"
    loader_2nd_base="$(expr $(printf '%d' $text_base) - 32)"
    oe_runmake LOADER_2ND_BASE=$(printf '0x%x' $loader_2nd_base)
}

do_compile:milkv-duos () {
    cp ${DEPLOY_DIR_IMAGE}/cvi_board_memmap.h ${S}/include/cvi_board_memmap.h

    oe_runmake -C ${S} fip-dep

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
    install -m 0644 ${B}/build/${CHIP_ARCH}/fip.bin ${DEPLOYDIR}
}

do_deploy:milkv-duos () {
    install -m 0644 ${B}/${CHIP_ARCH}/fip.bin ${DEPLOYDIR}
}

addtask deploy after do_compile

COMPATIBLE_MACHINE = "milkv-(duo|duo256m|duos)"
