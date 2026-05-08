require linux-milkv-common.inc

SUMMARY = "Milk-V Duo mainline kernel recipe"

LINUX_VERSION ?= "6.17.8"

BRANCH = "linux-6.17.y"
SRCREV = "v6.17.8"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;protocol=https;branch=${BRANCH} \
           file://dts-exclude-memory-occupied-by-opensbi.patch \
           file://0001-sophgo-add-cv1800-rtcsys-reset-handler.patch \
           file://milkv-duo_defconfig \
           file://multi.its \
           "

KERNEL_DEVICETREE:milkv-duo ?= "sophgo/cv1800b-milkv-duo.dtb"

COMPATIBLE_MACHINE = "milkv-duo"
