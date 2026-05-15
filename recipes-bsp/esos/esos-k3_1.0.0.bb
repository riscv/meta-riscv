SUMMARY = "K3 ESOS firmware (esos.itb)"
DESCRIPTION = "esos.itb is the firmware for the K3 board, loaded by FSBL before U-Boot and OpenSBI. \
It is responsible for initializing hardware modules and must be installed in the boot partition."
LICENSE = "CLOSED"

inherit deploy

ESOS_TOOLCHAIN_NAME = "spacemit-toolchain-elf-newlib-x86_64-v1.0.9"
ESOS_TOOLCHAIN_DIR = "${S}/tools/toolchain/${ESOS_TOOLCHAIN_NAME}"

SRCREV_FORMAT = "esos_lite"

SRC_URI = " \
    git://github.com/spacemit-com/esos.git;protocol=https;tag=k3-br-v1.0.0;name=esos;nobranch=1;destsuffix=esos-src \
    git://github.com/spacemit-com/esos-lite.git;protocol=https;tag=k3-br-v1.0.0;name=lite;nobranch=1;destsuffix=esos-src/components/esos-lite \
    http://archive.spacemit.com/toolchain/${ESOS_TOOLCHAIN_NAME}.tar.xz;name=toolchain;subdir=tools/toolchain;unpack=0 \
"
SRC_URI[toolchain.sha256sum] = "2a36944662ad7d98a9b54c872587b4a1bf7c182a7f203a5c464409686c836520"

S = "${UNPACKDIR}/esos-src"

DEPENDS += "dtc-native python3-native u-boot-tools-native python3-scons-native lzop-native"

# K3 target configuration (rt24 chip, non-interactive)
TARGET_CHIP = "rt24"

do_configure() {
    # Equivalent to buildroot ESOS_CONFIGURE_CMDS
    bbnote "Preparing to config esos sdk..."

    # Clean old configuration
    rm -rf ${S}/bsp/spacemit/.config
    rm -rf ${S}/bsp/spacemit/.esos_top.config

    # Create top-level configuration (non-interactive chip selection)
    bbnote "Selecting chip: ${TARGET_CHIP} (non-interactive)"
    echo "export TOP_TARGET_CHIP=${TARGET_CHIP}" > ${S}/bsp/spacemit/.esos_top.config

    bbnote "Target configuration:"
    cat ${S}/bsp/spacemit/.esos_top.config

    # Extract toolchain if not already present (archive fetched by SRC_URI)
    bbnote "Preparing toolchain..."
    mkdir -p ${S}/tools/toolchain
    if [ ! -d "${ESOS_TOOLCHAIN_DIR}" ]; then
        cd ${S}/tools/toolchain
        bbnote "Extracting toolchain..."
        tar -xf ${DL_DIR}/${ESOS_TOOLCHAIN_NAME}.tar.xz
        cd -
    else
        bbnote "Toolchain already present"
    fi

    # Create necessary directories and files for menuconfig
    bbnote "Creating .env/packages/Kconfig for menuconfig..."
    mkdir -p ${S}/.env/packages
    touch ${S}/.env/packages/Kconfig

    # Create initial rtconfig.h
    bbnote "Creating initial rtconfig.h..."
    touch ${S}/bsp/spacemit/rtconfig.h
    
    bbnote "ESOS config completed"
}

do_compile() {
    # Equivalent to buildroot ESOS_BUILD_CMDS
    bbnote "Starting esos build for all boards under ${TARGET_CHIP} chip..."

    cd ${S}

    # Use build_top.sh to build all boards (like buildroot does)
    bash ./build_top.sh

    bbnote "ESOS build completed"
}

do_install() {
    # Install only .elf files to firmware directory for initramfs
    install -d ${D}/lib/firmware

    # Install .elf files for initramfs early boot firmware loading
    if [ -f "${UNPACKDIR}/output/esos/rt24_os0_rcpu.elf" ]; then
        install -m 0644 "${UNPACKDIR}/output/esos/rt24_os0_rcpu.elf" "${D}/lib/firmware/"
        bbnote "Installed rt24_os0_rcpu.elf to package"
    else
        bbwarn "rt24_os0_rcpu.elf not found"
    fi

    if [ -f "${UNPACKDIR}/output/esos/rt24_os1_rcpu.elf" ]; then
        install -m 0644 "${UNPACKDIR}/output/esos/rt24_os1_rcpu.elf" "${D}/lib/firmware/"
        bbnote "Installed rt24_os1_rcpu.elf to package"
    else
        bbwarn "rt24_os1_rcpu.elf not found"
    fi
}

do_deploy() {
    install -d ${DEPLOYDIR}

    # Deploy esos.itb (prefer output directory version)
    if [ -f "${UNPACKDIR}/output/esos/esos.itb" ]; then
        install -m 0644 "${UNPACKDIR}/output/esos/esos.itb" "${DEPLOYDIR}/"
        bbnote "Deployed esos.itb from output directory"
    else
        bbfatal "esos.itb not found for deployment"
    fi
    bbnote "ESOS deployment completed"
}

addtask deploy after do_compile

FILES:${PN} = "/lib/firmware"

INSANE_SKIP:${PN} = "usrmerge arch already-stripped buildpaths"

COMPATIBLE_MACHINE = "(k3)"

