TUNE_ARCH = "riscv64"

TUNE_PKGARCH = "riscv64"

DEFAULTTUNE ?= "riscv"

# require arch-riscv.inc

TUNEVALID[riscv] = "Enable RISCV optimizations"
TUNE_CCARGS .= ""

AVAILTUNES += "riscv"
TUNE_FEATURES_tune-riscv = "riscv"
TUNE_ARCH_tune-riscv = "riscv"
TUNE_PKGARCH_tune-riscv = "riscv"
PACKAGE_EXTRA_ARCHS_tune-riscv = ""
