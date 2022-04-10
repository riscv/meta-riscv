SUMMARY = "An example kernel recipe that uses the linux-yocto and oe-core"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

SRC_URI = "git://github.com/esmil/linux.git;protocol=https;branch=starlight-5.12.y \
           file://0001-riscv-Use-mno-relax-when-using-lld-linker.patch \
           file://extra.cfg \
           file://modules.cfg \
          "

LINUX_VERSION ?= "5.12.10"
LINUX_VERSION_EXTENSION:append = "-starfive"

SRCREV = "52317e6702508987fc67914c9d4270b8777f2a9b"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

PV = "${LINUX_VERSION}+git${SRCPV}"

KCONFIG_MODE = "--alldefconfig"
KBUILD_DEFCONFIG:beaglev-starlight-jh7100 = "starlight_defconfig"

COMPATIBLE_MACHINE = "(beaglev-starlight-jh7100)"
