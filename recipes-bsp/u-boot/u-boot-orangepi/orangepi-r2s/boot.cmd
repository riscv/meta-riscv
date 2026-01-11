fdt_file=x1_orangepi-r2s.dtb
part uuid ${devtype} ${devnum}:2 uuid

setenv bootargs "earlycon=sbi earlyprintk console=ttyS0,115200 \
loglevel=8 clk_ignore_unused workqueue.default_affinity_scope=system \
root=PARTUUID=${uuid} rw rootwait"

load ${devtype} ${devnum}:1 ${kernel_addr_r} Image
load ${devtype} ${devnum}:1 ${ramdisk_addr_r} initramfs.img
load ${devtype} ${devnum}:1 ${fdt_addr_r} ${fdt_file}

booti ${kernel_addr_r} ${ramdisk_addr_r} ${fdt_addr_r}
