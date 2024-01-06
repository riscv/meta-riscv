VISIONFIVE2FW_DATE ?= "20231221^"
# JH7110_VF2_6.1_v5.10.3
SRC_URI += "git://github.com/starfive-tech/soft_3rdpart.git;protocol=https;lfs=1;branch=JH7110_VisionFive2_devel;rev=819f2f5da8997689e3814daad4b683c92b6ce8c2"
HOMEPAGE ?= "https://github.com/starfive-tech/soft_3rdpart"

IMG_GPU_POWERVR_VERSION = "img-gpu-powervr-bin-1.19.6345021"

PV ?= "${VISIONFIVE2FW_DATE}"
