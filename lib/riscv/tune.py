#
# SPDX-License-Identifier: MIT
#

import hashlib

import oe.tune

# RISC-V profile name -> equivalent ISA string, i.e. the mandatory extensions of
# each profile as ratified in the RISC-V Profiles specification
# (https://github.com/riscv/riscv-profiles):
#   RVA20U64, RVA22U64: RVA20/RVA22 Profiles, version 1.0
#   RVA23U64: RVA23 Profiles, version 1.0
# plus the extensions those imply (e.g. zmmul, zaamo, zca, zve*).
#
# The profile names are used as package architectures, so each entry here is an
# ABI claim: packages built for "rva22u64" must keep meaning exactly this
# extension set. Do not regenerate these from compiler output (e.g. gcc's
# -march expansion); a newer compiler, or clang, may expand a profile
# differently. Changing an entry changes what existing package feeds mean.
RISCV_PROFILES = {
    'rva20u64': 'rv64imafdc_ziccamoa_ziccif_zicclsm_ziccrse_zicntr_zicsr_zmmul_za128rs_zaamo_zalrsc_zca_zcd',
    'rva22u64': 'rv64imafdcb_zic64b_zicbom_zicbop_zicboz_ziccamoa_ziccif_zicclsm_ziccrse_zicntr_zicsr_zihintpause_zmmul_za64rs_zaamo_zalrsc_zfhmin_zca_zcd_zba_zbb_zbs_zkt',
    'rva23u64': 'rv64imafdcbv_zic64b_zicbom_zicbop_zicboz_ziccamoa_ziccif_zicclsm_ziccrse_zicntr_zicond_zicsr_zihintntl_zihintpause_zihpm_zimop_zmmul_za64rs_zaamo_zalrsc_zawrs_zfa_zfhmin_zca_zcb_zcd_zcmop_zba_zbb_zbs_zkt_zvbb_zve32f_zve32x_zve64d_zve64f_zve64x_zvfhmin_zvkb_zvkt_zvl128b_zvl32b_zvl64b_supm',
}

# Extensions implied by another enabled extension (per GCC's
# riscv_implied_info). zicsr (implied by f) is left out for backwards
# compatibility, i.e. so the package archs of the existing non-profile tunes
# (e.g.  riscv64imafdc_zicsr_zifencei) stay the same.
RISCV_IMPLIED = {
    'm': ['zmmul'],
    'a': ['zaamo', 'zalrsc'],
    'c': ['zca'],
    'b': ['zba', 'zbb', 'zbs'],
    'v': ['zve32f', 'zve32x', 'zve64d', 'zve64f', 'zve64x', 'zvl32b', 'zvl64b', 'zvl128b'],
    'zcb': ['zca'],
    'zcd': ['zca'],
    'zcmop': ['zca'],
    'zvbb': ['zvkb'],
    # The vector subsets nest, for tunes with some of them but not v. Each
    # entry lists everything it implies, since riscv_implied() only does one
    # level of lookup.
    'zve64d': ['zve32f', 'zve32x', 'zve64f', 'zve64x', 'zvl32b', 'zvl64b'],
    'zve64f': ['zve32f', 'zve32x', 'zve64x', 'zvl32b', 'zvl64b'],
    'zve64x': ['zve32x', 'zvl32b', 'zvl64b'],
    'zve32f': ['zve32x', 'zvl32b'],
    'zve32x': ['zvl32b'],
    'zvfhmin': ['zve32f', 'zve32x', 'zvl32b'],
    'zvl128b': ['zvl32b', 'zvl64b'],
    'zvl64b': ['zvl32b'],
    # a reservation set of at most 64 bytes is also at most 128 bytes
    'za64rs': ['za128rs'],
}

# riscv_isa_to_tune(isa)
#
# Like oe.tune.riscv_isa_to_tune(), but also accepts the profile names listed in
# RISCV_PROFILES.
def riscv_isa_to_tune(isa):
    return oe.tune.riscv_isa_to_tune(RISCV_PROFILES.get(isa.lower(), isa))

# riscv_implied(enabled)
#
# Return the set of extensions implied by those in enabled.
def riscv_implied(enabled):
    implied = {i for e in enabled for i in RISCV_IMPLIED.get(e, [])}
    if 'c' in enabled and 'd' in enabled:
        implied |= {'zcd', 'zca'}
    return implied

# Single-letter extensions, in ISA string order. b isn't written out, as its
# zba, zbb and zbs components are used instead.
RISCV_SINGLE_ORDER = 'eimafdqlckjtpvh'

