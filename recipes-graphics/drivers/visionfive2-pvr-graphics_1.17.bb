LICENSE="CLOSED"

PROVIDES = "virtual/libgles2"
COMPATIBLE_MACHINE = "visionfive2"

SRC_URI = " \
    git://github.com/starfive-tech/soft_3rdpart.git;protocol=https;lfs=1;branch=JH7110_VisionFive2_devel;rev=13975a3826bb98bd9a201780131262b6dd373452 \
"

inherit update-rc.d

S = "${WORKDIR}/git"

IMG_GPU_POWERVR_VERSION = "img-gpu-powervr-bin-1.17.6210866"

PACKAGES += " \
    ${PN}-firmware \
    ${PN}-tools \
"

do_install () {
    tar xz --no-same-owner -f ${S}/IMG_GPU/out/${IMG_GPU_POWERVR_VERSION}.tar.gz -C ${D}
    mv ${D}/${IMG_GPU_POWERVR_VERSION}/target/* ${D}
    install -d ${D}/usr/include/
    cp -r ${D}/${IMG_GPU_POWERVR_VERSION}/staging/usr/include/drv/ ${D}/usr/include/
    cp -r ${D}/${IMG_GPU_POWERVR_VERSION}/staging/usr/include/GLES/ ${D}/usr/include/
    cp -r ${D}/${IMG_GPU_POWERVR_VERSION}/staging/usr/include/GLES2/ ${D}/usr/include/
    cp -r ${D}/${IMG_GPU_POWERVR_VERSION}/staging/usr/include/GLES3/ ${D}/usr/include/
    install -d ${D}/usr/lib/pkgconfig/
    cp -r ${D}/${IMG_GPU_POWERVR_VERSION}/staging/usr/lib/pkgconfig/* ${D}/usr/lib/pkgconfig/

    # cleanup unused
    rm -rf ${D}/${IMG_GPU_POWERVR_VERSION}
}

INITSCRIPT_NAME = "rc.pvr"

FILES_SOLIBSDEV = ""
FILES:${PN} += " \
    ${libdir}/*.so \
"

FILES:${PN}-tools = " \
    ${prefix}/local/bin/ \
"

FILES:${PN}-firmware = " \
    ${base_libdir}/firmware/ \
"

RDEPENDS:${PN} += " \
    bash \
    libdrm \
    ${PN}-firmware \
"

RDEPENDS:${PN}-tools += " \
    python3 \
"

INSANE_SKIP:${PN} += "already-stripped dev-so"
# ignore dependency check for python scripting
INSANE_SKIP:${PN}-tools += "already-stripped file-rdeps"
INSANE_SKIP:${PN}-firmware += "arch"
