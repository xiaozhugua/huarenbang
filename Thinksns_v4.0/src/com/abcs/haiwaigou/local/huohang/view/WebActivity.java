package com.abcs.haiwaigou.local.huohang.view;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.sociax.android.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 *
 */

public class WebActivity extends Activity {

    @InjectView(R.id.local_guangao_wv)
    WebView localGuangaoWv;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    Handler mHandler=new Handler();

    String store_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_guanggao_activity);
        ButterKnife.inject(this);

         store_id=getIntent().getStringExtra("store_id");

        tv_title.setText("关于本店");

        //支持javascript
        localGuangaoWv.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        localGuangaoWv.getSettings().setSupportZoom(false);
        localGuangaoWv.getSettings().setDefaultTextEncodingName("UTF-8");//设置编码格式
        // 设置出现缩放工具
        localGuangaoWv.getSettings().setBuiltInZoomControls(false);
        //扩大比例的缩放
        localGuangaoWv.getSettings().setUseWideViewPort(false);
        //自适应屏幕
        localGuangaoWv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        localGuangaoWv.getSettings().setLoadWithOverviewMode(true);
        localGuangaoWv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { // 屏蔽超链接
                if (url != null)
                    view.loadUrl(url);
                return true;
            }
        });

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            localGuangaoWv.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        ProgressDlgUtil.showProgressDlg("loading",this);

//        http://huaqiaobang.com/mobile/index.php?act=test_cy&op=native_html&store_id=11
        String inntent_Url="http://huaqiaobang.com/mobile/index.php?act=test_cy&op=native_html&store_id="+store_id;
        localGuangaoWv.loadUrl(inntent_Url);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ProgressDlgUtil.stopProgressDlg();
            }
        }, 1000);
    }

    @OnClick(R.id.relative_back)
    public void onClick() {
        finish();
    }
}
