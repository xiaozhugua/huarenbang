package com.abcs.haiwaigou.local.huohang.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.local.huohang.bean.Acty;
import com.abcs.haiwaigou.local.huohang.bean.DatasEntrySer;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.huaqiaobang.ytbt.common.utils.ToastUtil;
import com.abcs.sociax.android.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zds 货行商家
 */
public class ItemShangJiaFragment extends Fragment {

    Activity activity;
    View view;
    @InjectView(R.id.tv_location_name)
    TextView tvLocationName;
    @InjectView(R.id.linear_location)
    LinearLayout linearLocation;
    @InjectView(R.id.tv_location_desc)
    TextView tvLocationDesc;
    @InjectView(R.id.linear_laba)
    LinearLayout linearLaba;
    @InjectView(R.id.tv_peisong)
    TextView tvPeisong;
    @InjectView(R.id.linear_server)
    LinearLayout linearServer;
    @InjectView(R.id.tv_shijian)
    TextView tvShijian;
    @InjectView(R.id.linear_shijian)
    LinearLayout linearShijian;
    @InjectView(R.id.tv_shangjia_server)
    TextView tvShangjiaServer;
    @InjectView(R.id.linear_shangjia_server)
    LinearLayout linearShangjiaServer;
    @InjectView(R.id.tv_fapiao)
    TextView tvFapiao;
    @InjectView(R.id.linear_fapiao)
    LinearLayout linearFapiao;
    @InjectView(R.id.tv_huodaofukuan)
    TextView tvHuodaofukuan;
    @InjectView(R.id.linear_huodao)
    LinearLayout linearHuodao;
    @InjectView(R.id.linear_acty)
    LinearLayout linearActy;
    @InjectView(R.id.relative_phone)
    RelativeLayout relative_phone;



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNoticeChange( DatasEntrySer datasEntry) {
        if (datasEntry != null) {
            initTop(datasEntry);
        }
    }

    public static ItemShangJiaFragment newInstance(DatasEntrySer datasEntry) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("c_id", datasEntry);
        ItemShangJiaFragment fragment = new ItemShangJiaFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        view = inflater.inflate(R.layout.fragment_item_shangjia, null);
        activity = (Activity) getActivity();
        ButterKnife.inject(this, view);
        EventBus.getDefault().register(this);
        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                final DatasEntrySer datasEntry = (DatasEntrySer) bundle.getSerializable("c_id");

                if (datasEntry != null) {
                    initTop(datasEntry);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void initTop(final DatasEntrySer datasEntry) {
        try {
    /*地址*/
            if (!TextUtils.isEmpty(datasEntry.getStoreAddress())) {
                tvLocationName.setText(datasEntry.getStoreAddress());
            }
                /*描述*/
            if (!TextUtils.isEmpty(datasEntry.getStoreDes())) {
                tvLocationDesc.setText(datasEntry.getStoreDes());
            }
                /*配送时间*/
            if (!TextUtils.isEmpty(datasEntry.getStoreSendTime())) {
                tvShijian.setText("配送时间：" + datasEntry.getStoreSendTime());
            }
                /*电话*/

            linearLaba.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popHHGongGao(datasEntry);
                }
            });

            relative_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(datasEntry.getStorePhone())) {
                        call(datasEntry.getStorePhone());
                    }else {
                        ToastUtil.showMessage("暂无电话！");
                    }
                }
            });


                         /*活动信息*/
            if(datasEntry.getActivity()!=null){

                if(datasEntry.getActivity().size()>0){
                    linearActy.removeAllViews();
                    linearActy.setVisibility(View.VISIBLE);
                    for (int k = 0; k <datasEntry.getActivity().size() ; k++) {

                        Acty bean_hd=datasEntry.getActivity().get(k);
                        View view=View.inflate(getContext(), R.layout.item_huohang_acty,null);

                        ViewGroup parent3 = (ViewGroup) view.getParent();
                        if (parent3 != null) {
                            parent3.removeAllViews();
                        }

                        ImageView img=(ImageView) view.findViewById(R.id.img);
                        TextView tv=(TextView) view.findViewById(R.id.tv);

                        if(bean_hd!=null){
                            if(!TextUtils.isEmpty(bean_hd.img)){
    //                http://www.huaqiaobang.com/data/upload/shop/store/goods/11/11_05375327290881075.jpg
                                MyApplication.imageLoader.displayImage(bean_hd.img,img,MyApplication.options);
                            }

                            if(!TextUtils.isEmpty(bean_hd.name)){
                                tv.setText(bean_hd.name);
                            }
                        }

                        linearActy.addView(view);
                    }
                }else {
                    linearActy.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }


    /*活动公告*/
    private void popHHGongGao(DatasEntrySer datasEntry) {
        try {
            View root_view = View.inflate(activity, R.layout.pop_huohang_hh_gonggao, null);
            ImageView imge_close = (ImageView) root_view.findViewById(R.id.iv_close);
            TextView tv_content_gg = (TextView) root_view.findViewById(R.id.tv_content_gg);
            LinearLayout liner_act = (LinearLayout) root_view.findViewById(R.id.liner_act);

            if(datasEntry!=null){
                                 /*店铺描述*/
                if (!TextUtils.isEmpty(datasEntry.storeDes)) {
                    tv_content_gg.setText(datasEntry.storeDes);
                }
                /*店铺描述*/
                if (datasEntry.activity != null) {
                    if (datasEntry.activity.size() > 0) {
                        liner_act.removeAllViews();
                        for (int k = 0; k < datasEntry.activity.size(); k++) {

                            Acty bean_hd = datasEntry.activity.get(k);
                            View view = View.inflate(activity, R.layout.item_huohang_acty, null);

                            ViewGroup parent3 = (ViewGroup) view.getParent();
                            if (parent3 != null) {
                                parent3.removeAllViews();
                            }

                            ImageView img = (ImageView) view.findViewById(R.id.img);
                            TextView tv = (TextView) view.findViewById(R.id.tv);

                            if (bean_hd != null) {
                                if (!TextUtils.isEmpty(bean_hd.img)) {
                                    MyApplication.imageLoader.displayImage(bean_hd.img, img, MyApplication.getListOptions());
                                }

                                if (!TextUtils.isEmpty(bean_hd.name)) {
                                    tv.setText(bean_hd.name);
                                }
                            }

                            liner_act.addView(view);
                        }
                    }
                }
            }

            final PopupWindow popupWindow = new PopupWindow();
            popupWindow.setWidth(Util.WIDTH*5/6);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setContentView(root_view);

            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            params.alpha = 0.5f;
            activity.getWindow().setAttributes(params);
            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams params = activity.getWindow()
                            .getAttributes();
                    params.alpha = 1f;
                    activity.getWindow().setAttributes(params);
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

            popupWindow.showAtLocation(root_view, Gravity.CENTER, 0, 0);
            imge_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
