package com.abcs.haiwaigou.local.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.local.adapter.NewAdapter2;
import com.abcs.haiwaigou.local.beans.NewsBean_N;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.sociax.android.R;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.thinksns.sociax.thinksnsbase.utils.TLUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewMoreActivity extends AppCompatActivity implements View.OnClickListener, RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.t_title_name)
    TextView tTitleName;
    @InjectView(R.id.relative_top)
    RelativeLayout relativeTop;
    @InjectView(R.id.rv_new)
    EasyRecyclerView rvNew;
    private String subject;
    private NewAdapter2 adapter;
    private Boolean isRefresh = false;

    private String lastTime;
    private String lastId;
    private String countryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_more);
        ButterKnife.inject(this);
        initListener();
        if(!TextUtils.isEmpty(getIntent().getStringExtra("title"))){
            tTitleName.setText(getIntent().getStringExtra("title"));
        }else {
            tTitleName.setText("新闻");
        }

        rvNew.setLayoutManager(new LinearLayoutManager(this));
        rvNew.setRefreshListener(this);
        rvNew.setAdapter(adapter = new NewAdapter2(this));
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //item的点击事件
               NewsBean_N aNew = adapter.getAllData().get(position);
                Intent intent = new Intent(NewMoreActivity.this, LocalZiXunDetialsActivity.class);
                intent.putExtra("id", aNew.id);
                intent.putExtra("time", aNew.time+"");
                intent.putExtra("title", aNew.title);
                intent.putExtra("species", aNew.species);
                startActivity(intent);
            }
        });

        adapter.setNoMore(R.layout.view_nomore);
        adapter.setMore(R.layout.view_more, this);
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });


        if (MyApplication.getInstance().self!= null) {
            uid = MyApplication.getInstance().self.getId();
        }

        if (!TextUtils.isEmpty(getIntent().getStringExtra("countryId"))) {
            subject = getIntent().getStringExtra("subject");
            countryId = getIntent().getStringExtra("countryId");
            initData();
        }

    }

    private Handler handler = new Handler();
    private ArrayList<NewsBean_N> newList = new ArrayList<NewsBean_N>();
    private int type;
    String uid;

    private boolean isFirst=true;
    //请求网络加载数据
    private void initData() {

        if(isFirst){
            isFirst=false;
            ProgressDlgUtil.showProgressDlg("", this);
        }

        type = 0;
         uid = "0";// 默认无

        //URL_local_get_morenews
        Log.i("xuke210", "NewURL:" + TLUrl.getInstance().URL_local_get_morenews + "?uid=" + uid + "&type=" + type + "&countryId=" + countryId);
        HttpRequest.sendGet(TLUrl.getInstance().URL_local_get_morenews, "uid=" + uid + "&type=" + type + "&countryId=" + countryId,
                new HttpRevMsg() {
                    @Override
                    public void revMsg(final String msg) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ProgressDlgUtil.stopProgressDlg();
                                Log.i("xuke118", "newMsg:" + msg);
                                if (msg != null) {

                                    if (isRefresh) {
                                        adapter.clear();
                                        isRefresh = false;
                                    }

                                    try {
                                        JSONObject mainObj = new JSONObject(msg);
                                        String status = mainObj.optString("status");
                                        if (status.equals("1")) {
                                            JSONObject jsonObject = mainObj.optJSONObject("joData");
                                            lastId = jsonObject.getString("lastId");
                                            lastTime = jsonObject.getString("lastTime");
                                            Log.i("xuke210", "新闻:lastTime=" + lastTime + ",lastId=" + lastId);
                                            JSONArray newArray = jsonObject.optJSONArray("news");
                                            if (newArray != null) {

                                                if(newArray.length()>0){
                                                    newList.clear();
                                                    for (int i = 0; i < newArray.length(); i++) {
                                                        JSONObject newObj = newArray.getJSONObject(i);
                                                        NewsBean_N news = new NewsBean_N();
                                                        news.id=newObj.optString("id");
                                                        news.time=newObj.optLong("time");
                                                        news.title=newObj.optString("title");
                                                        news.purl=newObj.optString("purl");
                                                        news.timeStr=newObj.optString("timeStr");
                                                        news.source=newObj.optString("source");
                                                        news.species=newObj.optString("species");
                                                        newList.add(news);
                                                    }
                                                    adapter.addAll(newList);
                                                    adapter.notifyDataSetChanged();

                                                }else {
                                                    adapter.stopMore();
                                                }
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        ProgressDlgUtil.stopProgressDlg();
                                    }
                                }
                            }
                        });
                    }
                });

    }

    private void initMoreData() {
        String uid = "1";// 默认无
        type = 1;
        Log.i("xuke210", "moreNewURL:" + TLUrl.getInstance().URL_local_get_morenews + "?time=" + lastTime + "&id=" + lastId + "&uid=" + uid + "&type=" + type + "&countryId=" + countryId);
        HttpRequest.sendGet(TLUrl.getInstance().URL_local_get_morenews, "time=" + lastTime + "&id=" + lastId + "&uid=" + uid + "&type=" + type + "&countryId=" + countryId,
                new HttpRevMsg() {
                    @Override
                    public void revMsg(final String msg) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                if(!TextUtils.isEmpty(msg)){
                                    Log.i("xuke118", "moreNewMsg:" + msg);

                                    try {
                                        JSONObject mainObj = new JSONObject(msg);
                                        if (mainObj.optString("status").equals("1")) {
                                            JSONObject jsonObject = mainObj.optJSONObject("joData");
                                            lastId = jsonObject.optString("lastId");
                                            lastTime = jsonObject.optString("lastTime");
                                            JSONArray newArray = jsonObject.optJSONArray("news");
                                            if (newArray != null) {
                                                if(newArray.length() > 0){
                                                    newList.clear();
                                                    for (int i = 0; i < newArray.length(); i++) {
                                                        JSONObject newObj = newArray.getJSONObject(i);
                                                        NewsBean_N news = new NewsBean_N();
                                                        news.id=newObj.optString("id");
                                                        news.time=newObj.optLong("time");
                                                        news.title=newObj.optString("title");
                                                        news.purl=newObj.optString("purl");
                                                        news.timeStr=newObj.optString("timeStr");
                                                        news.source=newObj.optString("source");
                                                        news.species=newObj.optString("species");
                                                        newList.add(news);
                                                    }

                                                    adapter.addAll(newList);
                                                    adapter.notifyDataSetChanged();

                                                }else{
                                                    Log.i("xuke214", "暂无数据");
                                                    adapter.stopMore();
                                                }
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                });
    }


    private void initListener() {
        relativeBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_back:
                finish();
                break;
        }
    }

    //刷新数据
    @Override
    public void onRefresh() {
        isRefresh = true;
        initData();
    }

    //加载更多
    @Override
    public void onLoadMore() {
        isRefresh = false;
        initMoreData();
    }
}
