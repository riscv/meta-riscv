require recipes-kernel/linux/linux-riscv-common.inc

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

LINUX_VERSION ?= "4.18"

BRANCH = "riscv-linux-4.15"
SRCREV = "fe92d7905c6ea0ebeabeb725b8040754ede7c220"
