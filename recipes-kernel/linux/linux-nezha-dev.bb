require recipes-kernel/linux/linux-mainline-common.inc
FILESEXTRAPATHS:prepend = "${FILE_DIRNAME}/linux-nezha:"
SUMMARY = "Nezha dev kernel recipe"

SRCREV_meta ?= "ea948a0983d7b7820814e5bce4eda3079201bd95"
SRCREV_machine ?= "af3f4a1caec12845b809fba959e6334ab3b52a40"
FORK ?= "tekkamanninja"
BRANCH ?= "allwinner_nezha_d1_devel"
KMETA = "kernel-meta"

# It is necessary to add to SRC_URI link to the 'yocto-kernel-cache' due to
# override of the original SRC_URI:
# "do_kernel_metadata: Check the SRC_URI for meta-data repositories or
# directories that may be missing"
SRC_URI = " \
        git://github.com/${FORK}/linux.git;name=machine;protocol=https;branch=${BRANCH} \
        git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-5.15;destsuffix=${KMETA} \
        file://0001-riscv-fix-build-with-binutils-2.38.patch \
    "

LINUX_VERSION ?= "5.16.0"
LINUX_VERSION_EXTENSION:append = "-nezha"

KERNEL_FEATURES += "features/cgroups/cgroups.cfg"
KERNEL_FEATURES += "ktypes/standard/standard.cfg"

KBUILD_DEFCONFIG = "nezha_defconfig"

COMPATIBLE_MACHINE = "(nezha-allwinner-d1)"
