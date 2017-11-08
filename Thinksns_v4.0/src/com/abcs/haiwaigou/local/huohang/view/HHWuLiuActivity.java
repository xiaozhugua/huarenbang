package com.abcs.haiwaigou.local.huohang.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.huaqiaobang.ytbt.common.utils.ToastUtil;
import com.abcs.sociax.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HHWuLiuActivity extends BaseActivity {

    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.t_title_name)
    TextView tTitleName;
    @InjectView(R.id.tv_wuliu_state_net)
    TextView tvWuliuState;
    @InjectView(R.id.tv_wuliu_state)
    TextView tvWuliuStateNet;
    @InjectView(R.id.tv_wuliu_from)
    TextView tvWuliuFrom;
    @InjectView(R.id.tv_wuliu_number)
    TextView tvWuliuNumber;
    @InjectView(R.id.tv_wuliu_phone)
    TextView tvWuliuPhone;
    @InjectView(R.id.tv_copy)
    TextView tvCopy;
    @InjectView(R.id.tv_wuliu_desc)
    TextView tvWuliuDesc;

    private static final String TAG = "HHWuLiuActivity";
    private String orderId;
    private String state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hhwu_liu);
        ButterKnife.inject(this);

        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        orderId=getIntent().getStringExtra("orderId");
        state=getIntent().getStringExtra("state");

        getDatas(orderId);
         /*状态*/
        if(state.equals("10")){
            tvWuliuState.setText("已支付");
        }else   if(state.equals("20")){
            tvWuliuState.setText("已发货");
        }else   if(state.equals("30")){
            tvWuliuState.setText("已收货");
        }else   if(state.equals("40")){
            tvWuliuState.setText("已完成");
        }else  if(state.equals("0")){
            tvWuliuState.setText("已取消");
        }else  {
            tvWuliuState.setText("未知");
        }

    }

    Handler handler=new Handler();
    private void getDatas(String orderId) {
//        http://huohang.huaqiaobang.com/mobile/index.php?act=member_order&op=get_express&order_id=8198&key=fd8c6fe1103bb5462ae7d00d296643d2
        ProgressDlgUtil.showProgressDlg("",this);
        HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=member_order&op=get_express&key=" + MyApplication.getInstance().getMykey() + "&order_id=" + orderId, new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(msg)) {
                            try {
                                JSONObject jsonObject=new JSONObject(msg);

                                if(jsonObject!=null&&jsonObject.optInt("state")==1){
                                    final JSONObject jsonDatas=jsonObject.optJSONObject("datas");
                                    if(jsonDatas!=null){

                                        tvWuliuFrom.setText("承运来源："+jsonDatas.optString("express_name"));
                                        tvWuliuNumber.setText("运单编号："+jsonDatas.optString("express_number"));
                                        tvWuliuPhone.setText("官方电话："+jsonDatas.optString("express_phone"));
                                        tvWuliuDesc.setText(jsonDatas.optString("beizhu"));

                                        if(!TextUtils.isEmpty(jsonDatas.optString("express_phone"))){
                                            tvCopy.setVisibility(View.VISIBLE);
                                        }else {
                                            tvCopy.setVisibility(View.INVISIBLE);
                                        }

                                        tvCopy.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                copy(jsonDatas.optString("express_number"));
                                            }
                                        });

                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        ProgressDlgUtil.stopProgressDlg();
                    }
                });
            }
        });

    }

    public void copy(String string) {
        ClipboardManager clipboardManager;
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("info", string);
        clipboardManager.setPrimaryClip(clipData);
        ToastUtil.showMessage("运单编号已经复制到粘贴板，您可以粘贴到任意地方！");
    }

}
