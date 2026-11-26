#
# Copyright OpenEmbedded Contributors
#
# SPDX-License-Identifier: MIT
#

from oeqa.selftest.case import OESelftestTestCase
from oeqa.utils.commands import get_bb_var, get_bb_vars

class SpacemitTuneTests(OESelftestTestCase):
    @classmethod
    def setUpClass(cls):
        super().setUpClass()
        cls.orig_machine = get_bb_var('MACHINE')

    def machine_config(self, machine):
        # Builds set up by bitbake-setup select the machine with a builtin
        # fragment, which is fatal alongside a MACHINE assignment, so drop it
        return 'OE_FRAGMENTS:remove = "machine/%s"\nMACHINE = "%s"\n' % (self.orig_machine, machine)

    def test_spacemit_x60_tune(self):
        """
        Check the spacemit-x60 tune, which the K1 machines use by default:
        its package arch, -mcpu option and compatible package archs.
        """
        self.write_config(self.machine_config('bananapi-f3'))
        v = get_bb_vars(['DEFAULTTUNE', 'TUNE_PKGARCH', 'TUNE_CCARGS', 'PACKAGE_EXTRA_ARCHS'])
        self.assertEqual(v['DEFAULTTUNE'], 'spacemit-x60')
        # The package arch is part of the ABI, so this must never change
        self.assertEqual(v['TUNE_PKGARCH'], 'spacemit-x60')
        self.assertIn(' -mcpu=spacemit-x60', v['TUNE_CCARGS'])
        self.assertEqual(v['PACKAGE_EXTRA_ARCHS'].split(),
                         ['riscv64imafdc_zicsr_zifencei', 'riscv64imafdcv_zicsr_zifencei',
                          'rva20u64_zifencei', 'rva22u64_zifencei', 'spacemit-x60'])

        # The other tunes are still available on the K1 machines, without -mcpu
        self.write_config(self.machine_config('bananapi-f3') + 'DEFAULTTUNE = "riscv64gc"\n')
        self.assertNotIn('-mcpu', get_bb_var('TUNE_CCARGS'))

    def test_spacemit_x100_tune(self):
        """
        Check the spacemit-x100 tune, which the K3 machines use by default:
        its package arch, core tuning options and compatible package archs.
        """
        self.write_config(self.machine_config('k3-pico-itx-mainline'))
        v = get_bb_vars(['DEFAULTTUNE', 'TUNE_PKGARCH', 'TUNE_CCARGS', 'PACKAGE_EXTRA_ARCHS'])
        self.assertEqual(v['DEFAULTTUNE'], 'spacemit-x100')
        # The package arch is part of the ABI, so this must never change
        self.assertEqual(v['TUNE_PKGARCH'], 'spacemit-x100')
        self.assertIn(' -mtune=generic-ooo', v['TUNE_CCARGS'])
        self.assertEqual(v['PACKAGE_EXTRA_ARCHS'].split(),
                         ['riscv64imafdc_zicsr_zifencei', 'riscv64imafdcv_zicsr_zifencei',
                          'rva20u64_zifencei', 'rva22u64_zifencei', 'rva23u64_zifencei',
                          'spacemit-x100'])

        # The core tuning options can be changed, e.g. for GCC 17 or later
        self.write_config(self.machine_config('k3-pico-itx-mainline') +
                          'SPACEMIT_X100_CCARGS = "-mcpu=spacemit-x100"\n')
        ccargs = get_bb_var('TUNE_CCARGS')
        self.assertIn(' -mcpu=spacemit-x100', ccargs)
        self.assertNotIn('-mtune=generic-ooo', ccargs)

        # The other tunes are still available on the K3 machines, without the
        # core tuning options
        self.write_config(self.machine_config('k3-pico-itx-mainline') + 'DEFAULTTUNE = "riscv64gc"\n')
        self.assertNotIn('generic-ooo', get_bb_var('TUNE_CCARGS'))
