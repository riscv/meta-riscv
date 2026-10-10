do_install:append:th1520 () {
    install -d ${D}${sysconfdir}/profile.d
    cat <<EOF > ${D}${sysconfdir}/profile.d/powervr-graphics.sh
GALLIUM_DRIVER=zink
MESA_LOADER_DRIVER_OVERRIDE=zink
PVR_I_WANT_A_BROKEN_VULKAN_DRIVER=1
mesa_glthread=true

export GALLIUM_DRIVER MESA_LOADER_DRIVER_OVERRIDE PVR_I_WANT_A_BROKEN_VULKAN_DRIVER mesa_glthread
EOF
}
