FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

RDEPENDS:packagegroup-self-hosted-debug:remove:riscv32 = "strace tcf-agent"
