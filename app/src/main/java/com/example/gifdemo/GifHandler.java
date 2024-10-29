package com.example.gifdemo;

import android.graphics.Bitmap;

public class GifHandler {
    static {
        System.loadLibrary("gifdemo");
    }

    private long gifFileInfo;

    private GifHandler(long gifFileInfo) {
        this.gifFileInfo = gifFileInfo;
    }

    // 生成GifHandler
    public static GifHandler load(String path) {
        long gifFileInfo = openGifFile(path);
        return new GifHandler(gifFileInfo);
    }

    // 获取宽
    public int getWidth() {
        return getWidth(gifFileInfo);
    }

    // 获取高
    public int getHeight() {
        return getHeight(gifFileInfo);
    }

    // 将gif当前帧绘制到bitmap上
    public int updateFrame(Bitmap bitmap) {
        return updateFrame(gifFileInfo, bitmap);
    }

    /**
     * 打开gif文件
     *
     * @param path gif路径
     * @return native层GifFileType的指针
     */
    public static native long openGifFile(String path);

    /**
     * @param gifFileInfo native层GifFileType的指针
     * @return gif文件的高
     */
    public static native int getHeight(long gifFileInfo);

    /**
     * @param gifFileInfo native层GifFileType的指针
     * @return gif文件的宽
     */
    public static native int getWidth(long gifFileInfo);

    /**
     * @param gifFileInfo native层GifFileType的指针
     * @param bitmap      用于显示gif的bitmap
     * @return gif当前帧的展示时长
     */
    public static native int updateFrame(long gifFileInfo, Bitmap bitmap);
}
