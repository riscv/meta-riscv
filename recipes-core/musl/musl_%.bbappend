FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# RISCV-32 is supported via meta-riscv until musl port lands upstream
COMPATIBLE_HOST:riscv32 = ".*-musl.*"
