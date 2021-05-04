require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

SRC_URI = "git://github.com/starfive-tech/u-boot.git;protocol=git;branch=Fedora"

SRC_URI += " \
    file://0001-include-configs-starfive-vic7100-adjust-fdt_addr_r.patch \
"

#FIXME: U-Boot by default wants the extlinux.conf in the /boot/extlinux/extlinux.conf path
do_deploy_append() {
    if [ ! -d  ${DEPLOYDIR}/boot/extlinux ]; then
        install -d ${DEPLOYDIR}/boot/extlinux
    fi
    cp ${DEPLOYDIR}/extlinux.conf ${DEPLOYDIR}/boot/extlinux
}

SRCREV = "3f3ac01a29ad1cd5fa519d86f81daead2447f1d4"
