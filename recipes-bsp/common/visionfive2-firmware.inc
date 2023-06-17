VISIONFIVE2FW_DATE ?= "20230524"
# VF2_v3.0.4
SRC_URI += "git://github.com/starfive-tech/soft_3rdpart.git;protocol=https;lfs=1;branch=JH7110_VisionFive2_devel;rev=a8d360763d203874fefe38fad2b041b98d674743"
HOMEPAGE ?= "https://github.com/starfive-tech/soft_3rdpart"

PV ?= "${VISIONFIVE2FW_DATE}"
