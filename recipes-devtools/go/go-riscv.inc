FILESEXTRAPATHS_prepend := "${THISDIR}/go:"

export CGO_ENABLED_riscv64 = "0"

BRANCH = "riscvdev-go1.13"
SRCREV = "94a09bd32342daf02cbb43ddaff0d560bb2752f4"

SRC_URI_riscv64 = "\
    git://github.com/4a6f656c/riscv-go;protocol=https;branch=${BRANCH} \
    \
    file://0001-allow-CC-and-CXX-to-have-multiple-words.patch \
    file://0003-allow-GOTOOLDIR-to-be-overridden-in-the-environment.patch \
    file://0004-ld-add-soname-to-shareable-objects.patch \
    file://0005-make.bash-override-CC-when-building-dist-and-go_boot.patch \
    file://0006-cmd-dist-separate-host-and-target-builds.patch \
    file://0007-cmd-go-make-GOROOT-precious-by-default.patch \
    file://0008-use-GOBUILDMODE-to-set-buildmode.patch \
    \
    file://0010-cmd-go-make-content-based-hash-generation-less-pedan.patch \
"

S_riscv64 = "${WORKDIR}/git"

