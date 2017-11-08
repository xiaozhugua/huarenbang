package com.abcs.haiwaigou.local.huohang.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.abcs.haiwaigou.fragment.adapter.CFViewPagerAdapter;
import com.abcs.haiwaigou.local.huohang.bean.Acty;
import com.abcs.haiwaigou.local.huohang.bean.DatasEntrySer;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.huaqiaobang.view.CircleImageView;
import com.abcs.huaqiaobang.wxapi.WXEntryActivity;
import com.abcs.sociax.android.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class NewHuoHangActivity extends BaseActivity {

    List<String> names = new ArrayList<>();
    CFViewPagerAdapter viewPagerAdapter;

    @InjectView(R.id.main_pager)
    ViewPager mainPager;

    DatasEntrySer datasEntry;

    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.relative_more)
    RelativeLayout relativeMore;
    @InjectView(R.id.img_setting)
    ImageView img_setting;
    @InjectView(R.id.relative_seach)
    RelativeLayout relativeSeach;
    @InjectView(R.id.top)
    RelativeLayout top;
    @InjectView(R.id.tv_circle_img)
    CircleImageView tvCircleImg;
    @InjectView(R.id.tv_store_name)
    TextView tvStoreName;
    @InjectView(R.id.tv_des)
    TextView tvDes;
    @InjectView(R.id.line)
    View line;
    @InjectView(R.id.tv_huodong_num)
    TextView tvHuodongNum;
    @InjectView(R.id.view_click)
    View view_click;
    @InjectView(R.id.title)
    RelativeLayout title;
