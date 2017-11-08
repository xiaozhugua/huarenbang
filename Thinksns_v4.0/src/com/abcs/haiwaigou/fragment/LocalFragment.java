package com.abcs.haiwaigou.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abcs.haiwaigou.local.activity.CountryCityActivity;
import com.abcs.haiwaigou.local.activity.HireAndFindActivity;
import com.abcs.haiwaigou.local.activity.HuiLvActivity;
import com.abcs.haiwaigou.local.activity.LocalZiXunDetialsActivity;
import com.abcs.haiwaigou.local.activity.MyPublishActivity;
import com.abcs.haiwaigou.local.activity.NewLocalMuneActivity;
import com.abcs.haiwaigou.local.activity.NewMoreActivity;
import com.abcs.haiwaigou.local.activity.NewsTypeActivity;
import com.abcs.haiwaigou.local.adapter.GridVImgsAdapter;
import com.abcs.haiwaigou.local.beans.HHBanner;
import com.abcs.haiwaigou.local.beans.LocalHomeAds;
import com.abcs.haiwaigou.local.beans.LocalNews;
import com.abcs.haiwaigou.local.beans.LocalTop;
import com.abcs.haiwaigou.local.beans.NewLocal;
import com.abcs.haiwaigou.local.beans.NewsBean_N;
import com.abcs.haiwaigou.local.huohang.bean.DatasEntrySer;
import com.abcs.haiwaigou.local.huohang.bean.SelectStoreNew;
import com.abcs.haiwaigou.local.huohang.view.NewHuoHangActivity;
import com.abcs.haiwaigou.model.GGAds;
import com.abcs.haiwaigou.utils.ACache;
import com.abcs.haiwaigou.utils.MyString;
import com.abcs.haiwaigou.view.MyGridView;
import com.abcs.haiwaigou.view.MyListView;
import com.abcs.haiwaigou.view.zjzbanner.LMBanners;
import com.abcs.haiwaigou.view.zjzbanner.adapter.LBaseAdapter;
import com.abcs.haiwaigou.view.zjzbanner.transformer.TransitionEffect;
import com.abcs.hqbtravel.Contonst;
import com.abcs.hqbtravel.adapter.CeSuAdapter;
import com.abcs.hqbtravel.adapter.TravelZhenXuanAdapter;
import com.abcs.hqbtravel.entity.ZhenXuan;
import com.abcs.hqbtravel.view.activity.BiChiActivity;
import com.abcs.hqbtravel.view.activity.BiMaiActivity;
import com.abcs.hqbtravel.view.activity.BiWanActivity2;
import com.abcs.hqbtravel.view.activity.SeachYouJiByTagActivity;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.activity.GuanggaoActivity;
import com.abcs.huaqiaobang.adapter.CommonAdapter;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.huaqiaobang.util.ServerUtils;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.huaqiaobang.view.HqbViewHolder;
import com.abcs.huaqiaobang.view.MainScrollView;
import com.abcs.huaqiaobang.wxapi.WXEntryActivity;
import com.abcs.huaqiaobang.ytbt.common.utils.ToastUtil;
import com.abcs.sociax.android.R;
import com.abcs.sociax.t4.android.ActivityHome;
import com.abcs.sociax.t4.android.bean.CeSu;
import com.abcs.sociax.t4.android.fragment.FragmentSociax;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.thinksns.sociax.thinksnsbase.utils.TLUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.abcs.sociax.android.R.id.linear_setting;
import static com.abcs.sociax.android.R.id.tv_time;
import static com.abcs.sociax.t4.android.video.ToastUtils.showToast;

/**
 * Created by zds 新版全球首页
 */
public class LocalFragment extends FragmentSociax {

    static LocalFragment instance;

