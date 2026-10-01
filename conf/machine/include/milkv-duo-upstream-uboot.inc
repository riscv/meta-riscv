# Settings for the Milk-V Duo boards that boot with upstream U-Boot (duo and
# duo256m). U-Boot standard boot scans the boot partition and loads the kernel
# and the device tree described by extlinux.conf. duos keeps the vendor U-Boot
# and its uImage.fit flow, so it does not include this file.

UBOOT_EXTLINUX = "1"
UBOOT_EXTLINUX_CONSOLE ?= ""
UBOOT_EXTLINUX_FDT ?= "/${@os.path.basename(d.getVar('KERNEL_DEVICETREE'))}"
UBOOT_EXTLINUX_KERNEL_ARGS ?= "ro rootwait console=ttyS0,115200 earlycon=sbi"
UBOOT_EXTLINUX_KERNEL_IMAGE ?= "/${KERNEL_IMAGETYPE}"
UBOOT_EXTLINUX_ROOT ?= "root=/dev/mmcblk0p2"

IMAGE_BOOT_FILES ?= " \
    extlinux.conf;extlinux/extlinux.conf \
    fip.bin \
    ${KERNEL_IMAGETYPE} \
    ${@os.path.basename(d.getVar('KERNEL_DEVICETREE'))} \
"

# extlinux.conf is deployed by u-boot
WKS_FILE_DEPENDS:append = " virtual/bootloader"
