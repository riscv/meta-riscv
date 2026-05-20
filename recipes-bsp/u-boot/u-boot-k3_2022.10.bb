DESCRIPTION = "U-Boot bootloader for K3 Board (Spacemit K3 RISC-V Processor)"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

require recipes-bsp/u-boot/u-boot.inc

COMPATIBLE_MACHINE = "k3"

DEPENDS += "bc-native dtc-native bison-native flex-native u-boot-tools-native"

B = "${WORKDIR}/build"

BRANCH = "k3-br-v1.0.y"
SRC_URI = "git://github.com/spacemit-com/uboot-2022.10.git;protocol=https;branch=${BRANCH}"

SRCREV = "6747f87ae4cd359ff6e22daa38b06c3ecc2fecb4"

UBOOT_MAKE_TARGET = "all"

# Do not try to install boot.scr.uimg if it doesn't exist
# K3 U-Boot may not generate this file
SPL_BINARY = ""
UBOOT_ENV = ""

do_deploy:append() {
    # cmd_build_itb in config.mk fails silently for out-of-tree builds (srctree/objtree mismatch).
    # Build u-boot.itb here instead, where make all has completed and ${B} is fully populated.
    mkdir -p ${S}/board/spacemit/k3/dtb
    cp ${B}/arch/riscv/dts/k3*.dtb ${S}/board/spacemit/k3/dtb/
    cp ${B}/u-boot-nodtb.bin ${S}/board/spacemit/k3/u-boot-nodtb.bin
    mkimage -f ${S}/board/spacemit/k3/configs/uboot_fdt.its \
        -r ${DEPLOYDIR}/u-boot.itb
    rm -rf ${S}/board/spacemit/k3/dtb
    rm -f ${S}/board/spacemit/k3/u-boot-nodtb.bin

    # cmd_build_default_env same issue: srctree mkenvimage doesn't exist (compiled to ${B}/tools/).
    ${S}/scripts/get_default_envs.sh ${B} > ${B}/u-boot-env-default.txt
    mkenvimage \
        -s $(grep "^CONFIG_ENV_SIZE=" ${B}/.config | cut -d= -f2) \
        -o ${DEPLOYDIR}/u-boot-env-default.bin \
        ${B}/u-boot-env-default.txt
}
