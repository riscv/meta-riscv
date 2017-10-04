DESCRIPTION = "RISC-V Linux Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

COMPATIBLE_MACHINE = "(qemuriscv64|riscv64)"

LINUX_KERNEL_TYPE ?= "standard"
PV_append = "-rc2"
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

BRANCH = "riscv-for-submission-v9"

SRCREV = "4a8451edde0d6ebd877f2cc0585667ec1178f89e"
SRCREV_machine = "4a8451edde0d6ebd877f2cc0585667ec1178f89e"

SRC_URI = "git://github.com/riscv/riscv-linux.git;branch=${BRANCH} \
           file://0001-Use-compiler-to-find-libgcc.patch \
           file://sections.cfg \
           file://defconfig"

do_install_prepend() {
  # We are not building any modules, but the directory needs to be there.
  mkdir -p ${D}/lib/modules/${KERNEL_VERSION}/build
}
require recipes-kernel/linux/linux-yocto.inc
KERNEL_FEATURES_remove = "features/debug/printk.scc"
