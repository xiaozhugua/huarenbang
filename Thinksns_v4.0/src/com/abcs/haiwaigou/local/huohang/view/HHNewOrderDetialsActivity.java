package com.abcs.haiwaigou.local.huohang.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abcs.haiwaigou.activity.PayWayActivity;
import com.abcs.haiwaigou.broadcast.MyUpdateUI;
import com.abcs.haiwaigou.local.huohang.adapter.HHDetialsPartAdapter;
import com.abcs.haiwaigou.local.huohang.bean.DatasEntrySer;
import com.abcs.haiwaigou.local.huohang.bean.HHODetialsAds;
import com.abcs.haiwaigou.local.huohang.bean.NewOrderDetials;
import com.abcs.haiwaigou.model.ExtendOrderGoodsEntry;
import com.abcs.haiwaigou.utils.NumberUtils;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.dialog.PromptDialog;
import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.huaqiaobang.model.Options;
import com.abcs.huaqiaobang.util.Complete;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.huaqiaobang.ytbt.common.utils.ToastUtil;
import com.abcs.sociax.android.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/*新版货行的订单详情*/
public class HHNewOrderDetialsActivity extends BaseActivity {


    Handler handler = new Handler();

    NewOrderDetials newOrderDetials;
    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.t_title_name)
    TextView tTitleName;
    @InjectView(R.id.relative_top)
    RelativeLayout relativeTop;
    @InjectView(R.id.tv_state)
    TextView tvState;
    @InjectView(R.id.tv_time)
    TextView tvTime;
    @InjectView(R.id.tv_money)
    TextView tvMoney;
    @InjectView(R.id.relative_state)
    RelativeLayout relativeState;
    @InjectView(R.id.img_local)
    ImageView imgLocal;
    @InjectView(R.id.relative_address)
    RelativeLayout relativeAddress;
    @InjectView(R.id.img_store_logo)
    ImageView imgStoreLogo;
    @InjectView(R.id.tv_store_name)
    TextView tvStoreName;
    @InjectView(R.id.relative_to_store)
    RelativeLayout relativeToStore;
    @InjectView(R.id.relative_shangjia)
    RelativeLayout relative_shangjia;
    @InjectView(R.id.linear_goods)
    com.abcs.hqbtravel.wedgt.MyListView linearGoods;
    @InjectView(R.id.tv_more)
    TextView tvMore;
    @InjectView(R.id.img_more_up_dowm)
    ImageView imgMoreUpDowm;
    @InjectView(R.id.linear_more)
    LinearLayout linearMore;
    @InjectView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @InjectView(R.id.tv_order_time)
    TextView tvOrderTime;
    @InjectView(R.id.tv_order_payway)
    TextView tvOrderPayway;
    @InjectView(R.id.tv_order_paisong)
    TextView tvOrderPaisong;
    @InjectView(R.id.tv_copy)
    TextView tvCopy;
    @InjectView(R.id.tv_total_price)
    TextView tvHeji;
    @InjectView(R.id.tv_cancel_order)
    TextView tvCancelOrder;
    @InjectView(R.id.tv_to_pay)
    TextView tvToPay;

    private String orderId;
    HHDetialsPartAdapter adapter;
    List<ExtendOrderGoodsEntry>  defualtDatas=new ArrayList<>();
    List<ExtendOrderGoodsEntry>  moreDatas=new ArrayList<>();

    TextView tvPeopleName;
    TextView tvPeoplePhone;
    TextView tvPeopleAddress;


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotices(String notice) {   // 货行下订单成功，通知刷新列表
        Log.i("zds", "onNotices: " + notice);
        if (!TextUtils.isEmpty(notice) && notice.equals("updateHHoder")) {
           tvToPay.setVisibility(View.GONE);
        }
    }

    Typeface type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hhnew_order_detials);

        tvPeopleName=(TextView)findViewById(R.id.tv_name);
        tvPeoplePhone=(TextView)findViewById(R.id.tv_phone);
        tvPeopleAddress=(TextView)findViewById(R.id.tv_address);

        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        try {
            type = Typeface.createFromAsset(getAssets(), "font/ltheijian.TTF");
        } catch (Exception e) {
            e.printStackTrace();
        }

        newOrderDetials = (NewOrderDetials) getIntent().getSerializableExtra("bean");
        orderId = getIntent().getStringExtra("orderId");
        if (newOrderDetials != null) {
            initAllDatas(newOrderDetials);
        }

        if(!TextUtils.isEmpty(orderId)){
            getAddress(orderId);
        }
    }

    private boolean openMore = true;

    private void initAllDatas(final NewOrderDetials newOrderDetials) {
        try {
        /*状态*/
            if (!TextUtils.isEmpty(newOrderDetials.getOrderState())) {

                if (newOrderDetials.getOrderState().equals("10")) {
                    tvState.setText("等待支付");
                    tvToPay.setVisibility(View.VISIBLE);
                    tvCancelOrder.setVisibility(View.VISIBLE);
                } else if (newOrderDetials.getOrderState().equals("20")) {
                    tvState.setText("等待发货");
                    tvToPay.setVisibility(View.GONE);
                    tvCancelOrder.setVisibility(View.VISIBLE);
                } else if (newOrderDetials.getOrderState().equals("30")) {
                    tvState.setText("等待收货");
                    tvToPay.setVisibility(View.GONE);
                    tvCancelOrder.setVisibility(View.GONE);
                } else if (newOrderDetials.getOrderState().equals("40")) {
                    tvState.setText("已完成");
                    tvToPay.setVisibility(View.GONE);
                    tvCancelOrder.setVisibility(View.GONE);
                } else if (newOrderDetials.getOrderState().equals("0")) {
                    tvState.setText("已取消");
                    tvToPay.setVisibility(View.GONE);
                    tvCancelOrder.setVisibility(View.GONE);
                } else {
                    tvState.setText("未知");
                    tvToPay.setVisibility(View.GONE);
                    tvCancelOrder.setVisibility(View.GONE);
                }
            }

        /*总价*/
            if (newOrderDetials.getAtPrice() > 0) {
                if (type != null) {
                    tvMoney.setTypeface(type);
                    tvHeji.setTypeface(type);
                }

                tvMoney.setText("需付款：" + NumberUtils.formatPriceOuYuan(newOrderDetials.getAtPrice()));
                tvHeji.setText(NumberUtils.formatPriceOuYuan(newOrderDetials.getAtPrice()));

            }

        /*取消订单*/
            tvCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new PromptDialog(HHNewOrderDetialsActivity.this, "取消后无法恢复，确定取消该订单？", new Complete() {
                        @Override
                        public void complete() {
                            ProgressDlgUtil.showProgressDlg("Loading...", HHNewOrderDetialsActivity.this);

                            HttpRequest.sendPost("http://huohang.huaqiaobang.com/mobile/index.php?act=member_order&op=order_cancel", "&order_id=" + orderId + "&key=" + MyApplication.getInstance().getMykey(), new HttpRevMsg() {
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
                                                        Toast.makeText(HHNewOrderDetialsActivity.this, "成功取消！", Toast.LENGTH_SHORT).show();
                                                        MyUpdateUI.sendUpdateCollection(HHNewOrderDetialsActivity.this, MyUpdateUI.ORDER);
                                                    } else {
                                                        Toast.makeText(HHNewOrderDetialsActivity.this, "失败！", Toast.LENGTH_SHORT).show();
                                                    }

                                                    ProgressDlgUtil.stopProgressDlg();
                                                } else {
                                                    ProgressDlgUtil.stopProgressDlg();
                                                    Log.i("zds", "goodsDetail:解析失败");
                                                }
                                            } catch (JSONException e) {
                                                // TODO -generated catch block
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
                }
            });
        /*去支付*/
            tvToPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HHNewOrderDetialsActivity.this, PayWayActivity.class);
                    intent.putExtra("pay_sn", newOrderDetials.getPay_sn());
                    intent.putExtra("pay_sn_huodao", newOrderDetials.getOrder_sn());
                    intent.putExtra("yungou", false);
                    intent.putExtra("isFromOrder", true);
                    intent.putExtra("total_money", Double.valueOf(newOrderDetials.getAtPrice()));
                    startActivity(intent);
                }
            });
        /*店铺*/
            if (newOrderDetials.getStore() != null) {

                if (!TextUtils.isEmpty(newOrderDetials.getStore().storeNewLogo)) {
                    MyApplication.imageLoader.displayImage(newOrderDetials.getStore().storeNewLogo, imgStoreLogo, Options.getListOptions2());
                }
                if (!TextUtils.isEmpty(newOrderDetials.getStore().storeName)) {
                    tvStoreName.setText(newOrderDetials.getStore().storeName);
                }
                /*联系商家*/
                if (!TextUtils.isEmpty(newOrderDetials.getStore().storePhone)) {
                    relative_shangjia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            call(newOrderDetials.getStore().storePhone);
                        }
                    });
                }

                /*进店铺*/
                relativeToStore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (newOrderDetials.getStore() != null) {
                            DatasEntrySer datasEntrySer = new DatasEntrySer();
                            datasEntrySer.setActivity(newOrderDetials.getStore().activity);
                            datasEntrySer.setStoreAddress(newOrderDetials.getStore().storeAddress);
                            datasEntrySer.setStoreCoin(newOrderDetials.getStore().storeCoin);
                            datasEntrySer.setStoreDes(newOrderDetials.getStore().storeDes);
    //                                datasEntrySer.setStoreGoodsDetails(newOrderDetials.getStore().storeGoodsDetails);
                            datasEntrySer.setStoreId(newOrderDetials.getStore().storeId);
                            datasEntrySer.setStoreNewLogo(newOrderDetials.getStore().storeNewLogo);
                            datasEntrySer.setStoreName(newOrderDetials.getStore().storeName);
                            datasEntrySer.setStorePhone(newOrderDetials.getStore().storePhone);
                            datasEntrySer.setStoreSendTime(newOrderDetials.getStore().storeSendTime);
                            datasEntrySer.setNgc_id("");
                            datasEntrySer.setGoods_Id("");
                            checkIsSuccess(datasEntrySer, "");
                            MyApplication.saveHHStoreCoin(newOrderDetials.getStore().storeCoin);
                        }
                    }
                });
            }

            /*商品列表*/
            if (newOrderDetials.getExtendOrderGoods() != null && newOrderDetials.getExtendOrderGoods().size() > 0) {
                adapter=new HHDetialsPartAdapter(this);
                linearGoods.setAdapter(adapter);
                linearMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (openMore) { // 展开
                            openMore = false;
                            tvMore.setText("点击收起");
                            imgMoreUpDowm.setImageResource(R.drawable.hh_order_detials_up);
                            adapter.DatasAndAdd(moreDatas);
                        } else {   // 收起
                            openMore = true;
                            tvMore.setText("展开更多");
                            imgMoreUpDowm.setImageResource(R.drawable.hh_order_detials_down);
                            adapter.clearDatasAndAdd(defualtDatas);
                        }
                    }
                });

                linearGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ExtendOrderGoodsEntry evaluation_state=(ExtendOrderGoodsEntry)parent.getItemAtPosition(position);
                        if (newOrderDetials.getStore() != null) {
                            DatasEntrySer datasEntrySer = new DatasEntrySer();
                            datasEntrySer.setActivity(newOrderDetials.getStore().activity);
                            datasEntrySer.setStoreAddress(newOrderDetials.getStore().storeAddress);
                            datasEntrySer.setStoreCoin(newOrderDetials.getStore().storeCoin);
                            datasEntrySer.setStoreDes(newOrderDetials.getStore().storeDes);
    //                                datasEntrySer.setStoreGoodsDetails(newOrderDetials.getStore().storeGoodsDetails);
                            datasEntrySer.setStoreId(newOrderDetials.getStore().storeId);
                            datasEntrySer.setStoreNewLogo(newOrderDetials.getStore().storeNewLogo);
                            datasEntrySer.setStoreName(newOrderDetials.getStore().storeName);
                            datasEntrySer.setStorePhone(newOrderDetials.getStore().storePhone);
                            datasEntrySer.setStoreSendTime(newOrderDetials.getStore().storeSendTime);
                            datasEntrySer.setNgc_id(evaluation_state.ngc_id);
                            datasEntrySer.setGoods_Id(evaluation_state.goodsId);
                            checkIsSuccess(datasEntrySer, "");
                            MyApplication.saveHHStoreCoin(newOrderDetials.getStore().storeCoin);
                        }
                    }
                });

                if (newOrderDetials.getExtendOrderGoods().size() < 4) {  // 默认显示3个
                    adapter.clearDatasAndAdd(newOrderDetials.getExtendOrderGoods());
                    linearMore.setVisibility(View.INVISIBLE);
                } else {
                    moreDatas.clear();
                    defualtDatas.clear();
                    for (int j = 0; j < newOrderDetials.getExtendOrderGoods().size(); j++) {
                        ExtendOrderGoodsEntry evaluation_state = newOrderDetials.getExtendOrderGoods().get(j);
                        if(j<3){
                            defualtDatas.add(evaluation_state);
                        }else {
                            moreDatas.add(evaluation_state);
                        }
                    }

                    adapter.clearDatasAndAdd(defualtDatas);
                    linearMore.setVisibility(View.VISIBLE);
                }
            }

        /*订单信息*/
            if (!TextUtils.isEmpty(newOrderDetials.getOrder_sn())) {
                tvOrderNumber.setText("订单编号：" + newOrderDetials.getOrder_sn());
                tvCopy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        copy(newOrderDetials.getOrder_sn());
                    }
                });
            }

            if (newOrderDetials.getAddTime() > 0) {
                tvOrderTime.setText("下单时间：" + Util.formatzjz.format(newOrderDetials.getAddTime() * 1000) + "");
            }
            if (newOrderDetials.getPay_ways() == 0) {
                tvOrderPayway.setText("支付方式：货到付款/在线支付");
            } else if (newOrderDetials.getPay_ways() == 1) {
                tvOrderPayway.setText("支付方式：货到付款");
            } else if (newOrderDetials.getPay_ways() == 2) {
                tvOrderPayway.setText("支付方式：在线支付");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void copy(String string) {
        try {
            ClipboardManager clipboardManager;
            clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("info", string);
            clipboardManager.setPrimaryClip(clipData);
            ToastUtil.showMessage("订单号已经复制到粘贴板，您可以粘贴到任意地方！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用拨号界面
     *
     * @param phone 电话号码
     */
    private void call(String phone) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkIsSuccess(final DatasEntrySer bean, final String goods_Id) {
        try {
            Intent intent = new Intent(this, NewHuoHangActivity.class);
            intent.putExtra("datas", bean);
            intent.putExtra("goods_Id", goods_Id);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static final String TAG = "zds";
    private void getAddress(String orderId) {
        ProgressDlgUtil.showProgressDlg("loading",this);
//        http://huohang.huaqiaobang.com/mobile/index.php?act=member_order&op=new_show_order&key=d70a3678dbdeca8fcc17e54b02383a5c&order_id=8143
        HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=member_order&op=new_show_order&key=" + MyApplication.getInstance().getMykey() + "&order_id=" + orderId, new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "revMsg: "+msg);

                        if (!TextUtils.isEmpty(msg)) {
                            try {
                                HHODetialsAds hhoDetialsAds=new Gson().fromJson(msg, HHODetialsAds.class);
                                if(hhoDetialsAds!=null&&hhoDetialsAds.state==1){
                                    if(hhoDetialsAds.datas!=null){
                                        tvPeopleName.setText(hhoDetialsAds.datas.reciverName+"");
                                        tvPeoplePhone.setText(hhoDetialsAds.datas.phone+"");
                                        tvPeopleAddress.setText("地址：" + hhoDetialsAds.datas.address);
                                        if(hhoDetialsAds.datas.leftTime>0){
                                            tvTime.setText("剩余：" + formatSeconds(hhoDetialsAds.datas.leftTime));  // 剩余时间
                                        }
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

    }

    /**
     * 秒转化为天小时分秒字符串
     *
     * @param seconds
     * @return String
     */
    public static String formatSeconds(int seconds) {
        String timeStr = seconds + "秒";
        try {
            if (seconds > 60) {
                long second = seconds % 60;
                long min = seconds / 60;
                timeStr = min + "分" + second + "秒";
                if (min > 60) {
                    min = (seconds / 60) % 60;
                    long hour = (seconds / 60) / 60;
                    timeStr = hour + "小时" + min + "分" + second + "秒";
                    if (hour > 24) {
                        hour = ((seconds / 60) / 60) % 24;
                        long day = (((seconds / 60) / 60) / 24);
                        timeStr = day + "天" + hour + "小时" + min + "分" + second + "秒";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStr;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.relative_back, R.id.relative_to_store, R.id.tv_cancel_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back:
                finish();
                break;
            case R.id.relative_to_store:
                if (newOrderDetials != null) {
                    DatasEntrySer datasEntrySer = new DatasEntrySer();
                    datasEntrySer.setActivity(newOrderDetials.getStore().activity);
                    datasEntrySer.setStoreAddress(newOrderDetials.getStore().storeAddress);
                    datasEntrySer.setStoreCoin(newOrderDetials.getStore().storeCoin);
                    datasEntrySer.setStoreDes(newOrderDetials.getStore().storeDes);
//                                datasEntrySer.setStoreGoodsDetails(newOrderDetials.getStore().storeGoodsDetails);
                    datasEntrySer.setStoreId(newOrderDetials.getStore().storeId);
                    datasEntrySer.setStoreNewLogo(newOrderDetials.getStore().storeNewLogo);
                    datasEntrySer.setStoreName(newOrderDetials.getStore().storeName);
                    datasEntrySer.setStorePhone(newOrderDetials.getStore().storePhone);
                    datasEntrySer.setStoreSendTime(newOrderDetials.getStore().storeSendTime);
                    datasEntrySer.setNgc_id("");
                    datasEntrySer.setGoods_Id("");
                    checkIsSuccess(datasEntrySer, "");
                    MyApplication.saveHHStoreCoin(newOrderDetials.getStore().storeCoin);
                }
                break;
            case R.id.tv_cancel_order:
                new PromptDialog(this, "取消后无法恢复，确定取消该订单？", new Complete() {
                    @Override
                    public void complete() {
                        ProgressDlgUtil.showProgressDlg("Loading...", HHNewOrderDetialsActivity.this);

                        HttpRequest.sendPost("http://huohang.huaqiaobang.com/mobile/index.php?act=member_order&op=order_cancel", "&order_id=" + orderId + "&key=" + MyApplication.getInstance().getMykey(), new HttpRevMsg() {
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
                                                    Toast.makeText(HHNewOrderDetialsActivity.this, "成功取消！", Toast.LENGTH_SHORT).show();
                                                    MyUpdateUI.sendUpdateCollection(HHNewOrderDetialsActivity.this, MyUpdateUI.ORDER);
                                                    tvCancelOrder.setVisibility(View.INVISIBLE);
                                                } else {
                                                    Toast.makeText(HHNewOrderDetialsActivity.this, "失败！", Toast.LENGTH_SHORT).show();
                                                }

                                                ProgressDlgUtil.stopProgressDlg();
                                            } else {
                                                ProgressDlgUtil.stopProgressDlg();
                                                Log.i("zds", "goodsDetail:解析失败");
                                            }
                                        } catch (JSONException e) {
                                            // TODO -generated catch block
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
                break;
        }
    }


}
