package com.abcs.haiwaigou.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.sociax.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class KefuActivity2 extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.tljr_txt_share_title)
    TextView tljrTxtShareTitle;
    @InjectView(R.id.t_tel)
    TextView tTel;
    @InjectView(R.id.t_qq)
    TextView tQq;
    @InjectView(R.id.tljr_dialog_exit_ok)
    Button tljrDialogExitOk;
    @InjectView(R.id.linear_tel)
    LinearLayout linearTel;
    @InjectView(R.id.t_wx)
    TextView tWx;

    public static final String WX="wx";
    public static final String QQ="qq";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hwg_activity_kefu2);
        ButterKnife.inject(this);
        getDatas();
        tTel.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tTel.getPaint().setAntiAlias(true);//抗锯齿
        linearTel.setOnClickListener(this);
        tQq.setOnClickListener(this);
        tWx.setOnClickListener(this);
        tljrDialogExitOk.setOnClickListener(this);
    }


    Handler handler=new Handler();
    private String phone,qq,wx;
    private void getDatas(){
//        http://huohang.huaqiaobang.com/mobile/index.php?act=goods&op=get_customer_service
        HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=goods&op=get_customer_service", new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {
                // TODO Auto-generated method stub
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(msg)) {
                            return;
                        } else {
                            try {
                                JSONObject jsonObject=new JSONObject(msg);
                                if(jsonObject!=null&&jsonObject.optInt("state")==1){
                                    JSONObject jsonMian=jsonObject.optJSONObject("datas");
                                    if(!TextUtils.isEmpty(jsonMian.optString("phone"))){
                                        tTel.setText(jsonMian.optString("phone"));
                                        phone=jsonMian.optString("phone");
                                    }
                                    if(!TextUtils.isEmpty(jsonMian.optString("qq"))){
                                        tQq.setText("客服 Q Q："+jsonMian.optString("qq"));
                                        qq=jsonMian.optString("qq");
                                    }
                                    if(!TextUtils.isEmpty(jsonMian.optString("wx"))){
                                        tWx.setText("客服微信："+jsonMian.optString("wx"));
                                        wx=jsonMian.optString("wx");
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_tel:
                dialPhoneNumber(phone);
//                new PromptDialog(this, "您将要拨打0755-32838341", new Complete() {
//                    @Override
//                    public void complete() {
//
//                    }
//                });
                break;
            case R.id.tljr_dialog_exit_ok:
                finish();
                break;
            case R.id.t_qq:
                copy(qq,QQ);
                break;
            case R.id.t_wx:
                copy(wx,WX);
                break;
        }
    }

    public void copy(String string,String type) {
        ClipboardManager clipboardManager;
        clipboardManager= (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData=ClipData.newPlainText("info",string);
        clipboardManager.setPrimaryClip(clipData);
        if(type.equals(WX)){
            showToast("微信号已经复制到粘贴板，您可以粘贴到任意地方！");
        }else if(type.equals(QQ)){
            showToast("QQ号已经复制到粘贴板，您可以粘贴到任意地方！");
        }
//        Toast toast=Toast.makeText(this, "信息已经复制到粘贴板，你可以粘贴到任意地方！", Toast.LENGTH_SHORT);
//        View view=toast.getView();
//        view.setBackgroundResource(R.color.tr_colorPrimaryDark);
//        toast.setView(view);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
