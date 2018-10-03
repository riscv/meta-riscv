require recipes-kernel/linux/linux-riscv-common.inc

SUMMARY = "RISC-V Community BSP RISC-V Linux kernel with backported features and fiexes"
DESCRIPTION = "Linux kernel based on mainline kernel used by RISC-V Community BSP in order to \
provide support for some backported features and fixes, or because it was applied in linux-next \
and takes some time to become part of a stable version, or because it is not applicable for \
upstreaming."

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

LINUX_VERSION ?= "4.18"

BRANCH = "riscv-linux-4.18"
SRCREV = "33e7d0092ad14e012018327bdf4e904d62e71440"
