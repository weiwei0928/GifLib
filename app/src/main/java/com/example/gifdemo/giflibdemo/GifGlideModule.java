package com.example.gifdemo.giflibdemo;

import android.content.Context;
import android.support.rastermill.FrameSequenceDrawable;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.module.LibraryGlideModule;

import java.io.InputStream;

@GlideModule
public class GifGlideModule extends AppGlideModule {
//public class GifGlideModule extends LibraryGlideModule {


    @Override
    public void applyOptions(@NonNull  Context context, @NonNull  GlideBuilder builder) {
        super.applyOptions(context, builder);
    }

    @Override
    public void registerComponents(@NonNull Context context,
                                   @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.append(Registry.BUCKET_GIF, InputStream.class,
                FrameSequenceDrawable.class, new GifDecoder(glide.getBitmapPool()));
//        registry.append(Registry.BUCKET_GIF, InputStream.class,
//                FrameSequenceDrawable.class, new GifDecoder(glide.getBitmapPool()));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
