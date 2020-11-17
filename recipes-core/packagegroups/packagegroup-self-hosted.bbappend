FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

RDEPENDS_packagegroup-self-hosted-debug_remove_riscv32 = "strace tcf-agent"
