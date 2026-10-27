require conf/machine/include/riscv/arch-rv64gc.inc

# Extensions mandated by the RVA20U64 profile that aren't in arch-riscv-base.inc
TUNEVALID[ziccamoa] = "PMA guarantees for A-extension atomics extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "ziccamoa", "_ziccamoa", "", d)}"

TUNEVALID[ziccif] = "Patching instructions at run time extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "ziccif", "_ziccif", "", d)}"

TUNEVALID[zicclsm] = "Software that assumes misaligned access works extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zicclsm", "_zicclsm", "", d)}"

TUNEVALID[ziccrse] = "Guarantees LR/SC forward progress extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "ziccrse", "_ziccrse", "", d)}"

TUNEVALID[zicntr] = "Cycle and instruction counting for profiling extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zicntr", "_zicntr", "", d)}"

TUNEVALID[za128rs] = "Reservation set granularity (128-byte) extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "za128rs", "_za128rs", "", d)}"

TUNEVALID[zcd] = "16-bit encodings for 64-bit FP extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zcd", "_zcd", "", d)}"
