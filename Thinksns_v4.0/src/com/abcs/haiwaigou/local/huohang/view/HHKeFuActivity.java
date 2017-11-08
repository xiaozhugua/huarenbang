package com.abcs.haiwaigou.local.huohang.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.local.beans.KeFuBean;
import com.abcs.haiwaigou.local.huohang.adapter.HHKeFuChatAdapter;
import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.sociax.android.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HHKeFuActivity extends BaseActivity {

    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.easy_recyclevive)
    ListView easyRecyclevive;
    @InjectView(R.id.et_input)
    EditText etInput;
    @InjectView(R.id.t_send)
    TextView tSend;

    HHKeFuChatAdapter adapter;

    List<KeFuBean> data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hhke_fu);
        ButterKnife.inject(this);
        adapter=new HHKeFuChatAdapter(this);
        easyRecyclevive.setAdapter(adapter);

        data.clear();
        for (int i = 0; i < 40; i++) {
            if(i%2==0){
                data.add(new KeFuBean(0,123223434,"欢迎问叶娜"));
            }else {
                data.add(new KeFuBean(1,1239000,"维也纳欢饮您"));
            }
        }

        adapter.clearDatasAndAdd(data);

        tSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input=etInput.getText().toString().trim();
                if(!TextUtils.isEmpty(input)){
                    KeFuBean bean=new KeFuBean(1,System.currentTimeMillis(),input);
                   adapter.AddItemBean(bean);

                    easyRecyclevive.setSelection(easyRecyclevive.getBottom());
                }else {
                    showToast("请输入内容！");
                    return;
                }
                etInput.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
