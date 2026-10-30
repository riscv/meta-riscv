#
# Copyright OpenEmbedded Contributors
#
# SPDX-License-Identifier: MIT
#

import os
import sys
from unittest.case import TestCase

# oe-selftest only adds the layers' lib directories to sys.path after it has
# loaded the test modules, so add this layer's here for the import below.
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..', '..', '..')))
from riscv.tune import RISCV_IMPLIED, RISCV_PROFILES, riscv_isa_to_tune, riscv_implied, riscv_exact_pkgarch, riscv_pkgarch

def features(isa):
    return set(riscv_isa_to_tune(isa).split())

class TestRiscvIsaToTune(TestCase):
    def test_abbreviation(self):
        self.assertEqual(riscv_isa_to_tune('rv64gc'), 'rv 64 i m a f d c zicsr zifencei')
        self.assertEqual(riscv_isa_to_tune('rv32gcv'), 'rv 32 i m a f d c v zicsr zifencei')

    def test_explicit(self):
        self.assertEqual(riscv_isa_to_tune('rv32imac_zicsr_zifencei'), 'rv 32 i m a c zicsr zifencei')
        self.assertEqual(riscv_isa_to_tune('RV64IMAFD_Zicsr'), 'rv 64 i m a f d zicsr')

    def test_profile(self):
        for name, isa in RISCV_PROFILES.items():
            self.assertEqual(riscv_isa_to_tune(name), riscv_isa_to_tune(isa))

    def test_not_riscv(self):
        self.assertEqual(riscv_isa_to_tune('x86_64'), 'x86_64')

    def test_profile_name_case(self):
        for name in RISCV_PROFILES:
            self.assertEqual(riscv_isa_to_tune(name.upper()), riscv_isa_to_tune(name))

class TestRiscvImplied(TestCase):
    def test_implied(self):
        self.assertEqual(riscv_implied({'m'}), {'zmmul'})
        self.assertEqual(riscv_implied({'c', 'd'}), {'zca', 'zcd'})
        self.assertEqual(riscv_implied({'za64rs'}), {'za128rs'})
        self.assertIn('zve64d', riscv_implied({'v'}))
        # vector subsets, without v
        self.assertEqual(riscv_implied({'zve64d', 'zvl128b'}),
                         {'zve32f', 'zve32x', 'zve64f', 'zve64x', 'zvl32b', 'zvl64b'})

    def test_zicsr_kept(self):
        # Dropping zicsr would rename the existing non-profile package archs
        self.assertNotIn('zicsr', riscv_implied({'f', 'd'}))

    def test_profiles_nest(self):
        # Later profiles are supersets of earlier ones, which the arch-rva*.inc
        # files rely on for PACKAGE_EXTRA_ARCHS
        names = ['rva20u64', 'rva22u64', 'rva23u64']
        for lower, higher in zip(names, names[1:]):
            h = features(higher)
            self.assertLessEqual(features(lower), h | riscv_implied(h), '%s is not a subset of %s' % (lower, higher))

    def test_implied_closed(self):
        # riscv_implied() only does one level of lookup, so each entry must
        # already list everything its implied extensions imply in turn
        for ext, implied in RISCV_IMPLIED.items():
            with self.subTest(ext=ext):
                self.assertLessEqual(riscv_implied(set(implied)), set(implied))

