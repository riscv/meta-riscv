FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# RV32 port is still not upstream yet

SRC_URI:riscv32 = "\
    git://github.com/kraj/libseccomp.git;branch=riscv32;protocol=https \
    file://adjtime.patch \
    file://run-ptest \
"

SRCREV:riscv32 = "063724705f6715f9339fd8cbe2eb751f28b3b70d"

do_configure:prepend:riscv32() {
    sed -i -e "s|\[0.0.0\]|\['${PV}'\]|g" ${S}/configure.ac
}
