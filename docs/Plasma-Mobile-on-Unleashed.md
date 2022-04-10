# Running Plasma Mobile on the HiFive Unleased

![RISC-V Plamo](RISC-V-Plamo.jpg)

Written by: Alistair Francis <alistair.francis@wdc.com>

## Dependencies

To build plasma mobile you will need (as well as meta-riscv):

* URI: https://github.com/openembedded/openembedded-core
  * branch: master
  * revision: HEAD
* URI: https://github.com/openembedded/bitbake
  * branch: master
  * revision: HEAD
* URI: https://git.yoctoproject.org/meta-java
  * branch: master
  * revision: HEAD
* URI: https://github.com/meta-qt5/meta-qt5.git
  * branch: master
  * revision: HEAD
* URI: https://github.com/KDE/yocto-meta-kf5.git
  * branch: master
  * revision: HEAD
* URI: https://github.com/KDE/yocto-meta-kde.git
  * branch: master
  * revision: HEAD

You will also need the HiFive Unleashed with the Microsemi expansion board. As well as that you need a PCIe GPU and PCIe to USB card (for inputs). This was tested using an AMD r600 series GPU. Your millage will vary depending on the GPU selected.


## Setup Build Environment

Follow the usual steps you use to build Yocto images. There are more details on the main meta-riscv README. It is probably best to ensure you can bitbake ```core-image-full-cmdline``` before attempting Plasma Mobile.

Once you know you can build for the ```freedom-u540``` machine you should add these lines to your local.conf or distro:

```
## Required DISTRO_FEATURES
DISTRO_FEATURES   += " wayland x11 kde opengl pam polkit xattr"

## Create kde user for SDDM
INHERIT += "extrausers"
EXTRA_USERS_PARAMS = "useradd kde; \
                      usermod -P kde kde;"

## Use the Microsemi Expansion board DT in OpenSBI (passed to the kernel)
RISCV_SBI_FDT:freedom-u540      = "HiFiveUnleashed-MicroSemi-Expansion.dtb"

## Install GPU driver for the GPU being used, in this example it's a Radeon R5 230
PACKAGECONFIG:append:pn-mesa    = " r600"
```

You can then start the build by running:
```
MACHINE=freedom-u540 bitbake core-image-plasma-mobile
```

## Deploying

Follow the top-level README to flash the image to an SD card (you will probably need an SD card with more than 10GB of storage). Once you boot the device Plasma Mobile should start.

## Known issues

  * Sometimes the PCIe devices do not work on first boot. In case you can't detect the PCIe devices on the board try power cycling the board and then they should appear.

Please feel free to raise issues for any problems that you encounter.
