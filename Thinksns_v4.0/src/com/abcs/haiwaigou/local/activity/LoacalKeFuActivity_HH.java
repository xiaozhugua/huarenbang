package com.abcs.haiwaigou.local.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcs.haiwaigou.utils.ACache;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.huaqiaobang.ytbt.common.utils.ToastUtil;
import com.abcs.sociax.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

public class LoacalKeFuActivity_HH extends Activity {

     TextView localDetailTvContain ;  //广告的内容
     TextView localDetailTvName ;          //标题
     TextView tv_weixin_hao ;          //电话号
     TextView tv_weixin_hao2 ;          //微信号
     ImageView localDetailIvWeiXin ;  //微信二维码
     ImageView img_phone_icon ;
     ImageView localDetailClose ;
     com.abcs.huaqiaobang.view.CircleImageView local_detail_civ_head ;
    
    private ACache aCache;
    Handler handler=new Handler();

    private boolean nostore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_guanggao_detail_item2);
        aCache = ACache.get(this);

        nostore=getIntent().getBooleanExtra("nostore",false);
        initViews();
        initListenr();
        if(nostore){  // 没有店铺
            tv_weixin_hao.setVisibility(View.GONE);
            img_phone_icon.setVisibility(View.GONE);
            initNoStore();
        }else {
            img_phone_icon.setVisibility(View.VISIBLE);
            img_phone_icon.setVisibility(View.VISIBLE);
            initDatas();
        }
    }

    private void initListenr() {
        localDetailClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        tv_weixin_hao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copy(tv_weixin_hao2.getText().toString().trim(),"WX");
            }
        });
    }

    private void initViews() {
        localDetailTvContain = (TextView) findViewById(R.id.local_item_detail_tv_contain);  //广告的内容
        localDetailTvName = (TextView) findViewById(R.id.local_detail_tv_name);          //标题
        tv_weixin_hao = (TextView) findViewById(R.id.tv_weixin_hao);          //微信号
        tv_weixin_hao2 = (TextView) findViewById(R.id.tv_weixin_hao2);          //微信号
        localDetailIvWeiXin = (ImageView) findViewById(R.id.local_item_iv_weixin);  //微信二维码
        img_phone_icon = (ImageView) findViewById(R.id.img_phone_icon);  //微信二维码
        localDetailClose = (ImageView) findViewById(R.id.local_detail_close);
        local_detail_civ_head = (com.abcs.huaqiaobang.view.CircleImageView) findViewById(R.id.local_detail_civ_head);
    }

    public void copy(String string,String type) {
        ClipboardManager clipboardManager;
        clipboardManager= (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData=ClipData.newPlainText("info",string);
        clipboardManager.setPrimaryClip(clipData);
        if(type.equals("WX")){
            ToastUtil.showMessage("微信号已经复制到粘贴板，您可以粘贴到任意地方！");
        }else if(type.equals("QQ")){
            ToastUtil.showMessage("QQ号已经复制到粘贴板，您可以粘贴到任意地方！");
        }
    }
    private void initDatas(){
        //        http://newapi.tuling.me/huaqiaobang/mobile/index.php?act=native&op=native_tc&key=25918f85df03d5ebfa076600519ecd1e
        if(!TextUtils.isEmpty(aCache.getAsString("nodressid"))){
            Log.i("zds", "showGuanggaoWindow: local");
            initPetenr(aCache.getAsString("nodressid"));
        }else {
//            http://huohang.huaqiaobang.com/mobile/index.php?act=native_index&op=native_tc
            HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=native_index&op=native_tc", new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("getPartner", msg + "");

                            if (TextUtils.isEmpty(msg)) {
                                ToastUtil.showMessage("网络出错！");
                                return;
                            } else {
                                initPetenr(msg);
                                aCache.put("nodressid",msg);
                            }
                        }
                    });
                }
            });
        }
    }
    private void initNoStore(){
        //       http://huohang.huaqiaobang.com/mobile/index.php?act=native_index&op=native_storetc
        if(!TextUtils.isEmpty(aCache.getAsString("nostorfd"))){
            initPetenrNoStore(aCache.getAsString("nostorfd"));
        }else {
//            http://huohang.huaqiaobang.com/mobile/index.php?act=native_index&op=native_tc
            HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=native_index&op=native_storetc", new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("getPartner", msg + "");

                            if (TextUtils.isEmpty(msg)) {
                                ToastUtil.showMessage("网络出错！");
                                return;
                            } else {
                                initPetenrNoStore(msg);
                                aCache.put("nostorfd",msg);
                            }
                        }
                    });
                }
            });
        }
    }
    private void initPetenr(String msg_s){

        try {
            JSONObject jsonObject= new JSONObject(msg_s);
            localDetailTvContain.setText(jsonObject.optString("message"));
            tv_weixin_hao.setText(jsonObject.optString("phone"));

            if(jsonObject.has("we_chat")){
                tv_weixin_hao2.setText(jsonObject.optString("we_chat"));
            }

            if(!TextUtils.isEmpty(jsonObject.optString("img"))){
                MyApplication.imageLoader.displayImage(jsonObject.optString("img"),localDetailIvWeiXin,MyApplication.getListOptions());
                localDetailIvWeiXin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showMessage("亲，长按有惊喜哦！",1000);
                    }
                });
                localDetailIvWeiXin.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        shot();
                        ToastUtil.showMessage("截屏成功！，请使用微信扫一扫功能，在相册里选择刚才截屏的图片，识别该二维码即可关注我们啦！",6000);
                        return true;
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void initPetenrNoStore(String msg_s){

        try {
            JSONObject jsonObject= new JSONObject(msg_s);
            localDetailTvContain.setText(jsonObject.optString("message"));

            if(jsonObject.has("we_chat")){
                tv_weixin_hao2.setText(jsonObject.optString("we_chat"));
            }

            if(!TextUtils.isEmpty(jsonObject.optString("img"))){
                MyApplication.imageLoader.displayImage(jsonObject.optString("img"),localDetailIvWeiXin,MyApplication.getListOptions());
                localDetailIvWeiXin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showMessage("亲，长按有惊喜哦！",1000);
                    }
                });
                localDetailIvWeiXin.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        shot();
                        ToastUtil.showMessage("截屏成功！，请使用微信扫一扫功能，在相册里选择刚才截屏的图片，识别该二维码即可关注我们啦！",6000);
                        return true;
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*****截屏*******/
    private void shot() {

        // 获取屏幕
        View dView = getWindow().getDecorView();
        dView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);//截全屏
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        if (bmp != null) {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + "screenshot_hh.png";

                File file =new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();

                //将截图保存至相册并广播通知系统刷新
                MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), "jieping_hh", null);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
                sendBroadcast(intent);

            } catch (Exception e) {
            }
        }
    }
}
