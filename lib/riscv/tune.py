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
    'rva23u64': 'rv64imafdcbv_zic64b_zicbom_zicbop_zicboz_ziccamoa_ziccif_zicclsm_ziccrse_zicntr_zicond_zicsr_zihintntl_zihintpause_zihpm_zimop_zmmul_za64rs_zaamo_zalrsc_zawrs_zfa_zfhmin_zca_zcb_zcd_zcmop_zba_zbb_zbs_zkt_zvbb_zve32f_zve32x_zve64d_zve64f_zve64x_zvfhmin_zvkb_zvkt_zvl128b_zvl32b_zvl64b_supm',
}

# riscv_isa_to_tune(isa)
#
# Like oe.tune.riscv_isa_to_tune(), but also accepts the profile names listed in
# RISCV_PROFILES.
def riscv_isa_to_tune(isa):
    return oe.tune.riscv_isa_to_tune(RISCV_PROFILES.get(isa.lower(), isa))

# Single-letter extensions, in ISA string order. b isn't written out, as its
# zba, zbb and zbs components are used instead.
RISCV_SINGLE_ORDER = 'eimafdcv'

# Multi-letter z extensions are ordered by the letter after the z, in this
# order, and then alphabetically
RISCV_Z_ORDER = 'imafdqcbkjtpvh'

# riscv_exact_pkgarch(features)
#
# Return the package arch spelling out every extension in TUNE_FEATURES, e.g.
# riscv64imafdc_zicsr_zifencei.
def riscv_exact_pkgarch(features):
    current = set(features.split())
    if 'd' in current:
        current.add('f')
    if current & {'f', 'd'}:
        current.add('zicsr')
    if 'b' in current:
        current |= {'zba', 'zbb', 'zbs'}

    xlen = '64' if '64' in current else '32'
    single = ''.join(e for e in RISCV_SINGLE_ORDER if e in current)
    multi = sorted((e for e in current if len(e) > 1 and e[0] in 'sz'),
                   key=lambda e: (e[0] != 'z', RISCV_Z_ORDER.find(e[1]), e))
    return '_'.join(['riscv' + xlen + single] + multi)

# riscv_pkgarch(features)
#
# Return the package arch for a tune with the given TUNE_FEATURES, for setting
# TUNE_RISCV_PKGARCH:tune-<name>. A RISCV_PROFILES name (plus any extensions
# enabled on top of it) is preferred, since otherwise the full ISA extension
# list will exceed what the sstate class can handle. When several profiles
# match, the one with the most extensions is preferred, as it is the more
# specific/clearer match. If there's no clear profile matching the extension
# list, then fall back to riscv_exact_pkgarch().
def riscv_pkgarch(features):
    if not features:
        return ''

    current = set(features.split())

    best_match = None
    best_features = None
    for name in RISCV_PROFILES:
        profile_features = set(riscv_isa_to_tune(name).split())
        if profile_features.issubset(current):
            if best_features is None or len(profile_features) > len(best_features):
                best_match = name
                best_features = profile_features

    if best_match is None:
        return riscv_exact_pkgarch(features)

    extra = sorted(current - best_features)
    return '_'.join([best_match] + extra)
