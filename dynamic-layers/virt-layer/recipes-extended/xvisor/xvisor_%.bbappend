XVISOR_PLAT_BASE = "`basename ${XVISOR_PLAT}`"

do_compile[depends] += "virtual/kernel:do_deploy"

xvisor_image_compile() {
  ## Create the basic firmware image ##
  cd ${S}/tests/${XVISOR_PLAT}/basic
  oe_runmake

  dtc -I dts -O dtb -o ${B}/build/tests/${XVISOR_PLAT}/${XVISOR_PLAT_BASE}-guest.dtb ${S}/tests/${XVISOR_PLAT}/${XVISOR_PLAT_BASE}-guest.dts

  rm -f ${B}/vmm-disk-basic.img
  mkfs.vfat -F 32 -S 512 -n VMM-BASIC-IMAGE -C ${B}/vmm-disk-basic.img 40960

  mmd -i ${B}/vmm-disk-basic.img ::system
  mcopy -i ${B}/vmm-disk-basic.img ${S}/docs/banner/roman.txt ::system/banner.txt
  mcopy -i ${B}/vmm-disk-basic.img ${S}/docs/logo/xvisor_logo_name.ppm ::system/logo.ppm

  mmd -i ${B}/vmm-disk-basic.img ::images
  mmd -i ${B}/vmm-disk-basic.img ::images/riscv
  mmd -i ${B}/vmm-disk-basic.img ::images/${XVISOR_PLAT}
  mcopy -i ${B}/vmm-disk-basic.img ${B}/build/tests/${XVISOR_PLAT}/${XVISOR_PLAT_BASE}-guest.dtb ::images/${XVISOR_PLAT}-guest.dtb
  mcopy -i ${B}/vmm-disk-basic.img ${B}/build/tests/${XVISOR_PLAT}/basic/firmware.bin ::images/${XVISOR_PLAT}/firmware.bin
  mcopy -i ${B}/vmm-disk-basic.img ${S}/tests/${XVISOR_PLAT}/basic/nor_flash.list ::images/${XVISOR_PLAT}/nor_flash.list

  mcopy -i ${B}/vmm-disk-basic.img ${S}/tests/${XVISOR_PLAT}/xscript/one_guest_${XVISOR_PLAT_BASE}.xscript ::boot.xscript

  ## Create the Linux firmware image ##
  install -d ${B}/build/tests/${XVISOR_PLAT}/linux
  dtc -I dts -O dtb -o ${B}/build/tests/${XVISOR_PLAT}/linux/${XVISOR_PLAT_BASE}.dtb ${S}/tests/${XVISOR_PLAT}/linux/${XVISOR_PLAT_BASE}.dts

  rm -f ${B}/vmm-disk-linux.img
  mkfs.vfat -F 32 -S 512 -n VMM-LINUX-IMAGE -C ${B}/vmm-disk-linux.img 81920

  mmd -i ${B}/vmm-disk-linux.img ::system
  mcopy -i ${B}/vmm-disk-linux.img ${S}/docs/banner/roman.txt ::system/banner.txt
  mcopy -i ${B}/vmm-disk-linux.img ${S}/docs/logo/xvisor_logo_name.ppm ::system/logo.ppm

  mmd -i ${B}/vmm-disk-linux.img ::images
  mmd -i ${B}/vmm-disk-linux.img ::images/riscv
  mmd -i ${B}/vmm-disk-linux.img ::images/${XVISOR_PLAT}
  mcopy -i ${B}/vmm-disk-linux.img ${B}/build/tests/${XVISOR_PLAT}/${XVISOR_PLAT_BASE}-guest.dtb ::images/${XVISOR_PLAT}-guest.dtb
  mcopy -i ${B}/vmm-disk-linux.img ${B}/build/tests/${XVISOR_PLAT}/basic/firmware.bin ::images/${XVISOR_PLAT}/firmware.bin
  mcopy -i ${B}/vmm-disk-linux.img ${S}/tests/${XVISOR_PLAT}/linux/nor_flash.list ::images/${XVISOR_PLAT}/nor_flash.list
  mcopy -i ${B}/vmm-disk-linux.img ${S}/tests/${XVISOR_PLAT}/linux/cmdlist ::images/${XVISOR_PLAT}/cmdlist

  mcopy -i ${B}/vmm-disk-linux.img ${S}/tests/${XVISOR_PLAT}/xscript/two_guest_${XVISOR_PLAT_BASE}.xscript ::boot.xscript

  mcopy -i ${B}/vmm-disk-linux.img ${DEPLOY_DIR_IMAGE}/Image ::images/${XVISOR_PLAT}/Image
  mcopy -i ${B}/vmm-disk-linux.img /scratch/alistair/software/tier4/buildroot-images/riscv64-rootfs.cpio ::images/${XVISOR_PLAT}/rootfs.img
  mcopy -i ${B}/vmm-disk-linux.img ${B}/build/tests/${XVISOR_PLAT}/linux/${XVISOR_PLAT_BASE}.dtb ::images/${XVISOR_PLAT}/${XVISOR_PLAT_BASE}.dtb
}

do_compile:append:riscv32() {
  xvisor_image_compile
}

do_compile:append:riscv64() {
  xvisor_image_compile
}

do_install:append:riscv32() {
  install -d ${D}
  install -m 755 ${B}/vmm-disk-basic.img ${D}/
  install -m 755 ${B}/vmm-disk-linux.img ${D}/
}

do_install:append:riscv64() {
  install -d ${D}
  install -m 755 ${B}/vmm-disk-basic.img ${D}/
  install -m 755 ${B}/vmm-disk-linux.img ${D}/
}

do_deploy:append:riscv32() {
  install -m 755 ${D}/vmm-disk-basic.img ${DEPLOY_DIR_IMAGE}
  install -m 755 ${D}/vmm-disk-linux.img ${DEPLOY_DIR_IMAGE}
}

do_deploy:append:riscv64() {
  install -m 755 ${D}/vmm-disk-basic.img ${DEPLOY_DIR_IMAGE}
  install -m 755 ${D}/vmm-disk-linux.img ${DEPLOY_DIR_IMAGE}
}

FILES:${PN}:riscv32 += "/vmm.*"
FILES:${PN}:riscv32 += "/vmm-disk-basic.img"
FILES:${PN}:riscv32 += "/vmm-disk-linux.img"
FILES:${PN}:riscv64 += "/vmm.*"
FILES:${PN}:riscv64 += "/vmm-disk-basic.img"
FILES:${PN}:riscv64 += "/vmm-disk-linux.img"
