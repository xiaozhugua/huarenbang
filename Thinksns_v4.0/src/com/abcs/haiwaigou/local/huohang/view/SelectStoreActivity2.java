package com.abcs.haiwaigou.local.huohang.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.local.activity.CountryCityActivity;
import com.abcs.haiwaigou.local.huohang.bean.Acty;
import com.abcs.haiwaigou.local.huohang.bean.DatasEntrySer;
import com.abcs.haiwaigou.local.huohang.bean.HotGoodsEntry;
import com.abcs.haiwaigou.local.huohang.bean.SelectStoreNew;
import com.abcs.haiwaigou.utils.ACache;
import com.abcs.haiwaigou.utils.MyString;
import com.abcs.haiwaigou.utils.NumberUtils;
import com.abcs.haiwaigou.view.MyGridView;
import com.abcs.haiwaigou.view.zjzbanner.LMBanners;
import com.abcs.haiwaigou.view.zjzbanner.adapter.LBaseAdapter;
import com.abcs.haiwaigou.view.zjzbanner.transformer.TransitionEffect;
import com.abcs.haiwaigou.view.zjzbanner.utils.ScreenUtils;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.huaqiaobang.model.Options;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.huaqiaobang.ytbt.common.utils.ToastUtil;
import com.abcs.sociax.android.R;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*货行选店新版*/
public class SelectStoreActivity2 extends BaseActivity {

    ACache acach;

    @InjectView(R.id.tv_city_name)
    TextView tvCityName;
    @InjectView(R.id.liner_top)
    RelativeLayout linerTop;
    @InjectView(R.id.banners)
    LMBanners banners;
    @InjectView(R.id.my_listview)
    com.abcs.hqbtravel.wedgt.MyListView myListview;
    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.hh_emtry)
    RelativeLayout hh_emtry;  // 空页面
    @InjectView(R.id.liner_banner)
    LinearLayout liner_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huohang_select_store_new);
        ButterKnife.inject(this);
        acach = ACache.get(this);

        tvCityName.setFocusable(true);
        tvCityName.setFocusableInTouchMode(true);
        tvCityName.requestFocus();

        hh_emtry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectStoreActivity2.this, CountryCityActivity.class);
                intent.putExtra("isfromhh", true);
                startActivityForResult(intent, 1);
            }
        });

        if (TextUtils.isEmpty(Util.preference.getString(MyString.LOCAL_CITY_ID, ""))) {
            Intent intent = new Intent(SelectStoreActivity2.this, CountryCityActivity.class);
            intent.putExtra("isfromhh", true);
            startActivityForResult(intent, 1);
        } else {

            cityId=Util.preference.getString(MyString.LOCAL_CITY_ID, "");
            initCityName();

//            if (!TextUtils.isEmpty(acach.getAsString(MyApplication.HUOHANG_STORELIST))) {
//                parseDatas(acach.getAsString(MyApplication.HUOHANG_STORELIST));
//            } else {
//
//            }
            initAllDates();
        }


        linerTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectStoreActivity2.this, CountryCityActivity.class);
                intent.putExtra("isfromhh", true);
                startActivityForResult(intent, 1);
            }
        });

        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initCityName() {
        if(!TextUtils.isEmpty(Util.preference.getString(MyString.LOCAL_COUNTRY_NAME, ""))){
            tvCityName.setText(Util.preference.getString(MyString.LOCAL_COUNTRY_NAME, ""));
        }
        if(!TextUtils.isEmpty(Util.preference.getString(MyString.LOCAL_CITY_NAME, ""))){
            tvCityName.setText(Util.preference.getString(MyString.LOCAL_CITY_NAME, ""));
        }

        if(!TextUtils.isEmpty(Util.preference.getString(MyString.LOCAL_COUNTRY_NAME, ""))&&!TextUtils.isEmpty(Util.preference.getString(MyString.LOCAL_CITY_NAME, ""))){
            tvCityName.setText(Util.preference.getString(MyString.LOCAL_CITY_NAME, "")+"/"+Util.preference.getString(MyString.LOCAL_COUNTRY_NAME, ""));
        }
    }

    MyListViewAdapter adapter;
    Handler handler = new Handler();

    private void initAllDates() {
        ProgressDlgUtil.showProgressDlg("Loading...", this);
//       http://huohang.huaqiaobang.com/mobile/index.php?act=native&op=get_native_new_store_list&key=d70a3678dbdeca8fcc17e54b02383a5c&city_id=41
        HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=native&op=get_native_new_store_list&key=" + MyApplication.getInstance().getMykey() + "&city_id=" + cityId, new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {
                // TODO Auto-generated method stub
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(msg)) {
                            return;
                        } else {
                            parseDatas(msg);
//                            acach.remove(MyApplication.HUOHANG_STORELIST);
//                            acach.put(MyApplication.HUOHANG_STORELIST, msg);
                            ProgressDlgUtil.stopProgressDlg();
                        }
                    }
                });
            }
        });
    }

    private void initBanners(List<SelectStoreNew.goodsBanners> goodsBanner) {
      
        if (banners != null) {
            //设置Banners高度
            banners.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.dip2px(this, 250)));
            //本地用法
            banners.setAdapter(new UrlImgAdapter(this), goodsBanner);
            //参数设置
            banners.setAutoPlay(true);//自动播放
            banners.setVertical(false);//是否可以垂直
            banners.setScrollDurtion(500);//两页切换时间
            banners.setCanLoop(true);//循环播放
            banners.setSelectIndicatorRes(R.drawable.img_huohang_n_dian2);//选中的原点
            banners.setUnSelectUnIndicatorRes(R.drawable.img_huohang_n_dian1);//未选中的原点
