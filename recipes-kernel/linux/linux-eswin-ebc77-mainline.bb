require linux-mainline-common.inc
FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-eswin-ebc77-mainline:"

SUMMARY = "ESWIN EBC77 Mainline Linux Kernel"

inherit kernel

DEPENDS = "u-boot-mkimage-native dtc-native"

BRANCH = "master"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;protocol=https;branch=${BRANCH} \
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
           file://0021-clock-eswin-Add-eic7700-clock-driver.patch \
           file://0022-MAINTAINERS-Add-entry-for-ESWIN-EIC7700-clock-driver.patch \
           file://0023-riscv-dts-Dts-changes-for-emmc-boot.patch \
           file://0024-riscv-dts-eic7700-add-sdio-et.-al.patch \
           file://hifive-premier-p550_defconfig \
           file://iwd-wifi.cfg \
"

SRCREV ?= "18f7fcd5e69a04df57b563360b88be72471d6b62"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

LINUX_VERSION ?= "6.19-rc8"

INSANE_SKIP:append = " textrel"

KERNEL_DANGLING_FEATURES_WARN_ONLY = "1"

COMPATIBLE_MACHINE = "(eswin-ebc77-mainline)"
