require linux-mainline-common.inc
FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-starfive:"
SUMMARY = "BeagleV dev kernel recipe"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP = "1"

SRCREV = "${AUTOREV}"
BRANCH = "starlight"
SRC_URI = "git://github.com/esmil/linux.git;protocol=git;branch=${BRANCH} \
           file://extra.cfg \
           file://modules.cfg \
          "

LINUX_VERSION ?= "5.13.0"
LINUX_VERSION_EXTENSION_append = "-starlight"

KBUILD_DEFCONFIG_beaglev-starlight-jh7100 = "starlight_defconfig"

# Install firmwate blobs for wifi/bt
do_install_append() {
    for f in brcmfmac43430-sdio.AP6212.txt brcmfmac43430-sdio.bin brcmfmac43430-sdio.clm_blob nvram_ap6236.txt
    do
        install -Dm 0644 ${S}/firmware/brcm/$f ${D}${nonarch_base_libdir}/firmware/brcm/$f
    done
    ln -s brcmfmac43430-sdio.AP6212.txt ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43430-sdio.beagle,beaglev-starlight-jh7100.txt
}

PACKAGES += "linux-firmware-beaglev-bcm43430"

FILES_linux-firmware-beaglev-bcm43430 = "${nonarch_base_libdir}/firmware/brcm/"

COMPATIBLE_MACHINE = "(beaglev-starlight-jh7100)"
