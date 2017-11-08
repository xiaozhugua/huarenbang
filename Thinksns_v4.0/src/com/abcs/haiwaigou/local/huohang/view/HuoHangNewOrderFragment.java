package com.abcs.haiwaigou.local.huohang.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abcs.haiwaigou.activity.ApplyRefundActivity;
import com.abcs.haiwaigou.activity.PayWayActivity;
import com.abcs.haiwaigou.broadcast.MyBroadCastReceiver;
import com.abcs.haiwaigou.broadcast.MyUpdateUI;
import com.abcs.haiwaigou.local.huohang.bean.DatasEntrySer;
import com.abcs.haiwaigou.local.huohang.bean.NewOrderDetials;
import com.abcs.haiwaigou.model.ExtendOrderGoodsEntry;
import com.abcs.haiwaigou.model.HuoHMyOrder;
import com.abcs.haiwaigou.utils.NumberUtils;
import com.abcs.haiwaigou.view.BaseFragment;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.dialog.PromptDialog;
import com.abcs.huaqiaobang.util.Complete;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.sociax.android.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.abcs.sociax.android.R.id.tv_wuliu;
import static com.abcs.sociax.android.R.id.tv_zailai;

/**
 */
public class HuoHangNewOrderFragment extends BaseFragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener  {

    Activity activity;
    @InjectView(R.id.easy_recyclevive)
    EasyRecyclerView easyRecyclevive;
    @InjectView(R.id.linear_view_no_order)
    AutoLinearLayout linear_view_no_order;
    private View view;
    boolean first = true;
    String goods_id;
    MyBroadCastReceiver myBroadCastReceiver;
    MyAdapter adapter;

    private String type;

