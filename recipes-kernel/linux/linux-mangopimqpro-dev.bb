require linux-d1-dev.inc

FILESEXTRAPATHS:prepend = "${FILE_DIRNAME}/linux-nezha:"
SUMMARY = "MangoPi MQ Pro dev kernel recipe"
## TODO Should be changed to
## https://github.com/smaeul/u-boot/commit/528ae9bc6c55edd3ffe642734b4132a8246ea777#diff-7a77df959a917850f0b29cd98afe6c3ca6de627a0c52dd9983cd42fede7a0e34
## once it's is merged into kernel too
## mangopi_mq_pro_defconfig

KBUILD_DEFCONFIG = "nezha_defconfig"
COMPATIBLE_MACHINE = "mangopi-mq-pro"

SRC_URI:append = " \
	       	   file://mangopi-mq-pro.cfg \
		   file://0001-Bluetooth-Add-new-quirk-for-broken-local-ext-feature.patch \
		   file://0002-Bluetooth-btrtl-add-support-for-the-RTL8723CS.patch \
		   file://0003-drivers-net-bluetooth-Enable-quirk-for-broken-local-.patch \
		 "
## Fixme check if features enabled			  
KERNEL_MODULE_AUTOLOAD:append:mangopi-mq-pro = " hci_uart 8723ds "
