SUMMARY = "An example kernel recipe that uses the linux-yocto and oe-core"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

SRC_URI = "git://github.com/starfive-tech/linux.git;protocol=git;branch=esmil_starlight \
           file://0001-riscv-Use-mno-relax-when-using-lld-linker.patch \
           file://extra.cfg \
           file://modules.cfg \
          "

LINUX_VERSION ?= "5.12.3"
LINUX_VERSION_EXTENSION_append = "-starfive"

SRCREV = "96d371a61bf39024a6b11b5610fee602dc797f05"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

PV = "${LINUX_VERSION}+git${SRCPV}"

KCONFIG_MODE = "--alldefconfig"
KBUILD_DEFCONFIG_beaglev-starlight-jh7100 = "starlight_defconfig"

COMPATIBLE_MACHINE = "(beaglev-starlight-jh7100)"
