package com.abcs.haiwaigou.local.huohang.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.broadcast.MyUpdateUI;
import com.abcs.haiwaigou.local.beans.HuoHangAddressList;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.huaqiaobang.tljr.zrclistview.ZrcListView;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.sociax.android.R;
import com.nineoldandroids.animation.ObjectAnimator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.abcs.sociax.android.R.id.t_ok;

public class HuoHangEditAddressActivity extends BaseActivity implements View.OnClickListener {


    public Handler handler = new Handler();
    @InjectView(R.id.tljr_txt_news_title)
    TextView tljrTxtNewsTitle;
    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(t_ok)
    TextView tOk;
    @InjectView(R.id.tljr_grp_goods_title)
    RelativeLayout tljrGrpGoodsTitle;
    @InjectView(R.id.ed_phone)
    EditText edPhone;
    @InjectView(R.id.ed_store_name)
    EditText edStoreName;
    @InjectView(R.id.ed_address)
    EditText edAddress;
    @InjectView(R.id.ed_youbian)
    EditText edYoubian;
    @InjectView(R.id.ed_phone_beiyong)
    EditText edPhoneBeiyong;
    @InjectView(R.id.ed_my_phone)
    EditText edMyPhone;
    @InjectView(R.id.ed_my_phone_beiyong)
    EditText edMyPhoneBeiyong;
    @InjectView(R.id.btn_isdefault)
    TextView btnIsdefault;
    @InjectView(R.id.tv_xinxin)
    TextView tvXinxin;
    @InjectView(R.id.tv_jialing)
    TextView tvJialing;

    String param;
    String isDefault = "0";
    private String area_info;

    String address_id, memberId, mobPhone, address, telPhone, trueName;

    boolean isAdd;
    @InjectView(R.id.t_province)
    TextView tProvince;
    @InjectView(R.id.img_province)
    ImageView imgProvince;
    @InjectView(R.id.relative_province)
    RelativeLayout relativeProvince;
    @InjectView(R.id.zlist_province)
    ZrcListView zlistProvince;
    @InjectView(R.id.t_city)
    TextView tCity;
    @InjectView(R.id.img_city)
    ImageView imgCity;
    @InjectView(R.id.relative_city)
    RelativeLayout relativeCity;
    @InjectView(R.id.zlist_city)
    ZrcListView zlistCity;

