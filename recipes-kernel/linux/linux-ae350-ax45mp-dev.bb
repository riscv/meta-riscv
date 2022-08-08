require linux-mainline-common.inc
SUMMARY = "AE350 AX45MP dev kernel recipe"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP = "1"

SRCREV ?= "81c000a116c85b65b5f9df6f42bd5bebe16594e4"
BRANCH:ae350-ax45mp = "v5.18.y_ae350-ax45mp"
FORK ?= "andestech"
SRC_URI = "git://github.com/${FORK}/linux.git;protocol=https;branch=${BRANCH} \
          "

LINUX_VERSION ?= "v5.18.14"
LINUX_VERSION_EXTENSION:append:ae350-ax45mp = "-ae350"

KBUILD_DEFCONFIG:ae350-ax45mp = "ae350_ax45mp_defconfig"

COMPATIBLE_MACHINE = "(ae350-ax45mp)"
