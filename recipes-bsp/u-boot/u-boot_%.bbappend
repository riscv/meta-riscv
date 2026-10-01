###########################################################################
# Standard U-Boot recipe customizations
# PLEASE ADD YOUR MACHINE HOOKS IN ALPHANUMERIC ORDER, IN THE RIGHT SECTION
###########################################################################

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS:append = " u-boot-tools-native"
DEPENDS:append:milkv-duo = " xxd-native"

SRC_URI:append:th1520 = " \
            file://0001-ram-thead-th1520-Support-single-rank-firmware.patch \
            file://0002-Add-Support-for-Beagle-V-Ahead-board.patch \
            file://0003-riscv-cpu-th1520-Keep-the-partition-table-area-free-in-SPL.patch \
            file://0004-board-thead-licheepi4a-Add-load-addresses-to-the-environment.patch \
            file://th1520-boot.cfg \
            "
SRC_URI:append:beaglev-fire = " \
            file://boot.cmd \
            "

SRC_URI:append:freedom-u540 = " \
            file://tftp-mmc-boot.txt \
           "
SRC_URI:append:freedom-u540_sota = " file://uEnv.txt"

SRC_URI:append:k1 = " \
            file://bootcommand.cfg \
            "

# Upstream U-Boot patches for the Milk-V Duo boards. duos uses the vendor fork,
# see u-boot-milkv-duo_2021.10.bb.
SRC_URI:append:milkv-duo-common = "file://0001-mmc-cv1800b_sdhci-honor-no-1-8-v-DT-property.patch \
                                   file://0002-board-sophgo-move-ethernet-driver-to-common-director.patch \
                                   file://0003-board-sophgo-add-support-for-Milk-V-Duo-256M.patch \
                                   file://0001-board-sophgo-milkv_duo-add-environment-for-standard-.patch \
                                   file://0002-configs-milkv_duo-enable-BOOTSTD_DEFAULTS.patch \
                                   file://0003-configs-milkv_duo-reduce-CONFIG_STACK_SIZE-to-1MB.patch \
                                   file://0002-riscv-cpu-cv1800b-keep-U-Boot-out-of-reserved-memory.patch \
                                   file://0001-mmc-cv1800b_sdhci-configure-SDHCI-PHY.patch \
                                   "

SRC_URI:append:milkv-duo256m = " file://0001-board-sophgo-milkv_duo_256m-fix-fdtfile-quoting.patch"

SRC_URI:append:orangepi-r2s = " \
            file://0001-arch-riscv-k1-hot-fix-for-RAM-detection-for-boards-w.patch \
            "

###############################
# configure task customizations
###############################

do_configure:prepend:freedom-u540() {
    sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${UNPACKDIR}/tftp-mmc-boot.txt

    if [ -f "${UNPACKDIR}/${UBOOT_ENV}.txt" ]; then
        mkimage -O linux -T script -C none -n "U-Boot boot script" \
            -d ${UNPACKDIR}/${UBOOT_ENV}.txt ${UNPACKDIR}/boot.scr.uimg
    fi
}

#############################
# compile task customizations
#############################

# Only add opensbi dependency if opensbi is in image deps.
# Some machines are an exception because opensbi uses output from u-boot.
# milkv-duo uses the dtb that u-boot generates and beaglev-fire embeds
# u-boot.bin as the opensbi payload.

_DEPS = ""
_DEPS:riscv32 = "opensbi:do_deploy"
_DEPS:riscv64 = "opensbi:do_deploy"
_DEPS:beaglev-fire = ""
_DEPS:milkv-duo-common = ""
_DEPS:append:th1520 = " firmware-ddr-training-th1520:do_deploy" 

do_compile[depends] += "${_DEPS}"

do_compile:prepend:th1520() {
    cp ${DEPLOY_DIR_IMAGE}/th1520-ddr-firmware.bin ${B}
    export OPENSBI=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin
}

do_compile:prepend:freedom-u540() {
    export OPENSBI=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin
}

do_compile:prepend:k1() {
    export OPENSBI=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin
}

do_compile:prepend:visionfive2() {
    export OPENSBI=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin
}

#############################
# deploy task customizations
#############################

do_deploy:append:th1520() {
    install -m 644 ${B}/u-boot-with-spl.bin ${DEPLOYDIR}
}

do_deploy:append:freedom-u540() {
    if [ -f "${UNPACKDIR}/boot.scr.uimg" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 644 ${UNPACKDIR}/boot.scr.uimg ${DEPLOY_DIR_IMAGE}
    fi

    if [ -f "${UNPACKDIR}/uEnv.txt" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 644 ${UNPACKDIR}/uEnv.txt ${DEPLOY_DIR_IMAGE}
    fi
}

do_deploy:append:k1() {
    install -d ${DEPLOYDIR}
    install -m 644 ${B}/u-boot.itb ${DEPLOYDIR}/
    install -m 644 ${B}/u-boot-nodtb.bin ${DEPLOYDIR}/
    install -m 644 ${B}/u-boot.dtb ${DEPLOYDIR}/
}

do_deploy:append:milkv-duo-common() {
    install -m 0644 ${B}/u-boot.dtb ${DEPLOYDIR}
    install -m 0644 ${B}/.config ${DEPLOYDIR}/u-boot.config
}

do_deploy:append:visionfive2() {
    if [ -f "${B}/dts/upstream/src/riscv/starfive/${UBOOT_DTB_BINARY}" ]; then
        install -m 0644 ${B}/dts/upstream/src/riscv/starfive/${UBOOT_DTB_BINARY} ${DEPLOYDIR}
    fi
}

################
# Other settings
################

FILES:${PN}:append:freedom-u540 = " /boot/boot.scr.uimg"
