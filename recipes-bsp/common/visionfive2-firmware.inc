VISIONFIVE2FW_DATE ?= "20250427^"
# JH7110_VF2_6.12_v6.0.0
SRCREV = "b60da16be1b36453aa46599889c28310fd148fb8"
SRC_URI += "git://strlcat.eu/mirroring/jh7110_soft_3rdpart.git;protocol=https;branch=JH7110_VisionFive2_devel"
HOMEPAGE ?= "https://github.com/starfive-tech/soft_3rdpart"

IMG_GPU_POWERVR_VERSION = "img-gpu-powervr-bin-1.19.6345021"

PV ?= "${VISIONFIVE2FW_DATE}"
