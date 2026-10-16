#@TYPE: SOC
#@NAME: Spacemit K3
#@SOC: Spacemit K3
#@DESCRIPTION: SoC configuration for the Spacemit K3 - vendor repo version

require conf/machine/include/k3-common.inc

PREFERRED_PROVIDER_virtual/kernel ?= "linux-k3"
