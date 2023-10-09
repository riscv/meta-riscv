DESCRIPTION = "firmware contains u-boot, OpenSBI and freeRTOS for Milk-V Duo."
LICENSE = "CLOSED"

inherit nopackages deploy

INHIBIT_DEFAULT_DEPS = "1"
COMPATIBLE_MACHINE = "milkv-duo"

SRC_URI = "https://github.com/xyq1113723547/milkv-duo-bootfiles/raw/main/fip.bin;protocol=http"
SRC_URI[sha256sum] = "19670afc7c5361fbe81d61f843fe1df633f4ac77bf178c5421c76ddf314047a2"

do_deploy() {
        cp ${WORKDIR}/fip.bin ${DEPLOYDIR}/
}

addtask deploy after do_compile
do_deploy[dirs] += "${DEPLOYDIR}/"
