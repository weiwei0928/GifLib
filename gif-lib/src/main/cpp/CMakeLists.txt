cmake_minimum_required(VERSION 3.18.1)

file(GLOB_RECURSE GIF_LIB ${CMAKE_SOURCE_DIR}/giflib/*.*)
file(GLOB_RECURSE FRAME_SEQUENCE ${CMAKE_SOURCE_DIR}/*.cpp*)

add_library(gif-lib
        SHARED
        ${FRAME_SEQUENCE}
        ${GIF_LIB})

list(APPEND LIBS
        jnigraphics
        android
        GLESv2
        log
        )

set(LIBS)
list(APPEND LIBS
        jnigraphics
        android
        GLESv2
        log
        )

target_link_libraries(gif-lib ${LIBS})
