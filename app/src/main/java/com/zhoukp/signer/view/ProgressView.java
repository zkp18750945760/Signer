package com.zhoukp.signer.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * 作者：zhoukp
 * 时间：2017/12/25 16:32
 * 邮箱：zhoukaiping@szy.cn
 * 作用：自定义loadingview
 */

public class ProgressView extends View {

    /**
     * 100表示loading完成
     */
    private long max_value = 100;
    /**
     * 当前进度
     */
    private long currentProgress;
    /**
     * 进度条画笔
     */
    private Paint linePaint;
    /**
     * 文字画笔
     */
    private Paint textPaint;
    /**
     * 手机屏幕宽度
     */
    private int screenWidth;
    /**
     * 手机屏幕高度
     */
    private int screenHeight;

    private boolean isFinished;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //初始化画笔
        initPaint();

        //初始化数据
        initData(context);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        linePaint = new Paint();
        //设置画笔颜色
        linePaint.setColor(Color.parseColor("#FF0000"));
        //设置线条宽度
        linePaint.setStrokeWidth(2);
        //设置样式为空心
        linePaint.setStyle(Paint.Style.FILL);
        //设置消除抗锯齿
        linePaint.setAntiAlias(true);

        textPaint = new TextPaint();
        //设置画笔颜色
        textPaint.setColor(Color.parseColor("#008B00"));
        //设置线条宽度
        textPaint.setStrokeWidth(4);
        //设置重力方向
        textPaint.setTextAlign(Paint.Align.CENTER);
        //设置字体大小
        textPaint.setTextSize(55);
        //设置消除抗锯齿
        textPaint.setAntiAlias(true);
    }

    /**
     * 初始化数据
     *
     * @param context
     */
    private void initData(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        currentProgress = 0;
        isFinished = true;//默认是完成状态
    }

    public void setMax_value(long max_value) {
        this.max_value = max_value;
    }

    public void setCurrentProgress(long currentProgress) {
        this.currentProgress = currentProgress;
        if (currentProgress >= max_value) {
            isFinished = true;
        } else {
            isFinished = false;
        }
        invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isFinished) {
            linePaint.setColor(Color.parseColor("#FF0000"));
            RectF rectF = new RectF(100, screenHeight / 2 - 20, screenWidth - 100, screenHeight / 2 + 20);
            canvas.drawRoundRect(rectF, 30, 30, linePaint);
            linePaint.setColor(Color.parseColor("#008B00"));
            rectF = new RectF(100, screenHeight / 2 - 20, 100 + (float) (currentProgress * 1.0 / 100 * (screenWidth - 200)), screenHeight / 2 + 20);
            canvas.drawRoundRect(rectF, 30, 30, linePaint);

            canvas.drawText("下载中" + (currentProgress / max_value) * 100 + "%", screenWidth / 2 - 30, screenHeight / 2 + 100, textPaint);
        } else {
            linePaint.setColor(Color.parseColor("#008B00"));
            RectF rectF = new RectF(100, screenHeight / 2 - 20, screenWidth - 100, screenHeight / 2 + 20);
            canvas.drawRoundRect(rectF, 30, 30, linePaint);

            canvas.drawText("下载完成", screenWidth / 2 - 30, screenHeight / 2 + 100, textPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //设置尺寸
        setMeasuredDimension(screenWidth, screenHeight);
    }
}
