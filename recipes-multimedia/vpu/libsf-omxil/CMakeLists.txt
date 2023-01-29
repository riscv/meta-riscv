cmake_minimum_required(VERSION 3.16)
project(omx-il)

find_path(WAVE4_INCLUDE_PATH wave420l/config.h)
message("Found WAVE420l headers at: ${WAVE4_INCLUDE_PATH}/wave420l/")

find_path(WAVE5_INCLUDE_PATH wave511/config.h)
message("Found WAVE511 headers at: ${WAVE5_INCLUDE_PATH}/wave511/")

find_path(JPU_INCLUDE_PATH codaj12/config.h)
message("Found CODAJ12 headers at: ${JPU_INCLUDE_PATH}/codaj12/")

add_definitions(
    -DUSE_FEEDING_METHOD_BUFFER
)

include_directories(
    ${CMAKE_SOURCE_DIR}/core/
    ${CMAKE_SOURCE_DIR}/component/video/wave5/common/
    ${CMAKE_SOURCE_DIR}/component/video/wave5/dec/
    ${CMAKE_SOURCE_DIR}/component/video/wave4
    ${CMAKE_SOURCE_DIR}/component/helper/
    ${CMAKE_SOURCE_DIR}/component/image/common/
    ${CMAKE_SOURCE_DIR}/component/image/dec/
    ${CMAKE_SOURCE_DIR}/include/khronos/

    ${WAVE4_INCLUDE_PATH}/wave420l/
    ${WAVE4_INCLUDE_PATH}/wave420l/vpuapi/
    ${WAVE4_INCLUDE_PATH}/wave420l/sample/helper/

    ${WAVE5_INCLUDE_PATH}/wave511/sample_v2/component/
    ${WAVE5_INCLUDE_PATH}/wave511/sample_v2/helper/

    ${JPU_INCLUDE_PATH}/codaj12/jpuapi/
    ${JPU_INCLUDE_PATH}/codaj12/sample/helper/
)

set(library_SRCS
    core/SF_OMX_Core.c
    component/video/wave5/common/SF_OMX_video_common.c
    component/video/wave5/dec/SF_OMX_Vdec_decoder.c
    component/video/wave4/SF_OMX_Wave420L_encoder.c
    component/helper/sf_queue.c
    component/helper/sf_thread.c
    component/helper/sf_semaphore.c

    #mjpeg
    component/image/common/SF_OMX_mjpeg_common.c
    component/image/dec/SF_OMX_Mjpeg_decoder.c
)

add_library(sf-omx-il SHARED ${library_SRCS})
install(TARGETS sf-omx-il
    LIBRARY DESTINATION lib
)
install(FILES
    include/khronos/OMX_Audio.h
    include/khronos/OMX_Component.h
    include/khronos/OMX_CoreExt.h
    include/khronos/OMX_ImageExt.h
    include/khronos/OMX_IndexExt.h
    include/khronos/OMX_IVCommon.h
    include/khronos/OMX_Types.h
    include/khronos/OMX_Video.h
    include/khronos/OMX_ComponentExt.h
    include/khronos/OMX_ContentPipe.h
    include/khronos/OMX_Core.h
    include/khronos/OMX_Image.h
    include/khronos/OMX_Index.h
    include/khronos/OMX_Other.h
    include/khronos/OMX_VideoExt.h
    DESTINATION ${CMAKE_INSTALL_INCLUDEDIR}/khronos
)
