require recipes-devtools/openocd/openocd_git.bb

LIC_FILES_CHKSUM = "file://COPYING;md5=599d2d1ee7fc84c0467b3d19801db870"

PV = "riscv"

SRCREV = "b8f4b8887b0ad51e657bbbbc244ff69455984a50"
SRC_URI = "gitsm://github.com/riscv/riscv-openocd.git;protocol=https;branch=riscv"

EXTRA_OECONF += "--enable-jtag_vpi"

COMPATIBLE_HOST = "(x86_64.*|i.86.*|riscv32|riscv64).*-linux"
