# Tune for the SpacemiT X60 cores of the K1 SoC: RVA22U64 plus the vector and
# other extensions listed for them in Linux's arch/riscv/boot/dts/spacemit/k1.dtsi.
# The supervisor-level extensions listed there (sscofpmf, sstc, svinval,
# svnapot, svpbmt) don't affect user space code, so they're left out.

DEFAULTTUNE ?= "spacemit-x60"

require conf/machine/include/riscv/tune-riscv-all.inc

# Core-specific tuning. -march still selects the ISA, which takes precedence
# over the one -mcpu implies.
TUNEVALID[spacemit-x60] = "SpacemiT X60 cores"
TUNE_CCARGS:append = "${@bb.utils.contains('TUNE_FEATURES', 'spacemit-x60', ' -mcpu=spacemit-x60', '', d)}"

AVAILTUNES += "spacemit-x60"

TUNE_FEATURES:tune-spacemit-x60 ?= "${TUNE_FEATURES:tune-rva22u64} v zicond zihpm zfh zbc zvfh zvkt spacemit-x60"
# riscv_readable_pkgarch(): rva22u64_v_zbc_zfh_zicond_zifencei_zihpm_zvfh_zvkt
TUNE_RISCV_PKGARCH:tune-spacemit-x60 ?= "rva22u64_0x089e3d7dd8"
# Lowest priority first. Unlike rva22u64, this is a superset of riscv64gcv.
PACKAGE_EXTRA_ARCHS:tune-spacemit-x60 = "${TUNE_RISCV_PKGARCH:tune-riscv64gc} ${TUNE_RISCV_PKGARCH:tune-riscv64gcv} ${TUNE_RISCV_PKGARCH:tune-rva20u64} ${TUNE_RISCV_PKGARCH:tune-rva22u64} ${TUNE_RISCV_PKGARCH:tune-spacemit-x60}"
