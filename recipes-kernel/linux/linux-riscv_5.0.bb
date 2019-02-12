require recipes-kernel/linux/linux-riscv-common.inc

LINUX_VERSION ?= "5.0"

BRANCH = "hifive-unleashed-5.0"
SRCREV = "1fc509f5f47f0ef5b723666ab3670898ac3208a1"

SRC_URI = " \
    git://github.com/alistair23/linux.git;branch=${BRANCH} \
    file://pci.cfg \
"

# qemu uses in-tree defconfig
# freedom-u540 uses out-of-tree defconfig
SRC_URI_append_freedom-u540 = " file://defconfig"

SRC_URI_append_qemuriscv32 = " file://32bit.cfg"
