# Tune for the SpacemiT X60 cores of the K1 SoC: RVA22U64 plus the vector and
# other extensions listed for them in Linux's arch/riscv/boot/dts/spacemit/k1.dtsi.
# The supervisor-level extensions listed there (sscofpmf, sstc, svinval,
# svnapot, svpbmt) don't affect user space code, so they're left out.

DEFAULTTUNE ?= "spacemit-x60"

require conf/machine/include/riscv/arch-rva23u64-expansion.inc

# Core-specific tuning. -march still selects the ISA, which takes precedence
# over the one -mcpu implies.
TUNEVALID[spacemit-x60] = "SpacemiT X60 cores"
TUNE_CCARGS:append = "${@bb.utils.contains('TUNE_FEATURES', 'spacemit-x60', ' -mcpu=spacemit-x60', '', d)}"

AVAILTUNES += "spacemit-x60"

TUNE_FEATURES:tune-spacemit-x60 ?= "${TUNE_FEATURES:tune-rva22u64} v zicond zfh zbc zvfh zvkt spacemit-x60"
RISCVPKGARCH:tune-spacemit-x60 ?= "spacemit-x60"
# Lowest priority first. Unlike rva22u64, this is a superset of riscv64gcv.
PACKAGE_EXTRA_ARCHS:tune-spacemit-x60 = "${PACKAGE_EXTRA_ARCHS:tune-riscv64gcv} ${RISCVPKGARCH:tune-rva20u64} ${RISCVPKGARCH:tune-rva22u64} ${RISCVPKGARCH:tune-spacemit-x60}"
