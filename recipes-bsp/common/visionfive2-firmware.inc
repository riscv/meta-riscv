VISIONFIVE2FW_DATE ?= "20230621"
# VF2_v3.1.5
SRC_URI += "git://github.com/starfive-tech/soft_3rdpart.git;protocol=https;lfs=1;branch=JH7110_VisionFive2_devel;rev=e2b76cd5733ce85d7145a6d71fb50a6218abed5c"
HOMEPAGE ?= "https://github.com/starfive-tech/soft_3rdpart"

IMG_GPU_POWERVR_VERSION = "img-gpu-powervr-bin-1.19.6345021"

PV ?= "${VISIONFIVE2FW_DATE}"
