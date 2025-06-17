fdt_file=x1_orangepi-rv2.dtb
rootdev=/dev/mmcblk0p4

setenv bootargs "console=ttyS0,115200 earlycon=sbi root=${rootdev} rw rootwait  rootfstype=ext4 clk_ignore_unused"

load mmc 0:3 ${kernel_addr_r} Image
load mmc 0:3 ${ramdisk_addr_r} initramfs.img
load mmc 0:3 ${fdt_addr_r} ${fdt_file}

booti ${kernel_addr_r} ${ramdisk_addr_r} ${fdt_addr_r}