# Multi-letter extensions are ordered by their prefix in this order (standard,
# supervisor-level, then vendor extensions)...
RISCV_MULTI_PREFIXES = 'zsx'

# ...then by the letter after the prefix, in this order, and then
# alphabetically
RISCV_Z_ORDER = 'imafdqcbkjtpvh'

# _isa_features(features)
#
# Return the set of ISA features in TUNE_FEATURES, leaving out any other tune
# flags (e.g. "sifive-7-series"), which aren't part of the package arch.
def _isa_features(features):
    return {f for f in features.split()
            if f in ('rv', '32', '64') or len(f) == 1 or (f[0] in RISCV_MULTI_PREFIXES and f.isalnum())}

# riscv_exact_pkgarch(features)
#
# Return the package arch spelling out every extension in TUNE_FEATURES, e.g.
# riscv64imafdc_zicsr_zifencei. Extensions implied by others in the name are
# left out; as b is never written out, zba, zbb and zbs are always kept.
def riscv_exact_pkgarch(features):
    current = _isa_features(features)
    if 'd' in current:
        current.add('f')
    if current & {'f', 'd'}:
        current.add('zicsr')
    if 'b' in current:
        current |= {'zba', 'zbb', 'zbs'}
    current.discard('b')
    current -= riscv_implied(current)

    # Every extension must be written out, or tunes with different ISAs would
    # share a package arch
    unknown = {e for e in current if len(e) == 1 and e not in RISCV_SINGLE_ORDER}
    if unknown:
        raise ValueError('Unknown RISC-V extensions in TUNE_FEATURES: %s' % ' '.join(sorted(unknown)))

    xlen = '64' if '64' in current else '32'
    single = ''.join(e for e in RISCV_SINGLE_ORDER if e in current)
    multi = sorted((e for e in current if len(e) > 1 and e[0] in RISCV_MULTI_PREFIXES),
                   key=lambda e: (RISCV_MULTI_PREFIXES.index(e[0]), RISCV_Z_ORDER.find(e[1]), e))
    return '_'.join(['riscv' + xlen + single] + multi)

# Package archs longer than this keep their first part (the profile name, or
# the base ISA and single-letter extensions) and replace the rest with a hash,
# prefixed with 0x (which no extension name can start with), e.g.
# rva22u64_0x0123456789, to keep sstate and build paths short
RISCV_PKGARCH_MAXLEN = 32

# _shorten(pkgarch)
#
# Shorten pkgarch as described for RISCV_PKGARCH_MAXLEN. The hash is computed
# from the canonical extension list, so it only changes along with it.
def _shorten(pkgarch):
    if len(pkgarch) <= RISCV_PKGARCH_MAXLEN:
        return pkgarch
    base, rest = pkgarch.split('_', 1)
    return '%s_0x%s' % (base, hashlib.sha256(rest.encode()).hexdigest()[:10])

# riscv_readable_pkgarch(features)
#
# Return the full, readable package arch for a tune with the given
# TUNE_FEATURES, before riscv_pkgarch() shortens it. A RISCV_PROFILES name (plus any extensions
# enabled on top of it) is preferred, since otherwise the full ISA extension
# list will exceed what the sstate class can handle. When several profiles
# match, the one with the most extensions is preferred, as it is the more
# specific/clearer match. If there's no clear profile matching the extension
# list, then fall back to riscv_exact_pkgarch(). Either way, extensions implied
# by others are left out.
def riscv_readable_pkgarch(features):
    if not features:
        return ''

    current = _isa_features(features)
    # Match profiles whether or not implied extensions (or the b shorthand)
    # were listed. d implies f, which riscv_implied() leaves out as the exact
    # form always writes f out for d (see riscv_exact_pkgarch()).
    if {'zba', 'zbb', 'zbs'} <= current:
        current.add('b')
    if 'd' in current:
        current.add('f')
    implied = riscv_implied(current)
    current |= implied
    # f implies zicsr, which riscv_implied() leaves out to keep the existing
    # exact names (see RISCV_IMPLIED), so add it here for profile matching only
    if current & {'f', 'd'}:
        current.add('zicsr')

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

    extra = sorted(current - best_features - implied)
    return '_'.join([best_match] + extra)

# riscv_pkgarch(features)
#
# Return the package arch for a tune with the given TUNE_FEATURES, for setting
# TUNE_RISCV_PKGARCH:tune-<name>: riscv_readable_pkgarch(), shortened if it's
# longer than RISCV_PKGARCH_MAXLEN.
def riscv_pkgarch(features):
    return _shorten(riscv_readable_pkgarch(features))