class TestRiscvPkgarch(TestCase):
    def test_no_features(self):
        self.assertEqual(riscv_pkgarch(''), '')

    def test_profiles(self):
        for name in RISCV_PROFILES:
            self.assertEqual(riscv_pkgarch(riscv_isa_to_tune(name)), name)
            self.assertEqual(riscv_pkgarch(riscv_isa_to_tune(name) + ' zifencei'), name + '_zifencei')

    def test_existing_names_unchanged(self):
        self.assertEqual(riscv_pkgarch(riscv_isa_to_tune('rv64gc')), 'riscv64imafdc_zicsr_zifencei')
        self.assertEqual(riscv_pkgarch(riscv_isa_to_tune('rv64gcv')), 'riscv64imafdcv_zicsr_zifencei')
        self.assertEqual(riscv_pkgarch(riscv_isa_to_tune('rv64imafd_zicsr_zifencei')), 'riscv64imafd_zicsr_zifencei')
        self.assertEqual(riscv_pkgarch(riscv_isa_to_tune('rv32imac_zicsr_zifencei')), 'riscv32imac_zicsr_zifencei')

    def test_most_specific_profile(self):
        # rva23u64 without supm isn't rva23u64, but still contains all of rva22u64
        f = riscv_isa_to_tune('rva23u64').replace(' supm', '')
        pkgarch = riscv_pkgarch(f)
        self.assertTrue(pkgarch.startswith('rva22u64_v_'), pkgarch)
        # implied by v, so left out
        self.assertNotIn('zve', pkgarch)

    def test_implied_not_listed(self):
        # b is implied by zba/zbb/zbs, so leaving it out still matches the profile
        f = riscv_isa_to_tune('rva22u64').replace(' b ', ' ')
        self.assertEqual(riscv_pkgarch(f), 'rva22u64')

    def test_zicsr_not_listed(self):
        # zicsr is implied by f, so leaving it out still matches the profile
        f = riscv_isa_to_tune('rva20u64').replace(' zicsr', '')
        self.assertEqual(riscv_pkgarch(f), 'rva20u64')

    def test_f_not_listed(self):
        # f is implied by d, so leaving it out still matches the profile
        f = riscv_isa_to_tune('rva20u64').replace(' f ', ' ')
        self.assertEqual(riscv_pkgarch(f), 'rva20u64')

    def test_exact_drops_implied(self):
        f = riscv_isa_to_tune('rv32imafdcv_zmmul_zaamo_zalrsc_zca_zcd_zve32x_zvl32b_zicsr_zifencei')
        self.assertEqual(riscv_pkgarch(f), 'riscv32imafdcv_zicsr_zifencei')

    def test_exact_keeps_bitmanip(self):
        # b is never written out in the exact pkgarch, so zba/zbb/zbs must stay
        exact = 'riscv64imafdc_zicsr_zifencei_zba_zbb_zbs'
        self.assertEqual(riscv_pkgarch(riscv_isa_to_tune('rv64gc_zba_zbb_zbs')), exact)
        self.assertEqual(riscv_exact_pkgarch(riscv_isa_to_tune('rv64gcb')), exact)

    def test_exact_canonical_order(self):
        # Extensions are written in ISA string order, whatever order they're
        # listed in, e.g. as in RISCV_PROFILES
        f = ' '.join(sorted(riscv_isa_to_tune('rv64gc_zicbom_zicond_zfa_zba_zkt').split()))
        self.assertEqual(riscv_exact_pkgarch(f), 'riscv64imafdc_zicbom_zicond_zicsr_zifencei_zfa_zba_zkt')

    def test_exact_zicsr_only_with_float(self):
        # zicsr is only added for f/d, so a no-float tune keeps what it lists
        self.assertEqual(riscv_exact_pkgarch('rv 64 i m a c zifencei'), 'riscv64imac_zifencei')
        self.assertEqual(riscv_exact_pkgarch('rv 64 i m a c zicsr zifencei'), 'riscv64imac_zicsr_zifencei')

    def test_exact_e_base(self):
        self.assertEqual(riscv_exact_pkgarch(riscv_isa_to_tune('rv32emc')), 'riscv32emc')

    def test_profile_exact_roundtrip(self):
        # The exact form of a profile's extensions maps back to the profile
        for name in RISCV_PROFILES:
            with self.subTest(profile=name):
                exact = riscv_exact_pkgarch(riscv_isa_to_tune(name))
                self.assertEqual(riscv_pkgarch(riscv_isa_to_tune('rv' + exact[len('riscv'):])), name)

    def test_profile_plus_extension(self):
        # Any one extension on top of a profile is appended to its name, and
        # the result stays short. Extensions the added one implies are left
        # out (e.g. zca for zcb, zvkb for zvbb).
        known = set(RISCV_IMPLIED) | {'zbc', 'zicond', 'zvbb'}
        for name in RISCV_PROFILES:
            known |= features(name)
        for name in RISCV_PROFILES:
            base = features(name) | {'zifencei'}
            for ext in sorted(known - base - {'32', 'e'}):
                with self.subTest(profile=name, ext=ext):
                    pkgarch = riscv_pkgarch(' '.join(sorted(base | {ext})))
                    self.assertTrue(pkgarch.startswith(name + '_'), pkgarch)
                    if ext not in riscv_implied(base):
                        self.assertIn(ext, pkgarch.split('_'))
                    self.assertLessEqual(len(pkgarch), 64, pkgarch)
        self.assertEqual(riscv_pkgarch(riscv_isa_to_tune('rva20u64') + ' zcb'), 'rva20u64_zcb')
        self.assertEqual(riscv_pkgarch(riscv_isa_to_tune('rva22u64') + ' v'), 'rva22u64_v')

    def test_profile_missing_extension(self):
        # Dropping one of a profile's own (not implied) extensions never gives
        # the profile's name; it falls back to the largest profile still
        # contained, or to the exact form. zicsr is implied by f, f by d, and
        # b by zba/zbb/zbs, so dropping those changes nothing.
        names = list(RISCV_PROFILES)
        for name in names:
            base = features(name)
            for ext in sorted(base - riscv_implied(base) - {'rv', '64', 'zicsr', 'f', 'b'}):
                with self.subTest(profile=name, ext=ext):
                    rest = base - {ext}
                    pkgarch = riscv_pkgarch(' '.join(sorted(rest)))
                    self.assertNotEqual(pkgarch.split('_')[0], name, pkgarch)
                    lower = [n for n in names if features(n) <= rest | riscv_implied(rest) | {'zicsr'}]
                    if lower:
                        self.assertTrue(pkgarch.startswith(lower[-1] + '_'), pkgarch)
                    else:
                        self.assertEqual(pkgarch, riscv_exact_pkgarch(' '.join(sorted(rest))))
        for ext in ['zicsr', 'f', 'b']:
            for name in names:
                with self.subTest(profile=name, ext=ext):
                    self.assertEqual(riscv_pkgarch(' '.join(sorted(features(name) - {ext}))), name)

    def test_rv32_never_profile(self):
        # The profiles are all RV64, so the same extensions on RV32 must not
        # be given a profile name
        for name in RISCV_PROFILES:
            with self.subTest(profile=name):
                f = riscv_isa_to_tune(name).replace('rv 64', 'rv 32')
                self.assertTrue(riscv_pkgarch(f).startswith('riscv32'), riscv_pkgarch(f))
