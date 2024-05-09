LICENSE="CLOSED"

PROVIDES += "virtual/libgles1 virtual/libgles2 virtual/libgles3"
COMPATIBLE_MACHINE = "jh7110"

require recipes-bsp/common/visionfive2-firmware.inc
inherit update-rc.d systemd

SRC_URI += "file://rc.pvr.service"

DEPENDS:append:libc-musl = " gcompat patchelf-native"

SRC_URI += "\
        file://glesv1_cm.pc \
"
S = "${WORKDIR}/git"

PACKAGES += " \
    ${PN}-firmware \
    ${PN}-tools \
"

do_install () {
    tar xz --no-same-owner -f ${S}/IMG_GPU/out/${IMG_GPU_POWERVR_VERSION}.tar.gz -C ${D}
    mv ${D}/${IMG_GPU_POWERVR_VERSION}/target/* ${D}
    install -d ${D}${includedir} ${D}${sysconfdir}/init.d
    cp -r ${D}/${IMG_GPU_POWERVR_VERSION}/staging/usr/include/drv/ ${D}/usr/include/
    cp -r ${D}/${IMG_GPU_POWERVR_VERSION}/staging/usr/include/GLES/ ${D}/usr/include/
    cp -r ${D}/${IMG_GPU_POWERVR_VERSION}/staging/usr/include/GLES2/ ${D}/usr/include/
    cp -r ${D}/${IMG_GPU_POWERVR_VERSION}/staging/usr/include/GLES3/ ${D}/usr/include/
    install -d ${D}/usr/lib/pkgconfig/
    cp -r ${D}/${IMG_GPU_POWERVR_VERSION}/staging/usr/lib/pkgconfig/* ${D}/usr/lib/pkgconfig/
    install -Dm0644 ${WORKDIR}/glesv1_cm.pc ${D}${libdir}/pkgconfig/glesv1_cm.pc
    sed -i -e 's|^#!/bin/bash|#!/usr/bin/env sh|g' ${D}${sysconfdir}/init.d/rc.pvr
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${bindir}
        mv ${D}${sysconfdir}/init.d/rc.pvr ${D}${bindir}
        rmdir ${D}${sysconfdir}/init.d
        install -Dm 644 ${WORKDIR}/rc.pvr.service ${D}/${systemd_unitdir}/system/rc.pvr.service
    else
        rm -rf ${D}/lib/systemd
    fi
    # let vulkan-loader from core layer provide libvulkan
    rm -rf ${D}${libdir}/libvulkan*.so* ${D}${libdir}/pkgconfig/vulkan.pc
    # provided via separate arch-independent firmware package
    rm -rf ${D}/lib/firmware
    # Check directory empty before trying to delete
    if [ -z "$(ls -A ${D}/lib)" ]; then
        rmdir ${D}/lib
    fi

    # cleanup unused
    rm -rf ${D}/${IMG_GPU_POWERVR_VERSION}
}

do_install:append:libc-musl() {
    # libs
    for f in `find ${D}${libdir} -name '*.so*' -type f`
    do
        patchelf --add-needed libgcompat.so.0 $f
    done
}

INITSCRIPT_NAME = "rc.pvr"
SYSTEMD_SERVICE:${PN} = "rc.pvr.service"

FILES_SOLIBSDEV = ""
FILES:${PN} += " \
    ${libdir}/*.so \
"

FILES:${PN}-tools = " \
    ${prefix}/local/bin/ \
"

FILES:${PN}-firmware = " \
    ${base_libdir}/firmware/ \
"

RDEPENDS:${PN} += " \
    bash \
    libdrm \
    linux-firmware-visionfive2-imggpu \
"

RDEPENDS:${PN}-tools += " \
    python3 \
"

RPROVIDES:${PN} += "libgles3 libgles2 libgles1"

INSANE_SKIP:${PN} += "already-stripped dev-so"
# ignore dependency check for python scripting
INSANE_SKIP:${PN}-tools += "already-stripped file-rdeps"
INSANE_SKIP:${PN}-firmware += "arch"
# It will use gcompat at runtime therefore checking for these at compile time wont be useful as
# they dont match musl/gcompat but it should run fine
INSANE_SKIP:append:libc-musl = " file-rdeps"
