package com.bohui.art.start.welcome;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bohui.art.R;
import com.bohui.art.common.activity.AbsBaseActivity;
import com.bohui.art.common.helperutil.AbsBaseHelperUtil;
import com.bohui.art.start.MainActivity;
import com.framework.core.glideext.GlideUtil;
import com.framework.core.util.StatusBarCompatUtil;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/9
 * @description:
 */


public class WelcomeActivity extends AbsBaseActivity implements ViewPager.OnPageChangeListener,View.OnClickListener{
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    //下边四个点
    @BindView(R.id.ll)
    LinearLayout ll;
    //布局集合
    private ArrayList<View> views;
    // 小点图片
    private ImageView[] dots;
    // 记录当前选中位置
    private int currentIndex;

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        new StatusBarCompatUtil.Builder(this).setSupportType(2).builder().apply();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        createPagerViews();
        initViewPager();
        initDots();
    }

    private void initDots() {
        dots = new ImageView[views.size()];
        // 循环取得小点图片
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);// 都设为灰色
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
    }

    private void initViewPager() {
        viewPager.setOffscreenPageLimit(2);
        // 初始化Adapter
        WelcomePagerAdapter adapter = new WelcomePagerAdapter(views);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
    }

    private void createPagerViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<>();
        // 初始化引导图片列表
        View viewOne = inflater.inflate(R.layout.layout_welcome_one, null);
        ImageView iv_welcome_one = viewOne.findViewById(R.id.iv_welcome_one);
        GlideUtil.display(mContext,iv_welcome_one,R.drawable.img_welcome_1);
        views.add(viewOne);

        View viewTwo = inflater.inflate(R.layout.layout_welcome_two, null);
        ImageView iv_welcome_two = viewTwo.findViewById(R.id.iv_welcome_two);
        GlideUtil.display(mContext,iv_welcome_two,R.drawable.img_welcome_2);
        views.add(viewTwo);

        View viewThree = inflater.inflate(R.layout.layout_welcome_three, null);
        ImageView iv_welcome_three = viewThree.findViewById(R.id.iv_welcome_three);
        GlideUtil.display(mContext,iv_welcome_three,R.drawable.img_welcome_3);
        TextView startBtn = viewThree.findViewById(R.id.tv_welcome_start);
        mRxManager.add(RxView.clicks(startBtn).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if(mHelperUtil != null && mHelperUtil instanceof AbsBaseHelperUtil){
                    ((AbsBaseHelperUtil)mHelperUtil).startAty(MainActivity.class,true);
                }
            }
        }));
        views.add(viewThree);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurDot(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    /**
     * 设置当前的引导页
     */
    private void setCurView(int position) {
        if (position < 0 || position >= views.size()) {
            return;
        }
        viewPager.setCurrentItem(position);
    }

    /**
     * 这只当前引导小点的选中
     */
    private void setCurDot(int positon) {
        if (positon < 0 || positon > views.size() - 1 || currentIndex == positon) {
            return;
        }
        if (positon == 2) {
            ll.setVisibility(View.GONE);
        } else {
            ll.setVisibility(View.VISIBLE);
        }
        dots[positon].setEnabled(false);
        dots[currentIndex].setEnabled(true);
        currentIndex = positon;
    }

    /**
     * 解决app的字体大小随着手机设置的字体大小改变的现象(禁用)
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
