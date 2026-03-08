require linux-mainline-common.inc
FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-eswin-ebc77-mainline:"

SUMMARY = "ESWIN EBC77 Mainline Linux Kernel"

inherit kernel

DEPENDS = "u-boot-mkimage-native dtc-native"

BRANCH = "linux-6.19.y"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;protocol=https;branch=${BRANCH} \
           file://0001-perf-events-replace-READ_ONCE-with-standard-page-tab.patch \
           file://0002-mm-Move-the-fallback-definitions-of-pXXp_get.patch \
           file://0003-mm-Always-use-page-table-accessor-functions.patch \
           file://0004-checkpatch-Warn-on-page-table-access-without-accesso.patch \
           file://0005-mm-Allow-page-table-accessors-to-be-non-idempotent.patch \
           file://0006-riscv-hibernate-Replace-open-coded-pXXp_get.patch \
           file://0007-riscv-mm-Always-use-page-table-accessor-functions.patch \
           file://0008-riscv-mm-Simplify-set_p4d-and-set_pgd.patch \
           file://0009-riscv-mm-Deduplicate-_PAGE_CHG_MASK-definition.patch \
           file://0010-riscv-ptdump-Only-show-N-and-MT-bits-when-enabled-in.patch \
           file://0011-riscv-mm-Fix-up-memory-types-when-writing-page-table.patch \
           file://0012-riscv-mm-Expose-all-page-table-bits-to-assembly-code.patch \
           file://0013-riscv-alternative-Add-an-ALTERNATIVE_3-macro.patch \
           file://0014-riscv-alternative-Allow-calls-with-alternate-link-re.patch \
           file://0015-riscv-Fix-logic-for-selecting-DMA_DIRECT_REMAP.patch \
           file://0016-dt-bindings-riscv-Describe-physical-memory-regions.patch \
           file://0017-riscv-mm-Use-physical-memory-aliases-to-apply-PMAs.patch \
           file://0018-riscv-dts-starfive-jh7100-Use-physical-memory-ranges.patch \
           file://0019-riscv-dts-eswin-eic7700-Use-physical-memory-ranges-f.patch \
           file://0020-dt-bindings-clock-eswin-Documentation-for-eic7700-So.patch \
           file://0021-clk-divider-Add-devm_clk_hw_register_divider_parent_.patch \
           file://0022-clk-eswin-Add-eic7700-clock-driver.patch \
           file://0023-MAINTAINERS-Add-entry-for-ESWIN-EIC7700-clock-driver.patch \
           file://0024-riscv-dts-eswin-Add-Pinctrl-Node.patch \
           file://0025-riscv-dts-eswin-eic7700-clock-controller-node.patch \
           file://0026-riscv-dts-eswin-eic7700-Reset-controller-node.patch \
           file://0027-riscv-dts-eswin-Add-emmc-Node.patch \
           file://0028-riscv-dts-Dts-changes-for-emmc-boot.patch \
           file://0029-riscv-dts-eswin-Dts-Changes-for-SD-Card-Boot.patch \
           file://0030-riscv-Add-sata-sata_phy-node-in-dtsi-and-add-sata-re.patch \
           file://0031-riscv-dts-eswin-Add-ethernet-node-and-defconfig.patch \
           file://0032-riscv-dts-eswin-Add-ethernet0-aliase.patch \
           file://0033-riscv-dts-eswin-Add-cpu-devices-scaling-for-EIC7700-.patch \
           file://0034-riscv-dts-eswin-set-1.4GHz-as-the-maximum-cpu-freque.patch \
           file://0035-dts-add-mmc-axi-clk.patch \
           file://0036-riscv-dts-eic7700-add-sdio-et.-al.patch \
           file://hifive-premier-p550_defconfig \
           file://iwd-wifi.cfg \
"

SRCREV ?= "86818b2e7d9c22225b15f2ae91d3f35c4a07dfd9"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

LINUX_VERSION ?= "6.19.8"

INSANE_SKIP:append = " textrel"

KERNEL_DANGLING_FEATURES_WARN_ONLY = "1"

COMPATIBLE_MACHINE = "(eswin-ebc77-mainline)"
