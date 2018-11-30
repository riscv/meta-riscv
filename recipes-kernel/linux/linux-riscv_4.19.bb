require recipes-kernel/linux/linux-riscv-common.inc

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

LINUX_VERSION ?= "4.19"

BRANCH = "riscv-linux-4.19"
SRCREV = "998246b5d00db61bb6449aa81a1df145e5aadf6b"
