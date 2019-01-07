# Copyright (C) 2019 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
            file://0001-RISC-V-add-riscv32-and-riscv64-architecture-support.patch \
            file://0002-RISC-V-Add-cache-flush-syscall.patch \
            file://0003-RISC-V-Fix-syscall_cp-on-riscv64-32.patch \
            file://0004-Use-the-generic-fcntl.h-header-Currently-the-fcntl.h.patch \
            file://0005-Fix-missing-macros-from-fcntl.h.patch \
"
