package cdy.com.couponview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by cdy on 2017/8/8.
 */

public class CouponView extends LinearLayout {
    private Paint mPaint;
    private int mWidth;
    private int mHeight;


    /**
     * 间隔
     */
    private float mInterval = 8;
    /**
     * 圆或者三角形半径
     */
    private float mRadius = 10;
    /**
     * 垂直方向圆或三角形的数量
     */
    private int mVerticalCount;

    /**
     * 垂直方向初始偏移距离
     */
    private int mVerticalInitSize;
    /**
     * 水平方向圆或三角形的数量
     */
    private int mHorizontalCount;
    /**
     * 水平方向初始偏移量
     */
    private int mHorizontalInitSize;
    /**
     *  默认白色
     */
    private static final int DEFAULT_COLOR= Color.WHITE;


    /**
     * 默认的垂直方向的样式为none，即不进行绘制
     */
    private static final int DEFAULT_VERTICAL_STYLE_NONE = 0;
    /**
     * 默认的水平向的样式为none，即不进行绘制
     */
    private static final int DEFAULT_HORIZONTAL_STYLE_NONE = 0;

    //水平或垂直方向的边缘类型
    private int vertical_style_left = DEFAULT_VERTICAL_STYLE_NONE;
    private int vertical_style_right = DEFAULT_VERTICAL_STYLE_NONE;
    private int horizontal_style_top = DEFAULT_HORIZONTAL_STYLE_NONE;
    private int horizontal_style_bottom = DEFAULT_HORIZONTAL_STYLE_NONE;

    /**
     * 圆形或三角形的颜色，对应CouponView父容器背景色
     */
    private int mColor=DEFAULT_COLOR;


    public CouponView(Context context) {
        super(context);
        init(context,null);
    }

