# Running Plasma Mobile on the HiFive Unleased

![RISC-V Plamo](RISC-V-Plamo.jpg)

Written by: Alistair Francis <alistair.francis@wdc.com>

## Dependencies

To build plasma mobile you will need (as well as meta-riscv):

* URI: git://github.com/openembedded/openembedded-core
  * branch: master
  * revision: HEAD
* URI: git://github.com/openembedded/bitbake
  * branch: master
  * revision: HEAD
* URI: git://git.yoctoproject.org/meta-java
  * branch: master
  * revision: HEAD
* URI: git://github.com/meta-qt5/meta-qt5.git
  * branch: master
  * revision: HEAD
* URI: git://github.com/KDE/yocto-meta-kf5.git
  * branch: master
  * revision: HEAD
* URI: git://github.com/KDE/yocto-meta-kde.git
  * branch: master
  * revision: HEAD

You will also need the HiFive Unleased with the Microsemi expansion board. As well as that you need a PCIe GPU and PCIe to USB card (for inputs). This was tested using a AMD r600 series GPU. Your millage will vary depending on the GPU selected.


## Setup Build Environment

Follow the usual steps you use to build Yocto images. There are more details on the main meta-riscv README. It is probably best to ensure you can bitbake ```core-image-full-cmdline``` before attempting Plasma Mobile.

Once you know you can build for the ```freedom-u540``` machine you should add these lines to your local.conf or distro:

```
DISTRO_FEATURES_freedom-u540          += " wayland x11 opengl xattr gles directfb"
DISTRO_FEATURES_freedom-u540          += " polkit kde pam pulseaudio"
FEATURE_PACKAGES_append_freedom-u540   = " wayland mesa-megadriver"
FEATURE_PACKAGES_append_freedom-u540   = " mesa-driver-swrast"
IMAGE_INSTALL_append_freedom-u540      = " wayland pulseaudio pulseaudio-server"

RISCV_SBI_FDT_freedom-u540 = "HiFiveUnleashed-MicroSemi-Expansion.dtb"

PACKAGECONFIG_append_pn-mesa = "r600"
```

You can then start the buld by running:
```
MACHINE=freedom-u540 bitbake core-image-plasma-mobile
```

## Deploying

Follow the top level README to flash the image to an SD card (you will probably need an SD card with more then 10GB of storage). Once you boot the device Plasma Mobile should start.

## Known issues

 * Qt5 base will most likely fail to cross compile for RISC-V. As a work around you can apply the patch from here: https://github.com/meta-qt5/meta-qt5/pull/156/commits
 * Sometimes the PCIe devices do not work on first boot. In you can't detect the PCIe devices on the board try power cycling the board and then they should appear.

Please feel free to raise issues for any problems that you encounter.
