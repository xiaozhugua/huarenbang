package com.abcs.haiwaigou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.utils.NumberUtils;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.activity.FuyouPayActivity;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.huaqiaobang.pay.AliPay;
import com.abcs.huaqiaobang.util.NoDoubleClickListener;
import com.abcs.sociax.android.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PayWayActivity extends BaseActivity implements View.OnClickListener {

    public static final String YH = "yhk";
    public static final String WX = "wx";
    public static final String ZFB = "zfb";
    public static final String HUODAOFUKUAN = "huodaofukuan";

    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.tljr_txt_country_title)
    TextView tljrTxtCountryTitle;
    @InjectView(R.id.seperate)
    View seperate;
    @InjectView(R.id.tljr_grp_payway_title)
    RelativeLayout tljrGrpPaywayTitle;
    @InjectView(R.id.t_goods_total_money)
    TextView tGoodsTotalMoney;
    @InjectView(R.id.image2111)
    ImageView image2111;
    @InjectView(R.id.image211)
    ImageView image211;
    @InjectView(R.id.yh_check)
    CheckBox yhCheck;
    @InjectView(R.id.img_yl)
    ImageView imgYl;
    @InjectView(R.id.image22161)
    ImageView image22161;
    @InjectView(R.id.relative_yl)
    RelativeLayout relativeYl;
    @InjectView(R.id.wx_check)
    CheckBox wxCheck;
    @InjectView(R.id.img_wx)
    ImageView imgWx;
    @InjectView(R.id.relative_wx)
    RelativeLayout relativeWx;
    @InjectView(R.id.img_zfb)
    ImageView imgZfb;
    @InjectView(R.id.textView16)
    TextView textView16;
    @InjectView(R.id.image22131)
    ImageView image22131;
    @InjectView(R.id.zfb_check)
    CheckBox zfbCheck;
    @InjectView(R.id.huodao_pay_check)
    CheckBox huodao_pay_check;
    @InjectView(R.id.relative_zfb)
    RelativeLayout relativeZfb;
    @InjectView(R.id.tv_pay)
    Button tvPay;
    @InjectView(R.id.btn_pay)
    RelativeLayout btnPay;
    @InjectView(R.id.relative_huodao_pay)
    RelativeLayout relative_huodao_pay;  // 货到付款
    @InjectView(R.id.RelativeLayout1)
    LinearLayout RelativeLayout1;

    private Handler handler = new Handler();
    double total;
    int num;
    String activity_id;
    String pay_sn, pay_sn_huodao;
    boolean isYungou;
    boolean isUseVou;
    boolean isFromOrder;
    boolean isYH;
    boolean isWX;
    boolean isZFB;
    boolean isHuoDaoFuKuan;
    boolean isYE = false;
    boolean needMore = false;
    boolean isRC = false;
    boolean isYuE = false;
    boolean isPay = false;
    String type;
    IWXAPI iwxapi;
    private RequestQueue mRequestQueue;
    private String oderdec;/*下单成功描述*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hwg_activity_pay_way);
        ButterKnife.inject(this);
        AliPay.getInstance().init(this);
        mRequestQueue = Volley.newRequestQueue(this);

        //AppID：wxe134ccc9e61300a0
        iwxapi = WXAPIFactory.createWXAPI(this, "wxe134ccc9e61300a0");
        iwxapi.registerApp("wxe134ccc9e61300a0");
        setOnListener();
        isYungou = getIntent().getBooleanExtra("yungou", false);
        isFromOrder = getIntent().getBooleanExtra("isFromOrder", false);
        if (!isYungou) {
            total = (double) getIntent().getSerializableExtra("total_money");
            pay_sn = getIntent().getStringExtra("pay_sn");
            pay_sn_huodao = getIntent().getStringExtra("pay_sn_huodao");
//            oderdec = getIntent().getStringExtra("oderdec");
            tGoodsTotalMoney.setText(NumberUtils.formatPriceOuYuan(total));
        }
    }

    private void setOnListener() {
        relativeBack.setOnClickListener(this);
        relative_huodao_pay.setOnClickListener(this);
        relativeWx.setOnClickListener(this);
        relativeZfb.setOnClickListener(this);
        relativeYl.setOnClickListener(this);
        tvPay.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                isPay = false;
                pay();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_back:
                finish();
                break;
            case R.id.relative_wx:
                isWX = !isWX;
                type = isWX ? WX : null;
                isZFB = false;
                isYH = false;
                isHuoDaoFuKuan = false;
                wxCheck.setChecked(isWX);
                yhCheck.setChecked(isYH);
                zfbCheck.setChecked(isZFB);
                huodao_pay_check.setChecked(isHuoDaoFuKuan);
                tvPay.setText("确认支付"+NumberUtils.formatPriceOuYuan(total));
                break;
            case R.id.relative_zfb:
                isZFB = !isZFB;
                type = isZFB ? ZFB : null;
                isYH = false;
                isWX = false;
                isHuoDaoFuKuan = false;
                wxCheck.setChecked(isWX);
                yhCheck.setChecked(isYH);
                zfbCheck.setChecked(isZFB);
                huodao_pay_check.setChecked(isHuoDaoFuKuan);
                tvPay.setText("确认支付"+NumberUtils.formatPriceOuYuan(total));
                break;
            case R.id.relative_yl:
                isYH = !isYH;
                type = isYH ? YH : null;
                isZFB = false;
                isWX = false;
                isHuoDaoFuKuan = false;
                wxCheck.setChecked(isWX);
                yhCheck.setChecked(isYH);
                zfbCheck.setChecked(isZFB);
                huodao_pay_check.setChecked(isHuoDaoFuKuan);
                tvPay.setText("确认支付"+NumberUtils.formatPriceOuYuan(total));
                break;
            case R.id.relative_huodao_pay:  //货到付款
                isHuoDaoFuKuan = !isHuoDaoFuKuan;
                type = isHuoDaoFuKuan ? HUODAOFUKUAN : null;
                isZFB = false;
                isWX = false;
                isYH = false;
                wxCheck.setChecked(isWX);
                yhCheck.setChecked(isYH);
                zfbCheck.setChecked(isZFB);
                huodao_pay_check.setChecked(isHuoDaoFuKuan);
                tvPay.setText("货到付款");
                break;
        }
    }

    private void pay() {
        if (type == null) {
            showToast("请选择支付方式！");
            return;
        } else if (type.equals(WX)) {
            weixinPay();
        } else if (type.equals(YH)) {
            fuyouPay();
        } else if (type.equals(ZFB)) {
            String url = "http://huohang.huaqiaobang.com/mobile/index.php?act=member_payment&op=pay" +
                    "&key=" + MyApplication.getInstance().getMykey() +
                    "&pay_sn=" + pay_sn +
                    "&payment_code=alipay";
            Log.i("zdszds", "pay_url=" + url);
            Intent intent = new Intent(this, FuyouPayActivity.class);
            intent.putExtra("id", "1");
            intent.putExtra("url", url);
            startActivity(intent);
            finish();
        }else if(type.equals(HUODAOFUKUAN)){  // 货到付款
            payWithXianXia();
        }
    }

    /*货到付款*/
    private void payWithXianXia(){
//        http://huohang.huaqiaobang.com/mobile/index.php?act=native&op=changeOrderState&order_sn=&key=a7954eaa08758d90c9b6de16beb63e17
        ProgressDlgUtil.showProgressDlg("", this);
        Log.i("zdszds", "xianxia_url=" + "http://huohang.huaqiaobang.com/mobile/index.php?act=native&op=changeOrderState&order_sn=" + pay_sn_huodao+ "&key="+MyApplication.getInstance().getMykey());

        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET, "http://huohang.huaqiaobang.com/mobile/index.php?act=native&op=changeOrderState&order_sn=" + pay_sn_huodao+ "&key="+MyApplication.getInstance().getMykey(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("state") == 1) {
                        if(isFromOrder){
                            EventBus.getDefault().post("updateHHoder");
                        }


                        finish();
                    }

                    showToast(response.optString("datas"));

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    ProgressDlgUtil.stopProgressDlg();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mRequestQueue.add(jr);
    }

    private void fuyouPay() {
        ProgressDlgUtil.showProgressDlg("", this);
        Log.i("zdszds", "fuyou_url=" + "http://huohang.huaqiaobang.com/mobile/index.php?act=member_payment&op=pay&key=" + MyApplication.getInstance().getMykey() + "&pay_sn=" + pay_sn + "&payment_code=fomo" + "&uid=" + MyApplication.getInstance().self.getId());

        String url = "http://huohang.huaqiaobang.com/mobile/index.php?act=member_payment&op=pay&key=" + MyApplication.getInstance().getMykey() + "&pay_sn=" + pay_sn + "&payment_code=fomo" + "&uid=" + MyApplication.getInstance().self.getId();
        Log.i("zdszds", "pay_url=" + url);
        Intent intent = new Intent(this, FuyouPayActivity.class);
        intent.putExtra("id", "1");
        intent.putExtra("url", url);
        startActivity(intent);
        if(isFromOrder){
            EventBus.getDefault().post("updateHHoder");
        }

        ProgressDlgUtil.stopProgressDlg();
    }

    private void weixinPay() {
        Log.i("zdszds", "weixin_url=" + "http://huohang.huaqiaobang.com/mobile/index.php?act=member_payment&op=pay&key=" + MyApplication.getInstance().getMykey() + "&pay_sn=" + pay_sn + "&payment_code=weixin" + "&uid=" + MyApplication.getInstance().self.getId());
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,"http://huohang.huaqiaobang.com/mobile/index.php?act=member_payment&op=pay&key=" + MyApplication.getInstance().getMykey() + "&pay_sn=" + pay_sn + "&payment_code=weixin" + "&uid=" + MyApplication.getInstance().self.getId(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("zdszds", "weixin_msg=" + response);
                    if (response.getInt("status") == 1) {
                        if(isFromOrder){
                            EventBus.getDefault().post("updateHHoder");
                        }

                        isPay = true;
                        Log.i("zdszds", "成功跳转到微信支付");
                        JSONObject json = response.getJSONObject("msg");
                        PayReq req = new PayReq();
                        req.appId = json.getString("appid");
                        req.partnerId = json.getString("partnerid");
                        req.prepayId = json.getString("prepayid");
                        req.nonceStr = json.getString("noncestr");
                        req.timeStamp = json.getString("timestamp");
                        req.packageValue = json.getString("package");
                        req.sign = json.getString("sign");
                        req.extData = "app data"; // optional
                        Log.i("zdszds", "req=" + req);
                        iwxapi.sendReq(req);
                        finish();
                    } else {
                        Log.i("zdszds", "解析失败");
                        showToast("微信支付请求失败！请重试");
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.i("zdszds", e.toString());
                    e.printStackTrace();
                } finally {

                    ProgressDlgUtil.stopProgressDlg();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mRequestQueue.add(jr);
    }

}
