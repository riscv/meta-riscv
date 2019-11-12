require recipes-devtools/openocd/openocd_git.bb

SRC_URI = " \
    git://github.com/riscv/riscv-openocd.git;protocol=http;branch=riscv;name=openocd \
    git://repo.or.cz/r/git2cl.git;protocol=http;destsuffix=tools/git2cl;name=git2cl \
    git://repo.or.cz/r/jimtcl.git;protocol=http;destsuffix=git/jimtcl;name=jimtcl \
    git://repo.or.cz/r/libjaylink.git;protocol=http;destsuffix=git/src/jtag/drivers/libjaylink;name=libjaylink \
"

SRCREV_openocd = "f93ede5401c711e55d9852986aa399c0318efb22"
