package com.example.gifdemo.giflibdemo;

import android.os.Bundle;
import android.support.rastermill.FrameSequenceDrawable;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.gifdemo.R;


public class TestActivity extends AppCompatActivity {

    public static final String TAG = "mygif";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

//        Glide.get(this);
        imageView = findViewById(R.id.iv);
        String path = getExternalCacheDir().getPath() + "/test.gif";
        int gif = R.mipmap.loading_gif;
//        String gif = "http://2zhoumu-comic-public-test.oss-cn-hangzhou.aliyuncs.com/cover/comic/gc1100004.gif";
//        Glide.with(this).as(FrameSequenceDrawable.class).load(gif).into(imageView);
        GlideApp.with(this).as(FrameSequenceDrawable.class).load(path).into(imageView);
//        Glide.with(this).asGif().load(gif).into(imageView);

    }
}
