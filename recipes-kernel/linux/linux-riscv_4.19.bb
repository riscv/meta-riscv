require recipes-kernel/linux/linux-riscv-common.inc

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

LINUX_VERSION ?= "4.19"

BRANCH = "riscv-linux-4.19"
SRCREV = "15f0d45fb94880e203d62bdb156f812c2dbfc3d3"
