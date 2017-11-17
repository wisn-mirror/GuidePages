package com.wisn.guidePage.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisn on 2017/11/17.
 */

public class IndicatorView extends View implements ViewPager.OnPageChangeListener {
    private static final int DOT_SPACE_DIVIDE = 3;
    private static final float DOT_Y_POSITION = 0.9f;
    private int mPosition;
    private float mOffset;
    private List<SingleElement> mDotList;
    private int mDotXStart;
    private int mDotXPlus;
    private int mDotY;
    private SingleElement mSelectedDot;

    public IndicatorView(Context context) {
        super(context);
    }


    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setConfig(Activity activity, int count,
                          Bitmap dotDefault,
                          Bitmap dotSelected) {
        mDotList = new ArrayList<>();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int mScreenWidth = dm.widthPixels;
        int mScreenHeight = dm.heightPixels - statusBarHeight;

        mDotXStart = mScreenWidth / DOT_SPACE_DIVIDE - dotDefault.getWidth() / 2;
        mDotXPlus = mScreenWidth / DOT_SPACE_DIVIDE / (count - 1);
        mDotY = (int) (mScreenHeight * DOT_Y_POSITION);
        for (int i = 0; i < count; i++) {
            SingleElement
                    e =
                    new SingleElement(mDotXStart + mDotXPlus * i,
                                      mDotY,
                                      mDotXStart + mDotXPlus * i,
                                      mDotY,
                                      1.0f,
                                      1.0f,
                                      dotDefault);
            mDotList.add(e);
        }
        mSelectedDot =
                new SingleElement(mDotXStart + mDotXPlus * mPosition,
                                  mDotY,
                                  mDotXStart + mDotXPlus * mPosition,
                                  mDotY,
                                  1.0f,
                                  1.0f,
                                  dotSelected);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mDotList.size(); i++) {
            SingleElement e = mDotList.get(i);
            drawElement(canvas, e);
        }
        int x = mDotXStart + mDotXPlus * mPosition;
        mSelectedDot.xStart = x;
        mSelectedDot.xEnd = x;
        drawElement(canvas, mSelectedDot);
        super.onDraw(canvas);
    }

    private void drawElement(Canvas canvas, SingleElement e) {
        Bitmap bitmap = e.contentBitmap;
        Matrix m = e.m;
        Paint p = e.p;
        float dx = e.xStart + (e.xEnd - e.xStart) * mOffset;
        float dy = e.yStart + (e.yEnd - e.yStart) * mOffset;
        float alpha = e.alphaStart + (e.alphaEnd - e.alphaStart) * mOffset;
        m.setTranslate(dx, dy);
        p.setAlpha((int) (0xFF * alpha));
        canvas.drawBitmap(bitmap, m, p);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mPosition = position;
        mOffset = positionOffset;
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
