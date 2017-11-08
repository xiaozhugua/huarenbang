package com.abcs.haiwaigou.local.huohang.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.local.activity.LoacalKeFuActivity_HH;
import com.abcs.haiwaigou.local.huohang.bean.NewHHCity;
import com.abcs.haiwaigou.utils.ACache;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.huaqiaobang.model.Options;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.sociax.android.R;
import com.abcs.sociax.t4.android.img.RoundImageView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SelectedHHStoreActivity extends BaseActivity {

    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.relative_ruzhu)
    RelativeLayout relative_ruzhu;
    @InjectView(R.id.t_title_name)
    TextView tTitleName;
    @InjectView(R.id.listview)
    GridView listview;

    ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_hhstore);
        ButterKnife.inject(this);

        aCache = ACache.get(this);

        tTitleName.setText("选择城市");
        if(!TextUtils.isEmpty(aCache.getAsString("huohangxinban"))){
            parseMsg(aCache.getAsString("huohangxinban"));
        }else {
            lazyLoad2();
        }

        relative_ruzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelectedHHStoreActivity.this, LoacalKeFuActivity_HH.class);
                intent.putExtra("nostore",false);
                startActivity(intent);

//                startActivity(new Intent(SelectedHHStoreActivity.this, LoacalKeFuActivity_HH.class));
//                if(MyApplication.getInstance().getMykey()==null){
//                    Intent intent = new Intent(SelectedHHStoreActivity.this, WXEntryActivity.class);
//                    intent.putExtra("isthome", true);
//                    startActivity(intent);
//                }else {
//
//                }
            }
        });
        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    Handler handler = new Handler();

    protected void lazyLoad2() {
//        http://huohang.huaqiaobang.com/mobile/index.php?act=native_index&op=get_native_city
        ProgressDlgUtil.showProgressDlg("loading...", this);
        HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=native_index&op=get_native_city", new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(msg)) {
                            parseMsg(msg);
                            aCache.remove("huohangxinban");
                            aCache.put("huohangxinban", msg);
                        }

                        ProgressDlgUtil.stopProgressDlg();
                    }
                });
            }
        });

    }

    private void parseMsg(String msg) {
        try {
            NewHHCity city = new Gson().fromJson(msg, NewHHCity.class);
            if (city.state == 1) {
                if (city.datas != null) {
                    if (city.datas.size() > 0) {
                        listview.setAdapter(new GridViewAdapter(this,city.datas));
                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                NewHHCity.DatasBean datasBean=(NewHHCity.DatasBean)parent.getItemAtPosition(position);
                                if(datasBean!=null){
                                    Intent intent = new Intent();
                                    intent.putExtra("cityName", datasBean.cateName);
                                    intent.putExtra("countryName", datasBean.countryCnName);
                                    intent.putExtra("cityId", datasBean.cityId + "");
                                    setResult(1, intent);
                                    finish();
    //                                Util.preference.edit().putString(MyString.LOCAL_CITY_NAME, citysBean.cateName).commit();
    //                                Util.preference.edit().putString(MyString.LOCAL_CITY_ID, citysBean.cityId+"").commit();
    //                                Util.preference.edit().putString(MyString.LOCAL_COUNTRY_NAME, citysBean.country_name).commit();
                                }
                            }
                        });
                    } else {
                    }
                }
            } else {

            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    public class GridViewAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private Context mContext;
        private List<NewHHCity.DatasBean> mCities;

        public GridViewAdapter(Context context, List<NewHHCity.DatasBean> mCities) {
            this.mContext = context;
            this.mCities = mCities;
            this.inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mCities.size();
        }

        @Override
        public NewHHCity.DatasBean getItem(int position) {
            return mCities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder cityGridViewHolder = null;
            NewHHCity.DatasBean datasBean=getItem(position);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_hh_selectcity, parent, false);
                cityGridViewHolder = new ViewHolder(convertView);
                convertView.setTag(cityGridViewHolder);
            } else {
                cityGridViewHolder = (ViewHolder) convertView.getTag();
            }


            if(datasBean!=null){
                if(!TextUtils.isEmpty(datasBean.cateName)){
                    cityGridViewHolder.tvCity4.setText(datasBean.cateName);
                }

                if(!TextUtils.isEmpty(datasBean.countryCnName)){
                    cityGridViewHolder.itemCountryName.setText(datasBean.countryCnName);
                }
                if(!TextUtils.isEmpty(datasBean.catenameEn)){
                    cityGridViewHolder.tvEnglishCity4.setText(datasBean.catenameEn);
                }

                if (!TextUtils.isEmpty(datasBean.ossUrl)) {
                    MyApplication.imageLoader.displayImage(datasBean.ossUrl, cityGridViewHolder.civ, Options.getHDOptions());
                }
                if (!TextUtils.isEmpty(datasBean.nationalFlag)) {
                    MyApplication.imageLoader.displayImage(datasBean.nationalFlag, cityGridViewHolder.imageView, Options.getHDOptions());
                }
            }

            return convertView;
        }


         class ViewHolder {
            @InjectView(R.id.item_country_name)
            TextView itemCountryName;
            @InjectView(R.id.imageView)
            ImageView imageView;
            @InjectView(R.id.civ)
            RoundImageView civ;
            @InjectView(R.id.tv_city4)
            TextView tvCity4;
            @InjectView(R.id.tv_english_city4)
            TextView tvEnglishCity4;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }

}
