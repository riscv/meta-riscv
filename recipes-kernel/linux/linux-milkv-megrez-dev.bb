SUMMARY = "Milk-V Megrez dev kernel recipe"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

DEPENDS = "u-boot-mkimage-native dtc-native"

BRANCH ?= "rockos-v6.6.y"
FORK ?= "rockos-riscv"

SRC_URI = "git://github.com/${FORK}/rockos-kernel;protocol=https;branch=${BRANCH} \
           file://megrez_defconfig \
"

SRCREV ?= "f9057cdc4816276e8ffb15f263f25ba9bd5a077e"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

LINUX_VERSION ?= "6.6.73"
LINUX_VERSION_EXTENSION:append:milkv-megrez = "-milkv-megrez"
KERNEL_VERSION_SANITY_SKIP = "1"

# Disabled since broadcom and eswin dsp out of tree driver ftbfs
do_configure:append() {
	echo 'CONFIG_BCMDHD=n' | tee -a ${B}/.config
	echo 'CONFIG_ESWIN_DSP=n' | tee -a ${B}/.config
}

COMPATIBLE_MACHINE = "(milkv-megrez)"

