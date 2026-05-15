DESCRIPTION = "Linux Kernel 6.18 for K3 Board (Spacemit K3 RISC-V Processor)"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

# Force version number
LINUX_VERSION = "6.18.3"
PV = "${LINUX_VERSION}+git${SRCPV}"
LINUX_VERSION_EXTENSION = ""

# Source code location and version control
SRC_URI = "git://github.com/spacemit-com/linux-6.18.git;protocol=https;tag=k3-br-v1.0.0;nobranch=1"

# Use AUTOREV for development, replace with a stable commit SHA for reproducible builds

# Machine compatibility
COMPATIBLE_MACHINE = "k3"
ERROR_QA:remove = "version-going-backwards"


# Kernel configuration
KCONFIG_MODE = "alldefconfig"
KBUILD_DEFCONFIG = "k3_bianbu_defconfig"

# Remove RISC-V kernel features from linux-yocto.inc since this is not a yocto kernel
KERNEL_FEATURES:remove:riscv64 = " ${KERNEL_FEATURES_RISCV}"

# Deploy Image file in addition to FIT Image
# Also deploy compressed kernel as vmlinuz-<version> for titan bootfs
do_deploy:append() {
    if [ -f ${B}/arch/${ARCH}/boot/Image ]; then
        install -m 0644 ${B}/arch/${ARCH}/boot/Image ${DEPLOYDIR}/Image
    fi

    # Generate and deploy compressed kernel (vmlinuz)
    if [ -f ${B}/arch/${ARCH}/boot/Image.gz ]; then
        install -m 0644 ${B}/arch/${ARCH}/boot/Image.gz ${DEPLOYDIR}/vmlinuz-${LINUX_VERSION}-generic
    elif [ -f ${B}/arch/${ARCH}/boot/Image ]; then
        gzip -9 -c ${B}/arch/${ARCH}/boot/Image > ${DEPLOYDIR}/vmlinuz-${LINUX_VERSION}-generic
    fi

    # Deploy System.map and .config
    if [ -f ${B}/System.map ]; then
        install -m 0644 ${B}/System.map ${DEPLOYDIR}/System.map-${LINUX_VERSION}-generic
    fi
    if [ -f ${B}/.config ]; then
        install -m 0644 ${B}/.config ${DEPLOYDIR}/config-${LINUX_VERSION}-generic
    fi
}
