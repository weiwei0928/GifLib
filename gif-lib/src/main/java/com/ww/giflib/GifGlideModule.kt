package com.ww.giflib

import android.content.Context
import android.support.rastermill.FrameSequenceDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

@GlideModule
class GifGlideModule : AppGlideModule() {
    //public class GifGlideModule extends LibraryGlideModule {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
    }

    override fun registerComponents(
        context: Context,
        glide: Glide, registry: Registry
    ) {
        super.registerComponents(context, glide, registry)
        registry.append(
            Registry.BUCKET_GIF,
            InputStream::class.java,
            FrameSequenceDrawable::class.java, GifDecoder(glide.bitmapPool)
        )
        //        registry.append(Registry.BUCKET_GIF, InputStream.class,
//                FrameSequenceDrawable.class, new GifDecoder(glide.getBitmapPool()));
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}