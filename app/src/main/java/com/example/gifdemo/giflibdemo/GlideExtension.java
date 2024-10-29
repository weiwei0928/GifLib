package com.example.gifdemo.giflibdemo;

import android.support.rastermill.FrameSequenceDrawable;

import androidx.annotation.NonNull;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.request.RequestOptions;

@com.bumptech.glide.annotation.GlideExtension
public class GlideExtension {

    private GlideExtension() {

    }

    final static RequestOptions DECODE_TYPE = RequestOptions
            .decodeTypeOf(FrameSequenceDrawable.class)
            .lock();

//    @NonNull
//    @GlideType(FrameSequenceDrawable.class)
//    public static RequestBuilder<android.support.rastermill.FrameSequenceDrawable> asGif2(@NonNull RequestBuilder<FrameSequenceDrawable> requestBuilder) {
////        RequestBuilder<android.support.rastermill.FrameSequenceDrawable
//        return requestBuilder.apply(DECODE_TYPE);
//
//    }

}
