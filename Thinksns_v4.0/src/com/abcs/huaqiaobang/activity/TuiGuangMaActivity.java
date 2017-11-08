package com.abcs.huaqiaobang.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.sociax.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TuiGuangMaActivity extends BaseActivity {

    @InjectView(R.id.back)
    RelativeLayout back;
    @InjectView(R.id.top)
    RelativeLayout top;
    @InjectView(R.id.ed_tgm)
    EditText edTgm;
    @InjectView(R.id.tv_ok)
    TextView tvOk;
    @InjectView(R.id.activity_tui_guang_ma)
    LinearLayout activityTuiGuangMa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui_guang_ma);
        ButterKnife.inject(this);

        manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        edTgm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s!=null){
                    if(s.length()>0){
                        tvOk.setBackgroundColor(Color.parseColor("#fa0528"));
                    }else {
                        tvOk.setBackgroundColor(Color.parseColor("#cccccc"));
                    }

                }
            }
        });

    }

    Handler handler=new Handler();

    private void postTuiGuangMa(){
//        http://huohang.huaqiaobang.com/mobile/index.php?act=native&op=insert_promo&promo_code=2323&key=8a75808e27b632d5ec0b19e37a6c1edc
        HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "key="+ MyApplication.getInstance().getMykey()+"&act=native&op=insert_promo&promo_code=" + edTgm.getText().toString().trim(), new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            if(!TextUtils.isEmpty(msg)){
                                JSONObject jsonObject=new JSONObject(msg);
                                if(jsonObject!=null){
                                    if(jsonObject.optInt("state")==1){
                                        showToast(jsonObject.optString("datas"));
                                        finish();
                                    }else {
                                        showToast(jsonObject.optString("datas"));
                                    }
                                }

                            }else {
                                showToast("失败！");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
    private InputMethodManager manager;
    private void hideKeyBore(){

        //隐藏键盘
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    @OnClick({R.id.back, R.id.tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_ok:
                postTuiGuangMa();
                hideKeyBore();
                break;
        }
    }
}
