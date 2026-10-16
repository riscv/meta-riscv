#@TYPE: SOC
#@NAME: Spacemit K3
#@SOC: Spacemit K3
#@DESCRIPTION: SoC configuration for the Spacemit K3 - mainline kernel version

require conf/machine/include/k3-common.inc

# Only the kernel is mainline-based for now; everything else comes from vendor
# variants, which are set in k3-common.inc:
#
# SPL/FSBL: u-boot-spl-spacemit
# esos: esos-k3
# OpenSBI: opensbi-k3
# U-Boot: u-boot-k3
#
PREFERRED_PROVIDER_virtual/kernel ?= "linux-mainline"
