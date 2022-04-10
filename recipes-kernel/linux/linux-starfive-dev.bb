require linux-mainline-common.inc
FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-starfive:"
SUMMARY = "BeagleV dev kernel recipe"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP = "1"

SRCREV = "${AUTOREV}"
FORK ?= "starfive-tech"
BRANCH ?= "esmil_starlight"
SRC_URI = "git://github.com/${FORK}/linux.git;protocol=https;branch=${BRANCH} \
           file://0001-riscv-Use-mno-relax-when-using-lld-linker.patch \
           file://extra.cfg \
           file://modules.cfg \
          "

LINUX_VERSION ?= "5.14.0"
LINUX_VERSION_EXTENSION:append = "-starlight"

KBUILD_DEFCONFIG:beaglev-starlight-jh7100 = "beaglev_defconfig"

COMPATIBLE_MACHINE = "(beaglev-starlight-jh7100)"
