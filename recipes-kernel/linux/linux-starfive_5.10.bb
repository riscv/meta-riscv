SUMMARY = "An example kernel recipe that uses the linux-yocto and oe-core"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

SRC_URI = "git://github.com/starfive-tech/linux.git;protocol=git;branch=Fedora \
           file://0001-power-reset-Add-TPS65086-restart-driver.patch \
           file://0002-mfd-tps65086-make-interrupt-line-optional.patch \
           file://0003-riscv-dts-Add-JH7100-and-BeagleV-Starlight-support.patch \
           file://extra.cfg \
           file://wifi.cfg \
          "

LINUX_VERSION ?= "5.10.6"
LINUX_VERSION_EXTENSION_append = "-starfive"

SRCREV = "fc3f9b5caf0e15352378a522c1cfbcc0e9a156c6"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

PV = "${LINUX_VERSION}+git${SRCPV}"

KCONFIG_MODE = "--alldefconfig"
KBUILD_DEFCONFIG_beaglev-starlight-jh7100 = "starfive_vic7100_evb_sd_net_defconfig"

COMPATIBLE_MACHINE = "(beaglev-starlight-jh7100)"
