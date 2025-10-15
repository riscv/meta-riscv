fdt_file=k1-orangepi-rv2.dtb

setenv bootargs "console=ttyS0,115200 earlycon=sbi rw clk_ignore_unused rdinit=/bin/sh"

load mmc 0:3 ${kernel_addr_r} Image
load mmc 0:3 ${ramdisk_addr_r} initramfs.img
load mmc 0:3 ${fdt_addr_r} ${fdt_file}

booti ${kernel_addr_r} ${ramdisk_addr_r} ${fdt_addr_r}
