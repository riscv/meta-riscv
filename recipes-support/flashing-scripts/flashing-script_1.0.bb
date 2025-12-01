DESCRIPTION = "Simple flashing script deployed to deploy/ dir"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit deploy

EXCLUDE_FROM_WORLD = "1"
INHIBIT_DEFAULT_DEPS = "1"
# Only one empty meta-package
PACKAGES = "${PN}"

SRC_URI = "file://flash.sh"

S = "${UNPACKDIR}"

do_deploy() {
    install -D -m 0744 "${S}/flash.sh" "${DEPLOYDIR}/"
}
addtask deploy after do_patch

# Remove unnecessary tasks.
deltask do_configure
deltask do_compile
deltask do_install
