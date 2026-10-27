#
# SPDX-License-Identifier: MIT
#

import oe.tune

# RISC-V profile name -> equivalent ISA string. These are determined from compiler output, like:
#   $ riscv64-linux-gnu-gcc -march=rva20u64 -mabi=lp64d -Q --help=target | grep rv64
#   -march=rv64imafdc_ziccamoa_ziccif_zicclsm_ziccrse_zicntr_zicsr_zmmul_za128rs_zaamo_zalrsc_zca_zcd
RISCV_PROFILES = {
    'rva20u64': 'rv64imafdc_ziccamoa_ziccif_zicclsm_ziccrse_zicntr_zicsr_zmmul_za128rs_zaamo_zalrsc_zca_zcd',
    'rva22u64': 'rv64imafdcb_zic64b_zicbom_zicbop_zicboz_ziccamoa_ziccif_zicclsm_ziccrse_zicntr_zicsr_zihintpause_zmmul_za64rs_zaamo_zalrsc_zfhmin_zca_zcd_zba_zbb_zbs_zkt',
}

# riscv_isa_to_tune(isa)
#
# Like oe.tune.riscv_isa_to_tune(), but also accepts the profile names listed in
# RISCV_PROFILES.
def riscv_isa_to_tune(isa):
    return oe.tune.riscv_isa_to_tune(RISCV_PROFILES.get(isa.lower(), isa))
