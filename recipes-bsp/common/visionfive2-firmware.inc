VISIONFIVE2FW_DATE ?= "20230217"
# VF2_v2.10.4
SRC_URI += "git://github.com/starfive-tech/soft_3rdpart.git;protocol=https;lfs=1;branch=JH7110_VisionFive2_devel;rev=7e127ac4f4f4c4e202a3b5c5b2702c02ce1bde5e"
HOMEPAGE ?= "https://github.com/starfive-tech/soft_3rdpart"

PV ?= "${VISIONFIVE2FW_DATE}"
