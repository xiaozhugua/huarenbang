package com.abcs.haiwaigou.local.huohang.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.activity.GuanggaoActivity;
import com.abcs.huaqiaobang.wxapi.WXEntryActivity;
import com.abcs.imkfsdk.chat.ChatActivity;
import com.abcs.imkfsdk.chat.PeerDialog;
import com.abcs.sociax.android.R;
import com.abcs.sociax.t4.android.ActivityHome;
import com.abcs.sociax.t4.android.fragment.FragmentSociax;
import com.moor.imkf.GetPeersListener;
import com.moor.imkf.IMChatManager;
import com.moor.imkf.InitListener;
import com.moor.imkf.model.entity.Peer;
import com.moor.imkf.utils.NetUtils;
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zds 货行消息首页
 */
public class HHMessageFragment extends FragmentSociax {

    static HHMessageFragment instance;

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.img_logo)
    ImageView imgLogo;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.desc)
    TextView desc;
    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.relative_kefu)
    AutoRelativeLayout relativeKefu;
    @InjectView(R.id.img_logo_buy_des)
    ImageView imgLogoBuyDes;
    @InjectView(R.id.name_buy_des)
    TextView nameBuyDes;
    @InjectView(R.id.desc_buy_des)
    TextView descBuyDes;
    @InjectView(R.id.time_buy_des)
    TextView timeBuyDes;
    @InjectView(R.id.relative_bug_des)
    AutoRelativeLayout relativeBugDes;
    @InjectView(R.id.img_logo_order_msg)
    ImageView imgLogoOrderMsg;
    @InjectView(R.id.name_order_msg)
    TextView nameOrderMsg;
    @InjectView(R.id.desc_order_msg)
    TextView descOrderMsg;
    @InjectView(R.id.time_order_msg)
    TextView timeOrderMsg;
    @InjectView(R.id.relative_order_msg)
    AutoRelativeLayout relativeOrderMsg;
    @InjectView(R.id.img_logo_fanli)
    ImageView imgLogoFanli;
    @InjectView(R.id.name_fanli)
    TextView nameFanli;
    @InjectView(R.id.desc_fanli)
    TextView descFanli;
    @InjectView(R.id.time_fanli)
    TextView timeFanli;
    @InjectView(R.id.relative_fanli)
    AutoRelativeLayout relativeFanli;
    @InjectView(R.id.img_logo_address)
    ImageView imgLogoAddress;
    @InjectView(R.id.name_address)
    TextView nameAddress;
    @InjectView(R.id.desc_address)
    TextView descAddress;
    @InjectView(R.id.time_address)
    TextView timeAddress;
    @InjectView(R.id.relative_address)
    AutoRelativeLayout relativeAddress;