//        mLBanners.setHoriZontalTransitionEffect(TransitionEffect.Default);//选中喜欢的样式
//        banners.setHoriZontalCustomTransformer(new ParallaxTransformer(R.id.id_image));//自定义样式
            banners.setHoriZontalTransitionEffect(TransitionEffect.Alpha);//Alpha
            banners.setDurtion(5000);//切换时间
            if (goodsBanner.size() == 1) {
                banners.hideIndicatorLayout();//隐藏原点
            } else {
                banners.showIndicatorLayout();//显示原点
            }
            banners.setIndicatorPosition(LMBanners.IndicaTorPosition.BOTTOM_MID);//设置原点显示位置
        }
    }

    class UrlImgAdapter implements LBaseAdapter<SelectStoreNew.goodsBanners> {
        private Context mContext;

        public UrlImgAdapter(Context context) {
            mContext = context;
        }

        @Override
        public View getView(final LMBanners lBanners, final Context context, final int position, final SelectStoreNew.goodsBanners data) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.banner_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.id_image);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            if(data!=null){
                if(!TextUtils.isEmpty(data.goodsBanner)){
                    ImageLoader.getInstance().displayImage(data.goodsBanner, imageView, Options.getHDOptions());
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (data != null) {
                                DatasEntrySer datasEntrySer=new DatasEntrySer();
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
                }
            }
            return view;
        }

    }

    public String goodsId = "";

    /**
     * 检查是否选店成功
     */
    private void checkIsSuccess(final DatasEntrySer bean, final String goods_Id) {
        ProgressDlgUtil.showProgressDlg("Loading...", this);
//        http://www.huaqiaobang.com/mobile/index.php?act=native&op=is_empty_store&store_id=17&key=fff1e9593f844fd5e1d14074cb3adbc9
        HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=native&op=is_empty_store&key=" + MyApplication.getInstance().getMykey() + "&store_id=" + bean.getStoreId(), new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {
                // TODO Auto-generated method stub
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(msg)) {
                            ToastUtil.showMessage("选店失败,请重试");
                        } else {

                            try {
                                JSONObject object = new JSONObject(msg);
                                if (object != null) {
                                    if (object.optInt("state") == 1) {  // 选店成功

                                        Log.i("zds", "run: 选店success");
                                        Intent intent = new Intent(SelectStoreActivity2.this, NewHuoHangActivity.class);
                                        intent.putExtra("datas", bean);
                                        intent.putExtra("goods_Id", goods_Id);
                                        startActivity(intent);
                                        overridePendingTransition(0, 0);
                                    } else {
                                        ToastUtil.showMessage("选店失败,请重试");
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        ProgressDlgUtil.stopProgressDlg();
                    }
                });
            }
        });
    }

    private void parseDatas(String msg) {
        SelectStoreNew bean = new Gson().fromJson(msg, SelectStoreNew.class);

        if (bean != null) {
            if (bean.state == 1) {
                if(bean.datas != null){

                    if (bean.datas.size() > 0) {

                        /*baner图*/
                        if(bean.goodsBanner!=null){
                            if(bean.goodsBanner.size()>0){
                                initBanners(bean.goodsBanner);
                                liner_banner.setVisibility(View.VISIBLE);
                            }else {
                                liner_banner.setVisibility(View.GONE);
                            }
                        }else {
                            liner_banner.setVisibility(View.GONE);
                        }

                        /*main*/
                        if(bean.datas!=null){
                            if(bean.datas.size()>0){
                                adapter = new MyListViewAdapter(this, bean.datas);
                                myListview.setAdapter(adapter);
                                hh_emtry.setVisibility(View.GONE);
                            }else {
                                hh_emtry.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                }
            } else {
                hh_emtry.setVisibility(View.VISIBLE);
                liner_banner.setVisibility(View.GONE);
                myListview.setAdapter(null);
            }
        }
    }

    /*选店主页面*/
    public class MyListViewAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private Context mContext;
        private List<SelectStoreNew.DatasBean> mCities = new ArrayList<>();

        public MyListViewAdapter(Context context, List<SelectStoreNew.DatasBean> mCities) {
            this.mContext = context;
            this.mCities = mCities;
            this.inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mCities.size();
        }

        @Override
        public SelectStoreNew.DatasBean getItem(int position) {
            return mCities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder cityGridViewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_huohang_select_store_new, parent, false);
                cityGridViewHolder = new ViewHolder(convertView);
                convertView.setTag(cityGridViewHolder);
            } else {
                cityGridViewHolder = (ViewHolder) convertView.getTag();
            }

            final SelectStoreNew.DatasBean citysBean = getItem(position);
            if (citysBean != null) {

                if (!TextUtils.isEmpty(citysBean.storeNewImg)) {
                    MyApplication.imageLoader.displayImage(citysBean.storeNewImg, cityGridViewHolder.imgLogo, MyApplication.getListOptions());
                } else {
                    MyApplication.imageLoader.displayImage("", cityGridViewHolder.imgLogo, MyApplication.getListOptions());
                }
                if (!TextUtils.isEmpty(citysBean.storeName)) {
                    cityGridViewHolder.tvStoreName.setText(citysBean.storeName);
                } else {
                    cityGridViewHolder.tvStoreName.setText("");
                }

                cityGridViewHolder.tv_store_goods_num.setText(citysBean.goodsCount + "件商品");
                cityGridViewHolder.tvStoreSaleNum.setText("月售" + citysBean.goodsSaleCount);

                                        /*活动信息*/
                if (citysBean.activity != null) {

                    if (citysBean.activity.size() > 0) {
                        cityGridViewHolder.tvHuodongNum.setText(citysBean.activity.size()+"个活动");
                        cityGridViewHolder.linerHuodongNum.setVisibility(View.VISIBLE);
                        cityGridViewHolder.linearActy.removeAllViews();
                        cityGridViewHolder.linerHuodongNum.setEnabled(true);
                        cityGridViewHolder.linerHuodongNum.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(citysBean.activity.size()>=3){
                                    for (int k = citysBean.activity.size()-1; k >1; k--) {

                                        Acty bean_hd = citysBean.activity.get(k);
                                        View view = View.inflate(mContext, R.layout.item_huohang_acty_new, null);

                                        ViewGroup parent3 = (ViewGroup) view.getParent();
                                        if (parent3 != null) {
                                            parent3.removeAllViews();
                                        }

                                        ImageView img = (ImageView) view.findViewById(R.id.img);
                                        TextView tv = (TextView) view.findViewById(R.id.tv);

                                        if (bean_hd != null) {
                                            if (!TextUtils.isEmpty(bean_hd.img)) {
//                http://www.huaqiaobang.com/data/upload/shop/store/goods/11/11_05375327290881075.jpg
                                                MyApplication.imageLoader.displayImage(bean_hd.img, img, MyApplication.options);
                                            }

                                            if (!TextUtils.isEmpty(bean_hd.name)) {
                                                tv.setText(bean_hd.name);
                                            }
                                        }

                                        cityGridViewHolder.linearActy.addView(view);
                                    }
                                }else {
                                    return;
                                }

                                cityGridViewHolder.linerHuodongNum.setEnabled(false);
                            }
                        });

                        if( citysBean.activity.size()<3){
                            for (int k = 0; k < citysBean.activity.size(); k++) {

                                Acty bean_hd = citysBean.activity.get(k);
                                View view = View.inflate(mContext, R.layout.item_huohang_acty_new, null);

                                ViewGroup parent3 = (ViewGroup) view.getParent();
                                if (parent3 != null) {
                                    parent3.removeAllViews();
                                }

                                ImageView img = (ImageView) view.findViewById(R.id.img);
                                TextView tv = (TextView) view.findViewById(R.id.tv);

                                if (bean_hd != null) {
                                    if (!TextUtils.isEmpty(bean_hd.img)) {
//                http://www.huaqiaobang.com/data/upload/shop/store/goods/11/11_05375327290881075.jpg
                                        MyApplication.imageLoader.displayImage(bean_hd.img, img, MyApplication.options);
                                    }

                                    if (!TextUtils.isEmpty(bean_hd.name)) {
                                        tv.setText(bean_hd.name);
                                    }
                                }

                                cityGridViewHolder.linearActy.addView(view);
                            }
                        }else {
                            for (int k = 0; k < 2; k++) {

                                Acty bean_hd = citysBean.activity.get(k);
                                View view = View.inflate(mContext, R.layout.item_huohang_acty_new, null);

                                ViewGroup parent3 = (ViewGroup) view.getParent();
                                if (parent3 != null) {
                                    parent3.removeAllViews();
                                }

                                ImageView img = (ImageView) view.findViewById(R.id.img);
                                TextView tv = (TextView) view.findViewById(R.id.tv);

                                if (bean_hd != null) {
                                    if (!TextUtils.isEmpty(bean_hd.img)) {
//                http://www.huaqiaobang.com/data/upload/shop/store/goods/11/11_05375327290881075.jpg
                                        MyApplication.imageLoader.displayImage(bean_hd.img, img, MyApplication.options);
                                    }

                                    if (!TextUtils.isEmpty(bean_hd.name)) {
                                        tv.setText(bean_hd.name);
                                    }
                                }

                                cityGridViewHolder.linearActy.addView(view);
                            }
                        }
                    } else {
                        cityGridViewHolder.tvHuodongNum.setText(citysBean.activity.size()+"0个活动");
                        cityGridViewHolder.linerHuodongNum.setVisibility(View.INVISIBLE);
                    }
                }

                cityGridViewHolder.relative_store.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("ccc", "onItemClick: ");
                        if (citysBean != null) {
                            DatasEntrySer datasEntrySer=new DatasEntrySer();
                            datasEntrySer.setActivity(citysBean.activity);
                            datasEntrySer.setStoreAddress(citysBean.storeAddress);
                            datasEntrySer.setStoreCoin(citysBean.storeCoin);
                            datasEntrySer.setStoreDes(citysBean.storeDes);
                            datasEntrySer.setStoreGoodsDetails(citysBean.storeGoodsDetails);
                            datasEntrySer.setStoreId(citysBean.storeId);
                            datasEntrySer.setStoreNewLogo(citysBean.storeNewLogo);
                            datasEntrySer.setStoreImg(citysBean.storeImg);
                            datasEntrySer.setStoreName(citysBean.storeName);
                            datasEntrySer.setStorePhone(citysBean.storePhone);
                            datasEntrySer.setStoreSendTime(citysBean.storeSendTime);
                            datasEntrySer.setNgc_id("");
                            datasEntrySer.setGoods_Id("");
                            checkIsSuccess(datasEntrySer, "");
                            MyApplication.saveHHStoreCoin(citysBean.storeCoin);
                        }

                    }
                });


                if (citysBean.hotGoods != null) {

                    if (citysBean.hotGoods.size() > 0) {
                        cityGridViewHolder.myGridview.setAdapter(new GridAdapter(mContext, citysBean.hotGoods));
                        cityGridViewHolder.myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                HotGoodsEntry hotGoodsEntry = (HotGoodsEntry) parent.getItemAtPosition(position);
                                if (hotGoodsEntry != null) {
                                    DatasEntrySer datasEntrySer=new DatasEntrySer();
                                    datasEntrySer.setActivity(citysBean.activity);
                                    datasEntrySer.setStoreAddress(citysBean.storeAddress);
                                    datasEntrySer.setStoreCoin(citysBean.storeCoin);
                                    datasEntrySer.setStoreDes(citysBean.storeDes);
                                    datasEntrySer.setStoreGoodsDetails(citysBean.storeGoodsDetails);
                                    datasEntrySer.setStoreId(citysBean.storeId);
                                    datasEntrySer.setStoreNewLogo(citysBean.storeNewLogo);
                                    datasEntrySer.setStoreImg(citysBean.storeImg);
                                    datasEntrySer.setStoreName(citysBean.storeName);
                                    datasEntrySer.setStorePhone(citysBean.storePhone);
                                    datasEntrySer.setStoreSendTime(citysBean.storeSendTime);
                                    datasEntrySer.setNgc_id(hotGoodsEntry.ngc_id);
                                    datasEntrySer.setGoods_Id(hotGoodsEntry.goodsId);
                                    checkIsSuccess(datasEntrySer,hotGoodsEntry.goodsId);
                                    MyApplication.saveHHStoreCoin(citysBean.storeCoin);
                                }
                            }
                        });
                    } else {
                        cityGridViewHolder.myGridview.setAdapter(null);
                    }
                }
            }

            return convertView;
        }


        class ViewHolder {
            @InjectView(R.id.img_logo)
            ImageView imgLogo;
            @InjectView(R.id.tv_store_name)
            TextView tvStoreName;
            @InjectView(R.id.tv_store_sale_num)
            TextView tvStoreSaleNum;
            @InjectView(R.id.tv_store_goods_num)
            TextView tv_store_goods_num;
            @InjectView(R.id.tv_store_peisong)
            TextView tvStorePeisong;
            @InjectView(R.id.linear_acty)
            LinearLayout linearActy;
            @InjectView(R.id.tv_huodong_num)
            TextView tvHuodongNum;
            @InjectView(R.id.liner_huodong_num)
            LinearLayout linerHuodongNum;
            @InjectView(R.id.relative_store)
            RelativeLayout relative_store;
            @InjectView(R.id.my_gridview)
            MyGridView myGridview;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }


    /*促销商品*/
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private Context mContext;
        private List<HotGoodsEntry> mCities = new ArrayList<>();

        public GridAdapter(Context context, List<HotGoodsEntry> mCities) {
            this.mContext = context;
            this.mCities = mCities;
            this.inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mCities.size();
        }

        @Override
        public HotGoodsEntry getItem(int position) {
            return mCities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder cityGridViewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_huohang_select_store_cuxiao, parent, false);
                cityGridViewHolder = new ViewHolder(convertView);
                convertView.setTag(cityGridViewHolder);
            } else {
                cityGridViewHolder = (ViewHolder) convertView.getTag();
            }

            final HotGoodsEntry citysBean = getItem(position);
            if (citysBean != null) {

                if (!TextUtils.isEmpty(citysBean.goodsImage)) {
                    MyApplication.imageLoader.displayImage(citysBean.goodsImage, cityGridViewHolder.imgLogo, MyApplication.getListOptions());
                } else {
                    MyApplication.imageLoader.displayImage("", cityGridViewHolder.imgLogo, MyApplication.getListOptions());
                }
                if (!TextUtils.isEmpty(citysBean.goodsName)) {
                    cityGridViewHolder.tvGoodsName.setText(citysBean.goodsName);
                } else {
                    cityGridViewHolder.tvGoodsName.setText("");
                }
                if (!TextUtils.isEmpty(citysBean.goodsPrice)) {
                    cityGridViewHolder.tvGoodsPriceZhe.setText(NumberUtils.formatPriceOuYuan(Double.valueOf(citysBean.goodsPrice)));
                } else {
                    cityGridViewHolder.tvGoodsPriceZhe.setText("");
                }
                if (!TextUtils.isEmpty(citysBean.originalPrice)) {
                    cityGridViewHolder.tvGoodsPrice.setText(NumberUtils.formatPriceOuYuan(Double.valueOf(citysBean.originalPrice)));
                } else {
                    cityGridViewHolder.tvGoodsPrice.setText("");
                }

                if(!TextUtils.isEmpty(citysBean.goods_type)&&citysBean.goods_type.equals("2")){
                    cityGridViewHolder.imgLogoTag.setVisibility(View.VISIBLE);
                    cityGridViewHolder.tv_tag.setVisibility(View.INVISIBLE);
                }else if(!TextUtils.isEmpty(citysBean.goods_type)&&citysBean.goods_type.equals("3")) {
                    cityGridViewHolder.imgLogoTag.setVisibility(View.INVISIBLE);
                    cityGridViewHolder.tv_tag.setVisibility(View.VISIBLE);
                }else {
                    cityGridViewHolder.imgLogoTag.setVisibility(View.INVISIBLE);
                    cityGridViewHolder.tv_tag.setVisibility(View.INVISIBLE);
                }
            }

            return convertView;
        }


        class ViewHolder {
            @InjectView(R.id.img_logo_tag)
            ImageView imgLogoTag;
            @InjectView(R.id.tv_tag)
            TextView tv_tag;
            @InjectView(R.id.img_logo)
            ImageView imgLogo;
            @InjectView(R.id.relative_logo)
            RelativeLayout relativeLogo;
            @InjectView(R.id.tv_goods_name)
            com.abcs.haiwaigou.view.AlignTextView tvGoodsName;
            @InjectView(R.id.tv_goods_price_zhe)
            TextView tvGoodsPriceZhe;
            @InjectView(R.id.tv_goods_price)
            TextView tvGoodsPrice;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }

    private String cityId;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1 && data != null) {
            cityId = data.getStringExtra("cityId");
            Log.i("zds1", "onActivityResult: " + cityId);
            initCityName();

            if (!TextUtils.isEmpty(cityId)) {
                initAllDates();
                hh_emtry.setVisibility(View.GONE);
            } else {
                hh_emtry.setVisibility(View.VISIBLE);
            }
        }
    }

}
