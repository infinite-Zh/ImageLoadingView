package com.infinite.imageloadingview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by 19082 on 2016/1/29.
 */
public class ImageLoadingView extends View {
    private Resources mResources;
    private Paint mBitPaint;
    private Bitmap mBitmap;

    private int mTotalWidth, mTotalHeight;
    private int mBitWidth, mBitHeight;
    private Rect mSrcRect, mDestRect;
    private PorterDuffXfermode mXfermode;

    private Rect mDynamicRect;
    private int mCurrentTop;
    private int mStart, mEnd;

    private int mIndeterminateColor;

    private int mProgress;


    public ImageLoadingView(Context context, AttributeSet attrs) {
        super(context,attrs);
        mResources = getResources();
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.ImageLoadingView);

        int res= array.getResourceId(R.styleable.ImageLoadingView_loadingView,0);
        mIndeterminateColor= array.getColor(R.styleable.ImageLoadingView_indeterminateColor,Color.RED);
        initBitmap(res);
        initPaint();
        initXfermode();

    }

    private void initXfermode() {
        // 叠加处绘制源图
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    private void initPaint() {
        // 初始化paint
        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
        mBitPaint.setColor(mIndeterminateColor);

    }

    private void initBitmap(int res) {
        if (res<=0)
            return;
        // 初始化bitmap
        mBitmap = BitmapFactory.decodeResource(getResources(),res);
        mBitWidth = mBitmap.getWidth();
        mBitHeight = mBitmap.getHeight();
    }

    /**
     * @param resId 图片资源id
     */
    public void setLoadingView(int resId){
        initBitmap(resId);
    }

    /**
     * @param color 已完成进度的颜色
     */
    public void setIndeterminateColor(int color){
        mIndeterminateColor=color;
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 存为新图层
        int saveLayerCount = canvas.saveLayer(0, 0, mTotalWidth, mTotalHeight, mBitPaint,
                Canvas.ALL_SAVE_FLAG);
        // 绘制目标图
        canvas.drawBitmap(mBitmap, mSrcRect, mDestRect, mBitPaint);
        // 设置混合模式
        mBitPaint.setXfermode(mXfermode);
        // 绘制源图形
        canvas.drawRect(mDynamicRect, mBitPaint);
        // 清除混合模式
        mBitPaint.setXfermode(null);
        // 恢复保存的图层；
        canvas.restoreToCount(saveLayerCount);

    }

    /**
     *
     * @param progress 最大值为100
     */
    public void setProgress(int progress){
        mProgress=progress;
        float ratio=(float)mProgress/100;
        int height=mBitHeight;
        mCurrentTop= (int) (height*(1-ratio));
        if(mDynamicRect!=null){
            mDynamicRect.top = mCurrentTop;
        }
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);

        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(getSize(widthSize,mBitWidth,widthMode),getSize(heightSize,mBitHeight,heightMode));

    }

    private int getSize(int size,int actualSize,int mode){
        int res = 0;
        if (mode==MeasureSpec.AT_MOST){
            res=actualSize;
        }else if(mode==MeasureSpec.EXACTLY){
            res=size;
        }
        return res;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mSrcRect = new Rect(0, 0, mBitWidth, mBitHeight);
        // 让左边和上边有些距离
        int left = (int) TypedValue.complexToDimension(20, mResources.getDisplayMetrics());
        mDestRect = new Rect(left, left, left + mBitWidth, left + mBitHeight);
        mStart = left + mBitHeight;
        mCurrentTop = mStart;
        mEnd = left;
        mDynamicRect = new Rect(left, mStart, left + mBitWidth, left + mBitHeight);
    }
}
