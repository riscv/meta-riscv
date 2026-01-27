#!/bin/sh

IMAGE_TO_FLASH=${1:-core-image-minimal-beaglev-ahead.rootfs.ext4.sparse}

fastboot_wait_for_device() {
    echo -n "Waiting for device "
    while ! fastboot devices | grep -q .; do
        sleep 1
        echo -n "."
    done
    echo ""
}


echo "Please plug the BeagleV-Ahead board to your computer while maintaining the USB button"
echo "See: https://docs.beagleboard.org/boards/beaglev/ahead/02-quick-start.html#put-beaglev-ahead-in-usb-flash-mode"
fastboot_wait_for_device

echo "Flashing $IMAGE_TO_FLASH"
fastboot flash ram ./u-boot-with-spl.bin
fastboot reboot
fastboot_wait_for_device

fastboot oem format
fastboot flash uboot ./u-boot-with-spl.bin
fastboot flash boot ./boot.ext4.sparse
fastboot flash root ./${IMAGE_TO_FLASH}
echo "Flashing done"
fastboot reboot
