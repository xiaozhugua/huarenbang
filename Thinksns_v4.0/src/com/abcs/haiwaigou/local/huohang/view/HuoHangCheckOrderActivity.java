package com.abcs.haiwaigou.local.huohang.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.activity.PayWayActivity;
import com.abcs.haiwaigou.broadcast.MyUpdateUI;
import com.abcs.haiwaigou.local.huohang.bean.CheckOrderBean;
import com.abcs.haiwaigou.local.huohang.bean.StoreCartListEntry;
import com.abcs.haiwaigou.utils.NumberUtils;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.huaqiaobang.view.MainScrollView;
import com.abcs.huaqiaobang.wxapi.WXEntryActivity;
import com.abcs.sociax.android.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.abcs.sociax.android.R.id.relat_null;

public class HuoHangCheckOrderActivity extends BaseActivity {


    @InjectView(R.id.tljr_txt_news_title)
    TextView tljrTxtNewsTitle;
    @InjectView(R.id.tljr_hqss_news_titlebelow)
    TextView tljrHqssNewsTitlebelow;
    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.relative_help)
    RelativeLayout relativeHelp;
    @InjectView(R.id.seperate)
    View seperate;
    @InjectView(R.id.tljr_grp_goods_title)
    RelativeLayout tljrGrpGoodsTitle;
    @InjectView(R.id.t_tishi)
    TextView tTishi;
    @InjectView(R.id.t_no_add)
    TextView tNoAdd;
    @InjectView(R.id.rl_choose)
    RelativeLayout rlChoose;
    @InjectView(R.id.img_location)
    ImageView imgLocation;
    @InjectView(R.id.img_more)
    ImageView imgMore;
    @InjectView(R.id.t_name)
    TextView tName;
    @InjectView(R.id.linear_name)
    LinearLayout linearName;
    @InjectView(R.id.t_phone)
    TextView tPhone;
    @InjectView(R.id.liner_tel)
    LinearLayout linerTel;
    @InjectView(R.id.t_address)
    TextView tAddress;
    @InjectView(R.id.linear_add)
    LinearLayout linearAdd;
    @InjectView(R.id.t_id_card)
    TextView tIdCard;
    @InjectView(R.id.img_edit)
    ImageView imgEdit;
    @InjectView(R.id.linear_address)
    RelativeLayout linearAddress;
    @InjectView(R.id.seperate3)
    View seperate3;
    @InjectView(R.id.liner_mian)
    LinearLayout linerMian;
    @InjectView(R.id.main_scroll)
    MainScrollView mainScroll;
    @InjectView(R.id.tv_o)
    TextView tvO;
    @InjectView(R.id.tv_shifu_price)
    TextView tvShifuPrice;
    @InjectView(R.id.tv_shifu_price_shuiqian)
    TextView tvShifuPriceShuiqian;
    @InjectView(R.id.tv_huodaofukuan)
    TextView tvHuodaofukuan;
    @InjectView(relat_null)
    RelativeLayout relatNull;
    private View rootView;
    private String cart_id;
    private String store_id;
    String address;
    String address2;
    String name;
    String phone;
    String tel_phone;
    public Handler handler = new Handler();
    String address_id;
    String area_id;
    String idCard;
    String city_id;
    String vat_hash;
    String freight_hash;
    String offpay_hash;
    String offpay_hash_batch;
    double good_price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (rootView == null) {
            rootView = getLayoutInflater().inflate(R.layout.huohang_activity_check_order, null);
        }
        setContentView(rootView);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        cart_id = intent.getStringExtra("cart_id");
        store_id = intent.getStringExtra("store_id");

        initTypeFace();
        initCheckDatas();


    }

    double totalPrice=0.00;
    private void initCheckDatas() {
        ProgressDlgUtil.showProgressDlg("Loading...", this);
        Log.i("zds", "initCheckDatas: " +"http://huohang.huaqiaobang.com/mobile/index.php?act=buy&op=buy_step1&key=" + MyApplication.getInstance().getMykey() + cart_id + "&ifcart=1" + "&userId=" + MyApplication.getInstance().self.getId()+"&version=1.0"+"&store_id="+store_id);
        HttpRequest.sendPost("http://huohang.huaqiaobang.com/mobile/index.php?act=buy&op=buy_step1", "key=" + MyApplication.getInstance().getMykey() + cart_id + "&ifcart=1" + "&userId=" + MyApplication.getInstance().self.getId()+"&version=1.0"+"&store_id="+store_id, new HttpRevMsg(){
            @Override
            public void revMsg(final String msg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(!TextUtils.isEmpty(msg)){
                            try {
                                CheckOrderBean checkOrderBean=new Gson().fromJson(msg, CheckOrderBean.class);
                                if(checkOrderBean!=null&&checkOrderBean.code==200){
                                    if(checkOrderBean.datas!=null&&checkOrderBean.datas.state){
                                        if(checkOrderBean.datas.data!=null){
                                            vat_hash = checkOrderBean.datas.data.vatHash;
                                            freight_hash = checkOrderBean.datas.data.freightList;
                                            good_price=checkOrderBean.datas.data.storeGoodsTotal;  // 商品总价
                                            totalPrice=checkOrderBean.datas.data.storeGoodsTotal;
                                            tvShifuPrice.setText(NumberUtils.formatPriceOuYuan(good_price));
                                            /*地址信息*/
                                            if(checkOrderBean.datas.data.addressInfo!=null){
                                                address = checkOrderBean.datas.data.addressInfo.areaInfo;
                                                address2 = checkOrderBean.datas.data.addressInfo.address;
                                                name = checkOrderBean.datas.data.addressInfo.trueName;
                                                phone = checkOrderBean.datas.data.addressInfo.mobPhone;  //手机号码
                                                tel_phone = checkOrderBean.datas.data.addressInfo.telPhone;  //座机号码
                                                idCard = checkOrderBean.datas.data.addressInfo.idCard;
                                                area_id = checkOrderBean.datas.data.addressInfo.areaId;
                                                city_id = checkOrderBean.datas.data.addressInfo.cityId;
                                                address_id = checkOrderBean.datas.data.addressInfo.addressId;
                                                if (area_id.equals("0") || city_id.equals("0")) {
                                                    showToast("当前的地址出现未知错误，请修改改地址或重新选择地址");
                                                }
                                                initOffPayHash();
                                            }

                                             /*商品Item*/

                                            if(checkOrderBean.datas.data.storeCartList!=null&&checkOrderBean.datas.data.storeCartList.size()>0){
                                                linerMian.removeAllViews();
                                                Log.i("zds", "run: checkOrderBean.datas.data.storeCartList=="+checkOrderBean.datas.data.storeCartList);
                                                for (int i = 0; i < checkOrderBean.datas.data.storeCartList.size(); i++) {
                                                    final StoreCartListEntry storeCartListEntry=checkOrderBean.datas.data.storeCartList.get(i);
                                                    if(storeCartListEntry!=null){
                                                        View view = View.inflate(HuoHangCheckOrderActivity.this,R.layout.include_huohang_checkorder, null);

                                                        TextView t_store_name = (TextView) view.findViewById(R.id.t_store_name); // 店铺的name
                                                        LinearLayout liner_items = (LinearLayout) view.findViewById(R.id.liner_items); // 店铺的items 容器
                                                        final EditText ed_words = (EditText) view.findViewById(R.id.ed_words); // 买家留言

                                                        RelativeLayout liner_mang_pop = (RelativeLayout) view.findViewById(R.id.liner_mang_pop); // 满送提示
                                                        TextView tv_mang_price = (TextView) view.findViewById(R.id.tv_mang_price); // 满送提示总价
                                                        final TextView tv_song = (TextView) view.findViewById(R.id.tv_song); // 满送des
                                                        TextView tv_store_name = (TextView) view.findViewById(R.id.tv_store_name); // 满送的店名
                                                        ImageView iv_wenhao = (ImageView) view.findViewById(R.id.iv_wenhao); // 满送提示问号提示

                                                        LinearLayout tv_have_fapiao = (LinearLayout) view.findViewById(R.id.tv_have_fapiao); // 是否开发票
                                                        final ImageView iv_have_piao = (ImageView) view.findViewById(R.id.iv_have_piao); //
                                                        LinearLayout tv_no_fapiao = (LinearLayout) view.findViewById(R.id.tv_no_fapiao); // 是否开发票
                                                        final ImageView iv_no_piao = (ImageView) view.findViewById(R.id.iv_no_piao); //

                                                        LinearLayout liner_shui_root = (LinearLayout) view.findViewById(R.id.liner_shui_root); // 税率
                                                        TextView tvShuilvNe2=(TextView)view.findViewById(R.id.tv_shuilv_ne2);
                                                        TextView tvShuilvM2=(TextView)view.findViewById(R.id.tv_shuilv_m2);
                                                        TextView tvShuilvBr2=(TextView)view.findViewById(R.id.tv_shuilv_br2);
                                                        TextView tvTotalNetto=(TextView)view.findViewById(R.id.tv_total_Netto);
                                                        TextView tvTotalMwst=(TextView)view.findViewById(R.id.tv_total_Mwst);
                                                        TextView tvTotalBru=(TextView)view.findViewById(R.id.tv_total_Bru);

                                                        View lin_top= view.findViewById(R.id.lin_top); //与活动的分割线
                                                        View lin_bottom= view.findViewById(R.id.lin_bottom); //与活动的分割线
                                                        LinearLayout liner_hdong2=(LinearLayout)view.findViewById(R.id.liner_hdong2); //与活动的分割线

                                                        t_store_name.setText(storeCartListEntry.store_name);
                                                        tv_store_name.setVisibility(View.GONE);
    //                                                    tv_store_name.setText(storeCartListEntry.store_name);

                                                        /*买家留言*/
                                                        ed_words.addTextChangedListener(new TextWatcher() {
                                                            @Override
                                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                            }

                                                            @Override
                                                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                            }

                                                            @Override
                                                            public void afterTextChanged(Editable s) {
                                                                Log.i("zds", "afterTextChanged: s.length()"+s.length());
                                                                try {
                                                                    if(jsonObject_nativemsg.has(storeCartListEntry.storeId+"")){
                                                                        jsonObject_nativemsg.remove(storeCartListEntry.storeId+"");
                                                                        jsonObject_nativemsg.put(storeCartListEntry.storeId+"",s.toString());
                                                                    }else {
                                                                        jsonObject_nativemsg.put(storeCartListEntry.storeId+"",s.toString());
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }

                                                                Log.i("zds", "onClick: jsonObject_nativemsg==="+jsonObject_nativemsg.toString());
                                                            }
                                                        });

                                                        /*默认不需要发票*/
                                                        try {
                                                            jsonObject_fapaio.put(storeCartListEntry.storeId+"",native_invoice);
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        /*要发票*/
                                                      tv_have_fapiao.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              native_invoice=1;
                                                              iv_have_piao.setImageResource(R.drawable.iv_check);
                                                              iv_no_piao.setImageResource(R.drawable.iv_no_check);
                                                              try {
                                                                  if(jsonObject_fapaio.has(storeCartListEntry.storeId+"")){
                                                                      jsonObject_fapaio.remove(storeCartListEntry.storeId+"");
                                                                      jsonObject_fapaio.put(storeCartListEntry.storeId+"",native_invoice);
                                                                  }else {
                                                                      jsonObject_fapaio.put(storeCartListEntry.storeId+"",native_invoice);
                                                                  }
                                                              } catch (JSONException e) {
                                                                  e.printStackTrace();
                                                              }

                                                              Log.i("zds", "onClick: jsonObject_fapaio==="+jsonObject_fapaio.toString());
                                                          }
                                                      });

                                                        /*不要发票*/
                                                        tv_no_fapiao.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                native_invoice=2;
                                                                iv_have_piao.setImageResource(R.drawable.iv_no_check);
                                                                iv_no_piao.setImageResource(R.drawable.iv_check);
                                                                try {
                                                                    if(jsonObject_fapaio.has(storeCartListEntry.storeId+"")){
                                                                        jsonObject_fapaio.remove(storeCartListEntry.storeId+"");
                                                                        jsonObject_fapaio.put(storeCartListEntry.storeId+"",native_invoice);
                                                                    }else {
                                                                        jsonObject_fapaio.put(storeCartListEntry.storeId+"",native_invoice);
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                Log.i("zds", "onClick: jsonObject_fapaio==="+jsonObject_fapaio.toString());
                                                            }
                                                        });

                                                        liner_shui_root.setVisibility(VISIBLE);

                                                        /*首单满送*/
                                                        visible_rgId_click.put(storeCartListEntry.storeId+"",View.VISIBLE);
                                                        if(!TextUtils.isEmpty(storeCartListEntry.nativeRuleMoney)){
                                                            liner_mang_pop.setVisibility(View.VISIBLE);
                                                            tv_mang_price.setText("满"+storeCartListEntry.nativeRuleMoney);
                                                            visible_rgId.put(storeCartListEntry.storeId+"",View.VISIBLE);
                                                        }else {
                                                            liner_mang_pop.setVisibility(View.GONE);
                                                            visible_rgId.put(storeCartListEntry.storeId+"",View.GONE);
                                                        }

                                                        if(!TextUtils.isEmpty(storeCartListEntry.nativeRuleMessage)){
                                                            tv_mang_price.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    tiShiPop(storeCartListEntry.nativeRuleMessage);
                                                                }
                                                            });
                                                            iv_wenhao.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    tiShiPop(storeCartListEntry.nativeRuleMessage);
                                                                }
                                                            });
                                                        }


                                                        if(storeCartListEntry.nativeRuleList!=null&&storeCartListEntry.nativeRuleList.size()>0){
                                                            final ArrayList<StoreCartListEntry.NativeRuleListEntry> list_mang=new ArrayList<StoreCartListEntry.NativeRuleListEntry>();
                                                            list_mang.clear();
                                                            liner_mang_pop.setOnClickListener(new View.OnClickListener() {  // 弹窗 首单满送
                                                                @Override
                                                                public void onClick(View v) {
                                                                    View itemView = View.inflate(HuoHangCheckOrderActivity.this, R.layout.huoh_danmang_song, null);
                                                                    TextView tv_cancle = (TextView) itemView.findViewById(R.id.tv_cancle);
                                                                    LinearLayout liner_content = (LinearLayout) itemView.findViewById(R.id.liner_content);

                                                                    final PopupWindow popupWindow_store = new PopupWindow();
                                                                    popupWindow_store.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                                                                    popupWindow_store.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                                                                    popupWindow_store.setContentView(itemView);

                                                                    popupWindow_store.setAnimationStyle(R.style.popUpwindow_anim);//设置弹出和消失的动画
                                                                    //触摸点击事件
                                                                    popupWindow_store.setTouchable(true);
                                                                    //聚集
                                                                    popupWindow_store.setFocusable(true);
                                                                    //设置允许在外点击消失
                                                                    popupWindow_store.setOutsideTouchable(false);
                                                                    //点击返回键popupwindown消失
                                                                    popupWindow_store.setBackgroundDrawable(new BitmapDrawable());
                                                                    //背景变暗
                                                                    WindowManager.LayoutParams params = getWindow().getAttributes();
                                                                    params.alpha = 1f;
                                                                    getWindow().setAttributes(params);
                                                                    popupWindow_store.setTouchInterceptor(new View.OnTouchListener() {
                                                                        @Override
                                                                        public boolean onTouch(View v, MotionEvent event) {
                                                                            return false;
                                                                        }
                                                                    });
                                                                    //监听如果popupWindown消失之后背景变亮
                                                                    popupWindow_store.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                                                        @Override
                                                                        public void onDismiss() {
                                                                            WindowManager.LayoutParams params = getWindow()
                                                                                    .getAttributes();
                                                                            params.alpha = 1f;
                                                                            getWindow().setAttributes(params);
                                                                        }
                                                                    });
                                                                    popupWindow_store.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                                                                    if(popupWindow_store!=null){
                                                                        popupWindow_store.showAtLocation(v, Gravity.BOTTOM, 0,0);//显示位置  第三个参数设置以起始点的右下角为原点，向左、上各偏移的像素
                                                                    }


                                                                   /* tv_cancle.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            rg_id=0;
                                                                            tv_song.setText("(任选其一)");
                                                                            tv_song.setTextColor(getResources().getColor(R.color.huoh_song));
                                                                            popupWindow_store.dismiss();
                                                                        }
                                                                    });*/

                                                                    liner_content.removeAllViews();
                                                                    liner_content.invalidate();

                                                                    for (int i = 0; i < storeCartListEntry.nativeRuleList.size(); i++) {
                                                                        final StoreCartListEntry.NativeRuleListEntry ItemNatv=storeCartListEntry.nativeRuleList.get(i);
                                                                        if(ItemNatv!=null){
                                                                            StoreCartListEntry.NativeRuleListEntry mangSong=new StoreCartListEntry.NativeRuleListEntry();
                                                                            mangSong.rgId=ItemNatv.rgId;
                                                                            mangSong.goodsId=ItemNatv.goodsId;
                                                                            mangSong.goodsNum=ItemNatv.goodsNum;
                                                                            mangSong.title=ItemNatv.title;
                                                                            mangSong.money=ItemNatv.money;
                                                                            list_mang.add(mangSong);

                                                                            View view = View.inflate(HuoHangCheckOrderActivity.this,R.layout.text, null);
                                                                            TextView tv = (TextView) view.findViewById(R.id.tv);
                                                                            tv.setText(ItemNatv.title);
                                                                            liner_content.addView(view);

                                                                            view.setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    rg_id=ItemNatv.rgId;
                                                                                    tv_song.setText(ItemNatv.title);
                                                                                    tv_song.setTextColor(getResources().getColor(R.color.gray));
                                                                                    popupWindow_store.dismiss();
                                                                                    /*满送*/

                                                                                    isClickRgId=true;
                                                                                    visible_rgId.put(storeCartListEntry.storeId+"",View.GONE);
                                                                                    visible_rgId_click.put(storeCartListEntry.storeId+"",View.GONE);
                                                                                    try {
                                                                                        if(jsonObject_rgId.has(storeCartListEntry.storeId+"")){
                                                                                            jsonObject_rgId.remove(storeCartListEntry.storeId+"");
                                                                                            jsonObject_rgId.put(storeCartListEntry.storeId+"",rg_id);
                                                                                        }else {
                                                                                            jsonObject_rgId.put(storeCartListEntry.storeId+"",rg_id);
                                                                                        }
                                                                                    } catch (JSONException e) {
                                                                                        e.printStackTrace();
                                                                                    }
                                                                                    Log.i("zds", "onClick: jsonObject_rgId==="+jsonObject_rgId.toString());
                                                                                }
                                                                            });
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                        }

                                                        //税率信息 tax_arr
                                                        if (storeCartListEntry.taxArr!=null) {
                                                            tvShuilvNe2.setText(NumberUtils.formatPriceNo(storeCartListEntry.taxArr.nettoA) + "\n" + NumberUtils.formatPriceNo(storeCartListEntry.taxArr.nettoB) + "");
                                                            tvShuilvM2.setText(NumberUtils.formatPriceNo(storeCartListEntry.taxArr.mwstA) + "\n" + NumberUtils.formatPriceNo(storeCartListEntry.taxArr.mwstB) + "");
                                                            tvShuilvBr2.setText(NumberUtils.formatPriceNo(storeCartListEntry.taxArr.bruttoA) + "\n" + NumberUtils.formatPriceNo(storeCartListEntry.taxArr.bruttoB) + "");

                                                            tvTotalNetto.setText(NumberUtils.formatPriceNo((storeCartListEntry.taxArr.nettoA + storeCartListEntry.taxArr.nettoB)) + "");
                                                            tvTotalMwst.setText(NumberUtils.formatPriceNo((storeCartListEntry.taxArr.mwstA + storeCartListEntry.taxArr.mwstB)) + "");
                                                            tvTotalBru.setText(NumberUtils.formatPriceNo((storeCartListEntry.taxArr.bruttoA + storeCartListEntry.taxArr.bruttoB)) + "");
                                                        }

                                                        //活动信息 activity_arr
                                                        if (storeCartListEntry.activityArr != null) {

                                                            liner_hdong2.removeAllViews();

                                                            if (storeCartListEntry.activityArr.size() > 0) {
                                                                lin_top.setVisibility(VISIBLE);
                                                                lin_bottom.setVisibility(VISIBLE);
                                                                liner_hdong2.setVisibility(VISIBLE);
                                                                for (int h = 0; h < storeCartListEntry.activityArr.size(); h++) {

                                                                    StoreCartListEntry.ActivityArrEntry actith=storeCartListEntry.activityArr.get(h);
                                                                    if(actith!=null){
                                                                        View view_activity = View.inflate(HuoHangCheckOrderActivity.this, R.layout.item_hd_text2_big, null);

                                                                        ViewGroup parent = (ViewGroup) view_activity.getParent();
                                                                        if (parent != null) {
                                                                            parent.removeAllViews();
                                                                        }

                                                                        ImageView img = (ImageView) view_activity.findViewById(R.id.img);
                                                                        TextView tv = (TextView) view_activity.findViewById(R.id.tv);

                                                                        if (!TextUtils.isEmpty(actith.img)) {
                                                                            MyApplication.imageLoader.displayImage(actith.img, img, MyApplication.options);
                                                                        }

                                                                        if (!TextUtils.isEmpty(actith.activityName)) {
                                                                            tv.setText(actith.activityName);
                                                                        }

                                                                        liner_hdong2.addView(view_activity);
                                                                    }
                                                                }
                                                            } else {
                                                                liner_hdong2.setVisibility(GONE);
                                                            }
                                                        }

                                                        /*商品的列表*/
                                                        if(storeCartListEntry.goodsList!=null&&storeCartListEntry.goodsList.size()>0){
                                                            liner_items.removeAllViews();
                                                            for (int k = 0; k < storeCartListEntry.goodsList.size(); k++) {
                                                                final StoreCartListEntry.GoodsListEntry goodListItem=storeCartListEntry.goodsList.get(k);
                                                                if(goodListItem!=null){
                                                                    View itemView = View.inflate(HuoHangCheckOrderActivity.this,R.layout.huohang_item_order, null);

                                                                    ImageView img_logo=(ImageView) itemView.findViewById(R.id.img_logo);
                                                                    TextView tv_en_title=(TextView) itemView.findViewById(R.id.tv_en_title);
                                                                    TextView tv_title=(TextView) itemView.findViewById(R.id.tv_title);
                                                                    TextView tv_store=(TextView) itemView.findViewById(R.id.tv_store);
                                                                    TextView tv_price=(TextView) itemView.findViewById(R.id.tv_price);
                                                                    TextView tv_sui=(TextView) itemView.findViewById(R.id.tv_sui);
                                                                    TextView t_num=(TextView) itemView.findViewById(R.id.t_num);

                                                                    try {
                                                                        if(!TextUtils.isEmpty(goodListItem.goodsImageUrl)){
        //                http://www.huaqiaobang.com/data/upload/shop/store/goods/11/11_05375327290881075.jpg
                                                                            MyApplication.imageLoader.displayImage(goodListItem.goodsImageUrl,img_logo,MyApplication.options);
                                                                        }
                                                                        tv_title.setText(goodListItem.goodsName);
                                                                        tv_en_title.setText(goodListItem.goodsEnName);
                                                                        tv_store.setText("货号:"+goodListItem.goodsSerial);
                                                                        tv_price.setText(NumberUtils.formatPriceOuYuan(goodListItem.goodsPrice)+" "+goodListItem.taxRate);
                                                                        t_num.setText("X"+goodListItem.goodsNum);
//                                                                        tv_sui.setText(""+goodListItem.taxRate);
                                                                    } catch (Exception e) {
                                                                        e.printStackTrace();
                                                                    }

                                                                    liner_items.addView(itemView);
                                                                }
                                                            }
                                                        }

                                                        linerMian.addView(view);
                                                    }
                                                }
                                            }

                                            relatNull.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }catch (NullPointerException e){
                                e.printStackTrace();
                            }
                        }
                        ProgressDlgUtil.stopProgressDlg();
                    }
                });
            }
        });
    }

    private int rg_id=0;
    //    native_invoice    发票状态   1需要   2不需要
    private int native_invoice=2;

    JSONObject jsonObject_fapaio=new JSONObject();  // 发票
    JSONObject jsonObject_nativemsg=new JSONObject();  // 留言
    JSONObject jsonObject_rgId=new JSONObject(); // 满送
    Map<String,Integer> visible_rgId=new HashMap<>(); // 满送 提示是否可见
    Map<String,Integer> visible_rgId_click=new HashMap<>(); // 满送 提示是否可见
    private boolean isClickRgId=false;


    private void tiShiPop(String msg){
        View root_view=View.inflate(this,R.layout.pop_notices_msg2,null);
        TextView img_close=(TextView) root_view.findViewById(R.id.imge_close);
        com.abcs.haiwaigou.view.AlignTextView tv_msg=(com.abcs.haiwaigou.view.AlignTextView) root_view.findViewById(R.id.tv_msg);

        tv_msg.setText(msg);
        tv_msg.setTextSize(14.0f);

        final PopupWindow popupWindow= new PopupWindow();
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(root_view);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params =getWindow()
                        .getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        popupWindow.setAnimationStyle(R.style.popWindowAnimation);//设置弹出和消失的动画
        //触摸点击事件
        popupWindow.setTouchable(true);
        //聚集
        popupWindow.setFocusable(true);
        //设置允许在外点击消失
        popupWindow.setOutsideTouchable(false);
        //点击返回键popupwindown消失

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        popupWindow.showAtLocation(root_view, Gravity.CENTER, 0, 0);    //消息弹窗
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    public void initTypeFace() {
        try {
            Typeface type = Typeface.createFromAsset(getAssets(), "font/ltheijian.TTF");
            if (type != null) {
                tvShifuPrice.setTypeface(type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1 && data != null) {
            address2="";
            address = (String) data.getSerializableExtra("address");
            name = (String) data.getSerializableExtra("name");
            phone = (String) data.getSerializableExtra("phone");
            tel_phone = (String) data.getSerializableExtra("tel_phone");//座机号码
            address_id = (String) data.getSerializableExtra("address_id");
            city_id = (String) data.getSerializableExtra("city_id");
            area_id = (String) data.getSerializableExtra("area_id");
            Log.i("zjz", "city_id=" + city_id);
            Log.i("zjz", "area_id=" + area_id);
            Log.i("zjz", "address_id=" + address_id);
            address2 = null;
            initOffPayHash();
        }
    }

    private void initOffPayHash() {
        try {
            tName.setText(name.trim());
            if (phone.length() > 0) {
                tPhone.setText(phone);
            } else if (tel_phone.length() > 0) {
                tPhone.setText(tel_phone);
            }

            if (!TextUtils.isEmpty(address) && !TextUtils.isEmpty(address2)) {
                tAddress.setText("收货地址：" + address + " " + address2);
            } else if (!TextUtils.isEmpty(address)) {
                tAddress.setText("收货地址：" + address);
            } else if (!TextUtils.isEmpty(address2)) {
                tAddress.setText("收货地址：" + address2);
            }

            rlChoose.setVisibility(GONE);
            linearAddress.setVisibility(VISIBLE);
            ProgressDlgUtil.showProgressDlg("Loading...", this);

            Log.i("zdszds", "initOffPayHash: "+"http://huohang.huaqiaobang.com/mobile/index.php?act=member_buy&op=change_address"+"&key=" + MyApplication.getInstance().getMykey() + "&area_id=" + area_id + "&city_id=" + city_id + "&freight_hash=" + freight_hash);
            HttpRequest.sendPost("http://huohang.huaqiaobang.com/mobile/index.php?act=member_buy&op=change_address", "&key=" + MyApplication.getInstance().getMykey() + "&area_id=" + area_id + "&city_id=" + city_id + "&freight_hash=" + freight_hash, new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject object = new JSONObject(msg);
                                if (object.getInt("code") == 200) {
                                    JSONObject object1 = object.getJSONObject("datas");
                                    if (object1.has("error")) {
                                        showToast(object1.optString("error"));
                                    } else {
                                        offpay_hash = object1.optString("offpay_hash");
                                        offpay_hash_batch = object1.optString("offpay_hash_batch");
                                    }

                                    Log.i("zjz", "msg_offpayhash=" + msg);
                                    ProgressDlgUtil.stopProgressDlg();
                                } else {
                                    ProgressDlgUtil.stopProgressDlg();
                                    Log.i("zjz", "goodsDetail:解析失败");
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                Log.i("zjz", e.toString());
                                Log.i("zjz", msg);
                                e.printStackTrace();
                                ProgressDlgUtil.stopProgressDlg();
                            }
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.relative_back, R.id.rl_choose,R.id.img_edit,R.id.tv_huodaofukuan,R.id.linear_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_back:
                finish();
                break;
            case R.id.tv_huodaofukuan: /*提交我的订单*/
                confirmMyOrder();  // 提交订单
                break;
            case R.id.rl_choose:
                Intent intent;
                if (MyApplication.getInstance().getMykey() == null) {
                    intent = new Intent(this, WXEntryActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, HuoHangAddressActivity.class);
                    intent.putExtra("isBuy", true);
                    intent.putExtra("store_id", store_id);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.img_edit:
                intent = new Intent(this, HuoHangAddressActivity.class);
                intent.putExtra("isBuy", true);
                intent.putExtra("store_id", store_id);
                startActivityForResult(intent, 1);
                break;
            case R.id.linear_address:
                Log.d("zds", "onClick: store_id" + store_id);

                intent = new Intent(this, HuoHangAddressActivity.class);
                intent.putExtra("isBuy", true);
                intent.putExtra("store_id", store_id);
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
    }

    private void confirmMyOrder() {

        if (address_id == null) {
            showToast("请选择收货地址");
            return;
        }

        if(visible_rgId.containsValue(View.VISIBLE)&&visible_rgId_click.containsValue(View.VISIBLE)){
            showToast("亲，你还没有选择一个满送的商品！");
            return ;
        }

        ProgressDlgUtil.showProgressDlg("Loading...", this);
        String temp = null;

        if(jsonObject_rgId.length()>0){
            temp = "&key=" + MyApplication.getInstance().getMykey() + "&ifcart=1" + "&cart_id=" + cart_id + "&payment_type=online" + "&address_id="
                    + address_id + "&vat_hash=" + vat_hash + "&offpay_hash=" + offpay_hash + "&offpay_hash_batch=" + offpay_hash_batch
                    + "&pay_name=online" + "&invoice_id=" + "" + "&voucher=" + "" + "&rcb_pay=" + "" + "&pd_pay=" + "" + "" + "&login_mode=1" +
                    "&discount=" + "" + "&yyg_pay=" + "" + "&vip_pay=" + "" + "&userId=" + MyApplication.getInstance().self.getId()+"&rg_id="+jsonObject_rgId+"&native_invoice="+jsonObject_fapaio+"&native_message="+jsonObject_nativemsg+"&version=1.0"+"&order_form=2"+"&store_id="+store_id;
        }else {
            temp = "&key=" + MyApplication.getInstance().getMykey() + "&ifcart=1" + "&cart_id=" + cart_id + "&payment_type=online" + "&address_id="
                    + address_id + "&vat_hash=" + vat_hash + "&offpay_hash=" + offpay_hash + "&offpay_hash_batch=" + offpay_hash_batch
                    + "&pay_name=online" + "&invoice_id=" + "" + "&voucher=" + "" + "&rcb_pay=" + "" + "&pd_pay=" + "" + "" + "&login_mode=1" +
                    "&discount=" + "" + "&yyg_pay=" + "" + "&vip_pay=" + "" + "&userId=" + MyApplication.getInstance().self.getId()+"&native_invoice="+jsonObject_fapaio+"&native_message="+jsonObject_nativemsg+"&version=1.0"+"&order_form=2"+"&store_id="+store_id;
        }

        Log.i("zjz", "temp=" + temp);

        HttpRequest.sendPost("http://huohang.huaqiaobang.com/mobile/index.php?act=buy&op=buy_step2", temp, new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object = new JSONObject(msg);
                            Log.i("zjz", "msg_confirm_order=" + msg);
                            if (object.getInt("code") == 200) {
                                JSONObject object1 = object.optJSONObject("datas");
                                if (object1 != null) {
                                    JSONObject payinfo = object1.optJSONObject("pay_info");
                                    JSONArray jsonArray = object1.optJSONArray("order_list");
                                    String order_sn="";
                                    if(jsonArray!=null&&jsonArray.length()>0){
                                        JSONObject orderObject=jsonArray.getJSONObject(0);
                                        order_sn=orderObject.optString("order_sn");
                                    }

                                    if (payinfo.has("pay_sn") && payinfo != null) {

                                        Intent intent=new Intent(HuoHangCheckOrderActivity.this, PayWayActivity.class);
                                        intent.putExtra("pay_sn",payinfo.optString("pay_sn"));
                                        intent.putExtra("pay_sn_huodao",order_sn);
                                        intent.putExtra("yungou",false);
                                        intent.putExtra("isFromOrder", false);
                                        intent.putExtra("total_money",Double.valueOf(totalPrice));
                                        startActivity(intent);

                                        MyUpdateUI.sendUpdateCarNum(HuoHangCheckOrderActivity.this);
//                                        showToast("提交成功！" + object1.optString("order_remind"));
                                       handler.postDelayed(new Runnable() {
                                           @Override
                                           public void run() {
                                               finish();
                                           }
                                       },1000);

                                    }
                                } else if (object.optString("datas").contains("成功")) {
//                                    MyUpdateUI.sendUpdateCollection(HuoHangCheckOrderActivity.this, MyUpdateUI.CART);
                                    MyUpdateUI.sendUpdateCarNum(HuoHangCheckOrderActivity.this);
                                    showToast("提交成功！");
                                    Intent intent = new Intent(HuoHangCheckOrderActivity.this, HuoHangNewOrderActivity.class);
//                                    intent.putExtra("position", 0);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    showToast(object.optString("datas"));
                                }
                                ProgressDlgUtil.stopProgressDlg();
                            } else {
                                ProgressDlgUtil.stopProgressDlg();
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            ProgressDlgUtil.stopProgressDlg();
                        }
                    }
                });

            }
        });
    }

    @Override
    protected void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }
}
