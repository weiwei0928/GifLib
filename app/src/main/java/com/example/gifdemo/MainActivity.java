package com.example.gifdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gifdemo.databinding.ActivityMainBinding;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private GifHandler mGifHandler;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 申请权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            initData();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
    }

    private void initData() {
        // /storage/emulated/0/Android/data/com.example.gifdemo/cache/test.gif
        String path = getExternalCacheDir().getPath() + "/test.gif";
        Log.v("AAAAA", path);
        if (!new File(path).exists()) {
            Toast.makeText(this, "gif路径无效，请重试", Toast.LENGTH_SHORT).show();
            return;
        }
        mGifHandler = GifHandler.load(path);
        mBitmap = Bitmap.createBitmap(mGifHandler.getWidth(), mGifHandler.getHeight(), Bitmap.Config.ARGB_8888);
        binding.sampleText.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initData();
        } else {
            Toast.makeText(this, "请授予读写权限", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sample_text) {
//            int delay = mGifHandler.updateFrame(mBitmap);
//            binding.iv.setImageBitmap(mBitmap);
//            mHandler.removeMessages(1);
//            mHandler.sendEmptyMessageDelayed(1, delay);
            String path = getExternalCacheDir().getPath() + "/test.gif";
            Glide.with(this).load(path).into(binding.iv);
        }
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                int delay = mGifHandler.updateFrame(mBitmap);
                binding.iv.setImageBitmap(mBitmap);
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1, delay);
            }
        }
    };
}