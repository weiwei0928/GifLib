package com.ww.giflib

import android.graphics.Bitmap
import android.support.rastermill.FrameSequence
import android.support.rastermill.FrameSequenceDrawable
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import java.io.IOException
import java.io.InputStream

class GifDecoder(private val bitmapPool: BitmapPool) :
    ResourceDecoder<InputStream, FrameSequenceDrawable> {
    @Throws(IOException::class)
    override fun handles(source: InputStream, options: Options): Boolean {
        return true
    }

    @Throws(IOException::class)
    override fun decode(
        source: InputStream,
        width: Int,
        height: Int,
        options: Options
    ): Resource<FrameSequenceDrawable> {
        val frameSequence = FrameSequence.decodeStream(source)
        val frameSequenceDrawable =
            FrameSequenceDrawable(frameSequence, object : FrameSequenceDrawable.BitmapProvider {
                override fun acquireBitmap(minWidth: Int, minHeight: Int): Bitmap {
                    return bitmapPool[minWidth, minHeight, Bitmap.Config.ARGB_8888]
                }

                override fun releaseBitmap(bitmap: Bitmap) {
                    bitmapPool.put(bitmap)
                }
            })
        return GifResource(frameSequenceDrawable)
    }
}