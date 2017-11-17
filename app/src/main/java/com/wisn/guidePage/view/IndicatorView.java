package com.wisn.guidePage.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * Created by wisn on 2017/11/17.
 */

public class IndicatorView extends View implements ViewPager.OnPageChangeListener {
    private int count;
    private static final int DOT_SPACE_DIVIDE = 3;
    private static final float DOT_Y_POSITION = 0.9f;
    private static final String TAG = "IndicatorView";
    private int mPosition;
    private float mOffset;
    private int mDotXStart;
    private int mDotXPlus;
    private int mDotY;
    Bitmap dotDefault;
    Bitmap dotSelected;
    private Matrix mMatrix;
    private Paint mPaint;

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
        mMatrix = new Matrix();
        mPaint = new Paint();
        this.count = count;
        this.dotDefault = Bitmap.createScaledBitmap(dotDefault, 150, 150, true);
        this.dotSelected = Bitmap.createScaledBitmap(dotSelected, 150, 150, true);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int mScreenWidth = dm.widthPixels;
        int mScreenHeight = dm.heightPixels - statusBarHeight;
        Log.e(TAG, "mScreenWidth:" + mScreenWidth + " mScreenHeight:" + mScreenHeight);
        mDotXStart = mScreenWidth / DOT_SPACE_DIVIDE - dotDefault.getWidth() / 2;
        mDotXPlus = mScreenWidth / DOT_SPACE_DIVIDE / (count - 1);
        mDotY = (int) (mScreenHeight * DOT_Y_POSITION);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < count; i++) {
            mMatrix.setTranslate(mDotXStart + mDotXPlus * i, mDotY);
            canvas.drawBitmap(this.dotDefault, mMatrix, mPaint);
//            Rect rect=new Rect(20,20,60,60);
//            Rect rectf=new Rect(mDotXStart + mDotXPlus* i,mDotY,mDotXStart + mDotXPlus * (i+1),mDotY+mDotXPlus);
////            canvas.drawBitmap(this.dotDefault,mDotXStart + mDotXPlus * i,0,mPaint);
//            canvas.drawBitmap(this.dotDefault,rect,rectf,mPaint);
        }
        mMatrix.setTranslate(mDotXStart + mDotXPlus * mPosition + mDotXPlus * mOffset, mDotY);
        canvas.drawBitmap(this.dotSelected, mMatrix, mPaint);
        super.onDraw(canvas);
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
