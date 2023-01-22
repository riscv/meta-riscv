VISIONFIVE2FW_DATE ?= "20230111"

SRC_URI += "git://github.com/starfive-tech/soft_3rdpart.git;protocol=https;lfs=1;branch=JH7110_VisionFive2_devel;rev=b580826be8a00fb8143bc5ea3310ba12af612468"
HOMEPAGE ?= "https://github.com/starfive-tech/soft_3rdpart"

PV ?= "${VISIONFIVE2FW_DATE}"
