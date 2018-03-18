FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append = " file://0001-rtld-Add-missing-DT_NEEDED-DSOs-to-needed_list.patch"
SRC_URI_append = " file://0001-prelink-Add-RISC-V-support.patch"

