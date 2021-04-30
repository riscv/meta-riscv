SUMMARY = "An example kernel recipe that uses the linux-yocto and oe-core"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

SRC_URI = "git://github.com/starfive-tech/linux.git;protocol=git;branch=Fedora"

LINUX_VERSION ?= "5.10.6"
LINUX_VERSION_EXTENSION_append = "-beaglev"

SRCREV = "710cf052d6abda73584481d920b4b6befc7240ea"

PV = "${LINUX_VERSION}+git${SRCPV}"

KCONFIG_MODE = "--alldefconfig"
KBUILD_DEFCONFIG_beaglev-starlight-jh7100 = "starfive_vic7100_evb_sd_net_defconfig"

COMPATIBLE_MACHINE = "(beaglev-starlight-jh7100)"
