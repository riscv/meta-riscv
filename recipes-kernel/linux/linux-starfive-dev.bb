require linux-mainline-common.inc
FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-starfive:"
SUMMARY = "BeagleV dev kernel recipe"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP = "1"

SRCREV = "${AUTOREV}"
SRCREV:visionfive = "f754a58449f0de76312bf84249139f5d8b232c66"
BRANCH:visionfive = "esmil_starlight"
BRANCH:beaglev-starlight-jh7100 = "beaglev_fedora_devel"
FORK ?= "starfive-tech"
SRC_URI = "git://github.com/${FORK}/linux.git;protocol=https;branch=${BRANCH} \
           file://0001-riscv-Use-mno-relax-when-using-lld-linker.patch \
           file://extra.cfg \
           file://modules.cfg \
          "

LINUX_VERSION ?= "5.19.0-rc4"
LINUX_VERSION_EXTENSION:append:beaglev-starlight-jh7100 = "-starlight"

KBUILD_DEFCONFIG:beaglev-starlight-jh7100 = "beaglev_defconfig"
KBUILD_DEFCONFIG:visionfive = "visionfive_defconfig"

COMPATIBLE_MACHINE = "(beaglev-starlight-jh7100|visionfive)"
