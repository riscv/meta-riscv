VISIONFIVE2FW_DATE ?= "20240826^"
# JH7110_VF2_6.6_v5.13.1
SRCREV = "4918bd0772edbfd9ccf26e97b55091f72053ec81"
SRC_URI += "git://github.com/starfive-tech/soft_3rdpart.git;protocol=https;lfs=1;nobranch=1"
HOMEPAGE ?= "https://github.com/starfive-tech/soft_3rdpart"

IMG_GPU_POWERVR_VERSION = "img-gpu-powervr-bin-1.19.6345021"

PV ?= "${VISIONFIVE2FW_DATE}"
