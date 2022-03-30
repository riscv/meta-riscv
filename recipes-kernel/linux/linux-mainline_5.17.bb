require recipes-kernel/linux/linux-mainline-common.inc

LINUX_VERSION ?= "5.17+"
KERNEL_VERSION_SANITY_SKIP="1"
PV = "${LINUX_VERSION}+git${SRCPV}"

BRANCH = "linux-5.17.y"
SRCREV = "${AUTOREV}"
SRCPV = "${@bb.fetch2.get_srcrev(d)}"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;branch=${BRANCH} \
"
