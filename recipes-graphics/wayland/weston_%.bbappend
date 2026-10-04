FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:jh7110 = " \
    file://0006-libweston-reduce-checks-for-dmabufs-with-DRM-modifie.patch \
"
LDFLAGS:append:jh7110:libc-musl = " -Wl,--allow-shlib-undefined"

do_install:append:th1520() {
    install -d ${D}${sysconfdir}/systemd/system/weston.service.d
    cat <<EOF > ${D}${sysconfdir}/systemd/system/weston.service.d/graphics.conf
[Service]
Environment="PVR_I_WANT_A_BROKEN_VULKAN_DRIVER=1"
EOF
}
