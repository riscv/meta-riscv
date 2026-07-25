SUMMARY = "K3 ESOS firmware (prebuilt)"
DESCRIPTION = "Prebuilt ESOS firmware (esos.itb) for K3 boot image, loaded by FSBL before U-Boot."
# The spacemit-firmware repository uses a simple copyright notice allowing use
# and redistribution of firmware binaries as-is and without modification, but
# it does not otherwise follow typical open-source licensing terms.
LICENSE = "LicenseRef-Proprietary"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Proprietary;md5=0557f9d92cf58f2ccdd50f62f8ac0b28"

inherit deploy

COMPATIBLE_MACHINE = "(k3)"

BRANCH = "master"
SRC_URI = " \
    git://github.com/spacemit-com/spacemit-firmware.git;protocol=https;branch=${BRANCH} \
"

SRCREV = "5969642a5b46fee5ba7f21ca54c3129bff7bb049"

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 0644 ${S}/k3/k3-br-v${PV}/esos.itb ${DEPLOYDIR}/
}

addtask deploy after do_compile
