FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:jh7110 = " \
    file://0006-libweston-reduce-checks-for-dmabufs-with-DRM-modifie.patch \
"
LDFLAGS:append:jh7110:libc-musl = " -Wl,--allow-shlib-undefined"