    public CouponView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CouponView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        setWillNotDraw(false);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CouponView);
            vertical_style_left = ta.getInt(R.styleable.CouponView_vertical_style_left, DEFAULT_VERTICAL_STYLE_NONE);
            vertical_style_right = ta.getInt(R.styleable.CouponView_vertical_style_right, DEFAULT_VERTICAL_STYLE_NONE);
            horizontal_style_top = ta.getInt(R.styleable.CouponView_horizontal_style_top, DEFAULT_HORIZONTAL_STYLE_NONE);
            horizontal_style_bottom = ta.getInt(R.styleable.CouponView_horizontal_style_bottom, DEFAULT_HORIZONTAL_STYLE_NONE);
            mColor=ta.getColor(R.styleable.CouponView_color_parent_bg,Color.WHITE);
            mInterval=ta.getDimensionPixelSize(R.styleable.CouponView_interval,8);
            mRadius=ta.getDimensionPixelSize(R.styleable.CouponView_radius,10);
            ta.recycle();
        }
        mPaint.setColor(mColor);//设置为白色，需要根据父布局的背景色进行调整，否则绘制的这一块会比较显眼突出
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (vertical_style_left == 1) {
            drawVerticalLiftCircle(canvas);
        } else if (vertical_style_left == 2) {
            drawVerticalLeftTriangle(canvas);
        }
        if (vertical_style_right == 1) {
            drawVerticalRightCircle(canvas);
        } else if (vertical_style_right == 2) {
            drawVerticalRightTriangle(canvas);
        }
        if (horizontal_style_top == 1) {
            drawHorizontalTopCircle(canvas);
        } else if (horizontal_style_top == 2) {
            drawHorizontalTopTriangle(canvas);
        }
        if (horizontal_style_bottom == 1) {
            drawHorizontalBottomCircle(canvas);
        } else if (horizontal_style_bottom == 2) {
            drawHorizontalBottomTriangle(canvas);
        }
    }

    /**
     * 计算垂直方向需要画圆或三角形的数量
     *
     * @param mInterval 每个圆形或三角形之间的间距
     */
    private void calculateVerticalCount(float mInterval) {
        mVerticalCount = (int) ((mHeight - mInterval) / (2 * mRadius + mInterval));
        mVerticalInitSize = (int) ((mHeight - (2 * mRadius * mVerticalCount + (mVerticalCount + 1) * mInterval)) / 2);
    }

    /**
     * 计算水平方向上圆或三角形的数量和初始偏移量
     *
     * @param mInterval 每个圆形或三角形之间的间距
     */
    private void calculateHorizontalCount(float mInterval) {
        mHorizontalCount = (int) ((mWidth - mInterval) / (2 * mRadius + mInterval));
        mHorizontalInitSize = (int) ((mWidth - (2 * mRadius * mHorizontalCount + (mHorizontalCount + 1) * mInterval)) / 2);
    }

    /**
     * 在水平方向上绘制上边三角形
     *
     * @param canvas
     */
    private void drawHorizontalTopTriangle(Canvas canvas) {
        //先计算出水平方向上的数量
        calculateHorizontalCount(0);
        Path path = new Path();
        float x = 0;
        //绘制上面部分
        for (int i = 0; i < mHorizontalCount; i++) {
            path.reset();
            x = mHorizontalInitSize + i * 2 * mRadius;
            path.moveTo(x, 0);
            x += mRadius;
            path.lineTo(x, mRadius);
            x += mRadius;
            path.lineTo(x, 0);
            path.close();
            canvas.drawPath(path, mPaint);
        }

    }

    /**
     * 在水平方向上绘制下边三角形
     *
     * @param canvas
     */
    private void drawHorizontalBottomTriangle(Canvas canvas) {
        //先计算出水平方向上的数量
        calculateHorizontalCount(0);
        Path path = new Path();
        float x = 0;
        //绘制下面部分
        for (int i = 0; i < mHorizontalCount; i++) {
            path.reset();
            x = mHorizontalInitSize + i * 2 * mRadius;
            path.moveTo(x, mHeight);
            x += mRadius;
            path.lineTo(x, mHeight - mRadius);
            x += mRadius;
            path.lineTo(x, mHeight);
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }

    /**
     * 在水平方向上绘制上边圆形
     *
     * @param canvas
     */
    private void drawHorizontalTopCircle(Canvas canvas) {
        //先计算出水平方向上的数量
        calculateHorizontalCount(mInterval);
        float x = mHorizontalInitSize + mInterval + mRadius;
        //绘制上面部分
        for (int i = 0; i < mHorizontalCount; i++) {
            canvas.drawCircle(x, 0, mRadius, mPaint);
            x += 2 * mRadius + mInterval;
        }

    }

    /**
     * 在水平方向上绘制下边圆形
     *
     * @param canvas
     */
    private void drawHorizontalBottomCircle(Canvas canvas) {
        //先计算出水平方向上的数量
        calculateHorizontalCount(mInterval);
        float x = mHorizontalInitSize + mInterval + mRadius;
        //绘制下面部分
        for (int i = 0; i < mHorizontalCount; i++) {
            canvas.drawCircle(x, mHeight, mRadius, mPaint);
            x += 2 * mRadius + mInterval;
        }
    }


    /**
     * 在垂直方向绘制左边半圆形
     *
     * @param canvas
     */
    private void drawVerticalLiftCircle(Canvas canvas) {
        //计算一下圆形的数量和初始偏移距离
        calculateVerticalCount(mInterval);
        //这次使用画弧来绘制出圆形
        RectF rectF = new RectF();
        //画左边
        for (int i = 0; i < mVerticalCount; i++) {
            rectF.left = -mRadius;
            rectF.top = mVerticalInitSize + mInterval * (i + 1) + i * 2 * mRadius;
            rectF.right = mRadius;
            rectF.bottom = rectF.top + 2 * mRadius;
            canvas.drawArc(rectF, -90, 180, false, mPaint);
        }

    }

    /**
     * 在垂直方向绘制右边半圆形
     *
     * @param canvas
     */
    private void drawVerticalRightCircle(Canvas canvas) {
        //计算一下圆形的数量和初始偏移距离
        calculateVerticalCount(mInterval);
        //这次使用画弧来绘制出圆形
        RectF rectF = new RectF();
        //画右边
        for (int i = 0; i < mVerticalCount; i++) {
            rectF.left = mWidth - mRadius;
            rectF.top = mVerticalInitSize + mInterval * (i + 1) + i * 2 * mRadius;
            rectF.right = rectF.left + 2 * mRadius;
            rectF.bottom = rectF.top + 2 * mRadius;
            canvas.drawArc(rectF, 90, 180, false, mPaint);
        }
    }

    /**
     * 在垂直方向绘制左边三角形
     *
     * @param canvas
     */
    private void drawVerticalLeftTriangle(Canvas canvas) {
        //计算一下三角形的数量和初始距离
        calculateVerticalCount(0);
        Path path = new Path();
        float y = 0;
        //先画左边
        for (int i = 0; i < mVerticalCount; i++) {
            path.reset();
            y = mVerticalInitSize + i * 2 * mRadius;
            path.moveTo(0, y);
            y += mRadius;
            path.lineTo(mRadius, y);
            y += mRadius;
            path.lineTo(0, y);
            path.close();
            canvas.drawPath(path, mPaint);
        }

    }

    /**
     * 在垂直方向绘制右边三角形
     *
     * @param canvas
     */
    private void drawVerticalRightTriangle(Canvas canvas) {
        //计算一下三角形的数量和初始距离
        calculateVerticalCount(0);
        Path path = new Path();
        float y = 0;
        //画右边
        for (int i = 0; i < mVerticalCount; i++) {
            path.reset();
            y = mVerticalInitSize + i * 2 * mRadius;
            path.moveTo(mWidth, y);
            y += mRadius;
            path.lineTo(mWidth - mRadius, y);
            y += mRadius;
            path.lineTo(mWidth, y);
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }
}

