require recipes-devtools/openocd/openocd_git.bb

PV = "riscv"

SRC_URI = " \
    git://github.com/riscv/riscv-openocd.git;protocol=https;branch=riscv;name=openocd \
    git://repo.or.cz/r/git2cl.git;protocol=http;branch=master;destsuffix=tools/git2cl;name=git2cl \
    git://repo.or.cz/r/jimtcl.git;protocol=http;branch=master;destsuffix=git/jimtcl;name=jimtcl \
    git://repo.or.cz/r/libjaylink.git;protocol=http;branch=master;destsuffix=git/src/jtag/drivers/libjaylink;name=libjaylink \
"

SRCREV_openocd = "50a5971be27ff11061776afc8014bbff22870b7c"

EXTRA_OECONF = "--enable-ftdi --disable-doxygen-html --disable-werror --enable-jtag_vpi"

COMPATIBLE_HOST = "(x86_64.*|i.86.*|riscv32|riscv64).*-linux"
