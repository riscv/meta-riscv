# Tune for the SpacemiT X100 cores of the K3 SoC: RVA23U64 plus the expansion
# options listed for them in Linux's arch/riscv/boot/dts/spacemit/k3.dtsi.
# The supervisor-level extensions listed there don't affect user space code,
# so they're left out. So are the vector crypto extensions (zvbc, zvkg,
# zvkned, zvknha, zvknhb, zvksed, zvksh): compilers don't generate them from
# ordinary code, and the libraries that use them check for them at run time.

DEFAULTTUNE ?= "spacemit-x100"

require conf/machine/include/riscv/arch-rva23u64-expansion.inc

# Core-specific tuning. GCC only supports -mcpu=spacemit-x100 from version 17
# on, so until then, tune for a generic out-of-order core, which the X100 is.
# With GCC 17 or later, this can be set to "-mcpu=spacemit-x100"; -march still
# selects the ISA, which takes precedence over the one -mcpu implies.
SPACEMIT_X100_CCARGS ?= "-mtune=generic-ooo"
TUNEVALID[spacemit-x100] = "SpacemiT X100 cores"
TUNE_CCARGS:append = "${@bb.utils.contains('TUNE_FEATURES', 'spacemit-x100', ' ${SPACEMIT_X100_CCARGS}', '', d)}"

AVAILTUNES += "spacemit-x100"

TUNE_FEATURES:tune-spacemit-x100 ?= "${TUNE_FEATURES:tune-rva23u64} zbc zfh zvfh zfbfmin zvfbfmin zvfbfwma spacemit-x100"
RISCVPKGARCH:tune-spacemit-x100 ?= "spacemit-x100"
# Lowest priority first
PACKAGE_EXTRA_ARCHS:tune-spacemit-x100 = "${PACKAGE_EXTRA_ARCHS:tune-rva23u64} ${RISCVPKGARCH:tune-spacemit-x100}"
