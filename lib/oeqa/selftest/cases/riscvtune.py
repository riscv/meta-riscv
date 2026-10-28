#
# Copyright OpenEmbedded Contributors
#
# SPDX-License-Identifier: MIT
#

import os
import sys

# oe-selftest only adds the layers' lib directories to sys.path after it has
# loaded the test modules, so add this layer's here for the import below.
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..', '..', '..')))
from riscv.tune import riscv_implied, riscv_isa_to_tune, riscv_pkgarch
from oeqa.selftest.case import OESelftestTestCase
from oeqa.utils.commands import bitbake, get_bb_var, get_bb_vars

class RiscvTuneTests(OESelftestTestCase):
    @classmethod
    def setUpClass(cls):
        super().setUpClass()
        cls.orig_machine = get_bb_var('MACHINE')

    def machine_config(self, machine):
        # Builds set up by bitbake-setup select the machine with a builtin
        # fragment, which is fatal alongside a MACHINE assignment, so drop it
        return 'OE_FRAGMENTS:remove = "machine/%s"\nMACHINE = "%s"\n' % (self.orig_machine, machine)

    def test_riscv_default_tunes(self):
        """
        Parse every RISC-V tune and check it yields a usable package arch, and
        that PACKAGE_EXTRA_ARCHS only lists archs of other tunes whose
        extensions are all available in this one.
        """
        # Only variables are checked here, so the riscv32 tunes can use the same
        # machine. The baremetal-riscv32 machines can't be used, as they set
        # DEFAULTTUNE after local.conf is parsed.
        self.write_config(self.machine_config('freedom-u540'))
        tunes = get_bb_var('AVAILTUNES').split()

        vars = {}
        for tune in tunes:
            self.write_config(self.machine_config('freedom-u540') + 'DEFAULTTUNE = "%s"\n' % tune)
            vars[tune] = get_bb_vars(['TUNE_FEATURES', 'TUNE_PKGARCH', 'PACKAGE_EXTRA_ARCHS', 'PACKAGE_ARCHS'])

        pkgarch_features = {v['TUNE_PKGARCH']: set(v['TUNE_FEATURES'].split()) for v in vars.values()}

        for tune, v in vars.items():
            with self.subTest(tune=tune):
                self.assertTrue(v['TUNE_FEATURES'], 'empty TUNE_FEATURES')
                self.assertTrue(v['TUNE_PKGARCH'], 'empty TUNE_PKGARCH')
                self.assertIn(v['TUNE_PKGARCH'], v['PACKAGE_ARCHS'].split())
                # sstate/package names get unwieldy past this
                self.assertLessEqual(len(v['TUNE_PKGARCH']), 64, v['TUNE_PKGARCH'])
                # The literal package arch must match what lib/riscv/tune.py derives
                self.assertEqual(v['TUNE_PKGARCH'], riscv_pkgarch(v['TUNE_FEATURES']))

                features = set(v['TUNE_FEATURES'].split())
                features |= riscv_implied(features)
                for arch in v['PACKAGE_EXTRA_ARCHS'].split():
                    self.assertIn(arch, pkgarch_features, '%s is not the package arch of any tune' % arch)
                    self.assertLessEqual(pkgarch_features[arch], features, '%s needs extensions %s lacks' % (arch, tune))

        # Profile tunes are named after the profile, and their literal
        # TUNE_FEATURES match the profile's definition in lib/riscv/tune.py
        for profile in ['rva20u64', 'rva22u64', 'rva23u64']:
            self.assertEqual(vars[profile]['TUNE_PKGARCH'], profile + '_zifencei')
            self.assertEqual(vars[profile]['TUNE_FEATURES'], riscv_isa_to_tune(profile) + ' zifencei')

        # Legacy tune names behave exactly like the tunes they alias
        for alias, target in [('riscv64', 'riscv64gc'), ('riscv32', 'riscv32gc')]:
            with self.subTest(alias=alias):
                self.assertIn(alias, tunes)
                self.assertEqual(vars[alias], vars[target])

    def test_riscv_unusual_tunes(self):
        """
        Define custom tunes with unusual extension combinations, as a BSP
        might, and check each one builds: the sanity checks accept the
        package arch, gcc and binutils accept the -march string, and the
        package arch fits in the sstate object names.
        """
        def profile(name, drop=None):
            return ' '.join(f for f in riscv_isa_to_tune(name).split() if f != drop) + ' zifencei'
        tunes = {
            # no float (lp64 ABI), with bitmanip
            'rv64-nofloat-bitmanip': riscv_isa_to_tune('rv64imac_zicsr_zifencei_zba_zbb_zbs'),
            # no profile, scalar extensions on top of gc
            'rv64gc-scalar-extras': riscv_isa_to_tune('rv64gc_zicbom_zicboz_zicond_zfa_zfhmin'),
            # profile plus vector crypto extensions
            'rva20-vector-crypto': profile('rva20u64') + ' v zvbb zvkt',
            # a profile missing one extension, falling back to the previous one
            'rva22-no-zkt': profile('rva22u64', 'zkt'),
            # rva23u64 without the full vector extension
            'rva23-no-v': profile('rva23u64', 'v'),
        }

        config = self.machine_config('freedom-u540')
        for tune, features in tunes.items():
            config += 'AVAILTUNES += "%s"\n' % tune
            config += 'TUNE_FEATURES:tune-%s = "%s"\n' % (tune, features)
            # Custom tunes must set their own package arch
            config += 'TUNE_RISCV_PKGARCH:tune-%s = "%s"\n' % (tune, riscv_pkgarch(features))
            config += 'PACKAGE_EXTRA_ARCHS:tune-%s = "${TUNE_RISCV_PKGARCH:tune-%s}"\n' % (tune, tune)

        for tune in tunes:
            with self.subTest(tune=tune):
                self.write_config(config + 'DEFAULTTUNE = "%s"\n' % tune)
                # small, and compiled with TUNE_CCARGS. It has no do_build, and
                # populate_sysroot also writes an sstate object.
                bitbake('libgcc-initial -c populate_sysroot')
