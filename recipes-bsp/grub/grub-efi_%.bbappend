FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:visionfive2 = " file://0263-Use-medany-instead-of-large-model-for-RISCV.patch"
