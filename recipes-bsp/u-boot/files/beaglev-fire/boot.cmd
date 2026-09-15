# The device tree overlays that the gateware keeps in its SPI flash are written
# for the vendor device tree, so they are not applied to the mainline one.

setenv bootargs "console=ttyS0,115200n8 earlycon root=/dev/mmcblk0p3 rootwait"

load ${devtype} ${devnum}:${distro_bootpart} ${kernel_addr_r} Image
load ${devtype} ${devnum}:${distro_bootpart} ${fdt_addr_r} mpfs-beaglev-fire.dtb

booti ${kernel_addr_r} - ${fdt_addr_r}
