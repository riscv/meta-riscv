#@TYPE: SOC
#@NAME: Spacemit K3
#@SOC: Spacemit K3
#@DESCRIPTION: SoC configuration for the Spacemit K3 - mainline kernel version

# Only the kernel is mainline for now; the boot chain still comes from
# the vendor BSP via k3-vendor.inc:
#
# SPL/FSBL: u-boot-spl-spacemit
# esos: esos-k3
# OpenSBI: opensbi-k3
# U-Boot: u-boot-k3
#
PREFERRED_PROVIDER_virtual/kernel ?= "linux-mainline"

require conf/machine/include/k3-vendor.inc
