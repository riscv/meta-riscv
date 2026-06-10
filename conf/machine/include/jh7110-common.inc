#@TYPE: SOC
#@NAME: StarFive JH7110
#@SOC: StarFive JH7110
#@DESCRIPTION: Common config for boards based on the StarFive JH7110

require jh7110.inc

# opensbi config
RISCV_SBI_FW_TEXT_START = "0x40000000"

#============================================
# Common Uboot Configuration
EXTRA_IMAGEDEPENDS += "virtual/bootloader"
UBOOT_ENTRYPOINT = "0x40200000"
UBOOT_DTB_LOADADDRESS = "0x46000000"
UBOOT_RD_LOADADDRESS = "0x46100000"
UBOOT_DTB = "1"
SPL_BINARY = "spl/u-boot-spl.bin"
SPL_NORMAL_BINARY = "u-boot-spl.bin.normal.out"
#============================================

#============================================
# Common Image Configuration
IMAGE_BOOT_FILES = " \
    fw_payload.bin \
    ${KERNEL_IMAGETYPE} \
    ${RISCV_SBI_FDT}  \
    boot.scr.uimg \
    vf2_uEnv.txt \
"
WKS_FILE ?= "visionfive2.wks"
#============================================
