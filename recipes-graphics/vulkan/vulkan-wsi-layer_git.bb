SUMMARY = "Vulkan layer implementing window system integration on top of a Vulkan ICD"
DESCRIPTION = "Implicit Vulkan layer that provides VK_KHR_surface, VK_KHR_swapchain, \
               VK_KHR_wayland_surface, VK_EXT_headless_surface & VK_KHR_display for ICDs that \
               do not implement window system integration themselves, such as the PowerVR DDK \
               shipped for the StarFive JH7110."
HOMEPAGE = "https://gitlab.freedesktop.org/mesa/vulkan-wsi-layer"
BUGTRACKER = "https://gitlab.freedesktop.org/mesa/vulkan-wsi-layer/-/issues"
SECTION = "graphics"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c2e771b72d60a13d2de384cb49055d00"

DEPENDS = "libdrm vulkan-headers vulkan-loader"

PV = "0+git"

SRC_URI = "git://gitlab.freedesktop.org/mesa/vulkan-wsi-layer.git;protocol=https;branch=main \
           file://0001-wayland-do-not-require-VK_KHR_calibrated_timestamps.patch \
           file://0002-cmake-do-not-build-with-Werror.patch \
           file://0003-display-only-advertise-surface-formats-the-ICD-can-i.patch \
           "
SRCREV = "f5148d4f8c541f8aeddc88170b9e2dbbea2e8b43"

inherit cmake pkgconfig features_check

REQUIRED_DISTRO_FEATURES = "vulkan"

# Name of the dma-buf heap under /dev/dma_heap that swapchain images come from
WSI_LAYER_HEAP ?= "linux,cma"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'wayland', d)} display"
PACKAGECONFIG[wayland] = "-DBUILD_WSI_WAYLAND=ON,-DBUILD_WSI_WAYLAND=OFF,wayland wayland-native wayland-protocols"
PACKAGECONFIG[display] = "-DBUILD_WSI_DISPLAY=ON,-DBUILD_WSI_DISPLAY=OFF"

EXTRA_OECMAKE = "-DSELECT_EXTERNAL_ALLOCATOR=dma_buf_heaps \
                 -DWSIALLOC_MEMORY_HEAP_NAME=${WSI_LAYER_HEAP} \
                 -DKERNEL_HEADER_DIR=${STAGING_INCDIR} \
                 "

do_install() {
    install -d ${D}${libdir} ${D}${datadir}/vulkan/implicit_layer.d
    install -m 0755 ${B}/libVkLayer_window_system_integration.so ${D}${libdir}
    sed -e 's|"library_path": "./|"library_path": "${libdir}/|' \
        ${B}/VkLayer_window_system_integration.json \
        > ${D}${datadir}/vulkan/implicit_layer.d/VkLayer_window_system_integration.json
    # The kernel creates the heaps root-only; open the one the layer uses to
    # the video group, as for /dev/dri/card*
    install -d ${D}${nonarch_base_libdir}/udev/rules.d
    echo 'SUBSYSTEM=="dma_heap", KERNEL=="${WSI_LAYER_HEAP}", GROUP="video", MODE="0660"' \
        > ${D}${nonarch_base_libdir}/udev/rules.d/60-vulkan-wsi-layer-dma-heap.rules
}

FILES:${PN} += "${libdir}/libVkLayer_window_system_integration.so ${datadir}/vulkan \
                ${nonarch_base_libdir}/udev/rules.d"
FILES_SOLIBSDEV = ""
