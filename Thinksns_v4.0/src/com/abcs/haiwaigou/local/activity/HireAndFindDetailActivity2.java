package com.abcs.haiwaigou.local.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.activity.PhotoPreviewActivity;
import com.abcs.haiwaigou.view.zjzbanner.LMBanners;
import com.abcs.haiwaigou.view.zjzbanner.adapter.LBaseAdapter;
import com.abcs.haiwaigou.view.zjzbanner.transformer.TransitionEffect;
import com.abcs.haiwaigou.view.zjzbanner.utils.ScreenUtils;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.huaqiaobang.model.Options;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.huaqiaobang.ytbt.common.utils.ToastUtil;
import com.abcs.sociax.android.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.thinksns.sociax.thinksnsbase.utils.TLUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class HireAndFindDetailActivity2 extends BaseActivity implements LMBanners.PositionChange {
    @Override
    public void setSelectedChange(int position) {
        Log.i("zzzddss", "initBanners: "+position);
        tvPosition.setText((position%bannerString.size()+1)+"");
        tvCount.setText("/"+bannerString.size());
    }

    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.t_title_name)
    TextView tTitleName;
    @InjectView(R.id.relative_share)
    RelativeLayout relativeShare;
    @InjectView(R.id.relative_top)
    RelativeLayout relativeTop;
    @InjectView(R.id.banners)
    LMBanners banners;
    @InjectView(R.id.tv_position)
    TextView tvPosition;
    @InjectView(R.id.tv_count)
    TextView tvCount;
    @InjectView(R.id.linear_pic)
    RelativeLayout linearPic;
    @InjectView(R.id.img_type)
    ImageView imgType;
    @InjectView(R.id.t_title)
    TextView tTitle;
    @InjectView(R.id.linear_top)
    LinearLayout linearTop;
    @InjectView(R.id.iv_open_wx)
    ImageView ivOpenWx;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.linaer_1)
    LinearLayout linaer1;
    @InjectView(R.id.tv_phone)
    TextView tvPhone;
    @InjectView(R.id.linaer_2)
    LinearLayout linaer2;
    @InjectView(R.id.tv_weixin)
    TextView tvWeixin;
    @InjectView(R.id.linaer_3)
    LinearLayout linaer3;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.img_call)
    ImageView img_call;
    @InjectView(R.id.linaer_4)
    LinearLayout linaer4;
    @InjectView(R.id.tv_detials)
    TextView tvDetials;
    @InjectView(R.id.linear_content)
    LinearLayout linearContent;
    @InjectView(R.id.null_view)
    View null_view;


    private String conId,menuId;
    Handler handler=new Handler();


    @Override
    public void onPause() {
        super.onPause();
        if (banners != null)
            banners.stopImageTimerTask();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (banners != null)
            banners.startImageTimerTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (banners != null) {
            banners.clearImageTimerTask();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_activity_hire_and_find_detail2);
        ButterKnife.inject(this);

        tTitleName.setText("详情");
        conId = getIntent().getStringExtra("conId");
        menuId = getIntent().getStringExtra("menuId");
        Log.i("zjz", "conId=" + conId);
        initView(conId);


    }

    private boolean isFirst=true;

    private void initView(final String conId) {
        try {
            if(isFirst){
                isFirst=false;
                ProgressDlgUtil.showProgressDlg("Loading...", this);
            }

            HttpRequest.sendGet(TLUrl.getInstance().URL_local_get_detail, "conId=" + conId, new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject mainObj = new JSONObject(msg);
                                Log.i("zjz", "local_Detail_msg=" + msg);
                                initJsonObject(mainObj);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } finally {
                                ProgressDlgUtil.stopProgressDlg();
                            }
                        }
                    });
                }
            });
