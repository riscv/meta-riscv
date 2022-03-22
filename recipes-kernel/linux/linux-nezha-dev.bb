require linux-mainline-common.inc
FILESEXTRAPATHS:prepend = "${FILE_DIRNAME}/linux-nezha:"
SUMMARY = "Nezha dev kernel recipe"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP = "1"

SRCREV = "af3f4a1caec12845b809fba959e6334ab3b52a40"
FORK ?= "tekkamanninja"
BRANCH ?= "allwinner_nezha_d1_devel"
SRC_URI = "git://github.com/${FORK}/linux.git;protocol=git;branch=${BRANCH} \
           file://enable-autofs4.cfg \
           file://enable-cgroups.cfg \
           file://enable-modules.cfg \
          "

LINUX_VERSION ?= "5.16.0"
LINUX_VERSION_EXTENSION:append = "-nezha"

KBUILD_DEFCONFIG = "nezha_defconfig"

COMPATIBLE_MACHINE = "(nezha-allwinner-d1)"
