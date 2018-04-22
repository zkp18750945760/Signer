package com.zhoukp.signer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.zhoukp.signer.utils.DensityUtil;

import java.util.ArrayList;

/**
 * 作者：zhoukp
 * 时间：2017/12/27 14:08
 * 邮箱：zhoukaiping@szy.cn
 * 作用：
 */

public class HoneycombView extends View {

    private OnClickListener onClickListener;
    //当前view的宽度
    private int width;
    //当前view的高度
    private int height;
    //六边形边长
    private float side;
    //画笔
    private Paint paint;
    //文字画笔
    private TextPaint textPaint;
    //文字长度
    private float textLength;
    //文字高度
    private float textHeight;

    private Path path;
    //正在绘制的六边形的中心点
    private Point point;
    //每个六边形的颜色
    private int[] colorS = {
            Color.parseColor("#0960BD"),
            Color.parseColor("#005792"),
            Color.parseColor("#0960BD"),
            Color.parseColor("#005792"),
            Color.parseColor("#0960BD"),
            Color.parseColor("#005792"),
            Color.parseColor("#00204A")
    };

    private String[] tittles = {
            "成绩上报",
            "成绩查询",
            "互评小组",
            "开始互评",
            "互评记录",
            "成绩修改",
            "综测"};

    private int curClick = -1;
    //各个六边形的中心点
    private ArrayList<Point> centerPoints;

    public HoneycombView(Context context) {
        this(context, null);
    }


    public HoneycombView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HoneycombView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        //设置样式
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        //设置线条宽度
        paint.setStrokeWidth(2);
        //设置消除抗锯齿
        paint.setAntiAlias(true);

        textPaint = new TextPaint();
        //设置字体大小
        textPaint.setTextSize(44);
        //设置颜色
        textPaint.setColor(Color.WHITE);
        //设置消除抗锯齿
        textPaint.setAntiAlias(true);

        path = new Path();
        centerPoints = new ArrayList<>();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float touchedX = event.getX();
                float touchedY = event.getY();
                curClick = onTouchquareS(touchedX, touchedY);
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (onClickListener != null) {
                    onClickListener.onClickListener(curClick);
                }
                curClick = -1;
                postInvalidate();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < tittles.length; i++) {
            textLength = textPaint.measureText(tittles[i]);
            textHeight = textPaint.descent() - textPaint.ascent();
            point = new Point();
            switch (i) {
                case 0:
                    point.x = width / 2;
                    point.y = (float) (height / 2 - Math.sqrt(3) * side);
                    paint.setColor(colorS[0]);
                    break;
                case 1:
                    point.x = width / 2 + 3.0f / 2 * side;
                    point.y = (float) (height / 2 - Math.sqrt(3) / 2 * side);
                    paint.setColor(colorS[1]);
                    break;
                case 2:
                    point.x = width / 2 + 3.0f / 2 * side;
                    point.y = (float) (height / 2 + Math.sqrt(3) / 2 * side);
                    paint.setColor(colorS[2]);
                    break;
                case 3:
                    point.x = width / 2;
                    point.y = (float) (height / 2 + Math.sqrt(3) * side);
                    paint.setColor(colorS[3]);
                    break;
                case 4:
                    point.x = width / 2 - 3.0f / 2 * side;
                    point.y = (float) (height / 2 + Math.sqrt(3) / 2 * side);
                    paint.setColor(colorS[4]);
                    break;
                case 5:
                    point.x = width / 2 - 3.0f / 2 * side;
                    point.y = (float) (height / 2 - Math.sqrt(3) / 2 * side);
                    paint.setColor(colorS[5]);
                    break;
                case 6:
                    point.x = width / 2;
                    point.y = height / 2;
                    paint.setColor(colorS[6]);
                    break;
                default:
                    break;
            }
            drawHoney(point, canvas, textLength, textHeight, i);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量

        //可能会有padding值，也计算在内
        width = measureSize(widthMeasureSpec, DensityUtil.dip2px(getContext(), 180) + getPaddingLeft() + getPaddingRight());
        height = measureSize(heightMeasureSpec, DensityUtil.dip2px(getContext(), 180) + getPaddingTop() + getPaddingBottom());

        setMeasuredDimension(width, height);

        side = (float) (width * 1.0 / 6);

        Log.e("zkp", "side==" + side + ", width==" + width);
    }

    //测量尺寸
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

    private void drawHoney(Point point, Canvas canvas, float textLength, float textHeight, int index) {
        path.reset();
        for (int i = 0; i < 6; i++) {
            switch (i) {
                case 0:
                    path.moveTo(point.x - side, point.y);
                    break;
                case 1:
                    path.lineTo((point.x - side / 2), (float) (point.y - Math.sqrt(3) / 2 * side));
                    break;
                case 2:
                    path.lineTo((point.x + side / 2), (float) (point.y - Math.sqrt(3) / 2 * side));
                    break;
                case 3:
                    path.lineTo(point.x + side, point.y);
                    break;
                case 4:
                    path.lineTo((point.x + side / 2), (float) (point.y + Math.sqrt(3) / 2 * side));
                    break;
                case 5:
                    path.lineTo((point.x - side / 2), (float) (point.y + Math.sqrt(3) / 2 * side));
                    break;
                default:

                    break;
            }
        }
        Point currentPoint = point;
        centerPoints.add(currentPoint);
        path.close();
        canvas.drawPath(path, paint);
        canvas.drawText(tittles[index], point.x - textLength / 2, point.y + textHeight / 4, textPaint);
    }

    private int onTouchquareS(float x, float y) {
        int curAction = -1;
        for (int index = 0; index < centerPoints.size(); index++) {
            float curX = centerPoints.get(index).x;
            float curY = centerPoints.get(index).y;
            if (side - Math.abs(y - curY) > Math.abs(x - curX) / Math.sqrt(3)) {
                curAction = index;
                break;
            }
        }
        return curAction;
    }

    //开放点击监听
    public interface OnClickListener {
        void onClickListener(int actionIndex);
    }

    class Point {
        float x;
        float y;
    }
}
