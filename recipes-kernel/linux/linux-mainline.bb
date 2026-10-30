require recipes-kernel/linux/linux-mainline-common.inc

SUMMARY = "Mainline Linux Kernel recipe for RISC-V platforms"

FILESEXTRAPATHS:prepend:k1 := "${THISDIR}/linux-mainline-k1:"
FILESEXTRAPATHS:prepend:k3 := "${THISDIR}/linux-mainline-k3:"
FILESEXTRAPATHS:prepend:eswin-ebc77-mainline := "${THISDIR}/linux-eswin-ebc77-mainline:"
FILESEXTRAPATHS:prepend:milkv-duo-common := "${THISDIR}/linux-milkv-duo:"
FILESEXTRAPATHS:prepend:visionfive2 := "${THISDIR}/linux-mainline-visionfive2:"

KERNEL_VERSION_SANITY_SKIP = "1"
LINUX_VERSION                       ?= "7.3"
BRANCH                              ?= "linux-7.3.y"
SRCREV                              ?= "c03c1a14507dfaf8b856c2608f9ac216021fc222"

# --- DEPENDS ---
DEPENDS:append:k1                    = " u-boot-tools-native"
DEPENDS:append:eswin-ebc77-mainline  = " u-boot-mkimage-native dtc-native"

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

SRC_URI:append:k3 = " \
        file://0001-riscv-dts-spacemit-k3-move-USB3-phy-to-board-level.patch \
        file://0002-riscv-dts-spacemit-k3-add-USB3-B-and-C-controllers-f.patch \
        file://0003-riscv-dts-spacemit-k3-add-rfkill-node-for-Bluetooth-.patch \
        file://0004-riscv-dts-spacemit-k3-add-rfkill-node-for-WLAN-on-th.patch \
        file://0005-dt-bindings-timer-thead-c900-aclint-mtimer-Add-Space.patch \
        file://0006-dt-bindings-interrupt-controller-thead-c900-aclint-m.patch \
        file://0007-dt-bindings-interrupt-controller-thead-c900-aclint-s.patch \
        file://0008-dt-bindings-timer-sifive-clint-Remove-spacemit-k3-cl.patch \
        file://0009-irqchip-aclint-sswi-Add-support-for-SpacemiT-K3.patch \
        file://0010-riscv-dts-spacemit-k3-Replace-incorrect-CLINT-node-w.patch \
        file://0011-riscv-dts-spacemit-k3-Drop-guest-properties-from-M-l.patch \
        file://0012-riscv-dts-spacemit-k3-fix-IMSIC-num-guest-ids.patch \
        file://0013-riscv-dts-spacemit-k3-Add-UFS-support.patch \
        file://0014-scsi-ufs-spacemit-dt-bindings-Add-UFS-controller-for.patch \
        file://0015-scsi-ufs-spacemit-k3-Add-UFS-Host-Controller-driver.patch \
        file://k3.cfg \
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
        file://0003-riscv-dts-sophgo-cv1800b-milkv-duo-drop-reserved-mem.patch \
        file://0004-riscv-dts-sophgo-sg2002-milkv-duo256m-drop-reserved-mem.patch \
        file://milkv-duo_defconfig \
        ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', '', 'file://no-cgroups.cfg', d)} \
"

SRC_URI:append:milkv-duo = " file://milkv-duo.cfg"
SRC_URI:append:milkv-duo256m = " file://milkv-duo256m.cfg"
SRC_URI:append:milkv-duos = " file://milkv-duos.cfg"

SRC_URI:append:visionfive2 = " file://misc.cfg"

# --- Misc per-machine ---
INSANE_SKIP:append:eswin-ebc77-mainline                 = " textrel"
KERNEL_DANGLING_FEATURES_WARN_ONLY:eswin-ebc77-mainline = "1"

KBUILD_DEFCONFIG:eswin-ebc77-mainline = ""
KBUILD_DEFCONFIG:milkv-duo-common = ""
KERNEL_FEATURES_RISCV:milkv-duo-common = ""