    @InjectView(R.id.relative_no_city)
    RelativeLayout relativeNoCity;
    @InjectView(R.id.null_view)
    View nullView;
    @InjectView(R.id.banners)
    LMBanners banners;
    @InjectView(R.id.hh_banners)  // 货行banner
            LMBanners hh_banners;
    @InjectView(R.id.tv_city)
    TextView tvCity;
    @InjectView(R.id.tv_country)
    TextView tvCountry;
    @InjectView(R.id.iv_yun)
    ImageView ivYun;
    @InjectView(R.id.tv_tianqi)
    TextView tvTianqi;
    @InjectView(R.id.tv_tianqi_des)
    TextView tvTianqiDes;
    @InjectView(R.id.linear_change)
    LinearLayout linearChange;
    @InjectView(linear_setting)
    LinearLayout linearSetting;
    @InjectView(R.id.img_huilv)
    ImageView imgHuilv;
    @InjectView(R.id.tv_jinri_huilv)
    TextView tvJinriHuilv;
    @InjectView(R.id.line_huilv)
    View lineHuilv;
    @InjectView(R.id.tv_number_huilv)
    TextView tvNumberHuilv;
    @InjectView(R.id.relat_huilv)
    RelativeLayout relatHuilv;
    @InjectView(R.id.rela_ss)
    RelativeLayout rela_ss;
    @InjectView(R.id.lin_zhaogong)
    LinearLayout linZhaogong;
    @InjectView(R.id.lin_shengyi)
    LinearLayout linShengyi;
    @InjectView(R.id.top)  // 头顶
            LinearLayout top;
    @InjectView(R.id.lin_fangwu)
    LinearLayout linFangwu;
    @InjectView(R.id.lin_chiwanguo)
    LinearLayout linChiwanguo;
    @InjectView(R.id.lin_qiaojie)
    LinearLayout linQiaojie;
    @InjectView(R.id.img_hd_name)
    TextView imgHdName;
    @InjectView(R.id.img_hd_time)
    TextView imgHdTime;
    @InjectView(R.id.img_hd_des)
    TextView imgHdDes;
    @InjectView(R.id.img_hd_canyu)
    TextView imgHdCanyu;
    @InjectView(R.id.relat_tt)
    RelativeLayout relatTt;
    @InjectView(R.id.relative_jiexiao)
    RelativeLayout relative_jiexiao;  // 一元云购
    @InjectView(R.id.img_hd_icon)
    ImageView imgHdIcon;
    @InjectView(R.id.img_hd_icon2)
    ImageView imgHdIcon2;
    @InjectView(R.id.img_hd_i_zhongjian)
    ImageView imgHdIZhongjian;
    @InjectView(R.id.tv_1)
    TextView tv1;
    @InjectView(R.id.tv_2_name)
    TextView tv2Name;
    @InjectView(R.id.tv_3)
    TextView tv3;
    @InjectView(R.id.tv_4)
    TextView tv4;
    @InjectView(R.id.tv_goods_title)
    TextView tvGoodsTitle;
    @InjectView(R.id.relat_goods_title)
    RelativeLayout relatGoodsTitle;
    @InjectView(R.id.relative_huohang)
    RelativeLayout relative_huohang;
    @InjectView(R.id.recycleview_goods)
    RecyclerView recycleviewGoods;
    @InjectView(R.id.tv_news_title)
    TextView tvNewsTitle;
    @InjectView(R.id.relat_news_title)
    RelativeLayout relatNewsTitle;
    @InjectView(R.id.recycleview_news)  // 今日资讯
    MyListView recycleviewNews;
    @InjectView(R.id.recycleview_duanzishou)  // 段子手
    MyListView recycleview_duanzishou;
    @InjectView(R.id.recycleview_pager)
    MyListView recycleviewPager;
    @InjectView(R.id.tv_news_more)
    TextView tvNewsMore;
    @InjectView(R.id.gridview_gg) // 新闻广告
            MyGridView gridviewGg;
    @InjectView(R.id.gridview_gg_jinri)  // 今日资讯
            MyGridView gridview_gg_jinri;
    @InjectView(R.id.gridview_gg_duanzishou)  // 段子手
            MyGridView gridview_gg_duanzishou;
    @InjectView(R.id.liner_banner)
    LinearLayout linerBanner;
    @InjectView(R.id.mainScrollView)
    MainScrollView mainScrollView;
    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.tv_city_name2)
    TextView tvCityName;
    @InjectView(R.id.linear_change_china)
    LinearLayout linearChangeChina;
    @InjectView(R.id.relative_search_china)
    RelativeLayout relativeSearchChina;
    @InjectView(R.id.setting)
    ImageView setting;
    @InjectView(R.id.lin_setting_china)
    LinearLayout linSettingChina;
    @InjectView(R.id.listview_zhenxuan)
    ListView listviewZhenxuan;
    @InjectView(R.id.linear_china)
    LinearLayout linearChina;
    @InjectView(R.id.gridview_menu_child)
    MyGridView gridview_menu_child;
    @InjectView(R.id.gridview_menu)
    MyGridView gridview_menu;
    @InjectView(R.id.img_banner)
    ImageView img_banner;  // banner
    @InjectView(R.id.img_hd_jindu)
    TextView img_hd_jindu;  // 开奖进度
    @InjectView(R.id.goods_prograss)
    ProgressBar goods_prograss;  // 开奖进度
    @InjectView(R.id.iv)
    ImageView iv;
    @InjectView(R.id.tv_location)
    TextView tvLocation;
    @InjectView(R.id.lin_des)
    LinearLayout linDes;
    @InjectView(R.id.relative_news)
    RelativeLayout relativeNews;
    @InjectView(R.id.relative_jinri_zixun)
    RelativeLayout relative_jinri_zixun;
    @InjectView(R.id.relative_shenghuo_zixun)
    RelativeLayout relative_shenghuo_zixun;
    @InjectView(R.id.relat_pager_title)
    RelativeLayout relatPagerTitle;
    @InjectView(R.id.relative_pager)
    RelativeLayout relativePager;
    @InjectView(R.id.tv_pager_more)
    TextView tvPagerMore;
    @InjectView(R.id.t_select)
    TextView tSelect;
    @InjectView(R.id.tv_huaren_num)
    TextView tv_huaren_num;
    @InjectView(R.id.tv_shangye)
    TextView tv_shangye;
    @InjectView(R.id.tv_pager_title)
    TextView tvPagerTitle;
    @InjectView(R.id.tv_duanzishou_title)
    TextView tvDuanzishouTitle;
    @InjectView(R.id.relat_duanzishou_title)
    RelativeLayout relatDuanzishouTitle;
    @InjectView(R.id.relative_duanzishou_list)
    RelativeLayout relativeDuanzishouList;
    @InjectView(R.id.tv_duanzishou_more)
    TextView tvDuanzishouMore;
    @InjectView(R.id.relative_duanzishou)
    RelativeLayout relativeDuanzishou;

    public static LocalFragment getInstance() {
        if (instance == null) {
            instance = new LocalFragment();
        }
        return instance;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (hh_banners != null)
            hh_banners.stopImageTimerTask();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hh_banners != null)
            hh_banners.startImageTimerTask();
    }


    @Override
    public int getLayoutId() {
        return R.layout.hqb_local_fragment_new;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    private void loadLocal() {


        try {
            cityId = Util.preference.getString(MyString.LOCAL_CITY_ID, "");

            if (!TextUtils.isEmpty(cityId)) {
                cityName = Util.preference.getString(MyString.LOCAL_CITY_NAME, "");
                tvCity.setText(cityName);
                if (!TextUtils.isEmpty(Util.preference.getString(MyString.LOCAL_COUNTRY_NAME, ""))) {
                    tvCountry.setText(Util.preference.getString(MyString.LOCAL_COUNTRY_NAME, ""));
                } else {
                    tvCountry.setText(cityName);
                }
            }else {
                cityId="41";
                tvCity.setText("维也纳（首都）");
                tvCountry.setText("奥地利");
    //            Intent intent = new Intent(activity, CountryCityActivity.class);
    //            startActivityForResult(intent, 1);
    //            nullView.setVisibility(View.VISIBLE);
            }

            if(!TextUtils.isEmpty(aCache.getAsString("local_top"))){
                parseTopMsg(aCache.getAsString("local_top"));
            }else {
                getTopDatas(cityId);
            }
//        if(!TextUtils.isEmpty(aCache.getAsString("local_newsd"))){
//            parseNewsMsg(aCache.getAsString("local_newsd"));
//        }else {
            getNewsDatas(cityId);
//        }
            if(!TextUtils.isEmpty(aCache.getAsString("local_adsf"))){
               /*底部广告*/
                parseAdsMsg(aCache.getAsString("local_adsf"));
            }else {
                getAdsDatas(cityId);
            }

            parseHuoHang(cityId);  // 请求货行banner
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void isHasLocation(String lat, String lng) {

//        http://120.24.19.29:7075/find/getCityBylnglat?lng=114.231234&lat=22.123456

        Log.i("zds", "isHasLocation: lat" + lat);
        Log.i("zds", "isHasLocation: lng" + lng);
        Log.i("zds", "isHasLocation: url" + Contonst.HOST + "/find/getCityBylnglat?" + "lat=" + lat + "&lng=" + lng);

        HttpRequest.sendGet(Contonst.HOST + "/find/getCityBylnglat", "lat=" + lat + "&lng=" + lng, new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("zds", "run: " + msg);
                        if (TextUtils.isEmpty(msg)) {
                            loadLocal();
                            return;
                        } else {
                            try {
                                JSONObject mainObject = new JSONObject(msg);

                                if (mainObject.optInt("result") == 1) {

                                    JSONObject objest = mainObject.optJSONObject("body");
                                    if (objest != null) {

                                        if (objest.optBoolean("isChina")) {  // 中国

                                            ChangeNetUrl(TLUrl.URL_CHIAN);
                                            MyApplication.savePosition(0);

                                            linearChina.setVisibility(View.VISIBLE);
                                            swipeRefreshLayout.setVisibility(View.GONE);
                                            getZhenXuan();
                                        } else {
                                            if (objest.has("area")) {  // 判断各大洲 切换服务器
                                                if (objest.optString("area").equals("亚洲")) {
                                                    ChangeNetUrl(TLUrl.URL_DONGNANYA);
                                                    MyApplication.savePosition(1);
                                                } else if (objest.optString("area").equals("欧洲")) {
                                                    ChangeNetUrl(TLUrl.URL_OUZHOU);
                                                    MyApplication.savePosition(2);
                                                } else if (objest.optString("area").equals("大洋洲")) {
                                                    ChangeNetUrl(TLUrl.URL_DONGNANYA);
                                                    MyApplication.savePosition(1);
                                                } else {
                                                    ChangeNetUrl(TLUrl.URL_OUZHOU);
                                                    MyApplication.savePosition(2);
                                                }
                                            }
                                            loadLocal();
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                loadLocal();
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    public void initMyData() {
        try {
            if (ServerUtils.isConnect(activity)) {
                relativeNoCity.setVisibility(View.GONE);

                /* 去掉定位*/
    //            if (!TextUtils.isEmpty(MyApplication.my_current_lat) && !TextUtils.isEmpty(MyApplication.my_current_lng)) {  // 定位成功
    //                isHasLocation(MyApplication.my_current_lat, MyApplication.my_current_lng);
    //            } else {
                ChangeNetUrl(TLUrl.URL_OUZHOU);
                MyApplication.savePosition(0); //默认选欧洲
                loadLocal();
    //            }
                initWanSu();
            } else {
                relativeNoCity.setVisibility(View.VISIBLE);
                ToastUtil.showMessage("请检查网络！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ACache aCache;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        view = inflater.inflate(R.layout.hqb_local_fragment_new, null);
        activity = (ActivityHome) getActivity();
        ButterKnife.inject(this, view);
        aCache = ACache.get(activity);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recycleviewGoods.setLayoutManager(layoutManager);
        // 防止srcroview内容太长会滚到底部
        linearSetting.setFocusable(true);
        linearSetting.setFocusableInTouchMode(true);
        linearSetting.requestFocus();
        initMyData();
        mainScrollView.setOnScroll(new MainScrollView.OnScroll() {
            @Override
            public void onScrollListener(int x, int y, int oldx, int oldy) {

                if (y > 50) {  // 改变top头顶的背景
                    top.setBackground(activity.getResources().getDrawable(R.color.white));
                    tvCityName.setTextColor(activity.getResources().getColor(R.color.local_text_left));
                    tvLocation.setBackground(activity.getResources().getDrawable(R.drawable.iv_local_dz));
                    setting.setImageResource(R.drawable.ic_more);
                } else {
                    top.setBackground(activity.getResources().getDrawable(R.color.transparent));
                    tvCityName.setTextColor(activity.getResources().getColor(R.color.white));
                    tvLocation.setBackground(activity.getResources().getDrawable(R.drawable.local_chang));
                    setting.setImageResource(R.drawable.ico_more_white);
                }

                if (y >= 5 && y < 15) {
                    top.setAlpha(0.3f);
                } else if (y >= 15 && y < 35) {
                    top.setAlpha(0.6f);
                } else if (y >= 35 && y < 50) {
                    top.setAlpha(0.8f);
                } else {
                    top.setAlpha(1.0f);
                }

            }
        });

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!ServerUtils.isConnect(activity)) {
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            showToast("请检查您的网络");
                        }
                    });
                    return;
                }

                getDatasFromNet();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        return view;
    }

    Handler handler = new Handler();
    private boolean isFirst = true;
    private String cityId;
    private String cityName;


//    private void parseHH(List<HuoH> huoHs) {
//
//        if (huoHs != null) {
//            if(huoHs.size() > 0){
//                recycleviewGoods.setAdapter(new HotGoodsAdapter(activity, huoHs));
//                relative_huohang.setVisibility(View.VISIBLE);
//            }else {
//                relative_huohang.setVisibility(View.GONE);
//            }
//
//        }
//    }



    /*头顶模块*/
    private void getTopDatas(String cityId) {
        try {
            if (isFirst) {
                ProgressDlgUtil.showProgressDlg("", activity);
            }

//        http://europe.huaqiaobang.com/japi/hrq/conListDetail/v2/getHomeTop?cityId=41
            HttpRequest.sendGet(TLUrl.getInstance().URL_bind_base + "/hrq/conListDetail/v2/getHomeTop", "cityId=" + cityId, new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(msg)) {
                                parseTopMsg(msg);
                                aCache.remove("local_top");
                                aCache.put("local_top", msg);
                            }
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*广告模块*/
    private void getAdsDatas(String cityId) {
        try {
//      http://europe.huaqiaobang.com/japi/hrq/conListDetail/v2/getHomeAds?cityId=41
            HttpRequest.sendGet(TLUrl.getInstance().URL_bind_base + "/hrq/conListDetail/v2/getHomeAds", "cityId=" + cityId, new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(msg)) {
                                  /*底部广告*/
                                parseAdsMsg(msg);
                                aCache.remove("local_adsf");
                                aCache.put("local_adsf", msg);
                            }
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*新闻模块*/
    private void getNewsDatas(String cityId) {
        try {
//     http://europe.huaqiaobang.com/japi/hrq/conListDetail/v2/getHomeNews?cityId=41
            HttpRequest.sendGet(TLUrl.getInstance().URL_bind_base + "/hrq/conListDetail/v2/getHomeNews", "cityId=" + cityId, new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(msg)) {
                                parseNewsMsg(msg);
                                aCache.remove("local_newsd");
                                aCache.put("local_newsd", msg);
                            }
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseNewsMsg(String msg){
        try {
            final LocalNews newLocal = new Gson().fromJson(msg, LocalNews.class);
            if (newLocal != null && newLocal.status == 1) {
                if (newLocal.msg != null && newLocal.msg.size() > 0) {
                    if (newLocal.msg.size() == 1) {
                        parseNews(newLocal.msg.get(0).news,recycleviewNews,relative_jinri_zixun,tvNewsMore,false,false);
                    } else if (newLocal.msg.size() == 2) {
                        parseNews(newLocal.msg.get(0).news,recycleviewNews,relative_jinri_zixun,tvNewsMore,false,false);
                        parseNews(newLocal.msg.get(1).news,recycleviewPager,relative_shenghuo_zixun,tvPagerMore,true,false);
                    } else if (newLocal.msg.size() == 3) {
                        parseNews(newLocal.msg.get(0).news,recycleviewNews,relative_jinri_zixun,tvNewsMore,false,false);
                        parseNews(newLocal.msg.get(1).news,recycleviewPager,relative_shenghuo_zixun,tvPagerMore,true,false);
                        parseNews(newLocal.msg.get(2).news,recycleview_duanzishou,relativeDuanzishou,tvDuanzishouMore,false,true);
                    }
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    private void parseNews(final List<NewsBean_N> news, MyListView myListView,RelativeLayout relatives,TextView tv_more,boolean isShenghZiXun,boolean isDuanZiShou){
        try {
            if(news.size()>0){
                if(isDuanZiShou){
                    p_duanzishous.clear();
                    if (news.size() > 2) {
                        for (int i = 0; i < 2; i++) {
                            p_duanzishous.add(news.get(i));
                        }

                        myListView.setAdapter(new HotNewsAdapter(activity, p_duanzishous));
                    } else {
                        myListView.setAdapter(new HotNewsAdapter(activity, news));
                    }

                    tv_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent;
                            intent = new Intent(activity, NewMoreActivity.class);
                            intent.putExtra("countryId", "200000");  // 固定
                            intent.putExtra("title", "段子手");  // 标题
                            startActivity(intent);
                        }
                    });
                }else if(isShenghZiXun){
                    paper.clear();
                    if (news.size() > 2) {
                        for (int i = 0; i < 2; i++) {
                            paper.add(news.get(i));
                        }

                        myListView.setAdapter(new HotNewsAdapter(activity, paper));
                    } else {
                        myListView.setAdapter(new HotNewsAdapter(activity, news));
                    }

                    tv_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent;
                            intent = new Intent(activity, NewMoreActivity.class);
                            intent.putExtra("countryId", "100000");  // 固定
                            intent.putExtra("title", "生活资讯");  // 标题
                            startActivity(intent);
                        }
                    });
                }else {
                    myListView.setAdapter(new HotNewsAdapter(activity,news));
                    tv_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent;
                            intent = new Intent(activity, NewsTypeActivity.class);
                            intent.putExtra("countryId", countryId);
                            intent.putExtra("cityId", cityId);
                            startActivity(intent);
                        }
                    });
                }

                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        NewsBean_N newsBean = (NewsBean_N) parent.getItemAtPosition(position);
                        if (newsBean != null) {
                            Intent intent = new Intent(activity, LocalZiXunDetialsActivity.class);
                            intent.putExtra("id", newsBean.id);
                            intent.putExtra("time", newsBean.time + "");
                            intent.putExtra("title", newsBean.title);
                            intent.putExtra("species", newsBean.species);
                            startActivity(intent);
                        }
                    }
                });

                relatives.setVisibility(View.VISIBLE);
            }else {
                relatives.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void parseAdsMsg(String msg) {
        try {
            final LocalHomeAds newLocal = new Gson().fromJson(msg, LocalHomeAds.class);
            if (newLocal != null && newLocal.status == 1) {
                if (newLocal.msg != null && newLocal.msg.size() > 0) {
                    if (newLocal.msg.size() == 1) {
                        parseGG(newLocal.msg.get(0),gridviewGg);
                        gridviewGg.setVisibility(View.VISIBLE);
                        gridview_gg_jinri.setVisibility(View.GONE);
                        gridview_gg_duanzishou.setVisibility(View.GONE);
                    } else if (newLocal.msg.size() == 2) {
                        parseGG(newLocal.msg.get(0),gridviewGg);
                        parseGG(newLocal.msg.get(1),gridview_gg_jinri);
                        gridviewGg.setVisibility(View.VISIBLE);
                        gridview_gg_jinri.setVisibility(View.VISIBLE);
                        gridview_gg_duanzishou.setVisibility(View.GONE);
                    } else if (newLocal.msg.size() == 3) {
                        parseGG(newLocal.msg.get(0),gridviewGg);
                        parseGG(newLocal.msg.get(1),gridview_gg_jinri);
                        parseGG(newLocal.msg.get(2),gridview_gg_duanzishou);
                        gridviewGg.setVisibility(View.VISIBLE);
                        gridview_gg_jinri.setVisibility(View.VISIBLE);
                        gridview_gg_duanzishou.setVisibility(View.VISIBLE);
                    }
                }
            }

            if(isFirst){
                isFirst=false;
                ProgressDlgUtil.stopProgressDlg();
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

    }

    String countryId;
    private void parseTopMsg(String msg) {
        try {
            final LocalTop newLocal = new Gson().fromJson(msg, LocalTop.class);
            if (newLocal != null && newLocal.status == 1) {
                if (newLocal.msg != null) {
                    img_banner.setScaleType(ImageView.ScaleType.FIT_XY);
                    MyApplication.imageLoader.displayImage(newLocal.msg.banner, img_banner, MyApplication.getListOptions());

                    countryId=newLocal.msg.countryId+"";

                    /*天气*/
                    if (!TextUtils.isEmpty(newLocal.msg.img)) {
                        MyApplication.imageLoader.displayImage(newLocal.msg.img, ivYun, MyApplication.getListOptions());
                    }
                    if (!TextUtils.isEmpty(newLocal.msg.temp)) {
                        tvTianqi.setText(newLocal.msg.temp);
                    }
                    if (!TextUtils.isEmpty(newLocal.msg.txt)) {
                        tvTianqiDes.setText(newLocal.msg.txt);
                    }

                    /*税率*/
                    if (newLocal.msg.bannerTitle != null) {
                        tvNumberHuilv.setText("100" + newLocal.msg.bannerTitle.hb + "=" + newLocal.msg.bannerTitle.hl + "CNY");

                         /*华人在此生活*/
                        if (!TextUtils.isEmpty(newLocal.msg.bannerTitle.hqNum)) {
                            tv_huaren_num.setText(newLocal.msg.bannerTitle.hqNum);
                        }
                        if (!TextUtils.isEmpty(newLocal.msg.bannerTitle.syNum)) {
                            tv_shangye.setText(newLocal.msg.bannerTitle.syNum);
                        }
                    }

                    /*menu*/
                    if (newLocal.msg.menu != null) {
                        parseMenu(newLocal.msg.menu);
                    }
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    private void getDatasFromNet() {
        getTopDatas(cityId);
        getNewsDatas(cityId);
        parseHuoHang(cityId);  // 请求货行banner
        getAdsDatas(cityId);

    }

    List<NewsBean_N> paper = new ArrayList<>(); // 生活资讯只显示两条
    List<NewsBean_N> p_duanzishous = new ArrayList<>(); // 段子手只显示两条

    /*新版货行优惠专区*/
    private void parseHuoHang(String cityId) {
        try {
//        http://huohang.huaqiaobang.com/mobile/index.php?act=native_index&op=get_native_hot_goods_list&city_id=41
            HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=native_index&op=get_native_hot_goods_list&city_id=" + cityId, new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("zds", "local_huohang===" + msg);
                            if (!TextUtils.isEmpty(msg)) {

                                try {
                                    HHBanner hhBanner = new Gson().fromJson(msg, HHBanner.class);
                                    if (hhBanner != null) {
                                        if (hhBanner.state == 1) {
                                            if (hhBanner.datas != null) {
                                                if (hhBanner.datas.size() > 0) {
                                                    initLunBo(hhBanner.datas);
                                                    relative_huohang.setVisibility(View.VISIBLE);
                                                } else {
                                                    relative_huohang.setVisibility(View.GONE);
                                                }
                                            }
                                        } else {
                                            relative_huohang.setVisibility(View.GONE);
                                        }
                                    }
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLunBo(List<SelectStoreNew.goodsBanners> datas) {

        if (hh_banners != null) {
            //设置Banners高度
            hh_banners.setLayoutParams(new RelativeLayout.LayoutParams(MyApplication.getWidth(), MyApplication.getWidth() * 190 / 750));
//            banners.setLayoutParams(new RelativeLayout.LayoutParams(MyApplication.getWidth(), MyApplication.getWidth()*240/750));
            //本地用法
            hh_banners.setAdapter(new UrlImgAdapter(activity), datas);
            //网络图片
//        mLBanners.setAdapter(new UrlImgAdapter(MainActivity.this), networkImages);
            //参数设置
            hh_banners.setAutoPlay(true);//自动播放
            hh_banners.setVertical(false);//是否可以垂直
            hh_banners.setScrollDurtion(500);//两页切换时间
            hh_banners.setCanLoop(true);//循环播放
            hh_banners.setSelectIndicatorRes(R.drawable.local_d2);//选中的原点
            hh_banners.setUnSelectUnIndicatorRes(R.drawable.local_d1);//未选中的原点
//        mLBanners.setHoriZontalTransitionEffect(TransitionEffect.Default);//选中喜欢的样式
//        banners.setHoriZontalCustomTransformer(new ParallaxTransformer(R.id.id_image));//自定义样式
            banners.setHoriZontalTransitionEffect(TransitionEffect.Alpha);//Alpha
            banners.setDurtion(3000);//切换时间
            if (datas.size() == 1) {

                hh_banners.hideIndicatorLayout();//隐藏原点
            } else {

                hh_banners.showIndicatorLayout();//显示原点
            }
            hh_banners.setIndicatorPosition(LMBanners.IndicaTorPosition.BOTTOM_MID);//设置原点显示位置
        }
    }

    class UrlImgAdapter implements LBaseAdapter<SelectStoreNew.goodsBanners> {
        private Context mContext;

        public UrlImgAdapter(Context context) {
            mContext = context;
        }

        @Override
        public View getView(final LMBanners lBanners, final Context context, final int position, final SelectStoreNew.goodsBanners data) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.banner_item_no_scaletype, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.id_image);

//            750 190

            try {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(MyApplication.getWidth(), MyApplication.getWidth() * 190 / 750);
                imageView.setLayoutParams(layoutParams);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                MyApplication.imageLoader.displayImage(data.goodsBanner, imageView, MyApplication.getListOptions());

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (data != null) {
                            DatasEntrySer datasEntrySer = new DatasEntrySer();
                            datasEntrySer.setActivity(data.activity);
                            datasEntrySer.setStoreAddress(data.storeAddress);
                            datasEntrySer.setStoreCoin(data.storeCoin);
                            datasEntrySer.setStoreDes(data.storeDes);
                            datasEntrySer.setStoreGoodsDetails(data.storeGoodsDetails);
                            datasEntrySer.setStoreId(data.storeId);
                            datasEntrySer.setStoreNewLogo(data.storeNewLogo);
                            datasEntrySer.setStoreName(data.storeName);
                            datasEntrySer.setStorePhone(data.storePhone);
                            datasEntrySer.setStoreSendTime(data.storeSendTime);
                            datasEntrySer.setNgc_id(data.ngcId);
                            datasEntrySer.setGoods_Id(data.goodsId);
                            checkIsSuccess(datasEntrySer, data.goodsId);
                            MyApplication.saveHHStoreCoin(data.storeCoin);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return view;
        }

    }

    private void checkIsSuccess(final DatasEntrySer bean, final String goods_Id) {
        Intent intent = new Intent(activity, NewHuoHangActivity.class);
        intent.putExtra("datas", bean);
        intent.putExtra("goods_Id", goods_Id);
        startActivity(intent);
    }

    private static final String MUNU_ZHAOGONG = "招工";
    private static final String MUNU_FANGWU = "房屋";
    private static final String MUNU_SHENGYI = "生意";
    private static final String MUNU_CHW = "生活";
    private static final String MUNU_QIAOJIE = "侨";
    private static final String MUNU_TONGXUN = "通讯";

    private boolean isClik = true;

    private void parseMenu(final List<NewLocal.MsgBean.MenuBean> menu) {
        try {
            Log.d("zds", "lLoad: " + menu.size());
            gridview_menu.setVisibility(View.VISIBLE);
            gridview_menu.setAdapter(new CommonAdapter<NewLocal.MsgBean.MenuBean>(activity, menu, R.layout.fragment_shopping_store_item) {
                @Override
                public void convert(HqbViewHolder helper, NewLocal.MsgBean.MenuBean item, int position) {

                    helper.setText(R.id.tv_name, item.menuName);
                    helper.setImageByUrl(R.id.img_icon, item.imgUrl, 0);

                }
            });

            gridview_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final NewLocal.MsgBean.MenuBean menuBean = (NewLocal.MsgBean.MenuBean) parent.getItemAtPosition(position);
                    if (menuBean != null && menuBean.child != null) {
                        if (menuBean.child.size() > 0) {  // 修改生意转让不要打开二级
                            if(menuBean.menuName.contains(MUNU_SHENGYI)){
                                Intent tt = new Intent(activity, NewLocalMuneActivity.class);
                                tt.putExtra("title", "生意转让");
                                tt.putExtra("cityId", cityId);
                                tt.putExtra("menuId", "2010");
                                startActivity(tt);
                            }else {
                                openMenu(menuBean);
                            }
                        } else {
    //                        gridview_menu_child.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_out));
                            gridview_menu_child.setVisibility(View.GONE);
                            if (menuBean.menuName.contains(MUNU_QIAOJIE)) {
                                Toast.makeText(activity, "敬请期待", Toast.LENGTH_SHORT).show();
                            } else if (menuBean.menuName.contains(MUNU_TONGXUN)) {
                                Intent intent = new Intent(activity, HireAndFindActivity.class);  // 通讯录
                                intent.putExtra("title", "通讯录");
                                intent.putExtra("cityId", cityId);
                                intent.putExtra("menuId", "80");  // 通讯录的menuID 80
                                startActivity(intent);
                            } else {
                                Intent tt = new Intent(activity, NewLocalMuneActivity.class);
                                tt.putExtra("title", menuBean.menuName);
                                tt.putExtra("cityId", cityId);
                                tt.putExtra("menuId", menuBean.menuId + "");
                                startActivity(tt);
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*打开menu*/
    private void openMenu(final NewLocal.MsgBean.MenuBean menuBean) {
        try {
            if (isClik) {
                isClik = false;
    //                            gridview_menu_child.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
                gridview_menu_child.setVisibility(View.VISIBLE);
                gridview_menu_child.setAdapter(new MyAdapter(menuBean.child));
                gridview_menu_child.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        NewLocal.MsgBean.MenuBean.ChildBean childBean = (NewLocal.MsgBean.MenuBean.ChildBean) parent.getItemAtPosition(position);

                        if (menuBean.menuName.contains(MUNU_ZHAOGONG)|| menuBean.menuName.contains(MUNU_FANGWU)) {
                            if (childBean != null) {
                                Intent tt = new Intent(activity, NewLocalMuneActivity.class);
                                tt.putExtra("title", childBean.menuName);
                                tt.putExtra("cityId", cityId);
                                tt.putExtra("menuId", childBean.menuId + "");
                                startActivity(tt);
                                gridview_menu_child.setVisibility(View.GONE);
                                isClik = true;
                            }
                        } else if (menuBean.menuName.contains(MUNU_CHW)) {
                            Intent irw = null;
                            if (childBean.menuName.contains("吃")) {
                                irw = new Intent(activity, BiChiActivity.class);
                                irw.putExtra("cityId", cityId);
                                irw.putExtra("title", childBean.menuName);
                                irw.putExtra("current_lat", MyApplication.my_current_lat);
                                irw.putExtra("current_lng", MyApplication.my_current_lng);
                                startActivity(irw);
                            } else if (childBean.menuName.contains("玩")) {
                                irw = new Intent(activity, BiWanActivity2.class);
                                irw.putExtra("cityId", cityId);
                                irw.putExtra("title", childBean.menuName);
                                irw.putExtra("current_lat", MyApplication.my_current_lat);
                                irw.putExtra("current_lng", MyApplication.my_current_lng);
                                startActivity(irw);
                            } else if (childBean.menuName.contains("购")) {
                                irw = new Intent(activity, BiMaiActivity.class);
                                irw.putExtra("cityId", cityId);
                                irw.putExtra("title", childBean.menuName);
                                irw.putExtra("current_lat", MyApplication.my_current_lat);
                                irw.putExtra("current_lng", MyApplication.my_current_lng);
                                startActivity(irw);
                            } else {
                                Toast.makeText(activity, "敬请期待", Toast.LENGTH_SHORT).show();
                            }
                            gridview_menu_child.setVisibility(View.GONE);
                            isClik = true;

                        }
                    }
                });
            } else {
                isClik = true;
    //                            gridview_menu_child.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_out));
                gridview_menu_child.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean isChina;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1 && data != null) {
            cityName = data.getStringExtra("cityName");
            cityId = data.getStringExtra("cityId");
            isChina = data.getBooleanExtra("isChina", false);
            Log.i("zds1", "onActivityResult: " + isChina);
            if (isChina) {
                initChina();
                tvLocation.setVisibility(View.GONE);
//                tvCityName.setText("国内");
                tvCityName.setTextColor(activity.getResources().getColor(R.color.local_text_left));
                top.setBackground(activity.getResources().getDrawable(R.color.white));
                setting.setImageResource(R.drawable.cl);
            } else {
                isFirst=true;
                tvCityName.setTextColor(activity.getResources().getColor(R.color.white));
                setting.setImageResource(R.drawable.ico_more_white);
                tvLocation.setVisibility(View.VISIBLE);
                UpteloadData(cityName, cityId);
                top.setBackground(activity.getResources().getDrawable(R.color.transparent));
            }
        }
    }

    private void initChina() {
        getZhenXuan();
        tvCountry.setText("国内");
        linearChina.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
    }


    private void UpteloadData(String cate_name, String city_id) {

        try {
            linearChina.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);

            tvCity.setText(cate_name);
            if (!TextUtils.isEmpty(Util.preference.getString(MyString.LOCAL_COUNTRY_NAME, ""))) {
                tvCountry.setText(Util.preference.getString(MyString.LOCAL_COUNTRY_NAME, ""));
    //            tvCityName.setText(Util.preference.getString(MyString.LOCAL_COUNTRY_NAME, ""));
            } else {
                tvCountry.setText(cate_name);
    //            tvCityName.setText(cate_name);
            }
            cityId = city_id;
            getDatasFromNet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getZhenXuan() {
        try {
//        http://china.huaqiaobang.com/admintravel/city/queryNoCityImg?type=2

            ProgressDlgUtil.showProgressDlg("", activity);
            HttpRequest.sendGet(TLUrl.getInstance().getUrl() + "/admintravel/city/queryNoCityImg", "type=2", new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ProgressDlgUtil.stopProgressDlg();
                            Log.i("zds_zhenxuan", msg + "");

                            if (TextUtils.isEmpty(msg)) {
                                return;
                            } else {
                                try {
                                    ZhenXuan bean = new Gson().fromJson(msg, ZhenXuan.class);
                                    if (bean != null && bean.result == 1) {
                                        if (bean.body != null) {
                                            if (bean.body.size() > 0) {
                                                TravelZhenXuanAdapter mXuanAdapter = new TravelZhenXuanAdapter(activity, bean.body);
                                                listviewZhenxuan.setAdapter(mXuanAdapter);
                                                nullView.setVisibility(View.GONE);
                                            } else {
                                                showToast("还没有数据哦！");
                                            }
                                        }
                                    }
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            });

            listviewZhenxuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        ZhenXuan.BodyEntry bodyEntry = (ZhenXuan.BodyEntry) parent.getItemAtPosition(position);
                        if (bodyEntry != null && !TextUtils.isEmpty(bodyEntry.htmlUrl)) {
                            Intent intent = new Intent(activity, GuanggaoActivity.class);
                            intent.putExtra("url_local", bodyEntry.htmlUrl);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*本地新闻*/
    private void parseGG(List<GGAds> adsBeen,MyGridView gridviewGg) {

        try {
                /*新上的广告1*/
            if (adsBeen.size() > 0) {    // 底部广告
                gridviewGg.setAdapter(new GridVImgsAdapter(activity, adsBeen));
                gridviewGg.setVisibility(View.VISIBLE);
                gridviewGg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        GGAds gg_bean = (GGAds) parent.getItemAtPosition(position);
                        if (gg_bean != null) {

                            if (!TextUtils.isEmpty(gg_bean.url)) {
    //                            if (gg_bean.is_jump == 1) {  // 跳微吧
    //                                Intent intent;
    //                                if (TextUtils.isEmpty(MyApplication.getInstance().getMykey())) {
    //                                    intent = new Intent(activity, WXEntryActivity.class);
    //                                    intent.putExtra("isthome", true);
    //                                } else {
    //                                    intent = new Intent(activity, ActivityWeibaCommon.class);
    //                                    intent.putExtra("name", "全部微吧");
    //                                    intent.putExtra("type", ActivityWeibaCommon.FRAGMENT_WEIBA_ALL);
    //                                }
    //                                activity.startActivity(intent);
    //                            } else {
                                try {
                                    Intent intent = new Intent(activity, GuanggaoActivity.class);
                                    intent.putExtra("url", gg_bean.url);
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //                            }
                            }
                        }
                    }
                });
            } else {
                gridviewGg.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class HotNewsAdapter extends BaseAdapter {
        private List<NewsBean_N> list;
        Context context;

        public HotNewsAdapter(Context context, List<NewsBean_N> list) {
            super();
            this.context = context;
            this.list = list;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            NewsBean_N newsBean = (NewsBean_N) getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(activity).inflate(R.layout.item_new_local_news, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                if (newsBean != null) {
                    if (!TextUtils.isEmpty(newsBean.source)) {
                        holder.tvFrom.setText("来源：" + newsBean.source);
                        holder.tvFrom.setVisibility(View.VISIBLE);
                    } else {
                        holder.tvFrom.setVisibility(View.GONE);
                    }

                    if (!TextUtils.isEmpty(newsBean.timeStr)) {
                        holder.tvTime.setText(newsBean.timeStr);
                    } else {
                        if (newsBean.time < 2 * 1000000000) {
                            holder.tvTime.setText(Util.getMonthDay(newsBean.time * 1000));
                        } else {
                            holder.tvTime.setText(Util.getMonthDay(newsBean.time));
                        }
                    }

                    if (!TextUtils.isEmpty(newsBean.purl)) {
                        MyApplication.imageLoader.displayImage(MyApplication.encodeUrl(newsBean.purl), holder.imgLogo, MyApplication.getListOptions());
                        holder.imgLogo.setVisibility(View.VISIBLE);
                    }else {
                        holder.imgLogo.setVisibility(View.GONE);
                    }
                    if (!TextUtils.isEmpty(newsBean.title)) {
                        holder.tvTitle.setText(newsBean.title);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }


        class ViewHolder {
            @InjectView(R.id.img_logo)
            ImageView imgLogo;
            @InjectView(R.id.tv_title)
            TextView tvTitle;
            @InjectView(R.id.tv_from)
            TextView tvFrom;
            @InjectView(tv_time)
            TextView tvTime;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
//                imgLogo.setLayoutParams(new RelativeLayout.LayoutParams(MyApplication.getWidth()/3,(MyApplication.getWidth()/3*202/270)-50));
            }
        }
    }

    public class MyAdapter extends BaseAdapter {
        private static final String TAG = "MyAdapter";
        private List<NewLocal.MsgBean.MenuBean.ChildBean> list;

        public MyAdapter(List<NewLocal.MsgBean.MenuBean.ChildBean> list) {
            super();
            this.list = list;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            NewLocal.MsgBean.MenuBean.ChildBean childBean = (NewLocal.MsgBean.MenuBean.ChildBean) getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(activity).inflate(R.layout.item_newlocal_text, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                if (!TextUtils.isEmpty(childBean.menuName)) {
                    holder.tvName.setText(childBean.menuName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }


        class ViewHolder {
            @InjectView(R.id.tv_name)
            TextView tvName;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }

    /****
     * 获取设置的列表
     */
    private void settingPop() {

        View itemView = View.inflate(activity, R.layout.pop_newlocal_setting, null);
        LinearLayout line_tongxunlu = (LinearLayout) itemView.findViewById(R.id.line_tongxunlu);
        LinearLayout line_setting = (LinearLayout) itemView.findViewById(R.id.line_setting);
        LinearLayout line_fb = (LinearLayout) itemView.findViewById(R.id.line_fb);

        final PopupWindow pop = new PopupWindow(activity);
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
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 0.5f;
        activity.getWindow().setAttributes(params);
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
                WindowManager.LayoutParams params = activity.getWindow()
                        .getAttributes();
                params.alpha = 1f;
                activity.getWindow().setAttributes(params);
            }
        });
        pop.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        pop.showAsDropDown(linearSetting);

        line_fb.setOnClickListener(new View.OnClickListener() {  // 我的发布
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(cityId)) {
                    showToast("换个城市试试！");
                    pop.dismiss();
                    return;
                }
                Intent intent = null;
                if (MyApplication.getInstance().self != null) {
                    intent = new Intent(activity, MyPublishActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(activity, WXEntryActivity.class);
                    startActivity(intent);
                }
                pop.dismiss();
            }
        });
        line_setting.setOnClickListener(new View.OnClickListener() {  // 我的发布
            @Override
            public void onClick(View v) {  // 设置
                initCeSuPop();
                pop.dismiss();
            }
        });
//        line_tongxunlu.setOnClickListener(new View.OnClickListener() { //  我的消息
//            @Override
//            public void onClick(View v) { // 通讯录
//                if (TextUtils.isEmpty(cityId)) {
//                    showToast("通讯录暂支持国外！");
//                    pop.dismiss();
//                    return;
//
//                }
//                Intent intent;
//                if (TextUtils.isEmpty(MyApplication.getInstance().getMykey())) {
//                    intent = new Intent(activity, WXEntryActivity.class);
//                    intent.putExtra("isthome", true);
//                } else {
//                    intent = new Intent(activity, HireAndFindActivity.class);  // 通讯录
//                    intent.putExtra("title", "通讯录");
//                    intent.putExtra("cityId", cityId);
//                    intent.putExtra("menuId", "80");  // 通讯录的menuID 80
//                }
//                startActivity(intent);
//                pop.dismiss();
//            }
//        });

    }

    private final List<CeSu> ceSuList = new ArrayList<>();

    private void initWanSu() {
        try {
            ceSuList.clear();
//        http://www.huaqiaobang.com/mobile/index.php?act=test_cy&op=find_server
            HttpRequest.sendGet(TLUrl.getInstance().URL_hwg_base + "/mobile/index.php", "act=test_cy&op=find_server", new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            Log.i("cesu", msg + "");
                            if (!TextUtils.isEmpty(msg)) {

                                try {
                                    JSONArray jsonArray = new JSONArray(msg);
                                    Log.i("cesujsonArray", jsonArray.length() + "");
                                    if (jsonArray != null && jsonArray.length() > 0) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            final JSONObject object1 = jsonArray.getJSONObject(i);
                                            final CeSu ceSu = new CeSu();
                                            Log.i("cesuname", object1.optString("server_name") + "");
                                            Log.i("cesuurl", object1.optString("server_url") + "");

                                            ceSu.setId(object1.optString("id"));
                                            ceSu.setServerName(object1.optString("server_name"));
                                            ceSu.setServerUrl(object1.optString("server_url"));

                                            ceSuList.add(ceSu);
                                        }
                                        MyApplication.getInstance().setCeSuList(ceSuList);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCeSuPop() {

        try {
            final View root_view = View.inflate(activity, R.layout.item_travel_cesu, null);
            final TextView tv_compelete = (TextView) root_view.findViewById(R.id.tv_compelete);
            final ListView listview_cesu = (ListView) root_view.findViewById(R.id.listview_cesu);
            final RelativeLayout close = (RelativeLayout) root_view.findViewById(R.id.tyt);

            final List<CeSu> data_cesu = MyApplication.getInstance().getCeSuList();

            if (data_cesu != null) {

                if (data_cesu.size() > 0) {
                    final List<CeSu> data_ce = new ArrayList<>();
                    data_ce.clear();
                    final CeSuAdapter adapter = new CeSuAdapter(activity);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < data_cesu.size(); i++) {
                                CeSu da = new CeSu();
                                da.setServerName(data_cesu.get(i).getServerName());
                                da.setServerUrl(data_cesu.get(i).getServerUrl());
                                String to_time = getCurrentW(data_cesu.get(i).getServerUrl(), System.currentTimeMillis());
                                Log.i("zds", "to_time==" + to_time);
                                if (!TextUtils.isEmpty(to_time)) {
                                    da.setCurrWanSu(to_time + "  ms");
                                } else {
                                    da.setCurrWanSu("未知");
                                }
                                data_ce.add(da);
                            }

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_compelete.setText("测速完成，你可以...");
                                    adapter.addDatas(data_ce);
                                    listview_cesu.setAdapter(adapter);


                                    final PopupWindow popupWindow = new PopupWindow(root_view, Util.WIDTH * 4 / 5, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                                    WindowManager.LayoutParams params = activity.getWindow().getAttributes();
                                    params.alpha = 0.5f;
                                    activity.getWindow().setAttributes(params);
                                    popupWindow.setTouchable(true);
                                    popupWindow.setOutsideTouchable(true);
                                    popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            return false;
                                        }
                                    });
                                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

                                        @Override
                                        public void onDismiss() {
                                            WindowManager.LayoutParams params = activity.getWindow()
                                                    .getAttributes();
                                            params.alpha = 1f;
                                            activity.getWindow().setAttributes(params);
                                        }
                                    });
                                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ffffff")));

                                    Log.i("zds", "run: position" + MyApplication.getPosition());

                                    adapter.setSelectedPosition(MyApplication.getPosition());
                                    adapter.notifyDataSetChanged();
                                    popupWindow.showAtLocation(root_view, Gravity.CENTER, 0, 0);

                                    listview_cesu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Log.i("zds", "onItemClick: cesu_postion========" + i);
                                            CeSu dabe = (CeSu) adapterView.getItemAtPosition(i);
                                            ChangeNetUrl(dabe.getServerUrl());
                                            MyApplication.savePosition(i);
                                            popupWindow.dismiss();

                                        }
                                    });

                                    close.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            popupWindow.dismiss();
                                        }
                                    });


                                }
                            });
                        }
                    }).start();
                } else {
                    showToast("抱歉，测速失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class HotPagerAdapter extends BaseAdapter {
        private List<NewsBean_N> list;
        Context context;

        public HotPagerAdapter(Context context, List<NewsBean_N> list) {
            super();
            this.context = context;
            this.list = list;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            NewsBean_N newsBean = (NewsBean_N) getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(activity).inflate(R.layout.item_new_local_news, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                if (newsBean != null) {
                    if (!TextUtils.isEmpty(newsBean.source)) {
                        holder.tvFrom.setText("来源：" + newsBean.source);
                        holder.tvFrom.setVisibility(View.VISIBLE);
                    } else {
                        holder.tvFrom.setVisibility(View.GONE);
                    }

                    if (!TextUtils.isEmpty(newsBean.timeStr)) {
                        holder.tvTime.setText(newsBean.timeStr);
                    } else {
                        if (newsBean.time < 2 * 1000000000) {
                            holder.tvTime.setText(Util.getMonthDay(newsBean.time * 1000));
                        } else {
                            holder.tvTime.setText(Util.getMonthDay(newsBean.time));
                        }
                    }

                    if (!TextUtils.isEmpty(newsBean.purl)) {
                        MyApplication.imageLoader.displayImage(newsBean.purl, holder.imgLogo, MyApplication.getCircleFiveImageOptions());
                        holder.imgLogo.setVisibility(View.VISIBLE);
                    }else {
                        holder.imgLogo.setVisibility(View.GONE);
                    }
                    if (!TextUtils.isEmpty(newsBean.title)) {
                        holder.tvTitle.setText(newsBean.title);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }


        class ViewHolder {
            @InjectView(R.id.img_logo)
            ImageView imgLogo;
            @InjectView(R.id.tv_title)
            TextView tvTitle;
            @InjectView(R.id.tv_from)
            TextView tvFrom;
            @InjectView(tv_time)
            TextView tvTime;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
//                imgLogo.setLayoutParams(new RelativeLayout.LayoutParams(MyApplication.getWidth()/3,(MyApplication.getWidth()/3*202/270)-50));
            }
        }

    }
    
    /****
     * 改变服务器
     **/
    private void ChangeNetUrl(String url_base) {
        try {
            String basU_hua = url_base.substring(url_base.lastIndexOf("/") + 1, url_base.length());

            Log.i("zdstra", "basU_base==" + url_base);
            Log.i("zdstra", "basU_hua==" + basU_hua);
            TLUrl.URL_BASE = url_base;
            TLUrl.URL_huayouhui = basU_hua;
            TLUrl.getInstance().isChange = true;

            Log.i("zdstra", "basU_base2==" + TLUrl.getInstance().getUrl());
            Log.i("zdstra", "basU_hua2==" + TLUrl.getInstance().getHuaUrl());
            MyApplication.saveCurrentHost(url_base);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCurrentW(String uRLss, long start) {

//        InputStream is=null;
        try {
            URL url = new URL(uRLss);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");// 设置请求的方式
            urlConnection.setReadTimeout(5000);// 设置超时的时间
            urlConnection.setConnectTimeout(5000);// 设置链接超时的时间
            urlConnection.setDoInput(true);
            urlConnection.connect();
//            // 设置请求的头
//            urlConnection
//                    .setRequestProperty("User-Agent",
//                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            // 获取响应的状态码 404 200 505 302

            Log.i("zds", "start1=" + start);

            if (urlConnection.getResponseCode() == 200) {
//                // 获取响应的输入流对象
//                is = urlConnection.getInputStream();
//                // 释放资源
//                is.close();
                Log.i("zds", "start2=" + start);
                Log.i("zds", "end=" + System.currentTimeMillis());

                long to = System.currentTimeMillis() - start;

                Log.i("zds", "to=" + to);

                return to + "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (hh_banners != null) {
            hh_banners.clearImageTimerTask();
        }
        ButterKnife.reset(this);
    }

    @OnClick({R.id.relat_huilv, R.id.t_select, R.id.relative_no_city, R.id.linear_change, linear_setting, R.id.linear_change_china, R.id.relative_search_china, R.id.lin_setting_china, R.id.rela_ss})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relat_huilv: // 汇率换算
                startActivity(new Intent(activity, HuiLvActivity.class));
                break;
            case R.id.t_select: // 重新加载网络
            case R.id.relative_no_city: // 重新加载网络
                initMyData();
                break;
            case R.id.linear_change:
            case R.id.linear_change_china:
                Intent intent = new Intent(activity, CountryCityActivity.class);
                startActivityForResult(intent, 1);
                break;
            case linear_setting:
            case R.id.lin_setting_china:
                settingPop();
                break;
            case R.id.relative_search_china: // 搜索吃玩
            case R.id.rela_ss: // 搜索吃玩
                Intent d = new Intent(activity, SeachYouJiByTagActivity.class);
                d.putExtra("cityId", cityId);
                d.putExtra("type", "ismeishi");
                startActivity(d);
                break;
        }
    }
}
