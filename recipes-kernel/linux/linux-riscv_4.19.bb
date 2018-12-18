require recipes-kernel/linux/linux-riscv-common.inc

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

LINUX_VERSION ?= "4.19"

BRANCH = "riscv-linux-4.19"
SRCREV = "998246b5d00db61bb6449aa81a1df145e5aadf6b"

SRC_URI_append_freedom-u540 = " \
                                file://0001-risc-v-Fix-issue-ignoring-CONFIG_CMDLINE_OVERRIDE.patch \
                                file://0004-4.19-gemgxl-mgmt-implement-clock-switch-for-GEM-tx_clk.patch \
                                file://0007-4.19-gpio-sifive-support-GPIO-on-SiFive-SoCs.patch \
                              "
