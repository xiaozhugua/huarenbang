package com.abcs.haiwaigou.local.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abcs.haiwaigou.local.adapter.NewAdapter2;
import com.abcs.haiwaigou.local.beans.NewsBean_N;
import com.abcs.haiwaigou.model.GGAds;
import com.abcs.haiwaigou.view.BaseFragment;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.sociax.android.R;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class NewsTypesFragment extends BaseFragment  implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @Override
    protected void lazyLoad() {

    }

    private View view;
    String typeName,species,cityId;

    @InjectView(R.id.recyclerView)
    EasyRecyclerView rvNew;
    @InjectView(R.id.t_refresh)
    TextView t_refresh;

    private NewAdapter2 adapter;
    private Boolean isRefresh = false;
    Activity activity;

    private String lastTime;
    private String lastId;
    private String countryId;


    public static NewsTypesFragment newInstance(String species,String typeName,String countryId,String cityId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("species", species);
        bundle.putSerializable("typeName", typeName);
        bundle.putSerializable("countryId", countryId);
        bundle.putSerializable("cityId", cityId);
        NewsTypesFragment hireJobFragment = new NewsTypesFragment();
        hireJobFragment.setArguments(bundle);
        return hireJobFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (Activity) getActivity();
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.fragment_news_types, null);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup p = (ViewGroup) view.getParent();
        if (p != null)
            p.removeView(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            species = bundle.getString("species");
            typeName = bundle.getString("typeName");
            countryId = bundle.getString("countryId");
            cityId = bundle.getString("cityId");
        }
        ButterKnife.inject(this,view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvNew.setLayoutManager(layoutManager);
        rvNew.setRefreshListener(this);
        rvNew.setAdapter(adapter = new NewAdapter2(activity));
//        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//            }
//        });

        adapter.setNoMore(R.layout.view_nomore);
        adapter.setMore(R.layout.view_more, this);
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });

        if(!TextUtils.isEmpty(species)&&!TextUtils.isEmpty(countryId)&&!TextUtils.isEmpty(cityId)){
            initData();
        }

        return view;
    }
    private Handler handler = new Handler();
    private int type;
    private boolean isFirst=true;
    private int pageNo=1;
    private List<NewsBean_N> newList=new ArrayList<>();
    //请求网络加载数据
    private void initData() {
//       http://europe.huaqiaobang.com/japi/hrq/conListDetail/v2/getAllNews?countryId=198&type=1&species=country_international&cityId=41
        if(isFirst){
            isFirst=false;
            ProgressDlgUtil.showProgressDlg("", activity);
        }

        type = 1;  // type 1 下拉刷新  2 上拉加载

        HttpRequest.sendGet("http://europe.huaqiaobang.com/japi/hrq/conListDetail/v2/getAllNews", "countryId=" + countryId + "&type=" + type + "&species=" + species+"&cityId="+cityId+"&page="+pageNo,
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
                                        int status = mainObj.optInt("status");
                                        if (status==1) {
                                            JSONObject msg = mainObj.optJSONObject("msg");
                                            JSONObject jsonObject = msg.optJSONObject("joData");
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

                                                        JSONArray adsList=newObj.optJSONArray("adsList");
                                                        if (adsList != null) {
                                                            if(adsList.length()>0){
                                                                List<GGAds> ggAdsList=new ArrayList<>();
                                                                for (int h = 0; h < adsList.length(); h++) {
                                                                    JSONObject ggaObject = adsList.getJSONObject(h);
                                                                    GGAds ggAds=new GGAds();
                                                                    ggAds.img=ggaObject.optString("img");
                                                                    ggAds.is_jump=ggaObject.optInt("is_jump");
                                                                    ggAds.url=ggaObject.optString("url");
                                                                    ggAdsList.add(ggAds);
                                                                }
                                                                news.adsList=ggAdsList;
                                                            }
                                                        }

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
        type = 2;
        HttpRequest.sendGet("http://europe.huaqiaobang.com/japi/hrq/conListDetail/v2/getAllNews", "countryId=" + countryId + "&type=" + type + "&species=" + species+"&cityId="+cityId+ "&time=" + lastTime + "&id=" + lastId+"&page="+pageNo ,
                new HttpRevMsg() {
                    @Override
                    public void revMsg(final String msg) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                if(!TextUtils.isEmpty(msg)){
                                    try {
                                        JSONObject mainObj = new JSONObject(msg);
                                        if (mainObj.optInt("status")==1) {
                                            JSONObject msg = mainObj.optJSONObject("msg");
                                            JSONObject jsonObject = msg.optJSONObject("joData");
                                            lastId = jsonObject.optString("lastId");
                                            lastTime = jsonObject.optString("lastTime");
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
                                                        JSONArray adsList=newObj.optJSONArray("adsList");
                                                        if (adsList != null) {
                                                            if(adsList.length()>0){
                                                                List<GGAds> ggAdsList=new ArrayList<>();
                                                                for (int h = 0; h < adsList.length(); h++) {
                                                                    JSONObject ggaObject = adsList.getJSONObject(h);
                                                                    GGAds ggAds=new GGAds();
                                                                    ggAds.img=ggaObject.optString("img");
                                                                    ggAds.is_jump=ggaObject.optInt("is_jump");
                                                                    ggAds.url=ggaObject.optString("url");
                                                                    ggAdsList.add(ggAds);
                                                                }
                                                                news.adsList=ggAdsList;
                                                            }
                                                        }

                                                        newList.add(news);
                                                    }

                                                    adapter.addAll(newList);
                                                    adapter.notifyDataSetChanged();

                                                }else{
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

    //刷新数据
    @Override
    public void onRefresh() {
        isRefresh = true;
        pageNo=1;
        initData();
    }

    //加载更多
    @Override
    public void onLoadMore() {
        isRefresh = false;
        pageNo=pageNo+1;
        initMoreData();
    }
}
