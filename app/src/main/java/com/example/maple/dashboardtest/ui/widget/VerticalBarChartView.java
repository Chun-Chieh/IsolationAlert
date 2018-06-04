package com.example.maple.dashboardtest.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.maple.dashboardtest.R;

/**
 * @author Chun-Chieh Liang on 5/9/18.
 */
public class VerticalBarChartView extends View {
    int MAX = 100;
    int corner = 35;
    int data = 0; // num to display
    int tempData = 0;
    int textPadding = 20;
    Paint mPaint;
    int mColor;

    Context mContext;

    public VerticalBarChartView(Context context) {
        super(context);
        mContext = context;
    }

    public VerticalBarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    public VerticalBarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
            mPaint.setTextSize(getWidth() / 2);
            RectF oval3 = new RectF(0, getHeight() - pxToDip(mContext, 20), getWidth(), getHeight());
            canvas.drawRoundRect(oval3, pxToDip(mContext, corner), pxToDip(mContext, corner), mPaint);

//            canvas.drawText("0",
//                    getWidth() * 0.5f - mPaint.measureText("0") * 0.5f,
//                    getHeight() - pxToDip(mContext, 20) - 2 * pxToDip(mContext, textPadding),
//                    mPaint);
            return;
        }

        // accelerated animation when the number is tremendous
        int step = data / 100 + 1;

        if (tempData < data - step) {
            tempData = tempData + step;
        } else {
            tempData = data;
        }

        // rounded rectangle
        String S = tempData + "";

        if (S.length() < 4) {
            mPaint.setTextSize(getWidth() / 2);
        } else {
            mPaint.setTextSize(getWidth() / (S.length() - 1));
        }

        float textH = mPaint.ascent() + mPaint.descent();
        float MaxH = getHeight() - textH - 2 * pxToDip(mContext, textPadding);

        // The actual height of rounded rec
        float realH = MaxH / MAX * tempData;
        RectF oval3 = new RectF(0, getHeight() - realH, getWidth(), getHeight());

        // create a new rectangle
        canvas.drawRoundRect(oval3, pxToDip(mContext, corner), pxToDip(mContext, corner), mPaint);

        // write the number
//        canvas.drawText(S,
//                getWidth() * 0.5f - mPaint.measureText(S) * 0.5f,
//                getHeight() - realH - 2 * pxToDip(mContext, textPadding),
//                mPaint);
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

    private int pxToDip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5F);
    }
}

