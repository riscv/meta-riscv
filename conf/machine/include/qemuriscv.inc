PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot"

require conf/machine/include/qemu.inc
require conf/machine/include/tune-riscv.inc

MACHINE_FEATURES = "screen keyboard ext2 ext3 serial"

KERNEL_IMAGETYPE = "vmlinux"

GDBVERSION = "riscv"

SERIAL_CONSOLES = "115200;ttyS0"

IMAGE_FSTYPES_append = " ext4"

MACHINE_EXTRA_RRECOMMENDS += " kernel-modules"

EXTRA_IMAGEDEPENDS += "riscv-pk"
RISCV_BBL_PAYLOAD ?= "${KERNEL_IMAGETYPE}"

# qemuboot options
QB_KERNEL_CMDLINE_APPEND = "console=ttyS0,115200 console=tty"
QB_MEM = "-m 512"
QB_MACHINE = "-machine virt"
QB_DEFAULT_KERNEL = "bbl"
#QB_SLIRP_OPT = "-netdev user,id=net0,hostfwd=tcp::22222-:22"
QB_OPT_APPEND = "-show-cursor -monitor null"
QB_TAP_OPT = "-netdev tap,id=net0,ifname=@TAP@,script=no,downscript=no"
QB_NETWORK_DEVICE = "-device virtio-net-device,netdev=net0,mac=@MAC@"
QB_ROOTFS_OPT = "-drive id=disk0,file=@ROOTFS@,if=none,format=raw -device virtio-blk-device,drive=disk0"
QB_SERIAL_OPT = "-device virtio-serial-device -chardev null,id=virtcon -device virtconsole,chardev=virtcon"
QB_TCPSERIAL_OPT = " -device virtio-serial-device -chardev socket,id=virtcon,port=@PORT@,host=127.0.0.1 -device virtconsole,chardev=virtcon"

BAD_RECOMMENDATIONS += "\
    libcxx-dev \
    libcxx-staticdev \
    compiler-rt-dev \
    compiler-rt-staticdev \
"

ASSUME_PROVIDED += "\
    libcxx-dev \
    libcxx-staticdev \
    compiler-rt-dev \
    compiler-rt-staticdev \
"