//    @InjectView(R.id.main_tabs)
//    TabLayout mainTabs;
    @InjectView(R.id.lin_tab)
    RelativeLayout linTab;
    @InjectView(R.id.view_flipper)
    ViewFlipper view_flipper;
    @InjectView(R.id.liner_center)
    View linerCenter;
    @InjectView(R.id.bg_line_buttom)
    ImageView bgLineButtom;
    @InjectView(R.id.tv_shangping)
    TextView tvShangping;
    @InjectView(R.id.relative_shangpin)
    RelativeLayout relativeShangpin;
    @InjectView(R.id.bg_line_buttom2)
    ImageView bgLineButtom2;
    @InjectView(R.id.tv_shangjia)
    TextView tvShangjia;
    @InjectView(R.id.relative_shangjia)
    RelativeLayout relativeShangjia;
    @InjectView(R.id.relative_mian)
    RelativeLayout relative_mian;
    @InjectView(R.id.relative_select_store)
    RelativeLayout relative_select_store;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (view_flipper != null) {
            view_flipper.stopFlipping();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_huo_hang);
        ButterKnife.inject(this);

        relativeBack.setVisibility(View.VISIBLE);
        relative_mian.setVisibility(View.VISIBLE);
        relative_select_store.setVisibility(View.GONE);

        datasEntry = (DatasEntrySer) getIntent().getSerializableExtra("datas");
        names.clear();
        names.add("商品");
        names.add("商家");
        initTopView(datasEntry);
        initViewPager();

    }

    private String store_id;
    private void initTopView(DatasEntrySer datasEntry) {
        try {
            if (datasEntry != null) {
                /*logo*/
                if (!TextUtils.isEmpty(datasEntry.getStoreNewLogo())) {
                    MyApplication.imageLoader.displayImage(datasEntry.getStoreNewLogo(), tvCircleImg, MyApplication.options);
                }

                store_id=datasEntry.getStoreId();

                /*店铺名称*/
                if (!TextUtils.isEmpty(datasEntry.getStoreName())) {
                    tvStoreName.setText(datasEntry.getStoreName());
                }
                /*店铺描述*/
                if (!TextUtils.isEmpty(datasEntry.getStoreDes())) {
                    tvDes.setText(datasEntry.getStoreDes());
                }
                /*店铺描述*/
                if (datasEntry.getActivity() != null) {
                    if (datasEntry.getActivity().size() > 0) {
                        view_flipper.removeAllViews();
                        for (int k = 0; k < datasEntry.getActivity().size(); k++) {

                            Acty bean_hd = datasEntry.getActivity().get(k);
                            View view = View.inflate(this, R.layout.item_huohang_acty2, null);

                            ViewGroup parent3 = (ViewGroup) view.getParent();
                            if (parent3 != null) {
                                parent3.removeAllViews();
                            }

                            ImageView img = (ImageView) view.findViewById(R.id.img);
                            TextView tv = (TextView) view.findViewById(R.id.tv);

                            if (bean_hd != null) {
                                if (!TextUtils.isEmpty(bean_hd.img)) {
    //                http://www.huaqiaobang.com/data/upload/shop/store/goods/11/11_05375327290881075.jpg
                                    MyApplication.imageLoader.displayImage(bean_hd.img, img, MyApplication.getListOptions());
                                }

                                if (!TextUtils.isEmpty(bean_hd.name)) {
                                    tv.setText(bean_hd.name);
                                }
                            }

                            view_flipper.addView(view);
                        }

                        // 设置动画开始滚动
                        view_flipper.setInAnimation(this, R.anim.vp_bottom_in_activity);
                        view_flipper.setOutAnimation(this, R.anim.vp_bottom_out_activity);
                        view_flipper.setFlipInterval(3000);
                        view_flipper.startFlipping();

                        /*原来的*/
    //                    DatasEntry.Acty activityArr = datasEntry.activity.get(0);
    //                    if(activityArr!=null){
    //                        if (!TextUtils.isEmpty(activityArr.img)) {
    //                            MyApplication.imageLoader.displayImage(activityArr.img,imgSong , MyApplication.options);
    //                        }
    //                        if (!TextUtils.isEmpty(activityArr.activityName)) {
    //                            tvSong.setText(activityArr.activityName);
    //                        }else {
    //                            tvSong.setText("暂无活动");
    //                        }
    //                    }

                        tvHuodongNum.setText(datasEntry.getActivity().size() + "个活动");
                    } else {
                        tvHuodongNum.setText("0个活动");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViewPager() {

        try {
            //第三方Tab
            viewPagerAdapter = new CFViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.getDatas().add(ItemGoodsFragment.newInstance(datasEntry));
            viewPagerAdapter.getDatas().add(ItemShangJiaFragment.newInstance(datasEntry));
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
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
//        mainPager.setOffscreenPageLimit(1);
//        mainTabs.setupWithViewPager(mainPager);

//        mainTabs.post(new Runnable() {
//            @Override
//            public void run() {
//                MyApplication.setIndicator(mainTabs, 10, 10);
//            }
//        });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler handler=new Handler();
    @OnClick({R.id.relative_back, R.id.relative_more, R.id.relative_seach,R.id.relative_shangpin, R.id.relative_shangjia,R.id.view_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back:
                EventBus.getDefault().post("clearadapter");  // 清空购物车
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },500);
                break;
            case R.id.view_click: // 点击活动
                popHHGongGao();
                break;
            case R.id.relative_more:  // setting
                settingPop();
                break;
            case R.id.relative_seach:
                Intent intent=new Intent(this,HHNewSearchActivity.class);
                intent.putExtra("store_id",store_id);
                startActivity(intent);

                break;
            case R.id.relative_shangpin:
                changeText(0);
                break;
            case R.id.relative_shangjia:
                changeText(1);
                break;
        }
    }

    /****
     * 获取设置的列表
     */
    private void settingPop() {

        try {
            View itemView = View.inflate(this, R.layout.popup_bendi_setting, null);
            LinearLayout tv_mystore = (LinearLayout) itemView.findViewById(R.id.line_mystore);
            LinearLayout tv_myorder = (LinearLayout) itemView.findViewById(R.id.line_dingdan);
            LinearLayout tv_mynormal = (LinearLayout) itemView.findViewById(R.id.line_changyong);
            LinearLayout line_messge = (LinearLayout) itemView.findViewById(R.id.line_messge);
            LinearLayout line_bendian = (LinearLayout) itemView.findViewById(R.id.line_bendian);

            final PopupWindow pop = new PopupWindow(this);
            pop.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            pop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            pop.setContentView(itemView);

            pop.setAnimationStyle(R.style.popWindowAnimation);//设置弹出和消失的动画
            //触摸点击事件
            pop.setTouchable(true);
            //聚集
            pop.setFocusable(true);
            //设置允许在外点击消失
            pop.setOutsideTouchable(true);
            //点击返回键popupwindown消失
            pop.setBackgroundDrawable(new BitmapDrawable());
            //背景变暗
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.alpha = 0.5f;
            getWindow().setAttributes(params);
            pop.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            //监听如果popupWindown消失之后背景变亮
            pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams params = getWindow()
                            .getAttributes();
                    params.alpha = 1f;
                    getWindow().setAttributes(params);
                }
            });
            pop.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
            pop.showAsDropDown(img_setting);

            tv_mystore.setOnClickListener(new View.OnClickListener() {  // 我的店铺列表
                @Override
                public void onClick(View v) {

                    if(MyApplication.getInstance().getMykey()==null){
                        Intent intent = new Intent(NewHuoHangActivity.this, WXEntryActivity.class);
                        intent.putExtra("isthome", true);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(NewHuoHangActivity.this, HuoHangAddressActivity.class);
                        intent.putExtra("formSetting", true);
                        startActivity(intent);
                    }
    //                if (is_LocalMember == 1) {  //不是货行用户
    //                    startActivity(new Intent(NewHuoHangActivity.this, LoacalKeFuActivity_HH.class));
    //                } else {
    //
    //
    //                }
                    pop.dismiss();
                }
            });
            line_bendian.setOnClickListener(new View.OnClickListener() {  // 关于本店
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(NewHuoHangActivity.this, WebActivity.class);
                    intent.putExtra("store_id", store_id);
                    startActivity(intent);
                    pop.dismiss();
                }
            });
            tv_myorder.setOnClickListener(new View.OnClickListener() {  // 我的订单
                @Override
                public void onClick(View v) {
                    if(MyApplication.getInstance().getMykey()==null){
                        Intent intent = new Intent(NewHuoHangActivity.this, WXEntryActivity.class);
                        intent.putExtra("isthome", true);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(NewHuoHangActivity.this, HuoHangNewOrderActivity.class);
                        startActivity(intent);
                    }
    //                if (is_LocalMember == 1) {  //不是货行用户
    //                    startActivity(new Intent(NewHuoHangActivity.this, LoacalKeFuActivity_HH.class));
    //                } else {
    //
    //
    //                }
                    pop.dismiss();
                }
            });

            line_messge.setOnClickListener(new View.OnClickListener() { //  我的消息
                @Override
                public void onClick(View v) {
                    if(MyApplication.getInstance().getMykey()==null){
                        Intent intent = new Intent(NewHuoHangActivity.this, WXEntryActivity.class);
                        intent.putExtra("isthome", true);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(NewHuoHangActivity.this, NoticeActivity.class);
                        startActivity(intent);
                    }
    //                if (is_LocalMember == 1) {  //不是货行用户
    //                    startActivity(new Intent(NewHuoHangActivity.this, LoacalKeFuActivity_HH.class));
    //                } else {
    //
    //                }

                    pop.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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

    /*活动公告*/
    private void popHHGongGao() {
        try {
            View root_view = View.inflate(this, R.layout.pop_huohang_hh_gonggao, null);
            ImageView imge_close = (ImageView) root_view.findViewById(R.id.iv_close);
            TextView tv_content_gg = (TextView) root_view.findViewById(R.id.tv_content_gg);
            LinearLayout liner_act = (LinearLayout) root_view.findViewById(R.id.liner_act);

            if(datasEntry!=null){
                                 /*店铺描述*/
                if (!TextUtils.isEmpty(datasEntry.storeDes)) {
                    tv_content_gg.setText(datasEntry.storeDes);
                }
                /*店铺描述*/
                if (datasEntry.activity != null) {
                    if (datasEntry.activity.size() > 0) {
                        liner_act.removeAllViews();
                        for (int k = 0; k < datasEntry.activity.size(); k++) {

                            Acty bean_hd = datasEntry.activity.get(k);
                            View view = View.inflate(this, R.layout.item_huohang_acty, null);

                            ViewGroup parent3 = (ViewGroup) view.getParent();
                            if (parent3 != null) {
                                parent3.removeAllViews();
                            }

                            ImageView img = (ImageView) view.findViewById(R.id.img);
                            TextView tv = (TextView) view.findViewById(R.id.tv);

                            if (bean_hd != null) {
                                if (!TextUtils.isEmpty(bean_hd.img)) {
                                    MyApplication.imageLoader.displayImage(bean_hd.img, img, MyApplication.getListOptions());
                                }

                                if (!TextUtils.isEmpty(bean_hd.name)) {
                                    tv.setText(bean_hd.name);
                                }
                            }

                            liner_act.addView(view);
                        }
                    }
                }
            }

            final PopupWindow popupWindow = new PopupWindow();
            popupWindow.setWidth(Util.WIDTH*5/6);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setContentView(root_view);

            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.alpha = 0.5f;
            getWindow().setAttributes(params);
            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams params = getWindow()
                            .getAttributes();
                    params.alpha = 1f;
                    getWindow().setAttributes(params);
                }
            });
            popupWindow.setAnimationStyle(R.style.popWindowAnimation);//设置弹出和消失的动画
            //触摸点击事件
            popupWindow.setTouchable(true);
            //聚集
            popupWindow.setFocusable(true);
            //设置允许在外点击消失
            popupWindow.setOutsideTouchable(false);
            //点击返回键popupwindown消失

            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));

            popupWindow.showAtLocation(root_view, Gravity.CENTER, 0, 0);
            imge_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
