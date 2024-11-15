#### 基于 giflib+ framesequence 加载gif,解决glide 加载gif 卡顿内存高问题

添加仓库和依赖:

```kotlin
maven {
  url 'https://jitpack.io'
}

implementation 'com.github.weiwei0928:giflib:1.0.2'
implementation 'com.github.bumptech.glide:glide:4.16.0'
```



使用:

```kotlin
val path = "${Environment.getExternalStorageDirectory().path}/test.gif"
//GlideApp.with(this).`as`(FrameSequenceDrawable::class.java).load(path).into(imageView)
//GlideApp.with(this).`as`(FrameSequenceDrawable::class.java).load(R.drawable.test).into(imageView) //或：
GlideApp.with(this).asGifLib().load(path).into(imageView)
```

