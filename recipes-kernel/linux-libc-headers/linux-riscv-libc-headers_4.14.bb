require recipes-kernel/linux-libc-headers/linux-libc-headers.inc
BRANCH = "riscv-for-submission-v9"
SRCREV = "4a8451edde0d6ebd877f2cc0585667ec1178f89e"
SRC_URI = "git://github.com/riscv/riscv-linux.git;branch=${BRANCH} \
          "

S = "${WORKDIR}/git"

do_patch[depends] = "kern-tools-native:do_populate_sysroot"

PROVIDES = "linux-libc-headers"
