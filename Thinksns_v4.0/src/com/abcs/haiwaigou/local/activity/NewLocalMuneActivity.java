package com.abcs.haiwaigou.local.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abcs.haiwaigou.broadcast.MyBroadCastReceiver;
import com.abcs.haiwaigou.local.adapter.GirdDropDownAdapter;
import com.abcs.haiwaigou.local.beans.FiletersBean;
import com.abcs.haiwaigou.local.beans.FiletersBeanX;
import com.abcs.haiwaigou.local.beans.Newlocal_ZhaoGong;
import com.abcs.haiwaigou.local.beans.ZhaoGongTiaoJian;
import com.abcs.haiwaigou.local.view.dropdownmenu.DropDownMenu;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.sociax.android.R;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.thinksns.sociax.thinksnsbase.utils.TLUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.view.View.inflate;

public class NewLocalMuneActivity extends AppCompatActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.back)
    RelativeLayout back;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;

    MyAdapter adapter;
    String menuId;
    String title;
    String cityId;

    private List<String> headers = new ArrayList<>();
    List<FiletersBean> fileters=new ArrayList<>();
    private List<View> popupViews = new ArrayList<>();
    private GirdDropDownAdapter cityAdapter;
    private int pageNo = 1;
    private String fileterId;
    private boolean isRefresh = false;
    private boolean isFirst = true;
    Handler handler = new Handler();
    MyBroadCastReceiver myBroadCastReceiver;

    @Override
    public void onDestroy() {
        super.onDestroy();
        myBroadCastReceiver.unRegister();
        ButterKnife.reset(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhaogongfirm);
        ButterKnife.inject(this);
        myBroadCastReceiver = new MyBroadCastReceiver(this, updateUI);
        myBroadCastReceiver.register();
        title = getIntent().getStringExtra("title");
        tvTitle.setText(title);
        cityId = getIntent().getStringExtra("cityId");
        menuId = getIntent().getStringExtra("menuId");
        getTop();

    }
    MyBroadCastReceiver.UpdateUI updateUI = new MyBroadCastReceiver.UpdateUI() {
        @Override
        public void updateShopCar(Intent intent) {

        }

        @Override
        public void updateCarNum(Intent intent) {

        }

        @Override
        public void updateCollection(Intent intent) {
            if (intent.getStringExtra("type").equals("publish_success")) {
                Log.i("xxx", "updateCollection: ===publish_success");
                pageNo = 1;
                isRefresh = true;
                if (!TextUtils.isEmpty(fileterId)) {
                    getData(fileterId);
                } else {
                    getData("");
                }
            }
        }

        @Override
        public void update(Intent intent) {

        }
    };
    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    private void getData(String filterId) {
        if (isFirst) {
            isFirst = false;
            ProgressDlgUtil.showProgressDlg("", this);
        }

//        https://japi.tuling.me/hrq/conListDetail/v2/newestInfo?cityId=41&menuId=69
        String paramas;

        if (!TextUtils.isEmpty(filterId)) {
            paramas = "cityId=" + cityId + "&menuId=" + menuId + "&page=" + pageNo + "&pageSize=10" + "&filterId=" + filterId;
        } else {
            paramas = "cityId=" + cityId + "&menuId=" + menuId + "&page=" + pageNo + "&pageSize=10";
        }


        HttpRequest.sendGet(TLUrl.getInstance().URL_bind_base + "/hrq/conListDetail/v2/newestInfo", paramas, new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("zds", "zhaogong_msg===" + msg);
                        ProgressDlgUtil.stopProgressDlg();
                        if (isRefresh) {
                            adapter.clear();
                            isRefresh = false;
                        }

                        if (msg.length() != 0) {
                            final Newlocal_ZhaoGong newLocal = new Gson().fromJson(msg, Newlocal_ZhaoGong.class);
                            if (newLocal != null && newLocal.status == 1 && newLocal.msg != null) {
                                if (newLocal.msg.size() > 0) {
                                    adapter.addAll(newLocal.msg);
                                } else {
                                    if(pageNo==1){
                                        Toast.makeText(NewLocalMuneActivity.this, "抱歉，暂无数据！", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(NewLocalMuneActivity.this, "没有更多了！", Toast.LENGTH_SHORT).show();
                                    }
                                    adapter.stopMore();
                                }
                            }
                        }

                        ProgressDlgUtil.stopProgressDlg();
                    }
                });

            }
        });
    }

    private void getTop() {
//        https://japi.tuling.me/hrq/conListDetail/v2/getPubContion?cityId=41&menuId=69
        HttpRequest.sendGet(TLUrl.getInstance().URL_bind_base + "/hrq/conListDetail/v2/getPubContion", "cityId=" + cityId + "&menuId=" + menuId, new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("zds", "zhaogong_msg_tiaojian===" + msg);
                        if (msg.length() != 0) {
                            final ZhaoGongTiaoJian zhaoGongTiaoJian = new Gson().fromJson(msg, ZhaoGongTiaoJian.class);
                            if (zhaoGongTiaoJian != null && zhaoGongTiaoJian.status == 1 && zhaoGongTiaoJian.msg != null) {
                                if (zhaoGongTiaoJian.msg.fileter.size() > 0) {
                                    headers.clear();
                                    fileters.clear();
                                    popupViews.clear();
                                    for (int i = 0; i < zhaoGongTiaoJian.msg.fileter.size(); i++) {
                                        final FiletersBeanX filetersBeanX = zhaoGongTiaoJian.msg.fileter.get(i);
                                        if (!TextUtils.isEmpty(filetersBeanX.filterName)) {
                                            headers.add(filetersBeanX.filterName);
                                        }

                                        final ListView cityView = new ListView(NewLocalMuneActivity.this);
                                        cityAdapter = new GirdDropDownAdapter(NewLocalMuneActivity.this, filetersBeanX.fileters);
                                        cityView.setDividerHeight(0);
                                        cityView.setAdapter(cityAdapter);
                                        popupViews.add(cityView);

                                        //add item click event
                                        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                FiletersBean filetersBean = filetersBeanX.fileters.get(position);
//                                                cityAdapter.setCheckItem(position);
                                                mDropDownMenu.setTabText(filetersBeanX.fileters.get(position).name);
                                                if (filetersBean != null) {
                                                    isFirst = true;
                                                    pageNo = 1;
                                                    isRefresh = true;
                                                    Log.i("xxx", "onItemClick: "+filetersBean.id + "");
                                                    fileterId = filetersBean.id + "";
                                                    getData(filetersBean.id + "");
                                                }

                                                mDropDownMenu.closeMenu();
                                            }
                                        });

                                    }
                                    //init context view
                                    View contentView=View.inflate(NewLocalMuneActivity.this,R.layout.item_recycle_with_button,null);
                                    EasyRecyclerView easyRecyclevive =(EasyRecyclerView)contentView.findViewById(R.id.easy_recyclevive);
                                    ImageView img_pblish =(ImageView)contentView.findViewById(R.id.img_pblish);
                                    img_pblish.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent intent = new Intent(NewLocalMuneActivity.this, PublishMessageActivity2.class);
                                            intent.putExtra("filter", zhaoGongTiaoJian.msg);
                                            intent.putExtra("cityId", cityId);
                                            intent.putExtra("menuId", menuId);
                                            intent.putExtra("title", title);
                                            startActivity(intent);
                                        }
                                    });
                                    contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                    easyRecyclevive.setLayoutManager(new LinearLayoutManager(NewLocalMuneActivity.this));
                                    easyRecyclevive.setRefreshListener(NewLocalMuneActivity.this);
                                    easyRecyclevive.setAdapter(adapter = new MyAdapter(NewLocalMuneActivity.this));
                                    adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(int position) {

                                            Newlocal_ZhaoGong.MsgBean itemsEntity = adapter.getAllData().get(position);
                                            Intent intent = new Intent(NewLocalMuneActivity.this, HireAndFindDetailActivity2.class);
                                            intent.putExtra("conId", itemsEntity.id + "");
                                            intent.putExtra("menuId", menuId);
                                            startActivity(intent);
                                        }
                                    });

                                    adapter.setNoMore(R.layout.view_nomore);
                                    adapter.setMore(R.layout.view_more, NewLocalMuneActivity.this);
                                    adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            adapter.resumeMore();
                                        }
                                    });

                                    mDropDownMenu.setDropDownMenu(headers, popupViews, contentView);

                                    getData("");

                                }
                            }
                        }
                    }
                });

            }
        });
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        isRefresh = true;
        if (!TextUtils.isEmpty(fileterId)) {
            getData(fileterId);
        } else {
            getData("");
        }
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        if (!TextUtils.isEmpty(fileterId)) {
            getData(fileterId);
        } else {
            getData("");
        }
    }

    public class MyAdapter extends RecyclerArrayAdapter<Newlocal_ZhaoGong.MsgBean> {
        public MyAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHoder(parent);
        }

        public class MyHoder extends BaseViewHolder<Newlocal_ZhaoGong.MsgBean> {

            private ImageView iv_icon, img_v;
            private TextView tv_title, tv_content, tv_des;
            private LinearLayout line_tag;

            private List<ImageView> imgs = new ArrayList<>();


            public MyHoder(ViewGroup parent) {
                super(parent, R.layout.item_new_local_zhaogong);

                line_tag = (LinearLayout) itemView.findViewById(R.id.linear_tag);
                tv_title = (TextView) itemView.findViewById(R.id.tv_title);
                tv_content = (TextView) itemView.findViewById(R.id.tv_content);
                tv_des = (TextView) itemView.findViewById(R.id.tv_des);
                iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
                img_v = (ImageView) itemView.findViewById(R.id.img_v);
            }

            @Override
            public void setData(Newlocal_ZhaoGong.MsgBean data) {

                if (!TextUtils.isEmpty(data.imgUrls)) {
                    MyApplication.imageLoader.displayImage(data.imgUrls, img_v, MyApplication.getCircleImageOptions());
                    img_v.setVisibility(View.VISIBLE);
                } else {
                    img_v.setVisibility(View.GONE);
                }

                if (data.createTime < 2 * 1000000000) {
                    tv_des.setText(Util.getMonthDay(data.createTime * 1000) + "    " + data.views + "人浏览");
                } else {
                    tv_des.setText(Util.getMonthDay(data.createTime) + "    " + data.views + "人浏览");
                }

                if (data.status == 1) {  // 热
                    iv_icon.setImageResource(R.drawable.img_new_local_re);
                    iv_icon.setVisibility(View.VISIBLE);
                } else if(data.status == 2){ // 顶
                    iv_icon.setImageResource(R.drawable.img_new_local_z_ding);
                    iv_icon.setVisibility(View.VISIBLE);
                } else if(data.status == 3){  // 精
                    iv_icon.setImageResource(R.drawable.img_new_local_z_jing);
                    iv_icon.setVisibility(View.VISIBLE);
                }else {
                    iv_icon.setVisibility(View.INVISIBLE);
                }

                tv_title.setText(data.title);
                tv_content.setText(data.pubName);

                line_tag.removeAllViews();
                if (!TextUtils.isEmpty(data.tags)) {
                    String[] tags = data.tags.split(",");
                    if (tags != null) {

                        if (tags.length > 0) {
                            line_tag.setVisibility(View.VISIBLE);
                            if(tags.length>5){
                                for (int i = 0; i < 5; i++) {
                                    View items = inflate(getContext(), R.layout.item_text_tag, null);
                                    ViewGroup parent = (ViewGroup) items.getParent();
                                    if (parent != null) {
                                        parent.removeAllViews();
                                    }

                                    TextView t_tips = (TextView) items.findViewById(R.id.t_tips);
                                    if (!TextUtils.isEmpty(tags[i])) {
                                        t_tips.setText(tags[i] + "");
                                    }

                                    line_tag.addView(items);
                                }
                            }else {
                                for (int i = 0; i < tags.length; i++) {
                                    View items = inflate(getContext(), R.layout.item_text_tag, null);
                                    ViewGroup parent = (ViewGroup) items.getParent();
                                    if (parent != null) {
                                        parent.removeAllViews();
                                    }

                                    TextView t_tips = (TextView) items.findViewById(R.id.t_tips);
                                    if (!TextUtils.isEmpty(tags[i])) {
                                        t_tips.setText(tags[i] + "");
                                    }

                                    line_tag.addView(items);
                                }
                            }
                        } else {
                            line_tag.setVisibility(View.GONE);
                        }
                    }
                }


            }
        }
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
