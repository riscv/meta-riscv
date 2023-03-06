require recipes-kernel/linux/linux-mainline-common.inc

SUMMARY = "Allwinner D1 dev kernel recipe"
SRCREV_meta ?= "b7fc5d5cecaad5d97164bac8db5b5ee72f563bb0"
SRCREV_machine ?= "ca67838d84af4c9f85d06311c9e98e1adf46308f"
FORK ?= "smaeul"
BRANCH ?= "riscv/d1-wip"
KMETA = "kernel-meta"

# It is necessary to add to SRC_URI link to the 'yocto-kernel-cache' due to
# override of the original SRC_URI:
# "do_kernel_metadata: Check the SRC_URI for meta-data repositories or
# directories that may be missing"
SRC_URI = "git://github.com/${FORK}/linux.git;name=machine;protocol=https;branch=${BRANCH} \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=master;destsuffix=${KMETA} \
           "

SRC_URI:append:mangopi-mq-pro = " \
	       	   file://mangopi-mq-pro.cfg \
		   file://0001-Bluetooth-Add-new-quirk-for-broken-local-ext-feature.patch \
		   file://0002-Bluetooth-btrtl-add-support-for-the-RTL8723CS.patch \
		   file://0003-drivers-net-bluetooth-Enable-quirk-for-broken-local-.patch \
		 "
#
LINUX_VERSION ?= "6.1.0"
KERNEL_FEATURES += "features/cgroups/cgroups.cfg"
KERNEL_FEATURES += "ktypes/standard/standard.cfg"

KERNEL_VERSION_SANITY_SKIP="1"

COMPATIBLE_MACHINE = "(nezha-allwinner-d1|mangopi-mq-pro)"
LINUX_VERSION_EXTENSION:append:nezha-allwinner-d1 = "-nezha"
LINUX_VERSION_EXTENSION:append:mangopi-mq-pro = "-mangopi"


## Should be oveerriten in machine conf
KBUILD_DEFCONFIG ?= "allwinner_defconfig"
