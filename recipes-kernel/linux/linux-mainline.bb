require recipes-kernel/linux/linux-mainline-common.inc

SUMMARY = "Mainline Linux Kernel recipe for RISC-V platforms"

FILESEXTRAPATHS:prepend:k1 := "${THISDIR}/linux-mainline-k1:"
FILESEXTRAPATHS:prepend:eswin-ebc77-mainline := "${THISDIR}/linux-eswin-ebc77-mainline:"
FILESEXTRAPATHS:prepend:milkv-duo-common := "${THISDIR}/linux-milkv-duo:"
FILESEXTRAPATHS:prepend:zhihe-a210 := "${THISDIR}/linux-mainline-a210:"

KERNEL_VERSION_SANITY_SKIP = "1"
LINUX_VERSION                       ?= "7.2.10"
BRANCH                              ?= "linux-7.2.y"
SRCREV                              ?= "7b20d05fd38de8e0be168956f6fd241d750c7d69"

# --- DEPENDS ---
DEPENDS:append:k1                    = " u-boot-tools-native"
DEPENDS:append:eswin-ebc77-mainline  = " u-boot-mkimage-native dtc-native"
DEPENDS:append:milkv-duos = " u-boot-mkimage-native dtc-native"

# --- SRC_URI ---
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;protocol=https;branch=${BRANCH}"

SRC_URI:append:dc-roma-fml13v01 = " \
        file://ethernet.cfg \
        file://wifi.cfg \
"

SRC_URI:append:k1 = " \
        file://misc.cfg \
        file://k1-i2c.cfg \
        file://no-vector-unaligned-probe.cfg \
"

SRC_URI:append:bananapi-cm6-io = " \
        file://0001-dt-bindings-riscv-spacemit-Add-Banana-Pi-BPI-CM6-com.patch \
        file://0002-riscv-dts-spacemit-k1-Split-gmac_clk_ref-into-indepe.patch \
        file://0003-riscv-dts-spacemit-k1-Add-Banana-Pi-BPI-CM6-IO-board.patch \
"

SRC_URI:append:th1520 = " \
	file://0001-dt-binding-riscv-add-T-HEAD-CPU-reset.patch \
	file://0002-th1520-add-cpu-reset-node.patch \
	file://0001-dt-bindings-usb-Add-T-HEAD-TH1520-USB-controller.patch \
	file://0002-usb-dwc3-add-T-HEAD-TH1520-usb-driver.patch \
	file://0003-riscv-dts-thead-Add-TH1520-USB-nodes.patch \
        file://0004-riscv-dts-enable-USB-on-beaglev.patch \
        file://0005-power-reset-th1520-aon-send-RPCs-from-sleepable-handlers.patch \
        file://thead.cfg \
"

SRC_URI:append:eswin-ebc77-mainline = " \
           file://0001-mm-Move-the-fallback-definitions-of-pXXp_get.patch \
           file://0002-mm-Always-use-page-table-accessor-functions.patch \
           file://0003-checkpatch-Warn-on-page-table-access-without-accesso.patch \
           file://0004-mm-Allow-page-table-accessors-to-be-non-idempotent.patch \
           file://0005-riscv-hibernate-Replace-open-coded-pXXp_get.patch \
           file://0006-riscv-mm-Always-use-page-table-accessor-functions.patch \
           file://0007-riscv-mm-Simplify-set_p4d-and-set_pgd.patch \
           file://0008-riscv-mm-Deduplicate-_PAGE_CHG_MASK-definition.patch \
           file://0009-riscv-ptdump-Only-show-N-and-MT-bits-when-enabled-in.patch \
           file://0010-riscv-mm-Fix-up-memory-types-when-writing-page-table.patch \
           file://0011-riscv-mm-Expose-all-page-table-bits-to-assembly-code.patch \
           file://0012-riscv-alternative-Add-an-ALTERNATIVE_3-macro.patch \
           file://0013-riscv-alternative-Allow-calls-with-alternate-link-re.patch \
           file://0014-riscv-Fix-logic-for-selecting-DMA_DIRECT_REMAP.patch \
           file://0015-dt-bindings-riscv-Describe-physical-memory-regions.patch \
           file://0016-riscv-mm-Use-physical-memory-aliases-to-apply-PMAs.patch \
           file://0017-riscv-dts-starfive-jh7100-Use-physical-memory-ranges.patch \
           file://0018-riscv-dts-eswin-eic7700-Use-physical-memory-ranges-f.patch \
           file://0019-riscv-dts-eswin-Add-Pinctrl-Node.patch \
           file://0020-riscv-dts-eswin-eic7700-clock-controller-node.patch \
           file://0021-riscv-dts-eswin-eic7700-Reset-controller-node.patch \
           file://0022-riscv-dts-eswin-Add-emmc-Node.patch \
           file://0023-riscv-dts-Dts-changes-for-emmc-boot.patch \
           file://0024-riscv-dts-eswin-Dts-Changes-for-SD-Card-Boot.patch \
           file://0025-riscv-Add-sata-sata_phy-node-in-dtsi-and-add-sata-re.patch \
           file://0026-riscv-dts-eswin-Add-ethernet-node-and-defconfig.patch \
           file://0027-riscv-dts-eswin-Add-ethernet0-aliase.patch \
           file://0028-riscv-dts-eswin-Add-cpu-devices-scaling-for-EIC7700-.patch \
           file://0029-riscv-dts-eswin-set-1.4GHz-as-the-maximum-cpu-freque.patch \
           file://0030-dts-add-mmc-axi-clk.patch \
           file://0031-riscv-dts-eic7700-add-sdio-et.-al.patch \
           file://hifive-premier-p550_defconfig \
           file://iwd-wifi.cfg \
"

