require linux-milkv-common.inc

SUMMARY = "Milk-V Duo S mainline kernel recipe"

LINUX_VERSION ?= "6.19.9"

BRANCH = "linux-6.19.y"
SRCREV = "v6.19.9"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;protocol=https;branch=${BRANCH} \
           file://0001-riscv-dts-sophgo-add-sg2000-soc-and-milkv-duo-s.patch \
           file://0002-sophgo-add-cv1800-rtcsys-reset-handler.patch \
           file://0003-mmc-sdhci-of-dwcmshc-disable-HS200-for-Sophgo-CV18xx.patch \
           file://milkv-duo-s_defconfig \
           file://multi.its \
           "

# Feature: WiFi/BT
SRC_URI:append = " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'wifi', \
        'file://wifi.cfg', '', d)} \
    "

COMPATIBLE_MACHINE = "milkv-duo-s"
