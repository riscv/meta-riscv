# Settings shared by all Milk-V Duo boards (duo, duo256m, duos)

require conf/machine/include/riscv/tune-riscv.inc

# Override shared by all Milk-V Duo boards (duo, duo256m, duos). Use
# :milkv-duo-common for family-wide settings; :milkv-duo is the Duo (CV1800B) only.
MACHINEOVERRIDES =. "milkv-duo-common:"

MACHINE_FEATURES = "screen keyboard ext2 serial"

KERNEL_CLASSES = "kernel"
KERNEL_IMAGETYPE = "Image.gz"

PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot"
PREFERRED_PROVIDER_virtual/kernel ?= "linux-mainline"

PREFERRED_VERSION_openocd-native = "riscv"
PREFERRED_VERSION_openocd = "riscv"

SERIAL_CONSOLES = "115200;ttyS0"

IMAGE_FSTYPES += "wic.gz wic.bmap ext4"

# opensbi
RISCV_SBI_PLAT = "generic"

EXTRA_IMAGEDEPENDS += "milkv-duo-fsbl"

WKS_FILE ?= "milkv-duo.wks"
WKS_FILE_DEPENDS:append = " milkv-duo-fsbl"
