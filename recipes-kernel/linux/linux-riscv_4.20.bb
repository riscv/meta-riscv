require recipes-kernel/linux/linux-riscv-common.inc

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

LINUX_VERSION ?= "4.20-rc5"

BRANCH = "for-next"
SRCREV = "7ac3574ad42ff7b58f29a89390c0d2e6ac55e7bb"

# Remove this after official 4.20 release
DEFAULT_PREFERENCE = "-1"
