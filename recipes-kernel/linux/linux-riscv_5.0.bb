require recipes-kernel/linux/linux-riscv-common.inc

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

LINUX_VERSION ?= "5.0-rc3"

BRANCH = "hifive-unleashed-5.0"
SRCREV = "02225ef0623657aed2b27144238be46c6c8e1291"

SRC_URI = " \
    git://github.com/alistair23/linux.git;branch=${BRANCH} \
    file://pci.cfg \
"

# qemu uses in-tree defconfig
# freedom-u540 uses out-of-tree defconfig
SRC_URI_append_freedom-u540 = " file://defconfig"

SRC_URI_append_qemuriscv32 = " file://32bit.cfg"

# Remove this after official 5.0 release
DEFAULT_PREFERENCE = "-1"
