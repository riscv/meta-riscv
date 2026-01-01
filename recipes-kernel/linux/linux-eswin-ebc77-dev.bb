require linux-mainline-common.inc
FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-eswin-ebc77-dev:"

SUMMARY = "ESWIN EBC77 dev kernel recipe"

inherit kernel

DEPENDS = "u-boot-mkimage-native dtc-native"

BRANCH ?= "linux-6.6.18-EIC7X"
FORK ?= "eswincomputing"

SRC_URI = "git://github.com/${FORK}/linux-stable;protocol=https;branch=${BRANCH} \
           file://0001-eswin-ai_driver-dsp-fix-building-with-later-gcc-vers.patch \
           file://0002-net-wireless-ap12275-fix-building-out-of-tree.patch \
           file://0003-staging-media-eswin-es-media-ext-fix-building-out-of.patch \
           file://0004-memory-eswin-es_dev_buf-fix-building-out-of-tree.patch \
           file://0005-memory-eswin-fix-building-out-of-tree.patch \
           file://0006-media-i2c-fix-missing-rk628.patch \
           file://iwd-wifi.cfg \
"

SRCREV ?= "5c405e4d1fe41a25c98c0fab0dc3e94ff7c8adeb"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

LINUX_VERSION ?= "6.6.92"
LINUX_VERSION_EXTENSION:append:eswin-ebc77 = "-eswin-ebc77"
KERNEL_VERSION_SANITY_SKIP = "1"

KERNEL_DANGLING_FEATURES_WARN_ONLY = "1"

COMPATIBLE_MACHINE = "(eswin-ebc77)"

KBUILD_DEFCONFIG:eswin-ebc77 ?= "eic7700_defconfig"
