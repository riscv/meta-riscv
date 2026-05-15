SUMMARY = "Spacemit RISC-V Toolchain for K3"
DESCRIPTION = "External toolchain from Spacemit with support for K3 custom CSR registers"
LICENSE = "GPL-3.0-with-GCC-exception & GPL-3.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0-with-GCC-exception;md5=aef5f35c9272f508be848cd99e0151df"

SRC_URI = "https://archive.spacemit.com/toolchain/spacemit-toolchain-linux-glibc-x86_64-v${PV}.tar.xz"
SRC_URI[sha256sum] = "a4bb97aba723ea642db9261d517cd98660404a4e42a37b2c3b86a3adc4ee78e9"

S = "${UNPACKDIR}/spacemit-toolchain-linux-glibc-x86_64-v${PV}"

# This is a binary toolchain, no compilation needed
do_configure[noexec] = "1"
do_compile[noexec] = "1"

# Install the toolchain
do_install() {
    install -d ${D}${datadir}/spacemit-toolchain
    cp -r ${S}/* ${D}${datadir}/spacemit-toolchain/
}

FILES:${PN} = "${datadir}/spacemit-toolchain/*"

INSANE_SKIP:${PN} = "already-stripped ldflags file-rdeps"

# Prevent stripping of cross-compiled binaries and libraries
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

# Provide toolchain paths for other recipes
SPACEMIT_TOOLCHAIN_PATH = "${STAGING_DIR_NATIVE}${datadir}/spacemit-toolchain"

# Extend to native variant
BBCLASSEXTEND = "native"
