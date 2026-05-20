SUMMARY = "K3 ESOS firmware (prebuilt)"
DESCRIPTION = "Prebuilt ESOS firmware (esos.itb) for K3 boot image, loaded by FSBL before U-Boot."
# The spacemit-firmware repository uses a simple copyright notice allowing use
# and redistribution of firmware binaries as-is and without modification, but
# it does not otherwise follow typical open-source licensing terms.
LICENSE = "CLOSED"

inherit deploy

COMPATIBLE_MACHINE = "(k3)"

BRANCH = "master"
SRC_URI = " \
    git://github.com/spacemit-com/spacemit-firmware.git;protocol=https;branch=${BRANCH} \
"

SRCREV = "9c550e02f8781e4d287c6b64dd4c0f71a655ac69"

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 0644 ${S}/k3/k3-br-v1.0.0/esos.itb ${DEPLOYDIR}/
}

addtask deploy after do_compile
