VISIONFIVE2FW_DATE ?= "20230808"
# VF2_v3.4.5
SRC_URI += "git://github.com/starfive-tech/soft_3rdpart.git;protocol=https;lfs=1;branch=JH7110_VisionFive2_devel;rev=12dba467921f5c8154b2afb47eb946eb3b0353a2"
HOMEPAGE ?= "https://github.com/starfive-tech/soft_3rdpart"

IMG_GPU_POWERVR_VERSION = "img-gpu-powervr-bin-1.19.6345021"

PV ?= "${VISIONFIVE2FW_DATE}"
