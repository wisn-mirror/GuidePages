package com.wisn.guidePage;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wisn.guidePage.view.GuideView;
import com.wisn.guidePage.view.SingleElement;
import com.wisn.guidePage.view.SinglePage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisn on 2017/11/16.
 */

public class GuidePageActivity extends Activity {

    private ViewPager mMGuideViewPager;
    private Button start;
    private ArrayList<ImageView> mImageViewList;
    private int[]
            mImgIDs =
            {R.drawable.splash_iv_first,
             R.drawable.splash_iv_second,
             R.drawable.splash_iv_second,
             R.drawable.splash_iv_forth};
    private ArgbEvaluator mMArgbEvaluator;
    private RelativeLayout mRootView;
    private int colorBg[];
    private LinearLayout mIndicator;
    private GuideView mGuideView;

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
        mImageViewList = new ArrayList<>();
        for (int imgID : mImgIDs) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(imgID);
            mImageViewList.add(imageView);
        }
    }

    private void initView() {
        mMGuideViewPager = (ViewPager) findViewById(R.id.vp_guide);
        start = (Button) findViewById(R.id.btn_start);
        mRootView = (RelativeLayout) findViewById(R.id.rootView);
        mIndicator = (LinearLayout) findViewById(R.id.indicator);
        mGuideView = new GuideView(this,
                                   buildGuideContent(),
                                   true,
                                   BitmapFactory.decodeResource(getResources(), R.drawable.ic_dot_default),
                                   BitmapFactory.decodeResource(getResources(), R.drawable.ic_dot_selected));
        mMGuideViewPager.addOnPageChangeListener(mGuideView);
        mRootView.addView(mGuideView);
    }

    private List<SinglePage> buildGuideContent() {
        // prepare the information for our guide
        List<SinglePage> guideContent = new ArrayList<SinglePage>();

        SinglePage page01 = new SinglePage();
        page01.mBackground = getResources().getDrawable(R.drawable.splash_iv_first);
        SingleElement e01 = new SingleElement(200, 200, 400, 400, 0.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        SingleElement e02 = new SingleElement(700, 800, 700, 100, 0.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        page01.mElementsList.add(e01);
        page01.mElementsList.add(e02);
        guideContent.add(page01);

        SinglePage page02 = new SinglePage();
        page02.mBackground = getResources().getDrawable(R.drawable.splash_iv_second);
        SingleElement e03 = new SingleElement(400, 400, -100, -100, 1.0f, 0.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        SingleElement e04 = new SingleElement(700, 100, 700, -200, 1.0f, 0.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        page02.mElementsList.add(e03);
        page02.mElementsList.add(e04);
        guideContent.add(page02);

        SinglePage page03 = new SinglePage();
        page03.mBackground = getResources().getDrawable(R.drawable.splash_iv_third);
        SingleElement e05 = new SingleElement(-100, 2000, 100, 100, 1.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        SingleElement e06 = new SingleElement(100, 2000, 300, 120, 1.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        SingleElement e07 = new SingleElement(200, 2000, 600, 140, 1.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        SingleElement e08 = new SingleElement(300, 2000, 900, 160, 1.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        page03.mElementsList.add(e05);
        page03.mElementsList.add(e06);
        page03.mElementsList.add(e07);
        page03.mElementsList.add(e08);
        guideContent.add(page03);

        SinglePage page04 = new SinglePage();
        page04.mBackground = getResources().getDrawable(R.drawable.splash_iv_forth);
        SingleElement e09 = new SingleElement(100, 100, 3000, 3000, 1.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        SingleElement e10 = new SingleElement(300, 120, 3000, 3000, 1.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        SingleElement e11 = new SingleElement(600, 140, 3000, 3000, 1.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        SingleElement e12 = new SingleElement(900, 160, 3000, 3000, 1.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        page04.mElementsList.add(e09);
        page04.mElementsList.add(e10);
        page04.mElementsList.add(e11);
        page04.mElementsList.add(e12);
        guideContent.add(page04);

        return guideContent;
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