    public static HuoHangNewOrderFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", type);
        HuoHangNewOrderFragment orderFragment = new HuoHangNewOrderFragment();
        orderFragment.setArguments(bundle);
        return orderFragment;
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
                isRefresh=true;
                pageNo=1;
                initAllDates();
                Log.i("zds", "更新订单");
            }
        }

        @Override
        public void update(Intent intent) {

        }
    };


    private int pageNo = 1;
    private int page_count = 0;
    Handler handler = new Handler();
    private boolean isRefresh=true;



    private void initAllDates() {
        try {
            if (first) {
                first=false;
                ProgressDlgUtil.showProgressDlg("Loading...", activity);
            }

//        http://newapi.tuling.me/huaqiaobang/mobile/index.php?act=member_order&op=native_order_list&key=7661f71726932e44f85f677671ba7ebb
            HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=member_order&op=new_native_order_list&key=" + MyApplication.getInstance().getMykey() + "&order_state=" + type + "&page=" + pageNo, new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("zds", "msg=" + msg);
                            if(isRefresh){
                                isRefresh=false;
                                adapter.clear();
                            }

                            if (TextUtils.isEmpty(msg)) {
//                                Toast.makeText(activity, "暂无数据！", Toast.LENGTH_LONG).show();
                                linear_view_no_order.setVisibility(View.VISIBLE);
                            } else {
                                try {
                                    HuoHMyOrder response = new Gson().fromJson(msg, HuoHMyOrder.class);
                                    if(response!=null){
                                        if (response.state == 1) {
                                            page_count=response.page_count;
                                            linear_view_no_order.setVisibility(View.GONE);
                                            if (response.datas != null) {
                                                if (response.datas.size() > 0) {
                                                    adapter.addAll(response.datas);
                                                } else {
                                                    Toast.makeText(activity, "暂无数据！", Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            if(response.page_count>pageNo){
                                                pageNo=pageNo+1;
                                            }else {
                                                pageNo=-1;
                                            }

                                        }else {
                                            linear_view_no_order.setVisibility(View.VISIBLE);
                                            pageNo=-1;
                                            adapter.stopMore();
                                        }
                                    }
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
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

    private String updateHHoder;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotices(String notice) {   // 货行下订单成功，通知刷新列表
        Log.i("zds", "onNotices: " + notice);
        if (!TextUtils.isEmpty(notice) && notice.equals("updateHHoder")) {
            updateHHoder=notice;
            isRefresh=true;
            pageNo=1;
            initAllDates();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.activity_hhnew_order_message, container,false);
        activity = getActivity();
        ButterKnife.inject(this, view);
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
        }


        myBroadCastReceiver = new MyBroadCastReceiver(activity, updateUI);
        myBroadCastReceiver.register();
        easyRecyclevive.setLayoutManager(new LinearLayoutManager(activity));
        easyRecyclevive.setRefreshListener(this);
        adapter=new MyAdapter(activity);
        easyRecyclevive.setAdapter(adapter);
        adapter.setNoMore(R.layout.view_nomore);
        adapter.setMore(R.layout.view_more, this);
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
//        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent=new Intent(activity,HHNewOrderDetialsActivity.class);
//                startActivity(intent);
//            }
//        });
        
        initAllDates();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        myBroadCastReceiver.unRegister();
        super.onDestroy();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onRefresh() {
        pageNo=1;
        isRefresh=true;
        initAllDates();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                easyRecyclevive.setRefreshing(false);
            }
        }, 300);

    }

    @Override
    public void onLoadMore() {
        Log.i("xxx", "onLoadMore: ");
        if(pageNo==-1){
            adapter.stopMore();
            return;
        }
        initAllDates();
    }
    
    public class MyAdapter extends RecyclerArrayAdapter<HuoHMyOrder.DatasEntry> {

        public MyAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyAdapter.MyHoder(parent);
        }

        public class MyHoder extends BaseViewHolder<HuoHMyOrder.DatasEntry> {

            ImageView imgStoreLogo;
            TextView tvStoreName;
            TextView tvOrderState;
            AutoRelativeLayout relativeToStore;
            AutoLinearLayout linearGoodsList;
            AutoRelativeLayout relative_main;
            TextView tvCount;
            TextView tvTotalPrice;
            TextView tvZailai;
            TextView tvWuliu;


            public MyHoder(ViewGroup parent) {
                super(parent, R.layout.item_hh_order_message);

                imgStoreLogo=(ImageView) itemView.findViewById(R.id.img_store_logo);
                tvStoreName=(TextView) itemView.findViewById(R.id.tv_store_name);
                tvOrderState=(TextView) itemView.findViewById(R.id.tv_order_state);
                tvCount=(TextView) itemView.findViewById(R.id.tv_count);
                tvTotalPrice=(TextView) itemView.findViewById(R.id.tv_total_price);
                tvZailai=(TextView) itemView.findViewById(tv_zailai);
                tvWuliu=(TextView) itemView.findViewById(tv_wuliu);
                relativeToStore=(AutoRelativeLayout) itemView.findViewById(R.id.relative_to_store);
                linearGoodsList=(AutoLinearLayout) itemView.findViewById(R.id.linear_goods_list);
                relative_main=(AutoRelativeLayout) itemView.findViewById(R.id.relative_main);

            }

            @Override
            public void setData(final HuoHMyOrder.DatasEntry data) {

                try {
                    if (data != null) {

                        if(!TextUtils.isEmpty(data.store.storeNewLogo)){
                            MyApplication.imageLoader.displayImage(data.store.storeNewLogo, imgStoreLogo, MyApplication.getListOptions());
                        }else {
                            MyApplication.imageLoader.displayImage("", imgStoreLogo, MyApplication.getListOptions());
                        }
                        if(!TextUtils.isEmpty(data.store.storeName)){
                            tvStoreName.setText(data.store.storeName);
                        }else {
                            tvStoreName.setText("");
                        }

                        /*状态*/
                        if(data.orderState.equals("10")){
                            tvOrderState.setText("订单待支付");
                            tvWuliu.setText("取消订单");
                            tvZailai.setText("去支付");
                            tvZailai.setTextColor(Color.parseColor("#ff4956"));
                            tvWuliu.setVisibility(View.VISIBLE);
                        }else   if(data.orderState.equals("20")){
                            if(data.pay_ways==2){  // 在线支付
                                if(!data.refund_state.equals("0")){
                                    tvZailai.setText("退款中");
                                }else {
                                    tvZailai.setText("申请退款");
                                }
                            }else {
                                tvZailai.setText("再次购买");
                            }
                            tvZailai.setTextColor(Color.parseColor("#333333"));
                            tvOrderState.setText("订单待发货");
                            tvWuliu.setVisibility(View.INVISIBLE);
                        }else   if(data.orderState.equals("30")){
                            tvOrderState.setText("订单待收货");
                            tvZailai.setText("确认收货");
                            tvWuliu.setText("查看物流");
                            tvWuliu.setVisibility(View.VISIBLE);
                            tvZailai.setTextColor(Color.parseColor("#333333"));
                            tvWuliu.setTextColor(Color.parseColor("#333333"));
                        }else   if(data.orderState.equals("40")){
                            tvOrderState.setText("订单已完成");
                            tvZailai.setText("再次购买");
                            tvWuliu.setText("查看物流");
                            tvWuliu.setVisibility(View.VISIBLE);
                            tvZailai.setTextColor(Color.parseColor("#333333"));
                            tvWuliu.setTextColor(Color.parseColor("#333333"));
                        }else  if(data.orderState.equals("0")){
                            tvOrderState.setText("订单已取消");
                            tvWuliu.setVisibility(View.INVISIBLE);
                            tvZailai.setText("再次购买");
                            tvZailai.setTextColor(Color.parseColor("#333333"));
                        }else  {
                            tvOrderState.setText("未知");
                            tvWuliu.setVisibility(View.INVISIBLE);
                            tvZailai.setText("再次购买");
                            tvZailai.setTextColor(Color.parseColor("#333333"));
                        }

                        /*去店铺*/
                        relativeToStore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (data.store != null) {
                                    DatasEntrySer datasEntrySer = new DatasEntrySer();
                                    datasEntrySer.setActivity(data.store.activity);
                                    datasEntrySer.setStoreAddress(data.store.storeAddress);
                                    datasEntrySer.setStoreCoin(data.store.storeCoin);
                                    datasEntrySer.setStoreDes(data.store.storeDes);
    //                                datasEntrySer.setStoreGoodsDetails(data.store.storeGoodsDetails);
                                    datasEntrySer.setStoreId(data.store.storeId);
                                    datasEntrySer.setStoreNewLogo(data.store.storeNewLogo);
                                    datasEntrySer.setStoreName(data.store.storeName);
                                    datasEntrySer.setStorePhone(data.store.storePhone);
                                    datasEntrySer.setStoreSendTime(data.store.storeSendTime);
                                    datasEntrySer.setNgc_id("");
                                    datasEntrySer.setGoods_Id("");
                                    checkIsSuccess(datasEntrySer, "");
                                    MyApplication.saveHHStoreCoin(data.store.storeCoin);
                                }
                            }
                        });
                        /*商品数量和总价*/
                        tvCount.setText("共"+data.extendOrderGoods.size()+"件商品，总计");
                        tvTotalPrice.setText(NumberUtils.formatPriceOuYuan(Double.valueOf(data.atPrice)));
                        /*商品详情*/
                        relative_main.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(activity,HHNewOrderDetialsActivity.class);
                                NewOrderDetials newOrderDetials=new NewOrderDetials();
                                newOrderDetials.setAddTime(data.addTime);
                                newOrderDetials.setAtPrice(Double.valueOf(data.atPrice));
                                newOrderDetials.setExtendOrderGoods(data.extendOrderGoods);
                                newOrderDetials.setOrder_sn(data.orderSn);
                                newOrderDetials.setPay_sn(data.paySn);
                                newOrderDetials.setOrderState(data.orderState);
                                newOrderDetials.setPay_ways(data.pay_ways);
                                newOrderDetials.setStore(data.store);
                                intent.putExtra("bean",newOrderDetials);
                                intent.putExtra("orderId",data.orderId);
                               startActivity(intent);

                            }
                        });
                        final ArrayList<String> goodsIdList = new ArrayList<String>();
                        final ArrayList<String> goodsNumList = new ArrayList<String>();
                        goodsIdList.clear();
                        goodsNumList.clear();

                        /*查看物流*/
                        tvWuliu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(tvWuliu.getText().toString().trim().contains("查看物流")){
                                    Intent intent=new Intent(activity,HHWuLiuActivity.class);
                                    intent.putExtra("orderId",data.orderId);
                                    intent.putExtra("state",data.orderState);
                                    startActivity(intent);
                                }else if(tvWuliu.getText().toString().trim().contains("取消订单")) {
                                    new PromptDialog(activity, "取消后无法恢复，确定取消该订单？", new Complete() {
                                        @Override
                                        public void complete() {
                                            ProgressDlgUtil.showProgressDlg("Loading...", activity);
                                            HttpRequest.sendPost("http://huohang.huaqiaobang.com/mobile/index.php?act=member_order&op=order_cancel", "&order_id=" + data.orderId + "&key=" + MyApplication.getInstance().getMykey(), new HttpRevMsg() {
                                                @Override
                                                public void revMsg(final String msg) {
                                                    handler.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            try {
                                                                JSONObject object = new JSONObject(msg);
                                                                Log.i("zds", "msg=" + msg);
                                                                if (object.getInt("code") == 200) {
                                                                    if (object.optString("datas").equals("1")) {
                                                                        Toast.makeText(activity, "成功取消！", Toast.LENGTH_SHORT).show();
                                                                        MyUpdateUI.sendUpdateCollection(activity, MyUpdateUI.ORDER);
                                                                        MyUpdateUI.sendUpdateCollection(activity,MyUpdateUI.MYORDERNUM);
                                                                    } else {
                                                                        Toast.makeText(activity, "失败！", Toast.LENGTH_SHORT).show();
                                                                    }

                                                                    ProgressDlgUtil.stopProgressDlg();
                                                                } else {
                                                                    ProgressDlgUtil.stopProgressDlg();
                                                                    Log.i("zds", "goodsDetail:解析失败");
                                                                }
                                                            } catch (JSONException e) {
                                                                // TODO Auto-generated catch block
                                                                Log.i("zds", e.toString());
                                                                Log.i("zds", msg);
                                                                e.printStackTrace();
                                                                ProgressDlgUtil.stopProgressDlg();
                                                            }
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }).show();
                                }else {
                                    return;
                                }

                            }
                        });
                        /*再来一单*/
                        tvZailai.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(tvZailai.getText().toString().trim().contains("去支付")){
                                    Intent intent=new Intent(activity, PayWayActivity.class);
                                    intent.putExtra("pay_sn",data.paySn);
                                    intent.putExtra("pay_sn_huodao",data.orderSn);
                                    intent.putExtra("yungou",false);
                                    intent.putExtra("isFromOrder", true);
                                    intent.putExtra("total_money",Double.valueOf(data.atPrice));
                                    startActivity(intent);
                                }else  if(tvZailai.getText().toString().trim().contains("申请退款")){
                                    Intent intent = new Intent(activity, ApplyRefundActivity.class);
                                    intent.putExtra("isOrder", true);
                                    intent.putExtra("title", "订单退款");
                                    intent.putExtra("order_id",data.orderId);
                                    intent.putExtra("order_money", data.atPrice);
                                    startActivity(intent);
                                }else  if(tvZailai.getText().toString().trim().contains("再次购买")){
                                 /*判断商品*/
                                    StringBuilder goods_ids = new StringBuilder();
                                    StringBuilder quantity_1 = new StringBuilder();
                                    for (int i=0;i<goodsIdList.size();i++){
                                        goods_ids.append(goodsIdList.get(i));
                                        goods_ids.append(",");
                                        quantity_1.append(goodsNumList.get(i));
                                        quantity_1.append(",");
                                    }
                                    Log.i("zds", "buy_again: " + goods_ids);
                                    Log.i("zds", "buy_again: " + quantity_1);

                                    Log.i("zds", "buy_again: url" + "http://huohang.huaqiaobang.com/mobile/index.php?act=member_cart&op=ios_cart_add&" + "goods_id=" + goods_ids + "&quantity=" + quantity_1 + "&key=" + MyApplication.getInstance().getMykey() + "&version=1.0" + "&store_id=" + data.storeId);
                                    HttpRequest.sendPost("http://huohang.huaqiaobang.com/mobile/index.php?act=member_cart&op=ios_cart_add", "goods_id=" + goods_ids + "&quantity=" + quantity_1 + "&key=" + MyApplication.getInstance().getMykey() + "&version=1.0" + "&store_id=" + data.storeId, new HttpRevMsg() {
                                        @Override
                                        public void revMsg(final String msg) {

                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {

                                                    if (TextUtils.isEmpty(msg)) {
                                                        return;
                                                    } else {

                                                        try {
                                                            JSONObject object = new JSONObject(msg);
                                                            if (object.getInt("code") == 200) {

                                                                Intent intent = new Intent(activity, HuoHangCartActivity.class);
                                                                intent.putExtra("store_id", data.storeId);
                                                                startActivity(intent);


                                                            } else {
                                                                Log.i("zds", "add:解析失败");
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }else  if(tvZailai.getText().toString().trim().contains("确认收货")){
                                    new PromptDialog(activity, "是否确认收货？", new Complete() {
                                        @Override
                                        public void complete() {
                                            ProgressDlgUtil.showProgressDlg("Loading...", activity);
                                            HttpRequest.sendPost("http://huohang.huaqiaobang.com/mobile/index.php?act=member_order&op=order_receive", "&order_id=" + data.orderId + "&key=" + MyApplication.getInstance().getMykey(), new HttpRevMsg() {
                                                @Override
                                                public void revMsg(final String msg) {
                                                    handler.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            try {
                                                                JSONObject object = new JSONObject(msg);
                                                                Log.i("zds", "msg=" + msg);
                                                                if (object.getInt("code") == 200) {
                                                                    if (object.optString("datas").equals("1")) {
                                                                        Toast.makeText(activity, "成功收货！", Toast.LENGTH_SHORT).show();
                                                                        MyUpdateUI.sendUpdateCollection(activity, MyUpdateUI.ORDER);
                                                                        MyUpdateUI.sendUpdateCollection(activity,MyUpdateUI.MYORDERNUM);
                                                                    } else {
                                                                        Toast.makeText(activity, "失败！", Toast.LENGTH_SHORT).show();
                                                                    }

                                                                    ProgressDlgUtil.stopProgressDlg();
                                                                } else {
                                                                    ProgressDlgUtil.stopProgressDlg();
                                                                    Log.i("zds", "goodsDetail:解析失败");
                                                                }
                                                            } catch (JSONException e) {
                                                                // TODO Auto-generated catch block
                                                                Log.i("zds", e.toString());
                                                                Log.i("zds", msg);
                                                                e.printStackTrace();
                                                                ProgressDlgUtil.stopProgressDlg();
                                                            }
                                                        }
                                                    });

                                                }
                                            });
                                        }
                                    }).show();
                                }else {
                                    return;
                                }
                            }
                        });

                        linearGoodsList.removeAllViews();
                        linearGoodsList.invalidate();
                        for (int j = 0; j < data.extendOrderGoods.size(); j++) {

                            final ExtendOrderGoodsEntry evaluation_state=data.extendOrderGoods.get(j);

                            goodsIdList.add(evaluation_state.goodsId);
                            goodsNumList.add(evaluation_state.goodsNum);

                            View itemView = activity.getLayoutInflater().inflate(R.layout.item_hh_order_message_goodsnum_item, null);
                            TextView tv_title=(TextView) itemView.findViewById(R.id.tv_name);
                            TextView t_num=(TextView) itemView.findViewById(R.id.tv_count);


                            tv_title.setText(evaluation_state.goodsName);
                            t_num.setText("X"+evaluation_state.goodsNum);

                            linearGoodsList.addView(itemView);

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void checkIsSuccess(final DatasEntrySer bean, final String goods_Id) {
        Intent intent = new Intent(activity, NewHuoHangActivity.class);
        intent.putExtra("datas", bean);
        intent.putExtra("goods_Id", goods_Id);
        startActivity(intent);

    }

}
