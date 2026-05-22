require u-boot-spl-spacemit.inc

BRANCH ?= "k1-bl-v2.2.y"
SRC_URI = "git://github.com/amarula/uboot-2022.10;protocol=https;branch=${BRANCH}"
SRCREV ?= "c6f2746cb7993a6fb6c9f51b2bff318921e13f98"

COMPATIBLE_MACHINE = "(k1)"

do_deploy:append() {
        install -m 644 ${B}/bootinfo_emmc.bin ${DEPLOYDIR}/
        install -m 644 ${B}/bootinfo_sd.bin ${DEPLOYDIR}/
}
