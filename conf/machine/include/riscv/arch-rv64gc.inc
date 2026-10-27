DEFAULTTUNE ?= "riscv64gc"

# Also pull in the 32-bit tunes (and arch-riscv-base.inc through them), so that
# all of the generic tunes are available together, as with oe-core's
# tune-riscv.inc. arch-riscv-base.inc must only be included once.
require conf/machine/include/riscv/arch-rv32gc.inc

AVAILTUNES += "riscv64gcv riscv64gc riscv64 riscv64nc riscv64nf"

TUNE_FEATURES:tune-riscv64gc ?= "rv 64 i m a f d c zicsr zifencei"
TUNE_RISCV_PKGARCH:tune-riscv64gc ?= "riscv64imafdc_zicsr_zifencei"
PACKAGE_EXTRA_ARCHS:tune-riscv64gc = "${TUNE_RISCV_PKGARCH:tune-riscv64gc}"

TUNE_FEATURES:tune-riscv64gcv ?= "rv 64 i m a f d c v zicsr zifencei"
TUNE_RISCV_PKGARCH:tune-riscv64gcv ?= "riscv64imafdcv_zicsr_zifencei"
PACKAGE_EXTRA_ARCHS:tune-riscv64gcv = "${TUNE_RISCV_PKGARCH:tune-riscv64gcv}"

# Compatibility alias so that layers using old tunes don't break
TUNE_FEATURES:tune-riscv64 ?= "${TUNE_FEATURES:tune-riscv64gc}"
TUNE_RISCV_PKGARCH:tune-riscv64 ?= "${TUNE_RISCV_PKGARCH:tune-riscv64gc}"
PACKAGE_EXTRA_ARCHS:tune-riscv64 = "${PACKAGE_EXTRA_ARCHS:tune-riscv64gc}"

# No float
TUNE_FEATURES:tune-riscv64nf ?= "rv 64 i m a c zicsr zifencei"
TUNE_RISCV_PKGARCH:tune-riscv64nf ?= "riscv64imac_zicsr_zifencei"
PACKAGE_EXTRA_ARCHS:tune-riscv64nf = "${TUNE_RISCV_PKGARCH:tune-riscv64nf}"

# No compressed
TUNE_FEATURES:tune-riscv64nc ?= "rv 64 i m a f d zicsr zifencei"
TUNE_RISCV_PKGARCH:tune-riscv64nc ?= "riscv64imafd_zicsr_zifencei"
PACKAGE_EXTRA_ARCHS:tune-riscv64nc = "${TUNE_RISCV_PKGARCH:tune-riscv64nc}"
