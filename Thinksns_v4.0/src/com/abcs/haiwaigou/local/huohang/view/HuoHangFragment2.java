package com.abcs.haiwaigou.local.huohang.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.fragment.adapter.CFViewPagerAdapter;
import com.abcs.haiwaigou.local.evenbus.NoticeCityName;
import com.abcs.haiwaigou.local.evenbus.changesCity;
import com.abcs.haiwaigou.utils.MyString;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.sociax.android.R;
import com.abcs.sociax.t4.android.ActivityHome;
import com.abcs.sociax.t4.android.fragment.FragmentSociax;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class HuoHangFragment2 extends FragmentSociax {

    private static HuoHangFragment2 instance;

    public static HuoHangFragment2 newInstance() {
        if (instance == null) {
            instance = new HuoHangFragment2();
        }
        return instance;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    ActivityHome activity;
    View view;

    @InjectView(R.id.tv_city_name)
    TextView tvCityName;
    @InjectView(R.id.liner_top)
    RelativeLayout linerTop;
    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.tv_class)
    LinearLayout tv_class;

    List<String> names = new ArrayList<>();
    CFViewPagerAdapter viewPagerAdapter;
    @InjectView(R.id.bg_line_buttom)
    ImageView bgLineButtom;
    @InjectView(R.id.tv_shangping)
    TextView tvShangping;
    @InjectView(R.id.relative_chaoshi)
    RelativeLayout relativeChaoshi;
    @InjectView(R.id.bg_line_buttom2)
    ImageView bgLineButtom2;
    @InjectView(R.id.tv_shangjia)
    TextView tvShangjia;
    @InjectView(R.id.relative_pifa)
    RelativeLayout relativePifa;
    @InjectView(R.id.main_pager)
    public ViewPager mainPager;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ActivityHome) getActivity();
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.huohang_select_store_new, null);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup p = (ViewGroup) view.getParent();
        if (p != null)
            p.removeView(view);
        ButterKnife.inject(this, view);
        EventBus.getDefault().register(this);
        initCityName();
        if(!TextUtils.isEmpty(Util.preference.getString(MyString.LOCAL_CITY_ID, ""))){
            cityId=Util.preference.getString(MyString.LOCAL_CITY_ID, "");
        }else {
            cityId="41";

        }
        names.clear();
        names.add("商品");
        names.add("商家");
        initViewPager();

//        tvCityName.setFocusable(true);
//        tvCityName.setFocusableInTouchMode(true);
//        tvCityName.requestFocus();

        linerTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SelectedHHStoreActivity.class);
                startActivityForResult(intent, 1);
            }
        });


      /*  tv_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, HuoHangClassFicationActivity.class);
                intent.putExtra("cityId", cityId);
                startActivity(intent);
            }
        });*/
        return view;
    }

    private String cityId, cityName, countryName;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == 1 && data != null) {
                cityId = data.getStringExtra("cityId");
                cityName = data.getStringExtra("cityName");
                countryName = data.getStringExtra("countryName");
                Log.i("zds1", "onActivityResult: " + cityId);

                if (!TextUtils.isEmpty(countryName)) {
                    tvCityName.setText(countryName);
                }

                if (!TextUtils.isEmpty(cityName)) {
                    tvCityName.setText(cityName);
                }

                if (!TextUtils.isEmpty(countryName) && !TextUtils.isEmpty(cityName)) {
                    tvCityName.setText(cityName + "/" + countryName);
                }

                if (!TextUtils.isEmpty(cityId)) {
                    EventBus.getDefault().post(new changesCity(true,cityId));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.huohang_select_store_new;
    }

    private void initCityName() {
        if (!TextUtils.isEmpty(Util.preference.getString(MyString.LOCAL_COUNTRY_NAME, ""))) {
            tvCityName.setText(Util.preference.getString(MyString.LOCAL_COUNTRY_NAME, ""));
        }
        if (!TextUtils.isEmpty(Util.preference.getString(MyString.LOCAL_CITY_NAME, ""))) {
            tvCityName.setText(Util.preference.getString(MyString.LOCAL_CITY_NAME, ""));
        }

        if (!TextUtils.isEmpty(Util.preference.getString(MyString.LOCAL_COUNTRY_NAME, "")) && !TextUtils.isEmpty(Util.preference.getString(MyString.LOCAL_CITY_NAME, ""))) {
            tvCityName.setText(Util.preference.getString(MyString.LOCAL_CITY_NAME, "") + "/" + Util.preference.getString(MyString.LOCAL_COUNTRY_NAME, ""));
        }
    }
    private void changeText(int position){
        if(position==0){
            mainPager.setCurrentItem(0);
            tvShangping.setTextColor(getResources().getColor(R.color.colorPrimaryDark2));
            tvShangjia.setTextColor(getResources().getColor(R.color.textcloor));
            bgLineButtom.setVisibility(View.VISIBLE);
            bgLineButtom2.setVisibility(View.INVISIBLE);
        }else if(position==1){
            mainPager.setCurrentItem(1);
            tvShangping.setTextColor(getResources().getColor(R.color.textcloor));
            tvShangjia.setTextColor(getResources().getColor(R.color.colorPrimaryDark2));
            bgLineButtom.setVisibility(View.INVISIBLE);
            bgLineButtom2.setVisibility(View.VISIBLE);
        }
    }


    private void initViewPager() {
        //第三方Tab
        viewPagerAdapter = new CFViewPagerAdapter(activity.getSupportFragmentManager());
        viewPagerAdapter.getDatas().add(ItemChaoShiFragment.newInstance(cityId));
        viewPagerAdapter.getDatas().add(ItemPiFaFragment.newInstance(cityId));
        viewPagerAdapter.getTitle().add(0, names.get(0));
        viewPagerAdapter.getTitle().add(1, names.get(1));

        //滑动的viewpager
        mainPager.setAdapter(viewPagerAdapter);
        mainPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeText(position);
                MyApplication.current_position=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.relative_chaoshi, R.id.relative_pifa})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_chaoshi:
                changeText(0);
                break;
            case R.id.relative_pifa:
                changeText(1);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotices(NoticeCityName changes) {   // 两个字fragment 通知切换城市，重新刷新数据
        try {
            if(changes!=null){
                if(!TextUtils.isEmpty(changes.cityName)){
                    tvCityName.setText(changes.cityName);
                }

                if (!TextUtils.isEmpty(changes.countryNmae)) {
                    tvCityName.setText(changes.countryNmae);
                }

                if (!TextUtils.isEmpty(changes.cityName) && !TextUtils.isEmpty(changes.countryNmae)) {
                    tvCityName.setText(changes.cityName + "/" + changes.countryNmae);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }
}
