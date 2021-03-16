require recipes-devtools/openocd/openocd_git.bb

PV = "riscv"

SRC_URI = " \
    git://github.com/riscv/riscv-openocd.git;protocol=http;branch=riscv;name=openocd \
    git://repo.or.cz/r/git2cl.git;protocol=http;destsuffix=tools/git2cl;name=git2cl \
    git://repo.or.cz/r/jimtcl.git;protocol=http;destsuffix=git/jimtcl;name=jimtcl \
    git://repo.or.cz/r/libjaylink.git;protocol=http;destsuffix=git/src/jtag/drivers/libjaylink;name=libjaylink \
"

SRCREV_openocd = "50a5971be27ff11061776afc8014bbff22870b7c"

COMPATIBLE_HOST = "(x86_64.*|i.86.*|riscv32|riscv64).*-linux"
