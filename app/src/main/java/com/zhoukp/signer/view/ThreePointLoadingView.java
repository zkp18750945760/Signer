package com.zhoukp.signer.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.zhoukp.signer.R;
import com.zhoukp.signer.utils.DensityUtil;

/**
 * 作者：zhoukp
 * 时间：2017/12/27 8:38
 * 邮箱：zhoukaiping@szy.cn
 * 作用：贝塞尔曲线loading view
 */

public class ThreePointLoadingView extends View {

    class Point {
        float x;
        float y;
    }

    //画笔
    private Paint paint;
    //view的宽度
    private int width;
    //view的高度
    private int height;
    //圆的半径
    private float radius;
    //圆之间的距离
    private float margin;
    //A圆圆心坐标
    private Point pointA;
    //B圆圆心坐标
    private Point pointB;
    //C圆圆心坐标
    private Point pointC;
    //涉及到的总长度
    private float totalLength;
    //移动的总长度
    private float moveLength;
    //A的透明度
    private int alphaA = 255;
    //B的透明度
    private int alphaB = (int) (255 * 0.8);
    //C的透明度
    private int alphaC = (int) (255 * 0.6);

    //A圆心做二阶贝塞尔曲线的起点、控制点、终点
    private PointF pointP0;
    private PointF pointP1;
    private PointF pointP2;

    //A圆心贝塞尔曲线运动时的坐标
    private float AX;
    private float AY;

    //值动画
    private ValueAnimator animator;

    //值动画产生的x方向的偏移量
    private float offsetX = 0;
    //根据mOffsetX算得的y方向的偏移量
    private float offsetY;

    public ThreePointLoadingView(Context context) {
        this(context, null);
    }

    public ThreePointLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThreePointLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initData();
    }

    private void initData() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        //设置颜色
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorOrange));
        //设置样式为填充
        paint.setStyle(Paint.Style.FILL);

        pointA = new Point();
        pointB = new Point();
        pointC = new Point();

        pointP0 = new PointF();
        pointP1 = new PointF();
        pointP2 = new PointF();
    }

    // 测量尺寸
    private int measureSize(int measureSpec, int defaultSize) {
        final int mode = MeasureSpec.getMode(measureSpec);
        final int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            return size;
        } else if (mode == MeasureSpec.AT_MOST) {
            return Math.min(size, defaultSize);
        }
        return size;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量

        //可能会有padding值，也计算在内
        width = measureSize(widthMeasureSpec, DensityUtil.dip2px(getContext(), 180) + getPaddingLeft() + getPaddingRight());
        height = measureSize(heightMeasureSpec, DensityUtil.dip2px(getContext(), 180) + getPaddingTop() + getPaddingBottom());

        setMeasuredDimension(width, height);

        //间距为宽度的1/20
        margin = width * 1.0f / 20;

        //半径为宽度的1/40
        radius = width * 1.0f / 40;

        //x坐标变化范围为3个圆的直径加上2个间距
        totalLength = radius * 6 + margin * 2;

        //两个圆圆心的距离
        moveLength = radius * 2 + margin;

        //A圆圆心的起始坐标
        AX = pointA.x = (width - totalLength) / 2 + radius;
        AY = pointA.y = height / 2;

        //B圆圆心的起始坐标
        pointB.x = pointA.x + margin + radius * 2;
        pointB.y = height / 2;

        //C圆圆心的起始坐标
        pointC.x = pointB.x + margin + radius * 2;
        pointC.y = height / 2;

        //A圆心起始点、控制点、终点
        pointP0.set(pointA.x, pointA.y);
        pointP1.set(pointA.x + moveLength / 2, moveLength / 2);
        pointP2.set(pointB.x, pointB.y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制图形
        offsetY = (float) Math.sqrt(moveLength / 2 * moveLength / 2 - (moveLength / 2 - offsetX) * (moveLength / 2 - offsetX));

        //绘制A圆
        paint.setAlpha(alphaA);
        canvas.drawCircle(AX, AY, radius, paint);

        //绘制B圆
        paint.setAlpha(alphaB);
        canvas.drawCircle(pointB.x - offsetX, pointB.y + offsetY, radius, paint);

        //绘制C圆
        paint.setAlpha(alphaC);
        canvas.drawCircle(pointC.x - offsetX, pointC.y - offsetY, radius, paint);

        if (animator == null) {
            startAnimator();
        }
    }

    /**
     * 开始动画
     */
    private void startAnimator() {
        //范围在0到圆心移动的距离，这个是以B圆到A圆位置为基准的
        animator = ValueAnimator.ofFloat(0, moveLength);

        //添加对动画的监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //B圆和C圆对应的X的偏移量
                offsetX = (float) animation.getAnimatedValue();
                //动画播放进度
                float fraction = animation.getAnimatedFraction();

                //B移动到A，透明度变化255*0.8->255
                alphaB = (int) (255 * 0.8 + 255 * fraction * 0.2);
                // C移动到B，透明度变化255*0.6->255*0.8
                alphaC = (int) (255 * 0.6 + 255 * fraction * 0.2);
                // A移动到C，透明度变化255->255*0.6
                alphaA = (int) (255 - 255 * fraction * 0.4);

                //A圆的分段二阶贝塞尔曲线的处理
                if (fraction < 0.5) {
                    //前半段逻辑处理
                    fraction *= 2;
                    //A圆心起始点、控制点、终点
                    pointP0.set(pointA.x, pointA.y);
                    pointP1.set(pointA.x + moveLength / 2, pointB.y - moveLength / 2);
                    pointP2.set(pointB.x, pointB.y);


                    AX = getBazierValue(fraction, pointP0.x, pointP1.x, pointP2.x);
                    AY = getBazierValue(fraction, pointP0.y, pointP1.y, pointP2.y);
                } else {
                    //后半段逻辑处理
                    fraction -= 0.5;
                    fraction *= 2;

                    //A圆心起始点、控制点、终点
                    pointP0.set(pointB.x, pointB.y);
                    pointP1.set(pointB.x + moveLength / 2, pointB.y + (moveLength / 2));
                    pointP2.set(pointC.x, pointC.y);

                    AX = getBazierValue(fraction, pointP0.x, pointP1.x, pointP2.x);
                    AY = getBazierValue(fraction, pointP0.y, pointP1.y, pointP2.y);
                }
                //强制刷新
                postInvalidate();
            }
        });

        //动画无限模式
        animator.setRepeatCount(ValueAnimator.INFINITE);
        //时长1秒
        animator.setDuration(1000);
        //延迟0.5秒执行
        animator.setStartDelay(500);
        //开启动画
        animator.start();
    }

    /**
     * 二阶贝塞尔公式：B(t)=(1-t)^2*P0+2*t*(1-t)*P1+t^2*P2,(t∈[0,1])
     */
    private float getBazierValue(float fraction, float p0, float p1, float p2) {
        return (1 - fraction) * (1 - fraction) * p0 + 2 * fraction * (1 - fraction) * p1 + fraction * fraction * p2;
    }
}
