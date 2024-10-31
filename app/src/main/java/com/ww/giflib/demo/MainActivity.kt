package com.ww.giflib.demo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.rastermill.FrameSequenceDrawable
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ww.giflib.GlideApp


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 申请权限
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                1
            )
        }

        val imageView = findViewById<ImageView>(R.id.iv)
        val button = findViewById<Button>(R.id.btn)
        button.setOnClickListener {
            val path = "${Environment.getExternalStorageDirectory().path}/test.gif"
//        GlideApp.with(this).`as`(FrameSequenceDrawable::class.java).load(path).into(imageView)
            GlideApp.with(this).`as`(FrameSequenceDrawable::class.java).load(R.drawable.test).into(imageView)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        } else {
            Toast.makeText(this, "请授予读写权限", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val TAG: String = "gif-lib"
    }
}