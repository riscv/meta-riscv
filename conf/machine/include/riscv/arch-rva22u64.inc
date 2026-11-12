DEFAULTTUNE ?= "rva22u64"

require conf/machine/include/riscv/arch-rva20u64.inc

# Extensions mandated by the RVA22U64 profile that aren't in arch-rva20u64.inc
TUNEVALID[zic64b] = "Cache block size is 64 bytes extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zic64b", "_zic64b", "", d)}"

TUNEVALID[zicbop] = "Cache-block prefetching extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zicbop", "_zicbop", "", d)}"

TUNEVALID[zicboz] = "Cache-block zeroing extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zicboz", "_zicboz", "", d)}"

TUNEVALID[zihintpause] = "Pause hint extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zihintpause", "_zihintpause", "", d)}"

TUNEVALID[za64rs] = "Reservation set size of at most 64 bytes extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "za64rs", "_za64rs", "", d)}"

TUNEVALID[zfhmin] = "Minimal half-precision floating point extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zfhmin", "_zfhmin", "", d)}"

TUNEVALID[zkt] = "Data-independent execution latency extension."
TUNE_RISCV_MARCH .= "${@bb.utils.contains("TUNE_FEATURES", "zkt", "_zkt", "", d)}"

AVAILTUNES += "rva22u64"

# The RVA22U64 extensions, as expanded by riscv.tune.riscv_isa_to_tune().
# zifencei isn't part of these profiles per gcc's -march expansion, but the
# Linux kernel requires it, so add it on top of the profile's extensions.
TUNE_FEATURES:tune-rva22u64 ?= "rv 64 i m a f d c b zic64b zicbom zicbop zicboz ziccamoa ziccif zicclsm ziccrse zicntr zicsr zihintpause zmmul za64rs zaamo zalrsc zfhmin zca zcd zba zbb zbs zkt zifencei"
TUNE_RISCV_PKGARCH:tune-rva22u64 ?= "rva22u64_zifencei"
PACKAGE_EXTRA_ARCHS:tune-rva22u64 = "${TUNE_RISCV_PKGARCH:tune-rva22u64}"
