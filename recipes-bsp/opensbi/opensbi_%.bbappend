# Mainline OpenSBI supports the C906 out of the box, but it needs a few tweaks
# and a new reset driver for the sunxi watchdog.
FILESEXTRAPATHS:prepend:nezha := "${THISDIR}/files:"

SRC_URI:append:nezha = " \
    file://0001-lib-utils-fdt-Require-match-data-to-be-const.patch \
    file://0002-lib-utils-timer-Add-a-separate-compatible-for-the-D1.patch \
"

SRC_URI:c910 = "git://github.com/revyos/thead-opensbi.git;branch=th1520;protocol=https"
SRCREV:c910 = "61d7484c752a5e4c464d5dc18e21d9ac67fbbefa"

INSANE_SKIP:${PN}:nezha += "ldflags"
