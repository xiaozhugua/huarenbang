package com.abcs.haiwaigou.local.huohang.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

import com.abcs.haiwaigou.broadcast.MyBroadCastReceiver;
import com.abcs.haiwaigou.local.evenbus.ItemLeftCount;
import com.abcs.haiwaigou.local.evenbus.Notice;
import com.abcs.haiwaigou.local.evenbus.Notice2;
import com.abcs.haiwaigou.local.huohang.adapter.ItemRightAdapter;
import com.abcs.haiwaigou.local.huohang.bean.DatasEntrySer;
import com.abcs.haiwaigou.local.huohang.bean.HHItemLeft;
import com.abcs.haiwaigou.local.huohang.bean.HHItemRight;
import com.abcs.haiwaigou.utils.ACache;
import com.abcs.haiwaigou.utils.NumberUtils;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.tljr.data.Constent;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.huaqiaobang.util.NoDoubleClickListener;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.huaqiaobang.wxapi.WXEntryActivity;
import com.abcs.huaqiaobang.ytbt.common.utils.ToastUtil;
import com.abcs.sociax.android.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zds 货行商品
 */
public class ItemGoodsFragment extends Fragment implements ItemRightAdapter.OnShopCartGoodsChangeListener {

    Activity activity;
    View view;
    @InjectView(R.id.listview)
    ListView listview;
    @InjectView(R.id.listview_datas)
    ListView listviewDatas;
    @InjectView(R.id.linear_root)
    LinearLayout linearRoot;
    @InjectView(R.id.imge)
    public ImageView imge;
    @InjectView(R.id.tv_peisong_number)
    TextView tvPeisongNumber;
    @InjectView(R.id.re_che)
    public RelativeLayout reChe;
    @InjectView(R.id.tv_peisong_gfw)
    public TextView tvPeisongGfw;
    @InjectView(R.id.tv_peisong_qisong)
    public TextView tvPeisongQisong;
    @InjectView(R.id.re_peisong_jie)
    public RelativeLayout rePeisongJie;
    @InjectView(R.id.liner)
    public RelativeLayout liner;
    @InjectView(R.id.relative_tishi)
    public RelativeLayout relative_tishi;  // 提示
    @InjectView(R.id.linear_mian)
    public LinearLayout linear_mian;
    @InjectView(R.id.web_view)
    public WebView web_view;
    @InjectView(R.id.progress_bar)
    public ProgressBar progress_bar;


    ACache aCache;
    DatasEntrySer datasEntry;
    Handler handler=new Handler();
//    HHItemRightAdapter hhItemRightAdapter;
    MyBroadCastReceiver myBroadCastReceiver;
    public int store_goods_details=0; // 是否可以查看商品详情  1 可以看

