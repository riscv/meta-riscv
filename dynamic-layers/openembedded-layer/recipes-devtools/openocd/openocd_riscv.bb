require recipes-devtools/openocd/openocd_git.bb

PV = "riscv"

SRC_URI = " \
    git://github.com/riscv/riscv-openocd.git;protocol=http;branch=riscv;name=openocd \
    git://repo.or.cz/r/git2cl.git;protocol=http;destsuffix=tools/git2cl;name=git2cl \
    git://repo.or.cz/r/jimtcl.git;protocol=http;destsuffix=git/jimtcl;name=jimtcl \
    git://repo.or.cz/r/libjaylink.git;protocol=http;destsuffix=git/src/jtag/drivers/libjaylink;name=libjaylink \
    file://0001-bitbang-Make-bitbang_swd-extern-definition.patch \
"

SRCREV_openocd = "03f943ae239f59727ef47e46fffc31dd405a642d"

COMPATIBLE_HOST = "(x86_64.*|i.86.*|riscv32|riscv64).*-linux"
