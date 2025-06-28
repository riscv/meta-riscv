SUMMARY = "Firmware binaries and configs for Orange Pi devices"
DESCRIPTION = "Contains firmware binaries and configuration files used by various Orange Pi boards for components.Sourced from the official Orange Pi firmware repository"
LICENSE = "CLOSED"

inherit allarch

SRC_URI = "git://github.com/orangepi-xunlong/firmware.git;protocol=https;branch=master"
SRCREV = "db5e86200ae592c467c4cfa50ec0c66cbc40b158"


do_install:orangepi-rv2() {
	install -d ${D}${nonarch_base_libdir}/firmware
	install -d ${D}${nonarch_base_libdir}/firmware/brcm
	# Bluetooth firmware
	install -m 644 ${S}/BCM4345C5.hcd ${D}${nonarch_base_libdir}/firmware/
	install -m 644 ${S}/BCM4345C5.hcd ${D}${nonarch_base_libdir}/firmware/brcm/
	# WiFi firmware
	install -m 644 ${S}/nvram_ap6256.txt-orangepirv2 ${D}${nonarch_base_libdir}/firmware/nvram_ap6256.txt
	install -m 644 ${S}/fw_bcm43456c5_ag.bin ${D}${nonarch_base_libdir}/firmware/
}

FILES:${PN} = "${nonarch_base_libdir}/firmware"

COMPATIBLE_MACHINE = "(orangepi-rv2)"
