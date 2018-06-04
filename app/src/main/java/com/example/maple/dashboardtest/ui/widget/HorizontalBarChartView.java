package com.example.maple.dashboardtest.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.maple.dashboardtest.R;

public class HorizontalBarChartView extends View {
    int MAX = 100;
    int corner = 50;
    int data = 0;
    int tempData = 0;
    int textPadding = 20;
    Paint mPaint;
    int mColor;
    boolean hasLabel;

    Context mContext;

    public HorizontalBarChartView(Context context) {
        super(context);
        mContext = context;
    }

    public HorizontalBarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomChartView, 0, 0);
        try {
            hasLabel = array.getBoolean(R.styleable.CustomChartView_hasLabel, false);
        } finally {
            array.recycle();
        }
        mContext = context;
        initPaint();
    }

    public HorizontalBarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mColor = ContextCompat.getColor(mContext, R.color.colorAccent);
        mPaint.setColor(mColor);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (data == 0) {
            mPaint.setTextSize(getHeight() / 2);
            RectF oval3 = new RectF(0, 0, 20, getHeight());
            canvas.drawRoundRect(oval3, corner, corner, mPaint);

            if (hasLabel) {
                canvas.drawText("0",
                        20 + 2 * textPadding,
                        getHeight() * 0.5f - (mPaint.ascent() + mPaint.descent()) / 2,
                        mPaint);
            }
            return;
        }

        // accelerated animation when the number is tremendous
        int step = data / 100 + 1;

        if (tempData < data - step) {
            tempData = tempData + step;
        } else {
            tempData = data;
        }

        String S = tempData + "";

        mPaint.setTextSize(getHeight() * 0.8f);


        float textW = mPaint.ascent() + mPaint.descent();
        float MaxW = getWidth();

        if (hasLabel) {
            MaxW = getWidth() - textW - 3 * textPadding;
        }

        float realW = MaxW / MAX * tempData;
        RectF oval3 = new RectF(0, 0, realW, getHeight());
        canvas.drawRoundRect(oval3, corner, corner, mPaint);


        if (hasLabel) {
            canvas.drawText(S,
                    0 + realW + 2 * textPadding,
                    getHeight() * 0.5f - textW / 2,
                    mPaint);
        }

        if (tempData != data) {
            postInvalidate();
        }
    }

    public void setData(int data, int MAX) {
        this.data = data;
        tempData = 0;
        this.MAX = MAX;
        postInvalidate();
    }
}
