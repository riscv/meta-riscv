require recipes-kernel/linux/linux-mainline-common.inc

LINUX_VERSION ?= "7.0+"
KERNEL_VERSION_SANITY_SKIP = "1"
PV = "${LINUX_VERSION}+git${SRCPV}"

BRANCH = "linux-7.0.y"
SRCREV = "${AUTOREV}"
SRCPV = "${@bb.fetch2.get_srcrev(d)}"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;protocol=https;branch=${BRANCH} \
"

SRC_URI:append:dc-roma-fml13v01 = " file://ethernet.cfg"
