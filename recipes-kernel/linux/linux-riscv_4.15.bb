require recipes-kernel/linux/linux-riscv-common.inc

SUMMARY = "RISC-V Community BSP RISC-V Linux kernel with backported features and fiexes"
DESCRIPTION = "Linux kernel based on mainline kernel used by RISC-V Community BSP in order to \
provide support for some backported features and fixes, or because it was applied in linux-next \
and takes some time to become part of a stable version, or because it is not applicable for \
upstreaming."

LINUX_VERSION ?= "4.15"

BRANCH = "riscv-linux-4.15"
SRCREV = "758d792057a2c0276844bc88e790f3ddabfc43ae"