SRC_URI:append:milkv-duo-common = " \
        file://0001-sophgo-add-cv1800-rtcsys-reset-handler.patch \
        file://0001-riscv-dts-sophgo-cv180x-Add-PWR_GPIO-controller.patch \
        file://0002-riscv-dts-sophgo-Add-Milk-V-Duo-256M-board-support.patch \
        file://0003-riscv-dts-sophgo-cv1800b-milkv-duo-drop-reserved-mem.patch \
        file://0004-riscv-dts-sophgo-sg2002-milkv-duo256m-drop-reserved-mem.patch \
        file://milkv-duo_defconfig \
        ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', '', 'file://no-cgroups.cfg', d)} \
        file://multi.its \
"

SRC_URI:append:milkv-duo = " file://milkv-duo.cfg"
SRC_URI:append:milkv-duo256m = " file://milkv-duo256m.cfg"

SRC_URI:append:zhihe-a210 = " \
        file://0001-a210-vendor-import-10-thead-drivers.patch \
        file://0002-a210-vendor-import-20-zhihe-drivers.patch \
        file://0003-a210-vendor-import-35-driver-wiring.patch \
        file://0004-a210-vendor-import-40-a210-dts.patch \
        file://0005-a210-vendor-import-45-boot-peripherals.patch \
        file://0006-a210-vendor-import-50-dt-bindings.patch \
        file://0007-a210-vendor-import-55-optional-peripherals.patch \
        file://0008-a210-dts-build-only-a210-dev.dtb-unconditionally.patch \
        file://0009-a210-configs-add-a210_evb_defconfig-from-vendor-tree.patch \
        file://0010-watchdog-zhihe-a210-reset-the-SoC-from-a-reboot-noti.patch \
        file://0011-watchdog-zhihe-a210-also-power-off-from-the-reboot-n.patch \
        file://0012-a210-sync-A210-specific-sources-to-validated-vendor-.patch \
        file://0013-watchdog-zhihe-a210-wire-ZHIHE_WATCHDOG-into-the-bui.patch \
        file://0014-a210-usb-spi-include-linux-gpio-consumer.h-for-gpiod.patch \
        file://0015-riscv-errata-thead-apply-T-Head-CMO-errata-on-A210-X.patch \
        file://0016-PCI-zh-clear-pcie-phy-when-devm_phy_get-fails.patch \
        file://0017-riscv-dts-zhihe-a210-disable-the-rp3x1-PCIe-port.patch \
        file://0018-a210-build-port-vendor-drivers-to-6.6-7.0-API-change.patch \
        file://0019-a210-build-fix-defconfig-drops-and-more-6.6-7.0-API-.patch \
        file://0020-clk-zhihe-switch-divider-wrapper-to-.determine_rate.patch \
        file://0021-riscv-defconfig-a210-build-the-PCF8563-RTC-into-the-.patch \
        file://0022-crypto-zhihe-select-CRYPTO_LIB_CURVE25519-not-the-_G.patch \
        file://0023-a210-build-fix-v7.0-v7.1-API-drift.patch \
        file://0024-riscv-defconfig-a210-skip-the-vector-misaligned-boot.patch \
        file://0025-a210-drivers-adapt-deferred-vendor-drivers-to-mainli.patch \
        file://0026-riscv-configs-a210-enable-cpufreq-rpmsg-BMU-and-quad.patch \
        file://0027-gpio-pca953x-add-Awinic-AW9535-compatible.patch \
        file://0028-can-flexcan-add-Zhihe-A210-compatible.patch \
        file://0029-ASoC-zhihe-port-the-A210-audio-stack-to-mainline.patch \
        file://0030-ASoC-zhihe-drop-the-removed-possible-DAIFMT-clock-pr.patch \
        file://0031-cpufreq-zhihe-replace-strncpy-with-memcpy.patch \
        file://a210_evb_defconfig \
"

# --- Misc per-machine ---
INSANE_SKIP:append:eswin-ebc77-mainline                 = " textrel"
KERNEL_DANGLING_FEATURES_WARN_ONLY:eswin-ebc77-mainline = "1"
KERNEL_DANGLING_FEATURES_WARN_ONLY:zhihe-a210           = "1"

KBUILD_DEFCONFIG:eswin-ebc77-mainline = ""
KBUILD_DEFCONFIG:milkv-duo-common = ""
KBUILD_DEFCONFIG:zhihe-a210           = ""
KERNEL_DEVICETREE:milkv-duo     ?= "sophgo/cv1800b-milkv-duo.dtb"
KERNEL_DEVICETREE:milkv-duo256m ?= "sophgo/sg2002-milkv-duo256m.dtb"
# duos used to inherit the duo dtb through the old shared :milkv-duo override.
KERNEL_DEVICETREE:milkv-duos    ?= "sophgo/cv1800b-milkv-duo.dtb"
KERNEL_DEVICETREE:zhihe-a210    ?= "zhihe/a210-dev.dtb"
KERNEL_FEATURES_RISCV:milkv-duo-common = ""

do_deploy:append:milkv-duos() {
	cp ${B}/arch/riscv/boot/Image.gz ${B}
	cp ${UNPACKDIR}/multi.its ${B}
	mkimage -f ${B}/multi.its ${B}/uImage.fit
	install -m 744 ${B}/uImage.fit ${DEPLOYDIR}
	install -m 744 ${B}/arch/riscv/boot/dts/${KERNEL_DEVICETREE} ${DEPLOYDIR}/default.dtb
}
