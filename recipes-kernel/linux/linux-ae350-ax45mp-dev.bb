require linux-mainline-common.inc
SUMMARY = "AE350 AX45MP dev kernel recipe"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP = "1"

SRCREV ?= "c72c2c9d4f8334f233973339f517f45e24d5e8b5"
BRANCH:ae350-ax45mp = "v6.0.y_ae350-ax45mp"
FORK ?= "andestech"
SRC_URI = "git://github.com/${FORK}/linux.git;protocol=https;branch=${BRANCH} \
          "

LINUX_VERSION ?= "v6.0.2"
LINUX_VERSION_EXTENSION:append:ae350-ax45mp = "-ae350"

KBUILD_DEFCONFIG:ae350-ax45mp = "ae350_ax45mp_defconfig"

COMPATIBLE_MACHINE = "(ae350-ax45mp)"
