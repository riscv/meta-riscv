write_bootinfo() {
    echo "${IMAGE_FSTYPES}" | grep -q "wic" || return 0

    for img in ${IMGDEPLOYDIR}/${IMAGE_NAME}*.wic.gz; do
        [ -e "${img}" ] || return 0
        gunzip -f ${img}
        wic_file="${img%.gz}"

        # Patch bootinfo at offset 0
        dd if=${DEPLOY_DIR_IMAGE}/bootinfo_sd.bin of=${wic_file} bs=1 seek=0 conv=notrunc

        # Regenerate bmap from patched uncompressed image
        bmap_file="${wic_file}.bmap"
        if [ -e "${bmap_file}" ]; then
            bmaptool create ${wic_file} -o ${bmap_file}
        fi

        # Recompress
        gzip -f ${wic_file}
    done
}

IMAGE_POSTPROCESS_COMMAND:append = " write_bootinfo"
