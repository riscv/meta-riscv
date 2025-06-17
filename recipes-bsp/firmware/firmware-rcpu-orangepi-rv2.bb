FILESEXTRAPATHS:prepend := "${THISDIR}/orangepi-rv2:"

SUMMARY = "Orange Pi RV2 RCPU firmware (esos.elf)"
DESCRIPTION = "esos.elf is the firmware for the RCPU (Real-Time CPU), responsible for initializing some hardware modules and forwarding HDMI Audio interrupts \
It is dependent on the Linux kernel and the system will not boot without it. It needs to be installed in the initramfs /lib/firmware directory"
LICENSE = "CLOSED"

SRC_URI = "file://esos.elf"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}/lib/firmware
    install -m 0644 ${UNPACKDIR}/esos.elf ${D}/lib/firmware/
}

FILES:${PN} = "/lib/firmware"

INSANE_SKIP:${PN} = "usrmerge arch already-stripped"

COMPATIBLE_MACHINE = "(orangepi-rv2)"
