require linux-mainline-common.inc
FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-starfive:"
SUMMARY = "VisionFive dev kernel recipe"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP = "1"

SRCREV = "${AUTOREV}"

# pin srcrev for now to have a fixed target
# release JH7110_VF2_6.6_v5.13.1
SRCREV:visionfive2 = "d0e7c0486d768a294f4f2b390d00dab8bee5d726"
SRCREV:star64 = "e4c0928f1e42ed82ab9fa8918bc7094d3c0414d8"

BRANCH = "branch=visionfive"
BRANCH:visionfive2 = "nobranch=1"
BRANCH:star64 = "branch=Star64_devel"
BRANCH:beaglev-starlight-jh7100 = "branch=visionfive-6.4.y"

FORK ?= "starfive-tech"
FORK:star64 ?= "Fishwaldo"

REPO ?= "linux"
REPO:star64 ?= "Star64_linux"

SRC_URI = "git://github.com/${FORK}/${REPO}.git;protocol=https;${BRANCH} \
           file://0001-riscv-disable-generation-of-unwind-tables.patch \
           file://0001-gcc-plugins-Fix-build-for-upcoming-GCC-release.patch \
           file://0001-riscv-fix-building-external-modules.patch \
           file://0001-gcc-plugins-Rename-last_stmt-for-GCC-14.patch \
           file://0001-eswin-Repace-NULL-with-0-where-it-is-converted-from-.patch \
           file://0001-perf-cpumap-Make-counter-as-unsigned-ints.patch \
           file://modules.cfg \
          "

SRC_URI:append:visionfive = " \
           file://extra.cfg \
"

SRC_URI:jh7110 = " \
           git://github.com/${FORK}/${REPO}.git;protocol=https;${BRANCH} \
           file://0001-riscv-disable-generation-of-unwind-tables.patch \
           file://0001-Allow-building-of-PVR-GPU-driver-as-module.patch \
           file://0001-gcc-plugins-Rename-last_stmt-for-GCC-14.patch \
           file://0001-eswin-Repace-NULL-with-0-where-it-is-converted-from-.patch \
           file://0001-kbuild-Do-not-use-NOTIMMEDIATE.patch \
           file://visionfive2-graphics.cfg \
           file://modules.cfg \
"

SRC_URI:beaglev-starlight-jh7100 = " \
           git://github.com/${FORK}/${REPO}.git;protocol=https;${BRANCH} \
           file://0001-gcc-plugins-Rename-last_stmt-for-GCC-14.patch \
           file://modules.cfg \
           file://extra.cfg \
"
SRCREV_yocto-kernel-cache = "6df14e0eacedd9f025d88e310ef7fcc0bde6f550"

SRC_URI:append:visionfive2 = " git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=yocto-kernel-cache;branch=yocto-6.6;destsuffix=kernel-meta;protocol=https"

LINUX_VERSION ?= "6.2.0"
LINUX_VERSION:jh7110 = "6.6.20"
LINUX_VERSION:beaglev-starlight-jh7100 = "6.4.4"
LINUX_VERSION_EXTENSION:append:beaglev-starlight-jh7100 = "-starlight"

KBUILD_DEFCONFIG:beaglev-starlight-jh7100 = "starfive_jh7100_fedora_defconfig"
KBUILD_DEFCONFIG:visionfive = "visionfive_defconfig"
KBUILD_DEFCONFIG:visionfive2 = "starfive_visionfive2_defconfig"
KBUILD_DEFCONFIG:star64 = "pine64_star64_defconfig"

KERNEL_FEATURES:remove:riscv32 = " ${KERNEL_FEATURES_RISCV}"
KERNEL_FEATURES:remove:riscv64 = " ${KERNEL_FEATURES_RISCV}"


COMPATIBLE_MACHINE = "(beaglev-starlight-jh7100|visionfive|jh7110)"
