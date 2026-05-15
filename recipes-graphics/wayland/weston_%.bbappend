FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:jh7110 = " \
    file://0006-libweston-reduce-checks-for-dmabufs-with-DRM-modifie.patch \
"

SRC_URI:append:k3 = " \
    file://0001-backend-drm-set-default-compositor-to-gpu.patch \
"

LDFLAGS:append:jh7110:libc-musl = " -Wl,--allow-shlib-undefined"