//    @InjectView(R.id.tv_title)
//    TextView tvTitle;
//    @InjectView(R.id.listview)
//    ListView listview;
//    @InjectView(R.id.swipeRefresh)
//    SwipeRefreshLayout swipeRefresh;

    public static HHMessageFragment getInstance() {
        if (instance == null) {
            instance = new HHMessageFragment();
        }
        return instance;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hh_message;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    ActivityHome activity;
    View view;


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

//    private List<String> datas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        view = inflater.inflate(R.layout.fragment_hh_message, null);
        activity = (ActivityHome) getActivity();
        ButterKnife.inject(this, view);

//
//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                initAllDates();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeRefresh.setRefreshing(false);
//                    }
//                }, 2000);
//            }
//        });
//
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(activity, HHNewOrderMessageActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        initAllDates();
        return view;
    }

//    Handler handler = new Handler();
//    boolean isFirst = true;

//    /*listview Item 动画*/
//    private void startAniWithPosition(final int potisition, final View itemView) {
//        itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                int hight = MyApplication.getHeight();
//                if (potisition > (hight / itemView.getMeasuredHeight()) - 1) {
//                    return;
//                }
//                AnimatorSet set = new AnimatorSet();
//                set.playTogether(ObjectAnimator.ofFloat(itemView, "translateY", -itemView.getMeasuredHeight(), 0),
////                        ObjectAnimator.ofFloat(itemView,"scaleX",0.3f,1.0f),
////                        ObjectAnimator.ofFloat(itemView,"scaleY",0.3f,1.0f),
//                        ObjectAnimator.ofFloat(itemView, "alpha", 0, 1));
//                set.setStartDelay(100 * potisition);
//                set.setDuration(200).start();
//                itemView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//
//            }
//        });
//    }
//
//    private void initAllDates() {
//        datas.clear();
//        for (int i = 0; i < 50; i++) {
//            datas.add("" + i);
//        }
//
//        listview.setAdapter(new ItemSAdapter(activity, datas));
////        if (isFirst) {
////            ProgressDlgUtil.showProgressDlg("Loading...", activity);
////        }
////        HttpRequest.sendGet(TLUrl.getInstance().getUrl() + "/admintravel/city/queryGameImg", "type=1", new HttpRevMsg() {
////            @Override
////            public void revMsg(final String msg) {
////                // TODO Auto-generated method stub
////                handler.post(new Runnable() {
////                    @Override
////                    public void run() {
////                        if (TextUtils.isEmpty(msg)) {
////                            return;
////                        } else {
////
////                            TestBean testBean = new Gson().fromJson(msg, TestBean.class);
////                            if (testBean != null && testBean.result == 1) {
////                                if (testBean.body != null && testBean.body.size() > 0) {
////                                    list_view.setAdapter(new ItemSAdapter(activity, testBean.body));
////                                }
////                            }
////
////                      /*      try {
////                                JSONObject object=new JSONObject(msg);
////                                if(object!=null&&object.optInt("status")==1){
////                                    MyApplication.imageLoader.displayImage(object.optString("msg"),logo,MyApplication.options);
////                                }
////                            }catch (org.json.JSONException e){
////
////                                e.printStackTrace();
////                            }*/
////
////                            if (isFirst) {
////                                ProgressDlgUtil.stopProgressDlg();
////                                isFirst = false;
////                            }
////                        }
////                    }
////                });
////            }
////        });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    String url_dec="http://huohang.huaqiaobang.com/mobile/PurchaseInfo.html ";

    @OnClick({R.id.relative_kefu, R.id.relative_bug_des, R.id.relative_order_msg, R.id.relative_fanli, R.id.relative_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_kefu:
//                Intent tt = new Intent(activity, HHKeFuActivity.class);
//                startActivity(tt);
                if(MyApplication.getInstance().getMykey()==null){
                    Intent intent = new Intent(activity, WXEntryActivity.class);
                    intent.putExtra("isthome", true);
                    startActivity(intent);
                }else {
                   init();// 初始化容联客服
                }
                break;
            case R.id.relative_bug_des:
//                Toast.makeText(activity,"该功能暂未开发！",Toast.LENGTH_LONG).show();
                try {
                    Intent tt = new Intent(activity, GuanggaoActivity.class);
                    tt.putExtra("title_local", "购买说明");
                    tt.putExtra("url_local", url_dec);
                    startActivity(tt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.relative_order_msg:
                if(MyApplication.getInstance().getMykey()==null){
                    Intent intent = new Intent(activity, WXEntryActivity.class);
                    intent.putExtra("isthome", true);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(activity, HuoHangNewOrderActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.relative_fanli:
                try {
                    if(MyApplication.getInstance().getMykey()==null){
                        Intent intent = new Intent(activity, WXEntryActivity.class);
                        intent.putExtra("isthome", true);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(activity, NoticeActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.relative_address:
                if(MyApplication.getInstance().getMykey()==null){
                    Intent intent = new Intent(activity, WXEntryActivity.class);
                    intent.putExtra("isthome", true);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(activity, HuoHangAddressActivity.class);
                    intent.putExtra("formSetting", true);
                    startActivity(intent);
                }
                break;
        }
    }

    private void init() {

        if (!NetUtils.hasDataConnection(activity)) {
//            Toast.makeText(MainActivity.this, "当前没有网络连接", Toast.LENGTH_SHORT).show();
            return;
        }

        if (MyApplication.isKFSDK) {
            getPeers();
        } else {
            startKFService();
        }
    }

    private void getPeers() {
        IMChatManager.getInstance().getPeers(new GetPeersListener() {
            @Override
            public void onSuccess(List<Peer> peers) {
                if (peers.size() > 1) {
                    PeerDialog dialog = new PeerDialog();
                    Bundle b = new Bundle();
                    b.putSerializable("Peers", (Serializable) peers);
                    b.putString("type", "init");
                    dialog.setArguments(b);
                    dialog.show(activity.getFragmentManager(), "");

                } else if (peers.size() == 1) {
                    startChatActivity(peers.get(0).getId());
                } else {
                    startChatActivity("");
                }
            }

            @Override
            public void onFailed() {

            }
        });

    }

    private void startKFService() {
        new Thread() {
            @Override
            public void run() {
                IMChatManager.getInstance().setOnInitListener(new InitListener() {
                    @Override
                    public void oninitSuccess() {
                        MyApplication.isKFSDK = true;
                        getPeers();
                        Log.d("MobileApplication", "sdk初始化成功");

                    }

                    @Override
                    public void onInitFailed() {
                        MyApplication.isKFSDK = false;
                        Log.d("MobileApplication", "sdk初始化失败, 请填写正确的accessid");
                    }
                });

                //初始化IMSdk,填入相关参数
                IMChatManager.getInstance().init(MyApplication.getInstance(), "action", accessId, MyApplication.getInstance().self.getNickName()+"", MyApplication.getInstance().self.getId()+"");

            }
        }.start();

    }

    //    private String accessId="faab6b70-9860-11e7-be2a-9b965dace81b";  // 测试
    private String accessId="5d98e7e0-991e-11e7-9bf4-5370830de443";
    private void startChatActivity(String peerId) {
        Intent chatIntent = new Intent(activity, ChatActivity.class);
        chatIntent.putExtra("PeerId", peerId);
        startActivity(chatIntent);
    }
}
