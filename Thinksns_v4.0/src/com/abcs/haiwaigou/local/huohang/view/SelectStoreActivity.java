package com.abcs.haiwaigou.local.huohang.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
import com.abcs.haiwaigou.local.huohang.bean.DatasEntry;
import com.abcs.haiwaigou.local.huohang.bean.HotGoodsEntry;
import com.abcs.haiwaigou.local.huohang.bean.SelectStore;
import com.abcs.haiwaigou.utils.ACache;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.huaqiaobang.ytbt.common.utils.ToastUtil;
import com.abcs.sociax.android.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SelectStoreActivity extends BaseActivity {

    @InjectView(R.id.tv_titile)
    TextView tvTitile;
    @InjectView(R.id.line)
    View line;
    @InjectView(R.id.grid_view)
    com.abcs.haiwaigou.view.MyGridView gridView;
    @InjectView(R.id.img_logo_erweima)
    ImageView imgLogoErweima;
    @InjectView(R.id.tv_logo_time1)
    TextView tvLogoTime1;
    @InjectView(R.id.tv_logo_time)
    TextView tvLogoTime;
    @InjectView(R.id.tv_logo_time2)
    TextView tvLogoTime2;
    @InjectView(R.id.relat_logo_close)
    RelativeLayout relatLogoClose;
    @InjectView(R.id.rela_mian)
    RelativeLayout relaMian;
    @InjectView(R.id.relative_select_store)
    RelativeLayout relativeSelectStore;
    @InjectView(R.id.tv_change_contry)
    TextView tv_change_contry;

    ACache acach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_store);
        ButterKnife.inject(this);
        acach=ACache.get(this);
        if(TextUtils.isEmpty(cityId)){
            Intent intent = new Intent(SelectStoreActivity.this, CountryCityActivity.class);
            intent.putExtra("isfromhh",true);
            startActivityForResult(intent, 1);
        }else {
            if(!TextUtils.isEmpty(acach.getAsString(MyApplication.HUOHANG_STORELIST))){
                parseDatas(acach.getAsString(MyApplication.HUOHANG_STORELIST));
            }else {
                initAllDates();
            }
        }


        tv_change_contry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectStoreActivity.this, CountryCityActivity.class);
                intent.putExtra("isfromhh",true);
                startActivityForResult(intent, 1);
            }
        });
    }

    GridAdapter adapter;
    Handler handler=new Handler();

    private void initAllDates() {
        ProgressDlgUtil.showProgressDlg("Loading...", this);
//       http://huohang.huaqiaobang.com/mobile/index.php?act=native&op=get_native_store_list&key=a7954eaa08758d90c9b6de16beb63e17&city_id=41
        HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=native&op=get_native_store_list&key=" + MyApplication.getInstance().getMykey()+"&city_id="+cityId, new HttpRevMsg() {
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
                            acach.remove(MyApplication.HUOHANG_STORELIST);
                            acach.put(MyApplication.HUOHANG_STORELIST,msg);
                            ProgressDlgUtil.stopProgressDlg();
                        }
                    }
                });
            }
        });
    }

    public String goodsId = "";

    /**
     * 检查是否选店成功
     */
    private void checkIsSuccess(final DatasEntry bean, final String goods_Id) {
        ProgressDlgUtil.showProgressDlg("Loading...", this);
//        http://www.huaqiaobang.com/mobile/index.php?act=native&op=is_empty_store&store_id=17&key=fff1e9593f844fd5e1d14074cb3adbc9
        HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=native&op=is_empty_store&key=" + MyApplication.getInstance().getMykey()+"&store_id="+bean.storeId, new HttpRevMsg() {
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
                                JSONObject object=new JSONObject(msg);
                                if(object!=null){
                                    if(object.optInt("state")==1){  // 选店成功

                                        Log.i("zds", "run: 选店success");
                                        Intent intent=new Intent(SelectStoreActivity.this,NewHuoHangActivity.class);
                                        intent.putExtra("datas",bean);
                                        startActivity(intent);
                                        overridePendingTransition(0, 0);
                                        finish();
                                    }else {
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
        SelectStore bean = new Gson().fromJson(msg, SelectStore.class);

        if (bean != null) {
            if (bean.state == 1) {
                if (bean.datas != null && bean.datas.size() > 0) {
                    adapter = new GridAdapter(this, bean.datas);
                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            DatasEntry bean = (DatasEntry) parent.getItemAtPosition(position);
                            if (bean != null) {
                                checkIsSuccess(bean,"");
                                MyApplication.saveHHStoreCoin(bean.storeCoin);
                            }
                        }
                    });
                }

                if(bean.adv!=null&&!TextUtils.isEmpty(bean.adv.advImg)){
                    MyApplication.imageLoader.displayImage(bean.adv.advImg,imgLogoErweima,MyApplication.getListOptions());
                    relaMian.setVisibility(View.VISIBLE);
                }
                if(bean.adv!=null&&!TextUtils.isEmpty(bean.adv.advParam)){
                    tvLogoTime.setText(bean.adv.advParam);
                }
            } else {
                ToastUtil.showMessage("抱歉，该城市暂无店铺！");
                gridView.setAdapter(null);
                relaMian.setVisibility(View.INVISIBLE);
            }
        }
    }

    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private Context mContext;
        private List<DatasEntry> mCities = new ArrayList<>();

        public GridAdapter(Context context, List<DatasEntry> mCities) {
            this.mContext = context;
            this.mCities = mCities;
            this.inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mCities.size();
        }

        @Override
        public DatasEntry getItem(int position) {
            return mCities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CityGridViewHolder cityGridViewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_huohang_select_store, parent, false);
                cityGridViewHolder = new CityGridViewHolder(convertView);
                convertView.setTag(cityGridViewHolder);
            } else {
                cityGridViewHolder = (CityGridViewHolder) convertView.getTag();
            }

            final DatasEntry citysBean = getItem(position);
            if (citysBean != null) {

                if (!TextUtils.isEmpty(citysBean.storeImg)) {
                    MyApplication.imageLoader.displayImage(citysBean.storeImg + "", cityGridViewHolder.circ_iv, MyApplication.getCircleFiveImageOptions());
                }

                if (citysBean.hotGoods != null) {

                    if(citysBean.hotGoods.size() > 0){
                        cityGridViewHolder.liner_goods.removeAllViews();
                        for (int i = 0; i < citysBean.hotGoods.size(); i++) {
                            final HotGoodsEntry hotGoodsEntry = citysBean.hotGoods.get(i);
                            View root_view = View.inflate(mContext, R.layout.item_huohang_goods, null);
                            TextView tv_goods_price = (TextView) root_view.findViewById(R.id.tv_goods_price);
                            TextView tv_goods_price_zhe = (TextView) root_view.findViewById(R.id.tv_goods_price_zhe);
                            TextView tv_des = (TextView) root_view.findViewById(R.id.tv_des);
                            ImageView iv_goods_logo = (ImageView) root_view.findViewById(R.id.iv_goods_logo);

                            if (hotGoodsEntry != null) {
                                if (!TextUtils.isEmpty(hotGoodsEntry.goodsImage)) {
                                    MyApplication.imageLoader.displayImage(hotGoodsEntry.goodsImage, iv_goods_logo, MyApplication.getListOptions());
                                }else {
                                    MyApplication.imageLoader.displayImage("", iv_goods_logo, MyApplication.getListOptions());
                                }
                                if (!TextUtils.isEmpty(hotGoodsEntry.goodsPrice)) {
                                    if(!TextUtils.isEmpty(citysBean.storeCoin)){
                                        tv_goods_price.setText(citysBean.storeCoin + hotGoodsEntry.goodsPrice);
                                    }else {
                                        tv_goods_price.setText("€" + hotGoodsEntry.goodsPrice);
                                    }

//                                SpannableString spanString = new SpannableString(tv_goods_price.getText().toString().trim());
//                                AbsoluteSizeSpan span = new AbsoluteSizeSpan(14);
//                                spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                tv_goods_price.append(spanString);
                                }
                                if (!TextUtils.isEmpty(hotGoodsEntry.originalPrice)) {
                                    if(!TextUtils.isEmpty(citysBean.storeCoin)){
                                        tv_goods_price_zhe.setText(citysBean.storeCoin + hotGoodsEntry.originalPrice);
                                    }else {
                                        tv_goods_price_zhe.setText("€" + hotGoodsEntry.originalPrice);
                                    }

                                    tv_goods_price_zhe.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 删除线
                                }
                                if (!TextUtils.isEmpty(hotGoodsEntry.goodsName)) {
                                    tv_des.setText(hotGoodsEntry.goodsName);
                                }

                                root_view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (citysBean != null) {
                                            checkIsSuccess(citysBean,hotGoodsEntry.goodsId);
                                            MyApplication.saveHHStoreCoin(citysBean.storeCoin);
                                        }
                                    }
                                });
                            }

                            cityGridViewHolder.liner_goods.addView(root_view);
                        }
                        cityGridViewHolder.liner_goods.setVisibility(View.VISIBLE);
                    }else {
                        cityGridViewHolder.liner_goods.setVisibility(View.GONE);
                    }
                }
            }

            return convertView;
        }

        public class CityGridViewHolder {
            ImageView circ_iv;
            LinearLayout liner_goods;
            LinearLayout liner_mian;

            public CityGridViewHolder(View convertView) {
                circ_iv = (ImageView) convertView.findViewById(R.id.iv_logo);
                liner_goods = (LinearLayout) convertView.findViewById(R.id.liner_goods);
                liner_mian = (LinearLayout) convertView.findViewById(R.id.liner_mian);

            }
        }
    }

    private String cityId;
    private boolean isChina;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1 && data != null) {
            cityId = data.getStringExtra("cityId");
            isChina = data.getBooleanExtra("isChina", false);
            Log.i("zds1", "onActivityResult: " + cityId);
            if (!TextUtils.isEmpty(cityId)) {
                initAllDates();
            } else {
                ToastUtil.showMessage("请重新选择城市");
            }
        }
    }

}
