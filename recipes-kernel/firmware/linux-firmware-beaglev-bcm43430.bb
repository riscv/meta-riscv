# Copyright (C) 2021 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "WIFI/BT Binary Blobs"
HOMEPAGE = "https://github.com/esmil/linux/blob/starlight/firmware/brcm"
LICENSE = "\
    Firmware-broadcom_bcm43xx \
"

LICENSE:${PN} = "Firmware-broadcom_bcm43xx"
LIC_FILES_CHKSUM = "\
    file://LICENCE.broadcom_bcm43xx;md5=3160c14df7228891b868060e1951dfbc \
"

# These are not common licenses, set NO_GENERIC_LICENSE for them
# so that the license files will be copied from fetched source
NO_GENERIC_LICENSE[Firmware-broadcom_bcm43xx] = "LICENCE.broadcom_bcm43xx"
SRC_URI = "https://github.com/esmil/linux/raw/visionfive/firmware/brcm/brcmfmac43430-sdio.bin;name=brcmfmac43430 \
           https://github.com/esmil/linux/raw/visionfive/firmware/brcm/brcmfmac43430-sdio.clm_blob;name=brcmfmac43430-clm \
           https://github.com/esmil/linux/raw/visionfive/firmware/brcm/nvram_ap6236.txt;name=nvram \
           https://github.com/esmil/linux/raw/visionfive/firmware/brcm/brcmfmac43430-sdio.AP6212.txt;name=AP6212 \
           https://raw.githubusercontent.com/RPi-Distro/firmware-nonfree/master/LICENCE.broadcom_bcm43xx;name=license \
          "
SRC_URI[brcmfmac43430.sha256sum] = "ffbb2dbf135a4c22fdb00716088c556ff6b43fcff1503a59c3f2097992b18b96"
SRC_URI[brcmfmac43430-clm.sha256sum] = "3ce2e8884dbd37d63ca8bae07bf7ea17c110070f41a87c9832eac57f201e2e5d"
SRC_URI[AP6212.sha256sum] = "fdef0603345dd023ad28c0eff2d5167915c617bee2d6944da9a6da1c4ac87ca5"
SRC_URI[nvram.sha256sum] = "ed5d5509c9eff922dc55ddffa654d6fb54ae3f391ff60dd3c50be5b63bc64152"
SRC_URI[license.sha256sum] = "b16056fc91b82a0e3e8de8f86c2dac98201aa9dc3cbd33e8d38f1b087fcec30d"

S = "${WORKDIR}"

inherit allarch

CLEANBROKEN = "1"

do_configure () {
    :
}

do_install () {
    for f in brcmfmac43430-sdio.AP6212.txt brcmfmac43430-sdio.bin brcmfmac43430-sdio.clm_blob nvram_ap6236.txt
    do
        install -Dm 0644 ${S}/$f ${D}${nonarch_base_libdir}/firmware/brcm/$f
    done
    ln -s brcmfmac43430-sdio.AP6212.txt ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43430-sdio.beagle,beaglev-starlight-jh7100.txt
}

FILES:${PN} = "${nonarch_base_libdir}/firmware/brcm/"

INSANE_SKIP = "arch"
