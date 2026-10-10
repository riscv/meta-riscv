SUMMARY = "Imagination PowerVR Kernel Module Configuration"
DESCRIPTION = "Configure experimental hardware support for powervr kernel module"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

do_install () {
    install -d ${D}${sysconfdir}/modprobe.d
    cat <<EOF > ${D}${sysconfdir}/modprobe.d/powervr.conf
options powervr exp_hw_support=1
EOF
}