//        }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initJsonObject( JSONObject mainObj) {
        try {
            if (mainObj.optString("status").equals("1")) {
                final JSONObject msgObj = mainObj.optJSONObject("msg");

                /*电话*/
                if (tvPhone != null)
                    tvPhone.setText(msgObj.optString("contact"));

                img_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!TextUtils.isEmpty(msgObj.optString("contact").trim())){
                            if(msgObj.optString("contact").trim().contains(",")){
                                String[] phone=msgObj.optString("contact").trim().split(",");
                                if(phone!=null&&!TextUtils.isEmpty(phone[0])){
                                    call(phone[0]);
                                }
                            }else {
                                call(msgObj.optString("contact").trim());
                            }
                        }else {
                            ToastUtil.showMessage("暂不支持");
                        }
                    }
                });
                if (tTitle != null)
                    tTitle.setText(msgObj.optString("title"));

                if (!TextUtils.isEmpty(msgObj.optString("vx"))){
                    tvWeixin.setText(msgObj.optString("vx"));
                    ivOpenWx.setVisibility(View.VISIBLE);
                }else {
                    tvWeixin.setText("");
                    ivOpenWx.setVisibility(View.INVISIBLE);
                }

                if (msgObj.optInt("status") == 1) {  // 热
                    imgType.setImageResource(R.drawable.img_new_local_re);
                    imgType.setVisibility(View.VISIBLE);
                } else if(msgObj.optInt("status") == 2){ // 顶
                    imgType.setImageResource(R.drawable.img_new_local_z_ding);
                    imgType.setVisibility(View.VISIBLE);
                } else if(msgObj.optInt("status") == 3){  // 精
                    imgType.setImageResource(R.drawable.img_new_local_z_jing);
                    imgType.setVisibility(View.VISIBLE);
                }else {
                    imgType.setVisibility(View.INVISIBLE);
                }

                /*地址*/
                if (!TextUtils.isEmpty(msgObj.optString("ads"))) {
                    if (tvAddress != null)
                        tvAddress.setText(msgObj.optString("ads"));
                } else {
                    if (tvAddress != null)
                        tvAddress.setText("暂无");
                }

                /*详细内容*/
                if (!TextUtils.isEmpty(msgObj.optString("content"))) {
                    if (tvDetials != null)
                        tvDetials.setText(msgObj.optString("content"));
                    if (linearContent != null)
                        linearContent.setVisibility(View.VISIBLE);
                } else {
                    if (linearContent != null)
                        linearContent.setVisibility(View.GONE);
                }

                /*联系人*/
                if (!TextUtils.isEmpty(msgObj.optString("contactMan"))) {
                    if (tvName != null)
                        tvName.setText(msgObj.optString("contactMan"));
                } else {
                    tvName.setText("");
                }

                /*bangner轮播图*/
                if (TextUtils.isEmpty(msgObj.optString("imgUrls")) || msgObj.optString("imgUrls").equals("null")) {
                    linearPic.setVisibility(View.GONE);
                } else {
                     goods_images= msgObj.optString("imgUrls").split(",");
                    if (goods_images != null&&goods_images.length>0) {
                        initBanners();
                    }
                }
            }

            null_view.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copy(String string) {
        ClipboardManager clipboardManager;
        clipboardManager= (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData=ClipData.newPlainText("info",string);
        clipboardManager.setPrimaryClip(clipData);
        ToastUtil.showMessage("微信号已经复制到粘贴板，您可以粘贴到任意地方！");
    }

    private ArrayList<String> bannerString = new ArrayList<>();
    String goods_images[];

    private void initBanners() {
        bannerString.clear();
        for (int i = 0; i < goods_images.length; i++) {
            bannerString.add(Util.myTrim(goods_images[i]));
            Log.i("zjz", "goods_img=" + goods_images[i]);
        }
        if (banners != null) {
            //设置Banners高度
            banners.setPotionChenge(this);
            banners.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.dip2px(this, 190)));
            //本地用法
            banners.setAdapter(new UrlImgAdapter(this), bannerString);
            //网络图片
//        mLBanners.setAdapter(new UrlImgAdapter(MainActivity.this), networkImages);
            //参数设置
            banners.setAutoPlay(true);//自动播放
            banners.setVertical(false);//是否可以垂直
            banners.setScrollDurtion(500);//两页切换时间
            banners.setCanLoop(true);//循环播放
            banners.setSelectIndicatorRes(R.drawable.img_hwg_indicator_select);//选中的原点
            banners.setUnSelectUnIndicatorRes(R.drawable.img_hwg_indicator_unselect);//未选中的原点
//        mLBanners.setHoriZontalTransitionEffect(TransitionEffect.Default);//选中喜欢的样式
//        banners.setHoriZontalCustomTransformer(new ParallaxTransformer(R.id.id_image));//自定义样式
            banners.setHoriZontalTransitionEffect(TransitionEffect.Alpha);//Alpha
            banners.setDurtion(1800);//切换时间
            if (bannerString.size() == 1) {

                banners.hideIndicatorLayout();//隐藏原点
            } else {

                banners.showIndicatorLayout();//显示原点
            }

            banners.setIndicatorPosition(LMBanners.IndicaTorPosition.BOTTOM_MID);//设置原点显示位置
        }

        linearPic.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.relative_back, R.id.iv_open_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back:
                finish();
                break;
            case R.id.iv_open_wx:
                copy(tvWeixin.getText().toString().trim());
                break;
        }
    }


    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    private void call(String phone) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class UrlImgAdapter implements LBaseAdapter<String> {
        private Context mContext;

        public UrlImgAdapter(Context context) {
            mContext = context;
        }

        @Override
        public View getView(final LMBanners lBanners, final Context context, final int position, String data) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.banner_item2, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.id_image);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(Util.WIDTH, Util.WIDTH*510/750);
            imageView.setLayoutParams(layoutParams);
//            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ImageLoader.getInstance().displayImage(data, imageView, Options.getHDOptions());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("image", goods_images[position]);
                    intent.putExtra("ulist", bannerString);
                    intent.setClass(HireAndFindDetailActivity2.this, PhotoPreviewActivity.class);
                    startActivity(intent);
                }
            });
            return view;
        }

    }

}
