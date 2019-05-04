FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://0001-riscv-add-infrastructure-for-calling-functions-on-ot.patch \
            file://0002-riscv-import-the-supervisor-binary-interface-header-.patch \
            file://0003-riscv-implement-IPI-platform-functions-using-SBI.patch \
            file://0004-riscv-delay-initialization-of-caches-and-debug-UART.patch \
            file://0005-riscv-save-hart-ID-in-register-tp-instead-of-s0.patch \
            file://0006-riscv-add-support-for-multi-hart-systems.patch \
            file://0007-riscv-boot-images-passed-to-bootm-on-all-harts.patch \
            file://0008-riscv-do-not-rely-on-hart-ID-passed-by-previous-boot.patch \
            file://0009-riscv-hang-if-relocation-of-secondary-harts-fails.patch \
            file://0010-riscv-fu540-enable-SMP.patch \
            file://0011-riscv-qemu-enable-SMP.patch \
            file://0012-riscv-Add-a-SYSCON-driver-for-Andestech-s-PLIC.patch \
            file://0013-riscv-Add-a-SYSCON-driver-for-Andestech-s-PLMT.patch \
            file://0014-riscv-ax25-Add-platform-specific-Kconfig-options.patch \
            file://0015-riscv-ax25-Andes-specific-cache-shall-only-support-i.patch \
            file://0016-riscv-dts-ae350-support-SMP.patch \
            file://0017-riscv-ae350-enable-SMP.patch \
            file://0018-riscv-dts-fix-CONFIG_DEFAULT_DEVICE_TREE-failure.patch \
            file://0001-sifive-fu540-Set-default-arguments-to-help-booting.patch \
            file://tftp-boot.txt \
           "

DEPENDS += "u-boot-tools-native"

UBOOT_ENTRYPOINT = "0x80200000"

# Overwrite this for your server
TFTP_SERVER_IP ?= "127.0.0.1"

do_configure_prepend() {
    sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${S}/include/configs/sifive-fu540.h

    if [ -f "${WORKDIR}/${UBOOT_ENV}.txt" ]; then
        mkimage -A arm -O linux -T script -C none -n "U-Boot boot script" \
            -d ${WORKDIR}/${UBOOT_ENV}.txt ${WORKDIR}/boot.scr.uimg
    fi
}

do_deploy_append () {
    if [ -f "${WORKDIR}/boot.scr.uimg" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 755 ${WORKDIR}/boot.scr.uimg ${DEPLOY_DIR_IMAGE}
    fi
}

addtask deploy after do_install

FILES_${PN} += "/boot/boot.scr.uimg"
