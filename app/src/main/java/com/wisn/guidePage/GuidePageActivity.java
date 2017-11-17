package com.wisn.guidePage;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wisn.guidePage.view.IndicatorView;

import java.util.ArrayList;

/**
 * Created by wisn on 2017/11/16.
 */

public class GuidePageActivity extends Activity {

    private static final String TAG ="GuidePageActivity" ;
    private ViewPager mMGuideViewPager;
    private Button start;
    private ArrayList<ImageView> mImageViewList;
    private TypedArray mImgIDs;
    private ArgbEvaluator mMArgbEvaluator;
    private RelativeLayout mRootView;
    private int colorBg[];
    private LinearLayout mIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noTitleBar();
        setContentView(R.layout.activity_guidepage);
        initView();
        initData();
        initEvent();
        mMGuideViewPager.setAdapter(new GuidePagesAdapter());
    }

    private void initEvent() {
        mMGuideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int color = (int) mMArgbEvaluator.evaluate(positionOffset, colorBg[position % colorBg.length],
                                                           colorBg[(position + 1) % colorBg.length]);
                mRootView.setBackgroundColor(color);
            }

            @Override
            public void onPageSelected(int position) {
                if (position >= mImageViewList.size() - 1) {
                    start.setVisibility(View.VISIBLE);
                } else {
                    start.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuidePageActivity.this, MainActivity.class));
                GuidePageActivity.this.finish();
            }
        });
    }

    private void initData() {
        mMArgbEvaluator = new ArgbEvaluator();
        colorBg = getResources().getIntArray(R.array.splash_bg);
        mImgIDs = getResources().obtainTypedArray(R.array.splash_icon);
        mImageViewList = new ArrayList<>();
        Log.e(TAG," "+mImgIDs);
        for (int i=0;i<mImgIDs.length() ;i++) {
            Log.e(TAG," "+mImgIDs.getResourceId(i,0));
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(mImgIDs.getResourceId(i,0));
            mImageViewList.add(imageView);
        }
    }

    private void initView() {
        mMGuideViewPager = (ViewPager) findViewById(R.id.vp_guide);
        start = (Button) findViewById(R.id.btn_start);
        mRootView = (RelativeLayout) findViewById(R.id.rootView);
        mIndicator = (LinearLayout) findViewById(R.id.indicator);
        IndicatorView indicatorView = (IndicatorView) findViewById(R.id.IndicatorView);
        indicatorView.setConfig(this,4,BitmapFactory.decodeResource(getResources(), R.drawable.ic_dot_default),
                                BitmapFactory.decodeResource(getResources(), R.drawable.ic_dot_selected));
        mMGuideViewPager.addOnPageChangeListener(indicatorView);
    }


    private void noTitleBar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }



    private class GuidePagesAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = mImageViewList.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
