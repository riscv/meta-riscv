# K3-specific configuration for core-image-weston
require ${@bb.utils.contains('MACHINE', 'k3', 'image-k3.inc', '', d)}

# Image features for K3
IMAGE_FEATURES:append:k3 = " ssh-server-openssh allow-root-login "

# Set root password to 'bianbu'
inherit extrausers
EXTRA_USERS_PARAMS:k3 = "usermod -p '\$6\$sKMgaZHbRxDcYuHo\$tSd/ApNFXecZGM1owuH.hy71pjbPzfeupDYgyG6b5zkGgdfiLpuL2wyxOLvYkxDGxsOPZqZ76q8T6R/dFJUr2.' root;"

# Additional packages for K3
IMAGE_INSTALL:append:k3 = " \
    kernel-modules \
    linux-firmware-rtl8851 \
    linux-firmware-rtl8852 \
    linux-firmware-rtl8922 \
    k3-rootfs-overlay-weston \
    k3-rootfs-overlay-network \
    img-gpu-powervr \
    glmark2 \
"

# Image format: ext4 rootfs (titan packaging handles final output)
IMAGE_FSTYPES:append:k3 = " ext4 "

# Bootfs partition size (MB)
SDIMG_BOOTFS_SIZE:k3 ?= "256"

INITRAMFS_IMAGE:k3 = "initramfs-image-k3"

# Titan flashing tool support
require ${@bb.utils.contains('MACHINE', 'k3', 'image-k3-titan.inc', '', d)}