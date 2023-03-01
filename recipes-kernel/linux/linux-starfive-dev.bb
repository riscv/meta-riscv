require linux-mainline-common.inc
FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-starfive:"
SUMMARY = "VisionFive dev kernel recipe"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP = "1"

SRCREV = "${AUTOREV}"
BRANCH = "visionfive"
FORK ?= "starfive-tech"
SRC_URI = "git://github.com/${FORK}/linux.git;protocol=https;branch=${BRANCH} \
           file://extra.cfg \
           file://modules.cfg \
          "

LINUX_VERSION ?= "6.2.0"
LINUX_VERSION_EXTENSION:append:beaglev-starlight-jh7100 = "-starlight"

KBUILD_DEFCONFIG:beaglev-starlight-jh7100 = "starfive_jh7100_fedora_defconfig"
KBUILD_DEFCONFIG:visionfive = "visionfive_defconfig"

COMPATIBLE_MACHINE = "(beaglev-starlight-jh7100|visionfive)"
