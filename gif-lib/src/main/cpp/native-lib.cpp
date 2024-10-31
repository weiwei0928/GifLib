#include <jni.h>
#include <string>
#include <malloc.h>
#include <android/bitmap.h>
#include <string.h>

// 引入gif库
extern "C" {
#include "giflib/gif_lib.h"
}

// 存储gif的播放进度
struct GifPlayInfo {
    int current_frame;
    int total_frame;
};

extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_gifdemo_GifHandler_openGifFile(JNIEnv *env, jclass clazz, jstring jpath) {
    const char *path = env->GetStringUTFChars(jpath, 0); // 将java字符串转换成c字符串
    int error;// 打开是成功还是失败；0成功，1失败
    GifFileType *gifFileType = DGifOpenFileName(path, &error);
    DGifSlurp(gifFileType);// 将gif读入内核

    // 创建GifPlayInfo对象
    GifPlayInfo *gifPlayInfo = static_cast<GifPlayInfo *>(malloc(sizeof(GifPlayInfo)));
    memset(gifPlayInfo, 0, sizeof(GifPlayInfo)); // 清空
    // 设置总帧数和当前播放帧
    gifPlayInfo->total_frame = gifFileType->ImageCount;
    gifPlayInfo->current_frame = 0;
    gifFileType->UserData = gifPlayInfo; // 类似于view的setTag

    // 释放字符串
    env->ReleaseStringUTFChars(jpath, path);
    return reinterpret_cast<jlong>(gifFileType); // 将指针返回
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_gifdemo_GifHandler_getHeight(JNIEnv *env, jclass clazz, jlong gif_file_info) {
    GifFileType *gifFileType = reinterpret_cast<GifFileType *>(gif_file_info);
    return gifFileType->SHeight;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_gifdemo_GifHandler_getWidth(JNIEnv *env, jclass clazz, jlong gif_file_info) {
    GifFileType *gifFileType = reinterpret_cast<GifFileType *>(gif_file_info);
    return gifFileType->SWidth;
}

// 将argb转换成abgr
int32_t argb2Abgr(int alpha, GifByteType red, GifByteType green, GifByteType blue) {
    return ((alpha & 0xff) << 24) | ((blue & 0xff) << 16) | ((green) << 8) | (red);
}

// 计算展示时长
int delay(ExtensionBlock *ext) {
    return (10 * ((ext)->Bytes[2] << 8 | (ext)->Bytes[1]));
}

// 绘制当前帧
int myDrawFrame(GifFileType *gif, AndroidBitmapInfo *info, int *pixels, int frame_no) {
    SavedImage *savedImage = &(gif->SavedImages[frame_no]); // 获取当前帧
    GifImageDesc *frameInfo = &(savedImage->ImageDesc); // 当前帧的描述文件
    ColorMapObject *colorMap; // 当前帧的颜色列表
    if (frameInfo->ColorMap) {
        colorMap = frameInfo->ColorMap;
    } else {
        //没有的话就获取全局的颜色列表
        colorMap = gif->SColorMap;
    }
    ExtensionBlock *ext;
    //遍历当前帧的扩展块，找到具有GRAPHICS_EXT_FUNC_CODE标志位的，这个扩展快存放着对该帧图片的处置方法，是不处理还是其他
    for (int i = 0; i < savedImage->ExtensionBlockCount; ++i) {
        if (savedImage->ExtensionBlocks[i].Function == GRAPHICS_EXT_FUNC_CODE) {
            ext = &(savedImage->ExtensionBlocks[i]);
            break;
        }
    }

    int loc; // 当前位置
    GifColorType *color; // 当前像素的颜色
    int *px = pixels; // 二维指针
    int *line; // 行
    px = (int *) ((char *) px + info->stride * frameInfo->Top); // 计算像素，对每一个像素进行颜色填充

    for (int y = frameInfo->Top; y < frameInfo->Top + frameInfo->Height; ++y) {
        line = px; // 更新行数
        for (int x = frameInfo->Left; x < frameInfo->Left + frameInfo->Width; ++x) {
            loc = (y - frameInfo->Top) * frameInfo->Width + (x - frameInfo->Left);
            //判断处置方法，拿到当前帧loc位置的字节，看是否等于扩展块中索引为3的字节，并且数值为1
            if (savedImage->RasterBits[loc] == ext->Bytes[3] && ext->Bytes[0]) {
                continue;
            }
            color = &colorMap->Colors[savedImage->RasterBits[loc]];
            line[x] = argb2Abgr(255, color->Red, color->Green, color->Blue);
        }
        px = (int *) ((char *) px + info->stride);
    }
    return delay(ext);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_gifdemo_GifHandler_updateFrame(JNIEnv *env, jclass clazz, jlong gif_file_info,
                                                jobject bitmap) {
    // 获取到gif数据
    GifFileType *gifFileType = reinterpret_cast<GifFileType *>(gif_file_info);
    GifPlayInfo *gifPlayInfo = static_cast<GifPlayInfo *>(gifFileType->UserData);
    AndroidBitmapInfo info;// 获取bitmap信息
    AndroidBitmap_getInfo(env, bitmap, &info);
    void *srcBuf;// 解析bitmap，并将像素信息保存到srcBuf中
    AndroidBitmap_lockPixels(env, bitmap, &srcBuf);
    int delay = myDrawFrame(gifFileType, &info, (int *) srcBuf,
                            gifPlayInfo->current_frame); // 绘制gif
    AndroidBitmap_unlockPixels(env, bitmap); // 释放锁定
    gifPlayInfo->current_frame++;// 更新播放进度
    if (gifPlayInfo->current_frame >= gifPlayInfo->total_frame - 1) {
        gifPlayInfo->current_frame = 0;
    }
    return delay;
}