package com.ww.giflib

import android.support.rastermill.FrameSequenceDrawable
import com.bumptech.glide.load.resource.drawable.DrawableResource

class GifResource(drawable: FrameSequenceDrawable) :
    DrawableResource<FrameSequenceDrawable>(drawable) {
    override fun getResourceClass(): Class<FrameSequenceDrawable> {
        return FrameSequenceDrawable::class.java
    }

    override fun getSize(): Int {
        return 0
    }

    override fun recycle() {
        drawable!!.stop()
        drawable!!.destroy()
    }
}