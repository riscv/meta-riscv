require u-boot-spl-spacemit.inc

BRANCH ?= "k3-br-v1.0.y"
SRC_URI = "git://github.com/spacemit-com/uboot-2022.10.git;protocol=https;branch=${BRANCH}"
SRCREV ?= "1b10c8119e1a9b5451a4236f6b384f7c91eed1e2"

UBOOT_SPL_MACHINE ?= "k3_defconfig"

COMPATIBLE_MACHINE = "(k3)"

do_deploy:append() {
	install -m 644 ${B}/bootinfo_block.bin ${DEPLOYDIR}/
}
