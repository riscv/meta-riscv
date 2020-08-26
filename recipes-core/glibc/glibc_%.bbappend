# Upstream Glibc does not yet have RISC-V 32-bit support

GLIBC_GIT_URI_qemuriscv32 = "git://github.com/alistair23/glibc.git"
SRCBRANCH_qemuriscv32 = "alistair/rv32.5"
SRCREV_glibc_qemuriscv32 = "e9f32562dff35406fcf403a655144329bbf8eb0c"

SRC_URI_remove_qemuriscv32 = " \
	file://0016-Add-unused-attribute.patch \
	file://0028-inject-file-assembly-directives.patch \
	file://CVE-2020-6096.patch \
	file://CVE-2020-6096_2.patch \
"
