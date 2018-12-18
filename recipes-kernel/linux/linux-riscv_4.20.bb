require recipes-kernel/linux/linux-riscv-common.inc

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

LINUX_VERSION ?= "4.20-rc7"

BRANCH = "for-next"
SRCREV = "9e821587b1c02cd14e28ed9e0849d260b0d3d245"

SRC_URI_append_freedom-u540 = " \
                                file://0004-4.20-gemgxl-mgmt-implement-clock-switch-for-GEM-tx_clk.patch \
                                file://0007-4.20-gpio-sifive-support-GPIO-on-SiFive-SoCs.patch \
                              "

# Remove this after official 4.20 release
DEFAULT_PREFERENCE = "-1"
