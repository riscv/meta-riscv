# Every generic RISC-V tune provided by meta-riscv. Machines should require this
# instead of oe-core's conf/machine/include/riscv/tune-riscv.inc.

# riscv64gc remains the default for now. This must be set before the require,
# as each arch-*.inc defaults to its own tune.
DEFAULTTUNE ?= "riscv64gc"

require conf/machine/include/riscv/arch-rva23u64.inc
