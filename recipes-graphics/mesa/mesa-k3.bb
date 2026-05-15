SUMMARY = "Mesa 3D Graphics Library (K3 Spacemit build)"
DESCRIPTION = "Mesa is an open-source implementation of the OpenGL specification"
HOMEPAGE = "http://mesa3d.org"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://docs/license.rst;md5=63779ec98d78d823a9dc533a0735ef10"

SRC_URI = "git://github.com/spacemit-com/mesa.git;protocol=https;tag=k3-br-v1.0.0;nobranch=1"
PV = "24.0.0+git${SRCPV}"

inherit meson pkgconfig python3native gettext features_check

# Mesa requires x11 or wayland
REQUIRED_DISTRO_FEATURES ?= "opengl"

DEPENDS = " \
    bison-native \
    chrpath-replacement-native \
    flex-native \
    gettext-native \
    makedepend-native \
    python3-mako-native \
    python3-pyyaml-native \
    wayland-native \
"

DEPENDS += " \
    expat \
    img-gpu-powervr \
    libdrm \
    libxml2 \
    libxrandr \
    wayland \
    wayland-protocols \
    zlib \
"

PROVIDES += "virtual/libgl virtual/libgles1 virtual/libgles2 virtual/libgles3 virtual/egl virtual/mesa virtual/libgbm virtual/vulkan-icd"

# Replace mesa packages with mesa3d
RPROVIDES:${PN} += "mesa libegl libgles1 libgles2 libgl libgbm virtual-vulkan-icd"
RREPLACES:${PN} += "mesa libegl libgles1 libgles2 libgl libgbm virtual-vulkan-icd"
RCONFLICTS:${PN} += "mesa libegl libgles1 libgles2 libgl libgbm virtual-vulkan-icd"

PACKAGECONFIG ??= " \
    ${@bb.utils.filter('DISTRO_FEATURES', 'wayland', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'opengl egl gles gbm gallium', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11 opengl', 'x11 dri3 glx', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11 vulkan', 'dri3', '', d)} \
    pvr \
"

# Meson options
PACKAGECONFIG[wayland] = ",,wayland-native wayland wayland-protocols"
PACKAGECONFIG[x11] = ",,libxcb libx11 libxext libxxf86vm libxdamage libxfixes"
PACKAGECONFIG[glx] = "-Dglx=dri,-Dglx=disabled"
PACKAGECONFIG[opengl] = "-Dopengl=true,-Dopengl=false"
PACKAGECONFIG[gles] = "-Dgles1=enabled -Dgles2=enabled,-Dgles1=disabled -Dgles2=disabled"
PACKAGECONFIG[egl] = "-Degl=enabled,-Degl=disabled"
PACKAGECONFIG[gbm] = "-Dgbm=enabled,-Dgbm=disabled"
PACKAGECONFIG[dri3] = "-Ddri3=enabled,-Ddri3=disabled,libxshmfence"
PACKAGECONFIG[gallium] = "-Dgallium-drivers=swrast${GALLIUMDRIVERS},-Dgallium-drivers=''"
PACKAGECONFIG[pvr] = ",,img-gpu-powervr"

GALLIUMDRIVERS = ""
GALLIUMDRIVERS .= "${@bb.utils.contains('PACKAGECONFIG', 'pvr', ',pvr', '', d)}"

# Build platforms based on enabled features
PLATFORMS = ""
PLATFORMS .= "${@bb.utils.contains('PACKAGECONFIG', 'wayland', 'wayland,', '', d)}"
PLATFORMS .= "${@bb.utils.contains('PACKAGECONFIG', 'x11', 'x11', '', d)}"

EXTRA_OEMESON = " \
    -Dshared-glapi=enabled \
    -Dgallium-opencl=disabled \
    -Dzstd=disabled \
    -Dvulkan-drivers=[] \
    -Dvulkan-layers=[] \
    -Dgallium-pvr-alias=spacemit \
    -Dplatforms=${@'${PLATFORMS}'.strip(',') if '${PLATFORMS}' else 'wayland'} \
    -Dllvm=disabled \
    -Ddraw-use-llvm=false \
"

CFLAGS:append = " -fcommon"

do_install:append() {
    # Remove unneeded files
    rm -rf ${D}${datadir}/drirc.d/
}

FILES:${PN} = " \
    ${libdir}/libGL.so.* \
    ${libdir}/libGLESv*.so.* \
    ${libdir}/libEGL.so.* \
    ${libdir}/libgbm.so.* \
    ${libdir}/libglapi.so.* \
    ${libdir}/dri/*.so \
"

FILES:${PN}-dev = " \
    ${includedir} \
    ${libdir}/pkgconfig \
    ${datadir}/pkgconfig \
    ${libdir}/*.so \
    ${datadir}/mesa \
"
