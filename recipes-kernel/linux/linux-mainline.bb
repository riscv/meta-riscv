require recipes-kernel/linux/linux-mainline-common.inc

SUMMARY = "Mainline Linux Kernel recipe for RISC-V platforms"

FILESEXTRAPATHS:prepend:k1 := "${THISDIR}/linux-mainline-k1:"
FILESEXTRAPATHS:prepend:eswin-ebc77-mainline := "${THISDIR}/linux-eswin-ebc77-mainline:"
FILESEXTRAPATHS:prepend:milkv-duo := "${THISDIR}/linux-milkv-duo:"

KERNEL_VERSION_SANITY_SKIP = "1"
LINUX_VERSION                       ?= "7.2"
BRANCH                              ?= "master"
SRCREV                              ?= "8d3ae59288f1e7d58d76558a6ee96d533bc5019f"

# --- DEPENDS ---
DEPENDS:append:k1                    = " u-boot-tools-native"
DEPENDS:append:eswin-ebc77-mainline  = " u-boot-mkimage-native dtc-native"
DEPENDS:append:milkv-duo             = " u-boot-mkimage-native dtc-native"

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
           file://0019-dt-bindings-clock-eswin-Documentation-for-eic7700-So.patch \
           file://0020-clk-divider-Add-devm_clk_hw_register_divider_parent_.patch \
           file://0021-clk-eswin-Add-eic7700-clock-driver.patch \
           file://0022-MAINTAINERS-Add-entry-for-ESWIN-EIC7700-clock-driver.patch \
           file://0023-riscv-dts-eswin-Add-Pinctrl-Node.patch \
           file://0024-riscv-dts-eswin-eic7700-clock-controller-node.patch \
           file://0025-riscv-dts-eswin-eic7700-Reset-controller-node.patch \
           file://0026-riscv-dts-eswin-Add-emmc-Node.patch \
           file://0027-riscv-dts-Dts-changes-for-emmc-boot.patch \
           file://0028-riscv-dts-eswin-Dts-Changes-for-SD-Card-Boot.patch \
           file://0029-riscv-Add-sata-sata_phy-node-in-dtsi-and-add-sata-re.patch \
           file://0030-riscv-dts-eswin-Add-ethernet-node-and-defconfig.patch \
           file://0031-riscv-dts-eswin-Add-ethernet0-aliase.patch \
           file://0032-riscv-dts-eswin-Add-cpu-devices-scaling-for-EIC7700-.patch \
           file://0033-riscv-dts-eswin-set-1.4GHz-as-the-maximum-cpu-freque.patch \
           file://0034-dts-add-mmc-axi-clk.patch \
           file://0035-riscv-dts-eic7700-add-sdio-et.-al.patch \
           file://hifive-premier-p550_defconfig \
           file://iwd-wifi.cfg \
"

SRC_URI:append:milkv-duo = " \
        file://dts-exclude-memory-occupied-by-opensbi.patch \
        file://0001-sophgo-add-cv1800-rtcsys-reset-handler.patch \
        file://0001-riscv-dts-sophgo-cv180x-Add-PWR_GPIO-controller.patch \
        file://0002-riscv-dts-sophgo-Add-Milk-V-Duo-256M-board-support.patch \
        file://0003-duo256m-reserve-opensbi-region.patch \
        file://milkv-duo_defconfig \
        file://multi.its \
"

SRC_URI:append:milkv-duo256m = " \
           file://milkv-duo256m.cfg \
"

# --- Misc per-machine ---
INSANE_SKIP:append:eswin-ebc77-mainline                 = " textrel"
KERNEL_DANGLING_FEATURES_WARN_ONLY:eswin-ebc77-mainline = "1"

KBUILD_DEFCONFIG:eswin-ebc77-mainline = ""
KBUILD_DEFCONFIG:milkv-duo            = ""
KERNEL_DEVICETREE:milkv-duo     ?= "sophgo/cv1800b-milkv-duo.dtb"
KERNEL_DEVICETREE:milkv-duo256m ?= "sophgo/sg2002-milkv-duo256m.dtb"
KERNEL_FEATURES_RISCV:milkv-duo  = ""

do_deploy:append:milkv-duo() {
	cp ${B}/arch/riscv/boot/Image.gz ${B}
	cp ${UNPACKDIR}/multi.its ${B}
	mkimage -f ${B}/multi.its ${B}/uImage.fit
	install -m 744 ${B}/uImage.fit ${DEPLOYDIR}
	install -m 744 ${B}/arch/riscv/boot/dts/${KERNEL_DEVICETREE} ${DEPLOYDIR}/default.dtb
}
