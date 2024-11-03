package com.ww.giflib

import android.support.rastermill.FrameSequenceDrawable
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideType
import com.bumptech.glide.request.RequestOptions

/**
 * @Author weiwei
 * @Date 2024/11/3 23:37
 */
@GlideExtension
class GifExtension private constructor() {  // 将构造函数设为 private


    companion object {
        @JvmStatic
        @GlideType(FrameSequenceDrawable::class)
        fun asGifLib(requestBuilder: RequestBuilder<FrameSequenceDrawable>): RequestBuilder<FrameSequenceDrawable> {
            return requestBuilder.apply(RequestOptions.decodeTypeOf(FrameSequenceDrawable::class.java))
        }
    }
}