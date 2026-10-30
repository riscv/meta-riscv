DEFAULTTUNE ?= "riscv32gc"

require conf/machine/include/riscv/arch-riscv-base.inc

AVAILTUNES += "riscv32gc riscv32 riscv32nf"

TUNE_FEATURES:tune-riscv32gc ?= "rv 32 i m a f d c zicsr zifencei"
TUNE_RISCV_PKGARCH:tune-riscv32gc ?= "riscv32imafdc_zicsr_zifencei"
PACKAGE_EXTRA_ARCHS:tune-riscv32gc = "${TUNE_RISCV_PKGARCH:tune-riscv32gc}"

# Compatibility alias so that layers using old tunes don't break
TUNE_FEATURES:tune-riscv32 ?= "${TUNE_FEATURES:tune-riscv32gc}"
TUNE_RISCV_PKGARCH:tune-riscv32 ?= "${TUNE_RISCV_PKGARCH:tune-riscv32gc}"
PACKAGE_EXTRA_ARCHS:tune-riscv32 = "${PACKAGE_EXTRA_ARCHS:tune-riscv32gc}"

# No float
TUNE_FEATURES:tune-riscv32nf ?= "rv 32 i m a c zicsr zifencei"
TUNE_RISCV_PKGARCH:tune-riscv32nf ?= "riscv32imac_zicsr_zifencei"
PACKAGE_EXTRA_ARCHS:tune-riscv32nf = "${TUNE_RISCV_PKGARCH:tune-riscv32nf}"
