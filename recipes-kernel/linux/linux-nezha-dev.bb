require recipes-kernel/linux/linux-mainline-common.inc
FILESEXTRAPATHS:prepend = "${FILE_DIRNAME}/linux-nezha:"
SUMMARY = "Nezha dev kernel recipe"

SRCREV_meta ?= "b7fc5d5cecaad5d97164bac8db5b5ee72f563bb0"
SRCREV_machine ?= "b466df90d48fb7ef03f331e0c73c8012ac03a12e"
FORK ?= "smaeul"
BRANCH ?= "d1/all"
KMETA = "kernel-meta"

# It is necessary to add to SRC_URI link to the 'yocto-kernel-cache' due to
# override of the original SRC_URI:
# "do_kernel_metadata: Check the SRC_URI for meta-data repositories or
# directories that may be missing"
SRC_URI = " \
        git://github.com/${FORK}/linux.git;name=machine;protocol=https;branch=${BRANCH} \
        git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=master;destsuffix=${KMETA} \
        file://defconfig \
    "

LINUX_VERSION ?= "6.1.0"
LINUX_VERSION_EXTENSION:append = "-nezha"

KERNEL_FEATURES += "features/cgroups/cgroups.cfg"
KERNEL_FEATURES += "ktypes/standard/standard.cfg"

# using out-of-tree
#KBUILD_DEFCONFIG = "nezha_defconfig"

COMPATIBLE_MACHINE = "(nezha-allwinner-d1)"

KERNEL_VERSION_SANITY_SKIP="1"
