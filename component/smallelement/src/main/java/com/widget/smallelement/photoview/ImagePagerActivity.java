package com.widget.smallelement.photoview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.widget.smallelement.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hujiajun
 * @version V1.0
 * @Description: 仿微信图片点击放大拖拽效果
 * @date 16/08/01.
 */
public class ImagePagerActivity extends FragmentActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    public static final String EXTRA_BACK_POSITION = "back_position";

    private HackyViewPager mPager;
    int pagerPosition;
    private TextView indicator;

    private int currentPosition;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_image_detail_pager);

        pagerPosition = getIntent().getExtras().getInt(EXTRA_IMAGE_INDEX, 0);
        currentPosition = pagerPosition;
        List<String> urls = getIntent().getExtras().getStringArrayList(EXTRA_IMAGE_URLS);


        mPager = findViewById(R.id.pager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        indicator = findViewById(R.id.indicator);
        RelativeLayout tv_close = findViewById(R.id.tv_detail_pager_close);

        CharSequence text = getString(R.string.inv_c, 1, mPager.getAdapter().getCount());
        indicator.setText(text);
        // 更新下标
        mPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                currentPosition = arg0;
                CharSequence text = getString(R.string.inv_c, arg0 + 1, mPager.getAdapter().getCount());
                indicator.setText(text);
            }

        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        mPager.setCurrentItem(currentPosition);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        Intent intent = getIntent();
        intent.putExtra(EXTRA_BACK_POSITION,currentPosition);
        setResult(Activity.RESULT_OK,intent);
        super.finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }

    private class ImagePagerAdapter extends FragmentPagerAdapter {

        public List<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, List<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            return ImageDetailFragment.newInstance(url);
        }
    }

    public static void comeIn(Activity activity, int position, ArrayList<String> imgUrls,int requestCode){
        Intent intent = new Intent(activity, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(EXTRA_IMAGE_URLS,imgUrls);
        bundle.putInt(EXTRA_IMAGE_INDEX, position);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent,requestCode);
    }
}