    public  static ItemGoodsFragment newInstance( DatasEntrySer datasEntry) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("c_id", datasEntry);
        ItemGoodsFragment fragment = new ItemGoodsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    private String ngc_id;
    private String goods_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        view = inflater.inflate(R.layout.fragment_item_goods, null);
        activity = (Activity) getActivity();
        ButterKnife.inject(this, view);
        aCache=ACache.get(activity);
        myBroadCastReceiver = new MyBroadCastReceiver(activity, updateUI);
        myBroadCastReceiver.register();
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        try {
            if (bundle != null) {
                datasEntry =(DatasEntrySer) bundle.getSerializable("c_id");
                if(datasEntry!=null&&!TextUtils.isEmpty(datasEntry.getStoreId())){
                    ngc_id=datasEntry.getNgc_id();
                    goods_id=datasEntry.getGoods_Id();
                    store_goods_details=datasEntry.getStoreGoodsDetails();
                    checkIsInto(datasEntry.getStoreId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        itemRightAdapter=new ItemRightAdapter(activity);
        listviewDatas.setAdapter(itemRightAdapter);
        itemRightAdapter.setmTextShop(activity,tvPeisongNumber,liner);
        itemRightAdapter.setmActivity(activity,ItemGoodsFragment.this);
        itemRightAdapter.setOnShopCartGoodsChangeListener(ItemGoodsFragment.this);
        try {
            Typeface type = Typeface.createFromAsset(activity.getAssets(), "font/ltheijian.TTF");
            if (type != null) {
                tvPeisongGfw.setTypeface(type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initLister();
        getClassType();

        if(!TextUtils.isEmpty(MyApplication.getInstance().getMykey())&&!TextUtils.isEmpty(store_id)){
            initCarNum();
        }

        return view;
    }
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotices( String loginSuccess) {   // 登陆了 看原来购物车有没有商品  并且清空商品原来的缓存数据 因为有Vip价格组
        try {
            if(!TextUtils.isEmpty(loginSuccess)&&loginSuccess.equals("login_success_for_every")){

              handler.post(new Runnable() {
                  @Override
                  public void run() {
                      for (int i = 0; i < adapter.getCount(); i++) {
                          aCache.remove(store_id+""+i);
                      }
                  }
              });

                initCarNum();

                if(!TextUtils.isEmpty(right_classCurrent_id)){
                    initPopRight(right_classCurrent_id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNoticeChange( DatasEntrySer datasEntry) {
        if (datasEntry != null) {
            if(!TextUtils.isEmpty(datasEntry.getStoreId())){
                store_goods_details=datasEntry.getStoreGoodsDetails();
                checkIsInto(datasEntry.getStoreId());
            }

            getClassType();
        }
    }

    /*  购物车列表 按返回  带数据过来*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNoticeChange(final Notice notice) {
        try {
            Log.d("zds", "onNoticeChange:Itemgoods" + notice.googNum);
            Log.d("zds", "onNoticeChange:Itemgoods" + notice.googsToalPrice);
//        EventBus.getDefault().post(new Notice2(notice.googNum,notice.googsToalPrice));  // 发送数据通知搜索页面的购物车更新数据

            if (notice.googNum > 0) {
                tvPeisongNumber.setText(notice.googNum + "");
                tvPeisongNumber.setVisibility(View.VISIBLE);
                imge.setBackground(activity.getResources().getDrawable(R.drawable.img_huohang_n_card));
    //            img_peisong_che.setImageResource(R.drawable.bg_bottom_psong_che2);
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
    //            rePeisongJie.setBackgroundResource(R.drawable.bg_bottom_psong_jie);
            } else {
                tvPeisongNumber.setText(notice.googNum + "");
                tvPeisongNumber.setVisibility(View.GONE);
                imge.setBackground(activity.getResources().getDrawable(R.drawable.img_huohang_n_card));
    //            img_peisong_che.setImageResource(R.drawable.bg_bottom_psong_che2);
    //            tvPeisongQisong.setText("€0.00");
                tvPeisongQisong.setText("选好了");
                tvPeisongGfw.setText("购物车为空");
    //            rePeisongJie.setBackgroundResource(R.drawable.bg_bottom_psong_jie_no);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
    /* 从搜索加入购物车或者结算 通知数据更新*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClearApater(String clearadapter) {
        if(!TextUtils.isEmpty(clearadapter)&&clearadapter.equals("clearadapter")){
            Log.d("zds", "clearadapter:" + clearadapter);
            initAdapterNoData();
        }
    }

    public static String  district_id;
    public String address_id;
    private void checkIsInto(String storeId) {

        try {
            if (!TextUtils.isEmpty(storeId)) {
                store_id = storeId;
                if (MyApplication.getInstance().userLocal != null) {
                    if (MyApplication.getInstance().userLocal.length() > 0) {
                        for (int i = 0; i < MyApplication.getInstance().userLocal.length(); i++) {
                            JSONObject bean = MyApplication.getInstance().userLocal.optJSONObject(i);
                            if (!TextUtils.isEmpty(bean.optString("district_id"))) {
                                district_id = bean.optString("district_id");
                            }

                            if (!TextUtils.isEmpty(bean.optString("address_id"))) {
                                address_id = bean.optString("address_id");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    MyAdapter adapter;

    private String right_classCurrent_id;
    private void initLister(){
        try {
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HHItemLeft.DatasBean ssXaun = (HHItemLeft.DatasBean) parent.getItemAtPosition(position);
                    adapter.setSelectedPosition(position);
                    adapter.notifyDataSetChanged();

                    if (!TextUtils.isEmpty(ssXaun.classId)) {
                        position_left=position;
                        right_position=0; // 重置右边的定位
                        right_classCurrent_id=ssXaun.classId;
                        initPopRight(ssXaun.classId);
                        itemRightAdapter.setLeftPos(position_left);
                    }
                }
            });

            listviewDatas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    /*防止点击过快*/
                    long currentTime = Calendar.getInstance().getTimeInMillis();
                    if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                        lastClickTime = currentTime;

                        HHItemRight.DatasBean itemsEntity = (HHItemRight.DatasBean)parent.getItemAtPosition(position);
                        if(itemsEntity!=null){
                            popGoodsDetials(itemsEntity);
                        }
                    }
                }
            });

            rePeisongJie.setOnClickListener(new NoDoubleClickListener() {  // 结算
                @Override
                public void onNoDoubleClick(View v) {
                    Intent intent = null;
                    if (MyApplication.getInstance().self == null) {
                        intent = new Intent(activity, WXEntryActivity.class);
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
                        Intent che = new Intent(activity, WXEntryActivity.class);
                        che.putExtra("isthome", true);
                        startActivity(che);
                        return;
                    } else {
                        addToCart();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 加入购物车
     */
    private void addToCart() {

        try {
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

                                        Intent intent = new Intent(activity, HuoHangCartActivity.class);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            clearLeftCount();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void clearLeftCount() {
        try {
            if(adapter.getCount()>0){
                for (int i = 0; i <  adapter.getCount(); i++) {
                    HHItemLeft.DatasBean datasbean =(HHItemLeft.DatasBean)adapter.getItem(i);
                    datasbean.item_num=0;  // 重置数据
                }
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String cart_id;

    /*商品详情*/
    private  void popGoodsDetials(HHItemRight.DatasBean data) {
        try {
            View root_view = View.inflate(activity, R.layout.pop_huohang_goods_detials, null);
            ImageView imge_close = (ImageView) root_view.findViewById(R.id.imge_close);
            ImageView iv_logo = (ImageView) root_view.findViewById(R.id.iv_logo);
            TextView tv_name = (TextView) root_view.findViewById(R.id.tv_name);
            TextView tv_desc = (TextView) root_view.findViewById(R.id.tv_desc);
            RelativeLayout relative_img = (RelativeLayout) root_view.findViewById(R.id.relative_img);
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
//        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setContentView(root_view);

            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            params.alpha = 0.5f;
            activity.getWindow().setAttributes(params);
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
    private int count = 0; //记录cartId的个数
    private void jieSuan() {

        try {
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
                                                                            Toast.makeText(activity, "所选商品中[" + goods.optString("goods_name").substring(0, 12) + "]已经下架，无法购买，请重新下单！", Toast.LENGTH_LONG).show();
                                                                            return;
                                                                        }
                                                                        if (!goods.optBoolean("storage_state")) {
                                                                            Toast.makeText(activity, "所选商品中[" + goods.optString("goods_name").substring(0, 12) + "]库存不足，无法购买，请重新下单！", Toast.LENGTH_LONG).show();
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
                                                            intent = new Intent(activity, HuoHangCheckOrderActivity.class);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String store_id;
    private int position_left=0;
    // 分类
    private void getClassType() {
        try {
//        http://huohang.huaqiaobang.com/mobile/index.php?act=native&op=get_native_goods_class&key=a7954eaa08758d90c9b6de16beb63e17&store_id=11
//        http://huohang.huaqiaobang.com/mobile/index.php?act=native_index&op=get_native_goods_class&key=a7954eaa08758d90c9b6de16beb63e17&store_id=11
            HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=native_index&op=get_native_goods_class&key=" + MyApplication.getInstance().getMykey()+"&store_id="+store_id, new HttpRevMsg() {
    //        HttpRequest.sendGet(TLUrl.getInstance().URL_hwg_base + "/mobile/index.php", "act=native&op=get_native_goods_class&key=" + MyApplication.getInstance().getMykey(), new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("zds", "msg_left=" + msg);
                            if (TextUtils.isEmpty(msg)) {
                                return;
                            }
                            HHItemLeft peiSQuanBuLeft = new Gson().fromJson(msg, HHItemLeft.class);
                            if (peiSQuanBuLeft.state==1) {
                                if(linear_mian!=null){
                                    linear_mian.setVisibility(View.VISIBLE);
                                }

                                web_view.setVisibility(View.GONE);
                                relative_tishi.setVisibility(View.GONE);
                                if (peiSQuanBuLeft.datas != null) {
                                    if (peiSQuanBuLeft.datas.size() > 0) {

                                        for (int i = 0; i < peiSQuanBuLeft.datas.size(); i++) {
                                            HHItemLeft.DatasBean  element=peiSQuanBuLeft.datas.get(i);
                                            if(element!=null&&element.classId.equals(ngc_id)){
                                                position_left=i;
                                            }
                                        }

                                        adapter = new MyAdapter(activity, peiSQuanBuLeft.datas);
                                        listview.setAdapter(adapter);
                                        listview.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                listview.setSelection(position_left);
                                            }
                                        });

                                        adapter.setSelectedPosition(position_left);
                                        try {
                                            HHItemLeft.DatasBean dda = (HHItemLeft.DatasBean) adapter.getItem(position_left);
                                            if (dda != null) {
                                                right_classCurrent_id=dda.classId;
                                                initPopRight(dda.classId);
                                                itemRightAdapter.setLeftPos(position_left);
                                            }
                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                        }

                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            } else {  // webview 显示一张广告
                                if(linear_mian!=null){
                                    linear_mian.setVisibility(View.GONE);
                                }

                                web_view.setVisibility(View.VISIBLE);
                                relative_tishi.setVisibility(View.VISIBLE);
                                if(!TextUtils.isEmpty(peiSQuanBuLeft.msg)){  //url
                                    initWiewView(web_view,peiSQuanBuLeft.msg);
                                }

    //                            Intent intent=new Intent(activity, LoacalKeFuActivity_HH.class);
    //                            intent.putExtra("nostore",true);
    //                            startActivity(intent);
    //                            ToastUtil.showMessage("还没有数据哦！");

                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private void initWiewView(WebView webview,String url){
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.setFocusable(false);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 图片自适应
        webview.getSettings().setUseWideViewPort(false);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setBlockNetworkImage(Constent.noPictureMode); // 无图模式
        webview.getSettings().setBlockNetworkImage(Constent.netType.equals("未知"));

        webview.getSettings().setJavaScriptEnabled(true);
        try {
            Method m = WebSettings.class.getMethod("setMixedContentMode", int.class);
            if ( m == null ) {
                Log.e("WebSettings", "Error getting setMixedContentMode method");
            }
            else {
                m.invoke(webview.getSettings(), 2); // 2 = MIXED_CONTENT_COMPATIBILITY_MODE
                Log.i("WebSettings", "Successfully set MIXED_CONTENT_COMPATIBILITY_MODE");
            }
        }
        catch (Exception ex) {
            Log.e("WebSettings", "Error calling setMixedContentMode: " + ex.getMessage(), ex);
        }

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progress_bar.setVisibility(View.GONE);
                } else {
                    progress_bar.setVisibility(View.VISIBLE);
                    progress_bar.setProgress(newProgress);
                }
            }
        });

        webview.loadUrl(url);
    }



    public  ItemRightAdapter itemRightAdapter;
    private int right_position=0;
    private void initPopRight(String g_id) {
        try {
            if(!TextUtils.isEmpty(aCache.getAsString(store_id+""+position_left))){
                Log.i("zds", "initPopRight: acache"+aCache.getAsString(store_id+""+position_left));
                parseDatas(aCache.getAsString(store_id+""+position_left));
            }else {
                getDatasFromNet(g_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDatasFromNet(String g_id) {
        try {
            ProgressDlgUtil.showProgressDlg("",activity);
//        http://huohang.huaqiaobang.com/mobile/index.php?act=native&op=get_native_goods_list&key=a7954eaa08758d90c9b6de16beb63e17&class_id=2
//        http://huohang.huaqiaobang.com/mobile/index.php?act=native_index&op=get_native_goods_list&key=a7954eaa08758d90c9b6de16beb63e17&class_id=3
            HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=native_index&op=get_native_goods_list&key=" + MyApplication.getInstance().getMykey() + "&class_id=" + g_id, new HttpRevMsg() {
    //        HttpRequest.sendGet(TLUrl.getInstance().URL_hwg_base + "/mobile/index.php", "act=native&op=get_native_goods_list&key=" + MyApplication.getInstance().getMykey() + "&class_id=" + g_id, new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("zds", "msg_right=" + msg);
                            if (TextUtils.isEmpty(msg)) {
                                return;
                            }

                            parseDatas(msg);
                            aCache.put(store_id+""+position_left,msg,1*60*60*24);
                            ProgressDlgUtil.stopProgressDlg();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseDatas(String msg) {
        try {
            HHItemRight bean = new Gson().fromJson(msg, HHItemRight.class);
            if (bean.state == 1) {
                if (bean.datas != null) {
                    if(bean.datas.size() > 0){
                        for (int i = 0; i < bean.datas.size(); i++) {
                            HHItemRight.DatasBean element=bean.datas.get(i);
                            if(element!=null&&element.goodsId.equals(goods_id)){
                                right_position=i;
                            }
                        }

                        itemRightAdapter.clearDatasAndAdd(bean.datas);
                        listviewDatas.post(new Runnable() {
                            @Override
                            public void run() {
                                listviewDatas.setSelection(right_position);
                            }
                        });
                    }else {
                        ToastUtil.showMessage("还没有数据哦！");
                    }
                }
            } else {
                ToastUtil.showMessage("数据出错！");
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
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
                                    imge.setBackground(activity.getResources().getDrawable(R.drawable.img_huohang_n_card));
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
//                                    rePeisongJie.setBackgroundResource(R.drawable.bg_bottom_psong_jie);
                                } else {
                                    tvPeisongNumber.setText(cart_num + "");
                                    tvPeisongNumber.setVisibility(View.GONE);
                                    imge.setBackground(activity.getResources().getDrawable(R.drawable.img_huohang_n_card));
                                    tvPeisongQisong.setText("选好了");
                                    tvPeisongGfw.setText("购物车为空");
//                                    rePeisongJie.setBackgroundResource(R.drawable.bg_bottom_psong_jie_no);
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

    @Override
    public void onNumChange(int num, HHItemRight.DatasBean data) {


        try {
            double total_price = 0.00;
            if (itemRightAdapter.goodsPri.size() > 0) {
                for (Map.Entry<Integer, Double> entry : itemRightAdapter.goodsPri.entrySet()) {
                    total_price += entry.getValue();
                }
            }

            if (cart_price > 0) {
                if(!TextUtils.isEmpty(MyApplication.getHHStoreCoin())){
                    tvPeisongGfw.setText(MyApplication.getHHStoreCoin() + NumberUtils.formatPriceNo(total_price + cart_price));
                }else {
                    tvPeisongGfw.setText("€" + NumberUtils.formatPriceNo(total_price + cart_price));
                }

            } else {
                if(!TextUtils.isEmpty(MyApplication.getHHStoreCoin())){
                    tvPeisongGfw.setText(MyApplication.getHHStoreCoin() + NumberUtils.formatPriceNo(total_price));
                }else {
                    tvPeisongGfw.setText("€" + NumberUtils.formatPriceNo(total_price));
                }

            }

            tvPeisongGfw.setTextColor(getResources().getColor(R.color.colorPrimaryDark2));
            SpannableString spannableString = new SpannableString(tvPeisongGfw.getText().toString().trim());
            spannableString.setSpan(new RelativeSizeSpan(0.8f), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tvPeisongGfw.setText(spannableString);

            Log.i("zds", "NumChange: "+num);
            Log.i("zds", "NumChange: "+total_price);
            Log.i("zds", "============================= ");

            EventBus.getDefault().post(new Notice2((num),(total_price)));  // 发送数据通知搜索页面的购物车更新数据
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNumberChan(ItemLeftCount itemCount) {  // 左边的item d的数量改变  count 1 加 1 ； 0 减1

        try {
            Log.i("zdsss", "onNumberChan:"+itemCount.position+"============"+itemCount.count);
            HHItemLeft.DatasBean datasBean=(HHItemLeft.DatasBean)adapter.getItem(itemCount.position);
            if(datasBean!=null){
                if(itemCount.count==1){
                    datasBean.item_num++;
                }else if(itemCount.count==0) {
                    datasBean.item_num--;
                }

                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*左边的分类*/
    public class MyAdapter extends BaseAdapter {

        Context context;
        List<HHItemLeft.DatasBean> list=new ArrayList<>();
        public MyAdapter(Context context, List<HHItemLeft.DatasBean> list) {
            this.context = context;
            this.list = list;
        }

            @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            VieeHolder vieeHolder=null;
            HHItemLeft.DatasBean datasBean=(HHItemLeft.DatasBean)getItem(i);

            if(view==null){
                view= LayoutInflater.from(context).inflate(R.layout.item_huohang_text,viewGroup,false);
                vieeHolder=new VieeHolder(view);
                view.setTag(vieeHolder);
            }else {
                vieeHolder=(VieeHolder) view.getTag();
            }


            try {
                if(datasBean!=null){
                    if(!TextUtils.isEmpty(datasBean.className)){
                        vieeHolder.tv_title.setText(datasBean.className);
                    }

                    vieeHolder.tv_count.setText(datasBean.item_num+"");

                    if(datasBean.item_num>0){
                        vieeHolder.tv_count.setVisibility(View.VISIBLE);
                    }else {
                        vieeHolder.tv_count.setVisibility(View.INVISIBLE);
                    }

                }

                if (selectedPosition == i) {
                    vieeHolder.rela_bg.setSelected(true);
                    vieeHolder.rela_bg.setPressed(true);
                    vieeHolder.rela_bg.setBackgroundColor(context.getResources().getColor(R.color.white));
                } else {
                    vieeHolder.rela_bg.setSelected(false);
                    vieeHolder.rela_bg.setPressed(false);
                    vieeHolder.rela_bg.setBackgroundColor(context.getResources().getColor(R.color.hh_select_color));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return view;
        }

        public class VieeHolder{
            TextView tv_title,tv_count;
            RelativeLayout rela_bg;

            public VieeHolder(View view) {
                tv_title=(TextView) view.findViewById(R.id.tv_title);
                tv_count=(TextView) view.findViewById(R.id.tv_count);
                rela_bg=(RelativeLayout) view.findViewById(R.id.relative);
            }
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        private int selectedPosition = -1;// 选中的位置

    }

}
