package com.abcs.haiwaigou.local.huohang.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abcs.haiwaigou.broadcast.MyBroadCastReceiver;
import com.abcs.haiwaigou.local.evenbus.Notice;
import com.abcs.haiwaigou.local.evenbus.Notice2;
import com.abcs.haiwaigou.local.huohang.adapter.HHSearchAdapter;
import com.abcs.haiwaigou.local.huohang.adapter.SearchHisAdapter;
import com.abcs.haiwaigou.local.huohang.adapter.SearchHotAdapter;
import com.abcs.haiwaigou.local.huohang.bean.HHItemRight;
import com.abcs.haiwaigou.utils.NumberUtils;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.huaqiaobang.util.NoDoubleClickListener;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.huaqiaobang.wxapi.WXEntryActivity;
import com.abcs.huaqiaobang.ytbt.common.utils.ToastUtil;
import com.abcs.sociax.android.R;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.abcs.haiwaigou.local.huohang.view.ItemGoodsFragment.district_id;

public class HHNewSearchActivity extends BaseActivity implements HHSearchAdapter.OnShopCartGoodsChangeListener {
    @Override
    public void onNumChange(int num, HHItemRight.DatasBean data) {
        try {
            double total_price = 0.00;
            if (itemRightAdapter.goodsPri.size() > 0) {
                for (Map.Entry<Integer, Double> entry : itemRightAdapter.goodsPri.entrySet()) {
                    total_price += entry.getValue();
                }
            }

            if(!TextUtils.isEmpty(MyApplication.getHHStoreCoin())){
                tvPeisongGfw.setText(MyApplication.getHHStoreCoin() + NumberUtils.formatPriceNo(total_price + cart_price+from_goodList_totalprice));
            }else {
                tvPeisongGfw.setText("€" + NumberUtils.formatPriceNo(total_price + cart_price+from_goodList_totalprice));
            }

            tvPeisongGfw.setTextColor(getResources().getColor(R.color.colorPrimaryDark2));
            SpannableString spannableString = new SpannableString(tvPeisongGfw.getText().toString().trim());
            spannableString.setSpan(new RelativeSizeSpan(0.8f), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tvPeisongGfw.setText(spannableString);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @InjectView(R.id.img_ss)
    ImageView imgSs;
    @InjectView(R.id.et_search)
    EditText etSearch;
    @InjectView(R.id.img_clear)
    ImageView imgClear;
    @InjectView(R.id.layout_search_bar)
    RelativeLayout layoutSearchBar;
    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.relative_search)
    RelativeLayout relativeSearch;
    @InjectView(R.id.relative_title)
    RelativeLayout relativeTitle;
    @InjectView(R.id.line_top)
    View lineTop;
    @InjectView(R.id.tv)
    TextView tv;
    @InjectView(R.id.line_re)
    View lineRe;
    @InjectView(R.id.gridview_re)
    GridView gridviewRe;
    @InjectView(R.id.tv_history)
    TextView tvHistory;
    @InjectView(R.id.relative_delete)
    RelativeLayout relativeDelete;
    @InjectView(R.id.relative_his)
    RelativeLayout relativeHis;
    @InjectView(R.id.line_his)
    View lineHis;
    @InjectView(R.id.gridview_his)
    GridView gridviewHis;
    @InjectView(R.id.activity_hhnew_search)
    RelativeLayout activityHhnewSeach;
    @InjectView(R.id.relative_ss)
    RelativeLayout relative_ss;

    SearchHotAdapter searchHotAdapter;
    SearchHisAdapter searchHisAdapter;
    List<String> datas_hot = new ArrayList<>();
    List<String> historys = new ArrayList<>();

    public HHSearchAdapter itemRightAdapter;
    Handler handler = new Handler();

    /*buttom*/
    @InjectView(R.id.listview_datas)
    ListView listview_datas;
    @InjectView(R.id.line_buttom)
    View lineButtom;
    @InjectView(R.id.imge)
    ImageView imge;
    @InjectView(R.id.tv_peisong_number)
    TextView tvPeisongNumber;
    @InjectView(R.id.re_che)
    RelativeLayout reChe;
    @InjectView(R.id.tv_peisong_gfw)
    public TextView tvPeisongGfw;
    @InjectView(R.id.tv_peisong_qisong)
    public TextView tvPeisongQisong;
    @InjectView(R.id.re_peisong_jie)
    RelativeLayout rePeisongJie;
    @InjectView(R.id.relative_buttom)
    RelativeLayout relativeButtom;
    @InjectView(R.id.tv_ss)
    TextView tv_ss;

    int from_goodList_num=0;
    double from_goodList_totalprice=0.00;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotices( String loginSuccess) {   // 登陆了 看原来购物车有没有商品
        if(!TextUtils.isEmpty(loginSuccess)&&loginSuccess.equals("login_success_for_every")){
            initCarNum();
        }
    }


    /*  通知购物车更新数据*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChange(final Notice2 notice) {
        Log.d("zds", "onNoticeChange:search" + notice.googNum);
        Log.d("zds", "onNoticeChange:search" + notice.googsToalPrice);

        from_goodList_num=notice.googNum;
        from_goodList_totalprice=notice.googsToalPrice;
//        EventBus.getDefault().post(new Notice2(notice.googNum,notice.googsToalPrice));  // 发送数据通知搜索页面的购物车更新数据

    }

    /*  购物车列表 按返回  带数据过来*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNoticeChange(final Notice notice) {
        Log.d("zds", "onNoticeChange:search" + notice.googNum);
        Log.d("zds", "onNoticeChange:search" + notice.googsToalPrice);
//        EventBus.getDefault().post(new Notice2(notice.googNum,notice.googsToalPrice));  // 发送数据通知搜索页面的购物车更新数据

        if (notice.googNum > 0) {
            tvPeisongNumber.setText(notice.googNum + "");
            tvPeisongNumber.setVisibility(View.VISIBLE);
            imge.setBackground(getResources().getDrawable(R.drawable.img_huohang_n_card));
            tvPeisongQisong.setText("选好了");
            if(!TextUtils.isEmpty(MyApplication.getHHStoreCoin())){
                tvPeisongGfw.setText(MyApplication.getHHStoreCoin()+ NumberUtils.formatPriceNo(notice.googsToalPrice));
            }else {
                tvPeisongGfw.setText("€" + NumberUtils.formatPriceNo(notice.googsToalPrice));
            }

            tvPeisongGfw.setTextColor(getResources().getColor(R.color.colorPrimaryDark2));
            SpannableString spannableString = new SpannableString(tvPeisongGfw.getText().toString().trim());
            spannableString.setSpan(new RelativeSizeSpan(0.8f), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tvPeisongGfw.setText(spannableString);
        } else {
            tvPeisongNumber.setText(notice.googNum + "");
            tvPeisongNumber.setVisibility(View.GONE);
            imge.setBackground(getResources().getDrawable(R.drawable.img_huohang_n_card));
            tvPeisongQisong.setText("选好了");
            tvPeisongGfw.setText("购物车为空");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myBroadCastReceiver.unRegister();
        EventBus.getDefault().unregister(this);
    }

    public int cart_num = 0;
    public double cart_price;

    /****
     * 获取购物车数量
     */
    private void initCarNum() {
//        http://www.huaqiaobang.com/mobile/index.php?act=native&op=find_cart_num&key=5ff36f75680c05703394eff5013f6a1a&district_id=1
        String car_patamas = "act=native&op=find_cart_num&district_id=" + district_id + "&key=" + MyApplication.getInstance().getMykey() + "&store_id=" + store_id;
        HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", car_patamas, new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(msg)) {
                            return;
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(msg);

                            if (jsonObject.optInt("state") == 1) {

                                cart_num = jsonObject.optInt("cart_num");
                                cart_price = jsonObject.optDouble("cart_price");
                                double cart_taxprice = jsonObject.optDouble("cart_taxprice");
                                double rebate_money = jsonObject.optDouble("rebate_money");
                                if (cart_num > 0) {
                                    tvPeisongNumber.setText(cart_num + "");
                                    tvPeisongNumber.setVisibility(View.VISIBLE);
                                    imge.setBackground(getResources().getDrawable(R.drawable.img_huohang_n_card));
                                    tvPeisongQisong.setText("选好了");
                                    if(!TextUtils.isEmpty(MyApplication.getHHStoreCoin())){
                                        tvPeisongGfw.setText(MyApplication.getHHStoreCoin() + NumberUtils.formatPriceNo(cart_price));
                                    }else {
                                        tvPeisongGfw.setText("€" + NumberUtils.formatPriceNo(cart_price));
                                    }

                                    tvPeisongGfw.setTextColor(getResources().getColor(R.color.colorPrimaryDark2));
                                    SpannableString spannableString = new SpannableString(tvPeisongGfw.getText().toString().trim());
                                    spannableString.setSpan(new RelativeSizeSpan(0.8f), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                    tvPeisongGfw.setText(spannableString);
                                } else {
                                    tvPeisongNumber.setText(cart_num + "");
                                    tvPeisongNumber.setVisibility(View.GONE);
                                    imge.setBackground(getResources().getDrawable(R.drawable.img_huohang_n_card));
                                    tvPeisongQisong.setText("选好了");
                                    tvPeisongGfw.setText("购物车为空");
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    String store_id;
    MyBroadCastReceiver myBroadCastReceiver;

    MyBroadCastReceiver.UpdateUI updateUI = new MyBroadCastReceiver.UpdateUI() {
        @Override
        public void updateShopCar(Intent intent) {
        }

        @Override
        public void updateCarNum(Intent intent) {
            Log.i("zds", "updateCarNum: ");
            cart_num = 0;
            initCarNum();
        }

        @Override
        public void updateCollection(Intent intent) {
        }

        @Override
        public void update(Intent intent) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hhnew_search);
        ButterKnife.inject(this);
        myBroadCastReceiver = new MyBroadCastReceiver(this, updateUI);
        myBroadCastReceiver.register();
        EventBus.getDefault().register(this);
        store_id=getIntent().getStringExtra("store_id");
        itemRightAdapter=new HHSearchAdapter(this);
        listview_datas.setAdapter(itemRightAdapter);
        itemRightAdapter.setmTextShop(this,tvPeisongNumber);
        itemRightAdapter.setmActivity(this,this);
        itemRightAdapter.setOnShopCartGoodsChangeListener(this);

        listview_datas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HHItemRight.DatasBean itemsEntity = (HHItemRight.DatasBean)parent.getItemAtPosition(position);
                if(itemsEntity!=null){
                    popGoodsDetials(itemsEntity);
                }
            }
        });

        if(!TextUtils.isEmpty(store_id)){
            initHot(store_id);
            initCarNum();
        }

        initHistory();
        watchSearch();
        intiListen();
    }

    /*商品详情*/
    private  void popGoodsDetials(HHItemRight.DatasBean data) {
        try {
            View root_view = View.inflate(this, R.layout.pop_huohang_goods_detials, null);
            ImageView imge_close = (ImageView) root_view.findViewById(R.id.imge_close);
            ImageView iv_logo = (ImageView) root_view.findViewById(R.id.iv_logo);
            TextView tv_name = (TextView) root_view.findViewById(R.id.tv_name);
            TextView tv_desc = (TextView) root_view.findViewById(R.id.tv_desc);
            TextView tv_huohao = (TextView) root_view.findViewById(R.id.tv_huohao);
            TextView tv_goods_content = (TextView) root_view.findViewById(R.id.tv_goods_content);
            TextView tv_ou_yuan = (TextView) root_view.findViewById(R.id.tv_ou_yuan);

            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(Util.WIDTH*2/3,Util.WIDTH*2/3);
            iv_logo.setLayoutParams(layoutParams);

            if(!TextUtils.isEmpty(data.goodsImage)){
    //                http://www.huaqiaobang.com/data/upload/shop/store/goods/11/11_05375327290881075.jpg
                String index=data.goodsImage.substring(0,data.goodsImage.indexOf("_"));
                Log.i("zdsindex", "setData: "+index);
                MyApplication.imageLoader.displayImage("http://huohang.huaqiaobang.com/data/upload/shop/store/goods/"+index+"/"+data.goodsImage,iv_logo,MyApplication.getCircleImageOptions());

            }

            tv_name.setText(data.goodsName+"");
            tv_desc.setText(data.goodsEnName+"");
            tv_huohao.setText("货号:"+data.goodsSerial);
            if(!TextUtils.isEmpty(MyApplication.getHHStoreCoin())){
                tv_ou_yuan.setText(MyApplication.getHHStoreCoin()+data.goodsPrice);
            }else {
                tv_ou_yuan.setText("€"+data.goodsPrice);
            }

            if(!TextUtils.isEmpty(data.goodsJingle)){
                tv_goods_content.setText(data.goodsJingle);
            }else {
                tv_goods_content.setText("暂无信息");
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

    private void intiListen(){
        rePeisongJie.setOnClickListener(new NoDoubleClickListener() {  // 结算
            @Override
            public void onNoDoubleClick(View v) {
                Intent intent = null;
                if (MyApplication.getInstance().self == null) {
                    intent = new Intent(HHNewSearchActivity.this, WXEntryActivity.class);
                    intent.putExtra("isthome", true);
                    startActivity(intent);
                    return;
                } else {
                    if (Integer.valueOf(tvPeisongNumber.getText().toString().trim()) > 0) {
                        jieSuan();
                    } else {
                        ToastUtil.showMessage("您还没有选择商品呢！");
                        return;
                    }
                }
            }
        });

        reChe.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (MyApplication.getInstance().self == null) {
                    Intent che = new Intent(HHNewSearchActivity.this, WXEntryActivity.class);
                    che.putExtra("isthome", true);
                    startActivity(che);
                    return;
                } else {
                    addToCart();
                }
            }
        });
    }

    /***
     * 加入购物车
     */
    private void addToCart() {

        /*判断商品*/
        StringBuilder goods_ids = new StringBuilder();
        StringBuilder quantity_1 = new StringBuilder();

        if (itemRightAdapter.datas.size() > 0) {
            for (Map.Entry<Integer, HHItemRight.DatasBean> entry : itemRightAdapter.datas.entrySet()) {

                goods_ids.append(entry.getValue().goodsId);
                goods_ids.append(",");
                quantity_1.append(entry.getValue().numbers);
                quantity_1.append(",");

            }
        }

        Log.i("zds", "addToCart: " + goods_ids);
        Log.i("zds", "addToCart: " + quantity_1);

        Log.i("zds", "addToCart: url" + "http://huohang.huaqiaobang.com/mobile/index.php?act=member_cart&op=ios_cart_add&" + "goods_id=" + goods_ids + "&quantity=" + quantity_1 + "&key=" + MyApplication.getInstance().getMykey() + "&version=1.0" + "&store_id=" + store_id);
        HttpRequest.sendPost("http://huohang.huaqiaobang.com/mobile/index.php?act=member_cart&op=ios_cart_add", "goods_id=" + goods_ids + "&quantity=" + quantity_1 + "&key=" + MyApplication.getInstance().getMykey() + "&version=1.0" + "&store_id=" + store_id, new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        Log.i("addche", msg + "");

                        if (TextUtils.isEmpty(msg)) {
                            return;
                        } else {

                            try {
                                JSONObject object = new JSONObject(msg);
                                if (object.getInt("code") == 200) {

                                    Intent intent = new Intent(HHNewSearchActivity.this, HuoHangCartActivity.class);
                                    intent.putExtra("store_id", store_id);
                                    startActivity(intent);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            initAdapterNoData();
                                        }
                                    }, 500);
                                } else {
                                    Log.i("zds", "add:解析失败");
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                Log.i("zds", e.toString());
                                Log.i("zds", msg);
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    public void initAdapterNoData() {
        try {
            itemRightAdapter.datas.clear();
            itemRightAdapter.goodsNumMap.clear();
            itemRightAdapter.goodsPri.clear();
            itemRightAdapter.buyNum = 0;
            cart_num = 0;
            itemRightAdapter.notifyDataSetChanged();
            initCarNum();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String cart_id;
    private int count = 0; //记录cartId的个数
    private void jieSuan() {

        count = 0;
        StringBuilder goods_ids_jie = new StringBuilder();
        StringBuilder quantity_1_jie = new StringBuilder();

        if (itemRightAdapter.datas.size() > 0) {

            for (Map.Entry<Integer, HHItemRight.DatasBean> entry : itemRightAdapter.datas.entrySet()) {
                goods_ids_jie.append(entry.getValue().goodsId);
                goods_ids_jie.append(",");
                quantity_1_jie.append(entry.getValue().numbers);
                quantity_1_jie.append(",");
            }
        }

        Log.i("zds", "addToCart_j: " + goods_ids_jie);
        Log.i("zds", "addToCart_j: " + quantity_1_jie);

        Log.i("zds", "jieSuan: url" + "http://huohang.huaqiaobang.com/mobile/index.php?act=member_cart&op=new_cart_add&" + "goods_id=" + goods_ids_jie + "&quantity=" + quantity_1_jie + "&key=" + MyApplication.getInstance().getMykey() + "&version=1.0" + "&store_id=" + store_id);
        HttpRequest.sendPost("http://huohang.huaqiaobang.com/mobile/index.php?act=member_cart&op=new_cart_add", "goods_id=" + goods_ids_jie + "&quantity=" + quantity_1_jie + "&key=" + MyApplication.getInstance().getMykey() + "&version=1.0" + "&store_id=" + store_id, new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        Log.i("jie", msg + "");

                        if (TextUtils.isEmpty(msg)) {
                            return;
                        } else {

                            try {
                                JSONObject object = new JSONObject(msg);
                                if (object.optInt("code") == 200) {

                                    JSONObject datas = object.optJSONObject("datas");
                                    if (datas.has("state")) {

                                        if (datas.getBoolean("state")) {  //正常的流程

                                            if (datas.has("data")) {
                                                JSONObject data = datas.optJSONObject("data");
                                                if (data.has("store_cart_list")) {
                                                    JSONArray store_cart_list = data.optJSONArray("store_cart_list");
                                                    if (store_cart_list != null && store_cart_list.length() > 0) {
                                                          /*判断商品是否可以买*/
                                                        StringBuffer srtbuffer = new StringBuffer();
                                                        ArrayList<String> strings = new ArrayList<String>();

                                                        for (int i = 0; i < store_cart_list.length(); i++) {
                                                            JSONObject CartListObject = store_cart_list.getJSONObject(i);
                                                            JSONArray goodJSonArry = CartListObject.optJSONArray("goods_list");
                                                            if (goodJSonArry != null && goodJSonArry.length() > 0) {
                                                                for (int h = 0; h < goodJSonArry.length(); h++) {
                                                                    JSONObject goods = goodJSonArry.getJSONObject(h);
                                                                    if (!goods.optBoolean("state")) {
                                                                        Toast.makeText(HHNewSearchActivity.this, "所选商品中[" + goods.optString("goods_name").substring(0, 12) + "]已经下架，无法购买，请重新下单！", Toast.LENGTH_LONG).show();
                                                                        return;
                                                                    }
                                                                    if (!goods.optBoolean("storage_state")) {
                                                                        Toast.makeText(HHNewSearchActivity.this, "所选商品中[" + goods.optString("goods_name").substring(0, 12) + "]库存不足，无法购买，请重新下单！", Toast.LENGTH_LONG).show();
                                                                        return;
                                                                    }

                                                                    StringBuffer buffer = new StringBuffer();
                                                                    buffer.append("&cart_id[" + count + "]=");
                                                                    buffer.append(goods.optString("cart_id") + "|" + goods.optString("goods_num"));
                                                                    srtbuffer.append(buffer);
                                                                    strings.add(goods.optString("store_id"));
                                                                    cart_id = srtbuffer.toString();
                                                                    count++;
                                                                }
                                                            }
                                                        }

                                                        Log.i("zds", "cart_id=" + cart_id);
                                                        Log.i("zds", "count=" + count);
                                                        Intent intent = null;
                                                        intent = new Intent(HHNewSearchActivity.this, HuoHangCheckOrderActivity.class);
                                                        intent.putExtra("cart_id", cart_id);
                                                        intent.putExtra("store_id", store_id);
                                                        startActivity(intent);
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                initAdapterNoData();
                                                            }
                                                        }, 500);
                                                    }
                                                }
                                            }
                                        } else {  //有商品已经下架，提示用户先删除已经下架的商品

                                            ToastUtil.showMessage("购物车里的东西太久了，有商品已经下架，请删除购物车中下架的商品，再重新购买！");
                                        }

                                    }
                                } else {
                                    Log.i("zds", "add:解析失败");
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                Log.i("zds", e.toString());
                                Log.i("zds", msg);
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    public void watchSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s!=null){
                    if(TextUtils.isEmpty(s.toString().trim())){
                        relative_ss.setVisibility(View.VISIBLE);
                        listview_datas.setVisibility(View.GONE);
                        lineButtom.setVisibility(View.GONE);
                        relativeButtom.setVisibility(View.GONE);
                        tv_ss.setTextColor(getResources().getColor(R.color.text_hh));
                    }else {
                        relative_ss.setVisibility(View.GONE);
                        listview_datas.setVisibility(View.VISIBLE);
                        lineButtom.setVisibility(View.VISIBLE);
                        relativeButtom.setVisibility(View.VISIBLE);
                        tv_ss.setTextColor(getResources().getColor(R.color.colorPrimaryDark2));
                    }
                }
            }
        });
    }
    /*热门搜索*/
    private void initHot(String store_id) {
//        http://huohang.huaqiaobang.com/mobile/index.php?act=native&op=get_hot_search&key=69afe6c212301d08ebf54422e4da1ac0&store_id=11
        HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=native_index&op=get_hot_search&key="+MyApplication.getInstance().getMykey()+"&store_id="+store_id, new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        {
//                            "state": 1,
//                                "msg": "香米,豆,虾,鱼,餐巾纸,外卖袋"
//                        }
                        if(TextUtils.isEmpty(msg)){
                            ToastUtil.showMessage("服务器出现点问题！");
                            return;
                        }else {
                            try {
                                JSONObject jsonObject=new JSONObject(msg);
                                if(jsonObject!=null){
                                    if(jsonObject.optInt("state")==1){
                                        datas_hot.clear();
                                        String main=jsonObject.optString("msg");
                                        if(!TextUtils.isEmpty(main)){
                                            String[] hisArrays = main.split(",");
                                            for (int i = hisArrays.length - 1; i > -1; i--) {
                                                if (!TextUtils.isEmpty(hisArrays[i])) {
                                                    Log.e("zds", "hot" + hisArrays[i]);
                                                    datas_hot.add(hisArrays[i]);
                                                }
                                            }
                                            gridviewRe.setAdapter(searchHotAdapter = new SearchHotAdapter(HHNewSearchActivity.this, datas_hot));
                                            gridviewRe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    String item = (String) parent.getItemAtPosition(position);
                                                    relative_ss.setVisibility(View.GONE);
                                                    listview_datas.setVisibility(View.VISIBLE);
                                                    lineButtom.setVisibility(View.VISIBLE);
                                                    relativeButtom.setVisibility(View.VISIBLE);
                                                    tv_ss.setTextColor(getResources().getColor(R.color.colorPrimaryDark2));

                                                    updatas(item);
                                                }
                                            });
                                        }

                                        tv.setVisibility(View.VISIBLE);
                                        lineRe.setVisibility(View.VISIBLE);
                                        gridviewRe.setVisibility(View.VISIBLE);

                                    }else {
                                        ToastUtil.showMessage(jsonObject.optString("msg"));
                                        tv.setVisibility(View.GONE);
                                        lineRe.setVisibility(View.GONE);
                                        gridviewRe.setVisibility(View.GONE);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    /*历史搜索*/
    private void initHistory() {
        historys.clear();
        try {
            SharedPreferences sp = getSharedPreferences("histry_hh", 0);
            String save_history = sp.getString("history", "");
            String[] hisArrays = save_history.split(",");
            for (int i = hisArrays.length - 1; i > -1; i--) {
                if (!TextUtils.isEmpty(hisArrays[i])) {
                    Log.e("zds", "history" + hisArrays[i]);
                    historys.add(hisArrays[i]);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        searchHisAdapter = new SearchHisAdapter(this, historys);
        gridviewHis.setAdapter(searchHisAdapter);

        gridviewHis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String keywoed=(String) adapterView.getItemAtPosition(i);
                if(!TextUtils.isEmpty(keywoed)){
                    relative_ss.setVisibility(View.GONE);
                    listview_datas.setVisibility(View.VISIBLE);
                    lineButtom.setVisibility(View.VISIBLE);
                    relativeButtom.setVisibility(View.VISIBLE);
                    tv_ss.setTextColor(getResources().getColor(R.color.colorPrimaryDark2));
                    updatas(keywoed);
                }
            }
        });
    }

    private void Save() {
        try {
            String text = etSearch.getText().toString().trim();
            SharedPreferences sp = getSharedPreferences("histry_hh", 0);
            String save_Str = sp.getString("history", "");
            String[] hisArrays = save_Str.split(",");
            for (int i = 0; i < hisArrays.length; i++) {
                if (hisArrays[i].equals(text)) {
                    return;
                }
            }
            StringBuilder sb = new StringBuilder(save_Str);
            sb.append(text + ",");
            sp.edit().putString("history", sb.toString()).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.img_clear, R.id.relative_back, R.id.relative_search, R.id.relative_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_clear:
                etSearch.setText("");
                initHistory();
                break;
            case R.id.relative_back:
                EventBus.getDefault().post("clearadapter");  // 清空购物车
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },500);
                break;
            case R.id.relative_search:
                goForSearch();
                break;
            case R.id.relative_delete:
//                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
//                        hideSoftInputFromWindow(etSearch.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                searchHisAdapter.clearDatas();
                Clear();
                break;
        }
    }

    private void Clear() {
        SharedPreferences sp = getSharedPreferences("histry_hh", 0);
        sp.edit().clear().commit();
    }

    private void goForSearch() {

        if (TextUtils.isEmpty(etSearch.getText().toString().trim())) {
            showToast("请输入搜索的商品");
        } else {
            updatas(etSearch.getText().toString().trim());
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etSearch.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            Save();
        }
    }

    private void updatas(String keyword) {
        try {
            ProgressDlgUtil.showProgressDlg("Loading...", this);
//        http://huohang.huaqiaobang.com/mobile/index.php?act=goods&op=native_search&key=f374a0a4a02b6205b166b2b310e7511c&keyword=1+ "&store_id=" + store_id
            HttpRequest.sendPost("http://huohang.huaqiaobang.com/mobile/index.php?act=goods&op=native_search", "key=" + MyApplication.getInstance().getMykey() + "&keyword=" + keyword+ "&store_id=" + store_id, new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    // TODO Auto-generated method stub
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (TextUtils.isEmpty(msg)) {
                                return;
                            } else {
                                HHItemRight bean = new Gson().fromJson(msg, HHItemRight.class);
                                if (bean.state == 1) {
                                    if (bean.datas != null) {
                                        if (bean.datas.size() > 0) {
                                            listview_datas.setAdapter(itemRightAdapter);
                                            itemRightAdapter.clearDatasAndAdd(bean.datas);
                                        } else {
                                            ToastUtil.showMessage("没有数据哦！");
                                        }
                                    }
                                } else {
                                    ToastUtil.showMessage("没有数据哦！");
                                }
                            }
                            ProgressDlgUtil.stopProgressDlg();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
