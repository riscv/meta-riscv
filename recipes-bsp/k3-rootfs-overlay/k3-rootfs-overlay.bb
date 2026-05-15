SUMMARY = "K3 Board Configuration Files (Weston, Network)"
DESCRIPTION = "Image-specific configuration files for K3 boards (Weston compositor, systemd network). WiFi/BT firmware is provided by linux-firmware subpackages."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://weston \
        file://network"

PACKAGES =+ "${PN}-weston ${PN}-network"

S = "${UNPACKDIR}"

do_install() {
    if [ -d "${S}/weston" ]; then
        if [ -d "${S}/weston/etc" ]; then
            install -d ${D}${sysconfdir}
            cp -dr ${S}/weston/etc/* ${D}${sysconfdir}/
        fi

        if [ -d "${S}/weston/usr" ]; then
            install -d ${D}${prefix}
            cp -dr ${S}/weston/usr/* ${D}${prefix}/
        fi
    fi

    if [ -d "${S}/network/etc" ]; then
        install -d ${D}${sysconfdir}
        cp -dr ${S}/network/etc/* ${D}${sysconfdir}/
    fi
}

FILES:${PN}-weston = " \
    ${sysconfdir}/xdg \
    ${sysconfdir}/systemd/system/weston.service.d \
    /usr/local/bin/weston-autostart.sh \
    /root \
    /usr/share \
"

FILES:${PN}-network = " \
    ${sysconfdir}/systemd/network \
"

RDEPENDS:${PN}-network = "systemd"

# WiFi/BT firmware from linux-firmware (rtw89 driver)
RDEPENDS:${PN} = " \
    linux-firmware-rtl8851 \
    linux-firmware-rtl8852 \
    linux-firmware-rtl8922 \
"

COMPATIBLE_MACHINE = "(k3)"