    private String countryId,cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_activity_edit_address);
        ButterKnife.inject(this);
        countryCityList.clear();
        tProvince.setOnClickListener(this);
        tCity.setOnClickListener(this);
        initCountry();
        isAdd = getIntent().getBooleanExtra("isAdd", false);
        address_id = getIntent().getStringExtra("address_id");
        try {
            int bstart = tvXinxin.getText().toString().trim().indexOf("*");
            int bend = bstart + "*".length();
            int fstart = tvJialing.getText().toString().trim().indexOf("‘0’");
            int fend = fstart + "‘0’".length();
            SpannableStringBuilder style = new SpannableStringBuilder(tvXinxin.getText().toString().trim());
            style.setSpan(new ForegroundColorSpan(Color.RED), bstart, bend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            SpannableStringBuilder style2 = new SpannableStringBuilder(tvJialing.getText().toString().trim());
            style2.setSpan(new ForegroundColorSpan(Color.RED), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            tvXinxin.setText(style);
            tvJialing.setText(style2);

            if (isAdd) {  // 添加新地址
                tOk.setText("添加");
            } else {
                tOk.setText("保存");
                trueName = getIntent().getStringExtra("trueName");
                memberId = getIntent().getStringExtra("memberId");
                telPhone = getIntent().getStringExtra("telPhone");
                mobPhone = getIntent().getStringExtra("mobPhone");
                address = getIntent().getStringExtra("address");
                postCode = getIntent().getStringExtra("postCode");
                isDefault = getIntent().getStringExtra("isDefault");

                area_info = getIntent().getStringExtra("area_info");
                countryId = getIntent().getStringExtra("countryId");

                if(!TextUtils.isEmpty(area_info)){
                    if (area_info.contains("\t")) {
                        strings = area_info.split("\t");
                    } else {
                        strings = area_info.split(" ");
                    }

                    if(!TextUtils.isEmpty(strings[0])){
                        if (tProvince != null)
                            tProvince.setText(strings[0]);

                        if(!TextUtils.isEmpty(countryId)){
                            initCity(countryId);
                        }

                    }
                    if(!TextUtils.isEmpty(strings[1])){
                        if (tCity != null)
                            tCity.setText(strings[1]);
                    }
                }

                if (!TextUtils.isEmpty(telPhone)) {
                    edPhone.setText(telPhone);
                }
                if (!TextUtils.isEmpty(trueName)) {
                    edStoreName.setText(trueName);
                }

                if (!TextUtils.isEmpty(mobPhone)) {
                    edMyPhone.setText(mobPhone);
                }

                if (!TextUtils.isEmpty(address)) {
                    edAddress.setText(address);
                }
                if (!TextUtils.isEmpty(postCode)) {
                    edYoubian.setText(postCode);
                }

                if (!TextUtils.isEmpty(isDefault) && isDefault.equals("1")) {  // 默认
                    btnIsdefault.setBackgroundResource(R.drawable.iv_sele_y);
                } else {
                    btnIsdefault.setBackgroundResource(R.drawable.iv_sele_l);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 修改地址
    private void save() {
        try {
            Log.i("zds", "address_id=" + address_id);
            param = "&key=" + MyApplication.getInstance().getMykey() + "&address_id=" + address_id +"&address="
                    + e_Address + "&is_default=" + isDefault + "&tel_phone=" +
                    phone + "&true_name=" + storeName+ "&country_id=" + countryId+ "&city_id=" + cityId+ "&country_name=" + countryName+ "&city_name=" + cityName+ "&postcode=" + postCode;

            Log.i("zds", "param=" + param);

            HttpRequest.sendPost("http://huohang.huaqiaobang.com/mobile/index.php?act=native&op=edit_native", param, new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(msg);
                                if (json != null && json.has("state")) {
                                    int state = json.getInt("state");
                                    Log.i("zds", "edit_msg=" + msg);
                                    if (state == 1) {
                                        showToast("修改成功！");
                                        MyUpdateUI.sendUpdateCollection(HuoHangEditAddressActivity.this, MyUpdateUI.ADDRESS);
                                        finish();
                                    } else {
                                        showToast("失败！");
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 添加地址
    private void addAddress() {
        try {
            param = "&key=" + MyApplication.getInstance().getMykey() + "&address="
                    + e_Address + "&is_default=" + isDefault + "&tel_phone=" +
                    phone + "&true_name=" + storeName+ "&country_id=" + countryId+ "&city_id=" + cityId+ "&country_name=" + countryName+ "&city_name=" + cityName+ "&postcode=" + postCode;

            Log.i("zds", "param=" + param);

            HttpRequest.sendPost("http://huohang.huaqiaobang.com/mobile/index.php?act=native&op=add_native_address", param, new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    // TODO Auto-generated method stub
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(msg);
                                if (json != null && json.has("state")) {
                                    int state = json.getInt("state");
                                    Log.i("zds", "edit_msg=" + msg);
                                    if (state == 1) {
                                        showToast("添加成功！");
                                        MyUpdateUI.sendUpdateCollection(HuoHangEditAddressActivity.this, MyUpdateUI.ADDRESS);
                                       /* Intent intent=new Intent(HuoHangEditAddressActivity.this, BenDiPeiSongActivity3.class);
                                        intent.putExtra("district_id","1");
                                        intent.putExtra("store_name","我的店");
                                        intent.putExtra("district_name","维也纳");
                                        startActivity(intent);*/
                                        finish();
                                    } else {
                                        showToast("失败！");
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean isProvince;
    boolean isCity;
    private int durationRotate = 200;// 旋转动画时间
    private int durationAlpha = 200;// 透明度动画时间


    @OnClick({R.id.relative_back, R.id.t_ok, R.id.btn_isdefault})
    public void onClick(View view) {

        InputMethodManager imm;
        switch (view.getId()) {
            case R.id.relative_back:
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                finish();
                break;
            case R.id.t_province:
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                isCity = false;
                isProvince = !isProvince;
                if (isProvince) {
                    ObjectAnimator.ofFloat(imgProvince, "rotation", 0, 180).setDuration(durationRotate).start();
                    zlistProvince.setVisibility(View.VISIBLE);
                    ObjectAnimator.ofFloat(zlistProvince, "alpha", 0, 1).setDuration(durationAlpha).start();
                } else {
                    ObjectAnimator.ofFloat(imgProvince, "rotation", 180, 360).setDuration(durationRotate).start();

                    zlistProvince.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            zlistProvince.setVisibility(View.GONE);
                        }
                    }, durationAlpha);
                }
                zlistCity.setVisibility(View.GONE);
                break;
            case R.id.t_city:

                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (tProvince.getText().equals("")) {
                    showToast("请先选择国家！");
                    return;
                }
                isProvince = false;
                isCity = !isCity;
                if (isCity) {
                    ObjectAnimator.ofFloat(imgCity, "rotation", 0, 180).setDuration(durationRotate).start();
                    zlistCity.setVisibility(View.VISIBLE);
                    ObjectAnimator.ofFloat(zlistCity, "alpha", 0, 1).setDuration(durationAlpha).start();
                } else {
                    ObjectAnimator.ofFloat(imgCity, "rotation", 180, 360).setDuration(durationRotate).start();
                    ObjectAnimator.ofFloat(zlistCity, "alpha", 1, 0).setDuration(durationAlpha).start();
                    zlistCity.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            zlistCity.setVisibility(View.GONE);
                        }
                    }, durationAlpha);
                }
                zlistProvince.setVisibility(View.GONE);
                break;
            case t_ok:
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                comfirm();
                break;
            case R.id.btn_isdefault:

                if (is_select) {
                    btnIsdefault.setBackgroundResource(R.drawable.iv_sele_y);
                    isDefault = "1"; // 1为设置为默认，0为取消默认
                    is_select = false;
                } else {
                    btnIsdefault.setBackgroundResource(R.drawable.iv_sele_l);
                    isDefault = "0"; // 1为设置为默认，0为取消默认
                    is_select = true;
                }
                break;

        }
    }

    ArrayList<HuoHangAddressList.DatasEntry> provinceList = new ArrayList<HuoHangAddressList.DatasEntry>();
    ArrayList<HuoHangAddressList.DatasEntry> cityList = new ArrayList<HuoHangAddressList.DatasEntry>();
    ProvinceAdapter provinceAdapter;
    CityAdapter cityAdapter;

    public void setListViewHeight(ZrcListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    List<String>  countryCityList=new ArrayList<>();
    String[] strings;
    private void initCountry() {
//        http://huohang.huaqiaobang.com/mobile/index.php?act=native_index&op=get_country
        HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=native_index&op=get_country", new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {
                // TODO Auto-generated method stub
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(msg);
                            if (json != null && json.has("state")) {
                                int code = json.getInt("state");
                                Log.i("zjz", msg);
                                if (code == 1) {
                                    JSONArray jsonArray = json.getJSONArray("datas");
                                    provinceList.clear();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object1 = jsonArray.getJSONObject(i);
                                        HuoHangAddressList.DatasEntry a = new HuoHangAddressList.DatasEntry();
                                        a.country_name=(object1.optString("name"));
                                        a.area_id=(object1.optString("area_id"));
                                        a.area_info=(object1.optString("area_info"));
                                        if (object1.optString("area_info").contains("\t")) {
                                            strings = object1.optString("area_info").split("\t");
                                        } else {
                                            strings = object1.optString("area_info").split(" ");
                                        }

                                        provinceList.add(a);
                                    }
                                    Log.i("zjz", "provinceList=" + provinceList.size());
                                    provinceAdapter = new ProvinceAdapter(HuoHangEditAddressActivity.this, provinceList, zlistProvince);
                                    if (zlistProvince != null) {
                                        zlistProvince.setAdapter(provinceAdapter);
                                        setListViewHeight(zlistProvince);
                                    }
                                    provinceAdapter.notifyDataSetChanged();
                                } else {
                                    Log.i("zjz", "goodsActivity解析失败");
                                }
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            Log.i("zjz", "登录失败！");
                            Log.i("zjz", msg);
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    class ProvinceAdapter extends BaseAdapter {
        private ArrayList<HuoHangAddressList.DatasEntry> provinceList;
        Activity activity;
        LayoutInflater inflater = null;
        ZrcListView listView;
        public Handler handler = new Handler();


        public ProvinceAdapter(Activity activity, ArrayList<HuoHangAddressList.DatasEntry> provinceList,
                               ZrcListView listView) {
            // TODO Auto-generated constructor stub

            this.activity = activity;
            this.provinceList = provinceList;
            this.listView = listView;
            inflater = LayoutInflater.from(activity);
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            // TODO Auto-generated method stub
            ProvinceViewHolder mHolder = null;
            final HuoHangAddressList.DatasEntry address = getItem(position);
            if (convertView == null) {
                LayoutInflater mInflater = LayoutInflater.from(activity);
                convertView = mInflater.inflate(R.layout.hwg_item_province, null);
                mHolder = new ProvinceViewHolder();
                mHolder.t_province = (TextView) convertView.findViewById(R.id.t_province);
                mHolder.linear_root = (LinearLayout) convertView.findViewById(R.id.linear_root);

                convertView.setTag(mHolder);

            } else {
                mHolder = (ProvinceViewHolder) convertView.getTag();

            }
            try {
                mHolder.t_province.setText(address.country_name);
                mHolder.linear_root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isProvince = false;
                        ObjectAnimator.ofFloat(imgProvince, "rotation", 180, 360).setDuration(durationRotate).start();
                        zlistProvince.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                zlistProvince.setVisibility(View.GONE);
                            }
                        }, durationAlpha);
                        tProvince.setText(address.country_name);
                        relativeCity.setVisibility(View.VISIBLE);
                        tCity.setText("");
                        initCity(address.area_id);
                        countryId=address.area_id;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return provinceList == null ? 0 : provinceList.size();
        }

        @Override
        public HuoHangAddressList.DatasEntry getItem(int position) {
            if (provinceList != null && provinceList.size() != 0) {
                if (position >= provinceList.size()) {
                    return provinceList.get(0);
                }
                return provinceList.get(position);
            }
            return null;
        }


        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
    }

    class ProvinceViewHolder {
        TextView t_province;
        LinearLayout linear_root;
    }

    private void initCity(String areaID) {
        try {
            Log.i("zjz", "area_id=" + areaID);
//        http://huohang.huaqiaobang.com/mobile/index.php?act=native_index&op=get_city&country_id=186
            HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=native_index&op=get_city&country_id=" + areaID, new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    // TODO Auto-generated method stub
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(msg);
                                if (json != null && json.has("state")) {
                                    int code = json.getInt("state");
                                    Log.i("zjz", msg);
                                    if (code == 1) {
                                        JSONArray jsonArray = json.getJSONArray("datas");
                                        cityList.clear();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject object1 = jsonArray.getJSONObject(i);
                                            HuoHangAddressList.DatasEntry a = new HuoHangAddressList.DatasEntry();
                                            a.city_name=(object1.optString("name"));
                                            a.city_id=(object1.optString("area_id"));
                                            cityList.add(a);
                                        }

                                        Log.i("zjz", "cityList=" + cityList.size());
                                        cityAdapter = new CityAdapter(HuoHangEditAddressActivity.this, cityList, zlistCity);
                                        if (zlistCity != null) {

                                            zlistCity.setAdapter(cityAdapter);
                                            setListViewHeight(zlistCity);
                                        }
                                        cityAdapter.notifyDataSetChanged();
                                    } else {
                                        Log.i("zjz", "goodsActivity解析失败");
                                    }
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                Log.i("zjz", "登录失败！");
                                Log.i("zjz", msg);
                                e.printStackTrace();
                            }
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class CityAdapter extends BaseAdapter {
        private ArrayList<HuoHangAddressList.DatasEntry> cityList;
        Activity activity;
        LayoutInflater inflater = null;
        ZrcListView listView;
        public Handler handler = new Handler();


        public CityAdapter(Activity activity, ArrayList<HuoHangAddressList.DatasEntry> cityList,
                           ZrcListView listView) {
            // TODO Auto-generated constructor stub

            this.activity = activity;
            this.cityList = cityList;
            this.listView = listView;
            inflater = LayoutInflater.from(activity);
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            // TODO Auto-generated method stub
            CityViewHolder mHolder = null;
            final HuoHangAddressList.DatasEntry address = getItem(position);
            if (convertView == null) {
                LayoutInflater mInflater = LayoutInflater.from(activity);
                convertView = mInflater.inflate(R.layout.hwg_item_province, null);
                mHolder = new CityViewHolder();
                mHolder.t_province = (TextView) convertView.findViewById(R.id.t_province);
                mHolder.linear_root = (LinearLayout) convertView.findViewById(R.id.linear_root);

                convertView.setTag(mHolder);

            } else {
                mHolder = (CityViewHolder) convertView.getTag();

            }
            try {
                mHolder.t_province.setText(address.city_name);
                mHolder.linear_root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ObjectAnimator.ofFloat(imgCity, "rotation", 180, 360).setDuration(durationRotate).start();

                        isCity = false;
                        zlistCity.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                zlistCity.setVisibility(View.GONE);
                            }
                        }, durationAlpha);

                        tCity.setText(address.city_name);
                        cityId=address.city_id;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return cityList == null ? 0 : cityList.size();
        }

        @Override
        public HuoHangAddressList.DatasEntry getItem(int position) {
            if (cityList != null && cityList.size() != 0) {
                if (position >= cityList.size()) {
                    return cityList.get(0);
                }
                return cityList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
    }

    class CityViewHolder {
        TextView t_province;
        LinearLayout linear_root;
    }

    private boolean is_select = true;
    String phone, storeName, e_Address,countryName,cityName,postCode;

    private void comfirm() {
        try {
            phone = edPhone.getText().toString().trim();
            storeName = edStoreName.getText().toString().trim();
            e_Address = edAddress.getText().toString().trim();
            countryName = tProvince.getText().toString().trim();
            cityName = tCity.getText().toString().trim();
            postCode = edYoubian.getText().toString().trim();

            if (TextUtils.isEmpty(phone)) {
                showToast("电话不能为空！");
            } else if (TextUtils.isEmpty(storeName)) {
                showToast("联系人不能为空！");
            }else if (TextUtils.isEmpty(countryName)) {
                showToast("请选择国家");
            }else if (TextUtils.isEmpty(cityName)) {
                showToast("请选择城市");
            } else if (TextUtils.isEmpty(e_Address)) {
                showToast("详细地址不能为空！");
            } else {
                if (isAdd) {  // 添加新地址
                    addAddress();
                } else {
                    save();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }
}
