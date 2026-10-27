require conf/machine/include/riscv/arch-rva22u64.inc

# Extensions mandated by the RVA23U64 profile that aren't in arch-rva22u64.inc
TUNEVALID[zicond] = "Conditional zeroing operations extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zicond", "_zicond", "", d)}"

TUNEVALID[zihintntl] = "Non-temporal locality hints extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zihintntl", "_zihintntl", "", d)}"

TUNEVALID[zihpm] = "Hardware performance counters extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zihpm", "_zihpm", "", d)}"

TUNEVALID[zimop] = "May-be-operations extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zimop", "_zimop", "", d)}"

TUNEVALID[zawrs] = "Wait-on-reservation-set instructions extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zawrs", "_zawrs", "", d)}"

TUNEVALID[zfa] = "Additional floating point instructions extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zfa", "_zfa", "", d)}"

TUNEVALID[zcb] = "Extra compressed integer ops extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zcb", "_zcb", "", d)}"

TUNEVALID[zcmop] = "Compressed may-be-ops extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zcmop", "_zcmop", "", d)}"

TUNEVALID[zvbb] = "Vector basic bit-manipulation extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zvbb", "_zvbb", "", d)}"

TUNEVALID[zve32f] = "Embedded vector computation (32-bit integer, 32-bit FP) extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zve32f", "_zve32f", "", d)}"

TUNEVALID[zve32x] = "Embedded vector computation (32-bit integer) extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zve32x", "_zve32x", "", d)}"

TUNEVALID[zve64d] = "Embedded vector computation (64-bit integer, 64-bit FP) extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zve64d", "_zve64d", "", d)}"

TUNEVALID[zve64f] = "Embedded vector computation (64-bit integer, 32-bit FP) extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zve64f", "_zve64f", "", d)}"

TUNEVALID[zve64x] = "Embedded vector computation (64-bit integer) extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zve64x", "_zve64x", "", d)}"

TUNEVALID[zvfhmin] = "Vector half-precision minimal extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zvfhmin", "_zvfhmin", "", d)}"

TUNEVALID[zvkb] = "Vector cryptography bit-manipulation extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zvkb", "_zvkb", "", d)}"

TUNEVALID[zvkt] = "Vector data independent execution latency extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zvkt", "_zvkt", "", d)}"

TUNEVALID[zvl128b] = "Minimum vector length standard extensions (128-bit)."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zvl128b", "_zvl128b", "", d)}"

TUNEVALID[zvl32b] = "Minimum vector length standard extensions (32-bit)."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zvl32b", "_zvl32b", "", d)}"

TUNEVALID[zvl64b] = "Minimum vector length standard extensions (64-bit)."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zvl64b", "_zvl64b", "", d)}"

TUNEVALID[supm] = "User-mode pointer masking extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "supm", "_supm", "", d)}"
