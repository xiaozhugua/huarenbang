package com.abcs.huaqiaobang.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.DownloadListener;
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
 * Created by Administrator on 2016/11/26.
 */

public class GuanggaoActivity extends Activity {

    @InjectView(R.id.local_guangao_wv)
    WebView localGuangaoWv;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    Handler mHandler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_guanggao_activity);
        ButterKnife.inject(this);

        String title_local=getIntent().getStringExtra("title_local");

        if(!TextUtils.isEmpty(title_local)){
            tv_title.setText(title_local);
        }else {
            tv_title.setText("详情");
        }
        //支持javascript
        localGuangaoWv.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        localGuangaoWv.getSettings().setSupportZoom(true);
        localGuangaoWv.getSettings().setDefaultTextEncodingName("UTF-8");//设置编码格式
        // 设置出现缩放工具
        localGuangaoWv.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        localGuangaoWv.getSettings().setUseWideViewPort(false);
        //自适应屏幕
        localGuangaoWv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        localGuangaoWv.getSettings().setLoadWithOverviewMode(true);
        localGuangaoWv.setDownloadListener(new MyWebViewDownLoadListener());
        localGuangaoWv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Log.i("web_","shouldOverrideUrlLoading->"+url);
                try {
                    // 以下固定写法
                    final Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } catch (Exception e) {
                    // 防止没有安装的情况
                    e.printStackTrace();
                }

                view.loadUrl(url);
                return true;  // 不跳转第三方浏览器

            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // Auto-generated method stub
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Auto-generated method stub
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

        });

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            localGuangaoWv.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        ProgressDlgUtil.showProgressDlg("loading",this);

        String inntent_Url=getIntent().getStringExtra("url");
        if(!TextUtils.isEmpty(inntent_Url)){
            Log.i("zds", "onCreate: URL========"+inntent_Url);
            if(inntent_Url.startsWith("www")){
                localGuangaoWv.loadUrl("http://"+inntent_Url);
            }else {
                localGuangaoWv.loadUrl(inntent_Url);
            }
        }


        if(getIntent().getStringExtra("url_local")!=null){
            Log.i("zds", "onCreate: URL========"+getIntent().getStringExtra("url_local"));
            localGuangaoWv.loadUrl(getIntent().getStringExtra("url_local"));
        }

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

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

            Log.i("zds", "onDownloadStart: "+url);

//            if(url.endsWith(".apk")){
//
//            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },1000);
        }
    }
}
