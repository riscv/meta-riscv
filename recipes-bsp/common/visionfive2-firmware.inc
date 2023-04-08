VISIONFIVE2FW_DATE ?= "20230320"
# VF2_v2.11.5
SRC_URI += "git://github.com/starfive-tech/soft_3rdpart.git;protocol=https;lfs=1;branch=JH7110_VisionFive2_devel;rev=cd7b50cd9f9eca66c23ebd19f06a172ce0be591f"
HOMEPAGE ?= "https://github.com/starfive-tech/soft_3rdpart"

PV ?= "${VISIONFIVE2FW_DATE}"
