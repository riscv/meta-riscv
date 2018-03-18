DESCRIPTION = "RISC-V Linux Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

COMPATIBLE_MACHINE = "(qemuriscv64)"

LINUX_KERNEL_TYPE ?= "standard"
# patch version
PV_append = ".0"

KBRANCH ?= "${BRANCH}"
KMACHINE ?= "${MACHINE}"
KMETA = "meta"
KBUILD_DEFCONFIG_qemuriscv64 = "defconfig"
KCONFIG_MODE="--alldefconfig"

DEPENDS_append = " libgcc"

KERNEL_CC_append = " ${TOOLCHAIN_OPTIONS} ${SECURITY_NOPIE_CFLAGS}"
KERNEL_LD_append = " -no-pie"

#KERNEL_FEATURES_append_riscv += " cfg/smp.scc"

#KERNEL_ALT_IMAGETYPE = ""

#KERNEL_OUTPUT = "vmlinux"
#KERNEL_IMAGETYPE = "vmlinux"

inherit kernel siteinfo

BRANCH = "riscv-linux-4.15"

SRCREV = "2b0aa1de45f63535f6d26757aa22b48515289302"
SRCREV_machine = "2b0aa1de45f63535f6d26757aa22b48515289302"

SRC_URI = "git://github.com/riscv/riscv-linux.git;branch=${BRANCH} \
           file://earlyprintk.cfg \
          "

require recipes-kernel/linux/linux-yocto.inc
KERNEL_FEATURES_remove = "features/debug/printk.scc"
