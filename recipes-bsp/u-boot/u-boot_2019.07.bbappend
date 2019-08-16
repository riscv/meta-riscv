FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_freedom-u540 = " \
            file://0001-sifive-fu540-config-Add-mmc0-as-a-boot-target-device.patch \
            file://0002-net-macb-sync-header-definitions-as-taken-from-Linux.patch \
            file://0003-net-macb-add-support-for-faster-clk-rates.patch \
            file://0004-net-macb-use-bit-access-macro-from-header-file.patch \
            file://0005-net-macb-add-support-for-SGMII-phy-interface.patch \
            file://0006-net-macb-add-dma_burst_length-config.patch \
            file://0007-net-macb-apply-sane-DMA-configuration.patch \
            file://0008-clk-sifive-Factor-out-PLL-library-as-separate-module.patch \
            file://0009-clk-sifive-Sync-up-WRPLL-library-with-upstream-Linux.patch \
            file://0010-clk-sifive-Sync-up-DT-bindings-header-with-upstream-.patch \
            file://0011-clk-sifive-Sync-up-main-driver-with-upstream-Linux.patch \
            file://0012-clk-sifive-Drop-GEMGXL-clock-driver.patch \
            file://0013-net-macb-Extend-MACB-driver-for-SiFive-Unleashed-boa.patch \
            file://0014-riscv-sifive-fu540-Setup-ethaddr-env-variable-using-.patch \
            file://0015-doc-sifive-fu540-Update-README-for-steps-to-create-F.patch \
            file://0016-net-macb-Fix-check-for-little-endian-system-in.patch \
            file://0017-spi-Add-SiFive-SPI-driver.patch \
            file://0018-mmc-skip-select_mode_and_width-for-MMC-SPI-host.patch \
            file://0019-mmc-mmc_spi-Re-write-driver-using-DM-framework.patch \
            file://0020-riscv-sifive-fu540-Enable-SiFive-SPI-and-MMC-SPI-dri.patch \
            file://0021-doc-sifive-fu540-Update-README-for-SiFive-SPI-and-MM.patch \
            file://0022-net-macb-Extend-MACB-driver-for-SiFive-Unleashed-boa.patch \
            file://tftp-mmc-boot.txt \
           "

SRC_URI_append_freedom-u540_sota = " file://uEnv.txt"

DEPENDS_append_freedom-u540 = " u-boot-tools-native"

# Overwrite this for your server
TFTP_SERVER_IP ?= "127.0.0.1"

do_configure_prepend_freedom-u540() {
    sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${WORKDIR}/tftp-mmc-boot.txt

    if [ -f "${WORKDIR}/${UBOOT_ENV}.txt" ]; then
        mkimage -O linux -T script -C none -n "U-Boot boot script" \
            -d ${WORKDIR}/${UBOOT_ENV}.txt ${WORKDIR}/boot.scr.uimg
    fi
}

do_deploy_append_freedom-u540() {
    if [ -f "${WORKDIR}/boot.scr.uimg" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 755 ${WORKDIR}/boot.scr.uimg ${DEPLOY_DIR_IMAGE}
    fi

    if [ -f "${WORKDIR}/uEnv.txt" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 755 ${WORKDIR}/uEnv.txt ${DEPLOY_DIR_IMAGE}
    fi
}

FILES_${PN}_append_freedom-u540 = " /boot/boot.scr.uimg"
