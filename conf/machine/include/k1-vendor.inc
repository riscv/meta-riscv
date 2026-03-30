#@TYPE: SOC
#@NAME: SpacemiT K1
#@SOC: SpacemiT K1
#@DESCRIPTION: SOC configuration for the SpacemiT K1 SoC - vendor repo version

# This include is ONLY to be used for vendor-tree based builds for K1 platforms
# like the OrangePi RV2 and R2S. For mainline support, rely on k1.inc.

require conf/machine/include/riscv/tune-riscv.inc
require conf/machine/include/soc-family.inc

SOC_FAMILY = "k1"
