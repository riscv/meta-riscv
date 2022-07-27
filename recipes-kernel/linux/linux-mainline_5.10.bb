require recipes-kernel/linux/linux-mainline-common.inc
FILESEXTRAPATHS:prepend := "${THISDIR}/files/ae350-ax45mp:"

LINUX_VERSION ?= "5.10.x"
KERNEL_VERSION_SANITY_SKIP="1"

BRANCH = "linux-5.10.y"
SRCREV = "v5.10.133"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;protocol=http;branch=${BRANCH} \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRC_URI:append:ae350-ax45mp = " \
    file://0001-Add-AE350-platform-defconfig.patch \
    file://0002-Andes-support-for-Faraday-ATCMAC.patch \
    file://0003-Andes-support-for-ATCDMAC.patch \
    file://0004-Andes-support-for-FTSDC.patch \
    file://0005-Non-cacheability-and-Cache-support.patch \
    file://0006-Add-andes-sbi-call-vendor-extension.patch \
    file://0007-riscv-Porting-pte-update-function.patch \
    file://0008-Support-time32-stat64-sys_clone3-syscalls.patch \
    file://0009-dma-Support-smp-up-with-dma.patch \
    file://0010-riscv-Fix-atcdmac300-chained-irq-mapping-issue.patch \
    file://0011-riscv-DMA-add-msb-bit-patch.patch \
    file://0012-riscv-Remove-unused-Andes-SBI-call.patch \
    file://0013-faraday-ftmac100.c-Solve-NIC-RPKT_LOST-NORXBUF-issue.patch \
    file://0014-driver-net-ethernet-faraday-Add-ftmac100_debug-contr.patch \
    file://0015-Add-AE350-platform-defconfig-for-A45MP.patch \
    file://0016-Add-ae350-dts.patch \
    file://linux.cfg \
    "

# Use out-of-tree defconfig
SRC_URI:append:ae350-ax45mp = " \
    file://ae350_rv64_smp_defconfig \
    "
COMPATIBLE_MACHINE = "(ae350-ax45mp)"

KERNEL_FEATURES:remove = "cfg/fs/vfat.scc"
