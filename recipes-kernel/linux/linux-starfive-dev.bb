require linux-mainline-common.inc
FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-starfive:"
SUMMARY = "JH7110 dev kernel recipe"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP = "1"

SRCREV = "${AUTOREV}"

# pin srcrev for now to have a fixed target
# release JH7110_VF2_6.12_v6.0.0
SRCREV:visionfive2 = "4cecf169f38eb94b40e307f5f870055e4d9d64f1"
BRANCH:visionfive2 = "branch=JH7110_VisionFive2_6.12.y_devel"

FORK ?= "starfive-tech"

REPO ?= "linux"

SRC_URI = "git://github.com/${FORK}/${REPO}.git;protocol=https;${BRANCH} \
           file://0001-riscv-disable-generation-of-unwind-tables.patch \
           file://0001-gcc-plugins-Fix-build-for-upcoming-GCC-release.patch \
           file://0001-riscv-fix-building-external-modules.patch \
           file://0001-gcc-plugins-Rename-last_stmt-for-GCC-14.patch \
           file://0001-eswin-Repace-NULL-with-0-where-it-is-converted-from-.patch \
           file://modules.cfg \
          "

SRC_URI:jh7110 = " \
           git://github.com/${FORK}/${REPO}.git;protocol=https;${BRANCH} \
           file://0001-riscv-disable-generation-of-unwind-tables.patch \
           file://0001-Allow-building-of-PVR-GPU-driver-as-module.patch \
           file://0001-gcc-plugins-Rename-last_stmt-for-GCC-14.patch \
           file://0001-eswin-Repace-NULL-with-0-where-it-is-converted-from-.patch \
           file://0001-kbuild-Do-not-use-NOTIMMEDIATE.patch \
           file://0001-gcc-plugins-Always-define-CONST_CAST_GIMPLE-and-CONS.patch \
           file://0001-drm-img-rogue-fix-build-with-gcc-16.patch \
           file://visionfive2-graphics.cfg \
           file://modules.cfg \
"

SRCREV_yocto-kernel-cache = "96ce9b7ee67702aec75816c4d44a527061c418c5"

SRC_URI:append:visionfive2 = " git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=yocto-kernel-cache;branch=yocto-6.12;destsuffix=kernel-meta;protocol=https"

LINUX_VERSION ?= "6.2.0"
LINUX_VERSION:jh7110 = "6.12.5"

KBUILD_DEFCONFIG:visionfive2 = "starfive_visionfive2_defconfig"

KERNEL_FEATURES:remove:riscv32 = " ${KERNEL_FEATURES_RISCV}"
KERNEL_FEATURES:remove:riscv64 = " ${KERNEL_FEATURES_RISCV}"


COMPATIBLE_MACHINE = "(jh7110)"
