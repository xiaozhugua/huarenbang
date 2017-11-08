package com.abcs.haiwaigou.local.huohang.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.broadcast.MyBroadCastReceiver;
import com.abcs.haiwaigou.broadcast.MyUpdateUI;
import com.abcs.haiwaigou.fragment.adapter.CFViewPagerAdapter;
import com.abcs.huaqiaobang.model.BaseFragmentActivity;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.sociax.android.R;
import com.astuetz.PagerSlidingTabStrip;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 本地配送 我的订单新版页面
 */
public class HuoHangNewOrderActivity extends BaseFragmentActivity implements View.OnClickListener {

    public Handler handler = new Handler();

    CFViewPagerAdapter viewPagerAdapter;
    MyBroadCastReceiver myBroadCastReceiver;


    int currentType;
    int position = 0;
    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.t_title_name)
    TextView tTitleName;
    @InjectView(R.id.comment_tabs)
    PagerSlidingTabStrip commentTabs;
    @InjectView(R.id.linear_tab)
    LinearLayout linearTab;
    @InjectView(R.id.seperate_line)
    View seperateLine;
    @InjectView(R.id.comment_pager)
    ViewPager commentPager;
    @InjectView(R.id.linear_root)
    LinearLayout linearRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_activity_new_order_list);
        ButterKnife.inject(this);
        myBroadCastReceiver = new MyBroadCastReceiver(this, updateUI);
        myBroadCastReceiver.register();
        setOnListener();
        initViewPager();
    }

    private void initViewPager() {
        try {
            //第三方Tab
            viewPagerAdapter = new CFViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.getDatas().add(HuoHangNewOrderFragment.newInstance(""));//全部
            viewPagerAdapter.getDatas().add(HuoHangNewOrderFragment.newInstance("10"));//待支付
            viewPagerAdapter.getDatas().add(HuoHangNewOrderFragment.newInstance("20"));//代发货
            viewPagerAdapter.getDatas().add(HuoHangNewOrderFragment.newInstance("30"));//待收货
            viewPagerAdapter.getDatas().add(HuoHangNewOrderFragment.newInstance("40"));//已完成
            viewPagerAdapter.getDatas().add(HuoHangNewOrderFragment.newInstance("0"));//已取消


            viewPagerAdapter.getTitle().add(0, "全部");
            viewPagerAdapter.getTitle().add(1, "待付款");
            viewPagerAdapter.getTitle().add(2, "待发货");
            viewPagerAdapter.getTitle().add(3, "待收货");
            viewPagerAdapter.getTitle().add(4, "已完成");
            viewPagerAdapter.getTitle().add(5, "已取消");
            commentPager.setAdapter(viewPagerAdapter);
            commentPager.setOffscreenPageLimit(1);
            commentPager.setCurrentItem(position);
            commentTabs.setViewPager(commentPager);
            commentTabs.setIndicatorHeight(Util.dip2px(this, 4));
            commentTabs.setTextSize(Util.dip2px(this, 16));
            setSelectTextColor(position);
            setTextType();
            commentTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    // TODO Auto-generated method stub
                    setSelectTextColor(position);
                    currentType = position + 1;
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onPageScrollStateChanged(int position) {

                    System.out.println("Change Posiont:" + position);

                    // TODO Auto-generated method stub

                }
            });
            currentType = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTextType() {
        for (int i = 0; i < 6; i++) {
            View view = commentTabs.getChildAt(0);
            View viewText = ((LinearLayout) view).getChildAt(i);
            TextView tabTextView = (TextView) viewText;
            if (tabTextView != null) {
                Util.setFZLTHFont(tabTextView);
            }
        }
    }

    private void setSelectTextColor(int position) {
        for (int i = 0; i < 6; i++) {
            View view = commentTabs.getChildAt(0);
            View viewText = ((LinearLayout) view).getChildAt(i);
            TextView tabTextView = (TextView) viewText;
            if (tabTextView != null) {
                if (position == i) {
                    tabTextView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    tabTextView.setTextColor(getResources().getColor(R.color.hwg_text2));
                }
            }
        }
    }


    MyBroadCastReceiver.UpdateUI updateUI = new MyBroadCastReceiver.UpdateUI() {
        @Override
        public void updateShopCar(Intent intent) {

        }

        @Override
        public void updateCarNum(Intent intent) {

        }

        @Override
        public void updateCollection(Intent intent) {
            if (intent.getStringExtra("type").equals(MyUpdateUI.ORDER)) {
                Log.i("zjz", "更新订单");
            }
        }

        @Override
        public void update(Intent intent) {

        }
    };

    private void setOnListener() {
        relativeBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        myBroadCastReceiver.unRegister();
    }
}
