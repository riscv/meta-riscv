# RVA23U64 expansion options, which aren't part of openembedded-core's rva23u64
# tune, but can be added to TUNE_FEATURES by tunes built on it (or on earlier
# profiles).
#
# These are appended to TUNE_RISCV_MARCH after all of the extensions from
# arch-riscv.inc, so they aren't in canonical order, but GCC and binutils
# accept that.

require conf/machine/include/riscv/tune-riscv.inc

TUNEVALID[zfbfmin] = "Scalar BF16 conversion extension"
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zfbfmin", "_zfbfmin", "", d)}"

TUNEVALID[zfh] = "Half-precision floating-point extension"
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zfh", "_zfh", "", d)}"

TUNEVALID[zvfbfmin] = "Vector BF16 conversion extension"
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zvfbfmin", "_zvfbfmin", "", d)}"

TUNEVALID[zvfbfwma] = "Vector BF16 widening multiply-add extension"
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zvfbfwma", "_zvfbfwma", "", d)}"

TUNEVALID[zvfh] = "Vector half-precision floating-point extension"
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zvfh", "_zvfh", "", d)}"
