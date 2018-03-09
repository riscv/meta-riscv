DESCRIPTION = "RISC-V Linux Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

COMPATIBLE_MACHINE = "(qemuriscv64|riscv64)"

LINUX_KERNEL_TYPE ?= "standard"
# patch version
PV_append = ".0"

# KMETA ?= ""
KBRANCH ?= "${BRANCH}"
KMACHINE ?= "${MACHINE}"
KMETA = "meta"

DEPENDS_append = " libgcc"
KERNEL_CC_append = " ${TOOLCHAIN_OPTIONS} ${SECURITY_NOPIE_CFLAGS}"
KERNEL_LD_append = " -no-pie"

#KERNEL_FEATURES_append_riscv += " cfg/smp.scc"

KERNEL_ALT_IMAGETYPE = ""

#KERNEL_OUTPUT = "vmlinux"
#KERNEL_IMAGETYPE = "vmlinux"

inherit kernel siteinfo

BRANCH = "riscv-linux-4.15"

SRCREV = "7501d87f7ebf8337ab2efa3fe692612a3b845c6f"
SRCREV_machine = "7501d87f7ebf8337ab2efa3fe692612a3b845c6f"

SRC_URI = "git://github.com/riscv/riscv-linux.git;branch=${BRANCH} \
           file://sections.cfg \
           file://defconfig"

do_install_prepend() {
  # We are not building any modules, but the directory needs to be there.
  mkdir -p ${D}/lib/modules/${KERNEL_VERSION}/build
}
require recipes-kernel/linux/linux-yocto.inc
KERNEL_FEATURES_remove = "features/debug/printk.scc"
