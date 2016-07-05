# ImageLoadingView
以图片作为进度条展示


在xml中引用：
```
<com.infinite.imageloadingview.ImageLoadingView
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:id="@+id/loadingView2"
       app:indeterminateColor="#00bb9c"
       android:layout_below="@+id/loadingView"
       app:loadingView="@mipmap/ic_github"/>
       ```

属性说明：
```
<resources>
    <declare-styleable name="ImageLoadingView">
        <attr name="loadingView" format="reference"/>
        <attr name="indeterminateColor" format="color"/>
    </declare-styleable>
</resources>
```

|indeterminateColor|loadingView|
|:-:|:-:|
|已完成进度的颜色|图片资源|


在代码中设置方式：

```
loadingView.setLoadingView(R.mipmap.infinite);
loadingView.setIndeterminateColor(Color.GRAY);
```

最后，设置进度：

```
loadingView.setProgress(msg.arg1);
```
