package com.abcs.haiwaigou.local.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.fragment.adapter.CFViewPagerAdapter;
import com.abcs.haiwaigou.local.fragment.NewsTypesFragment;
import com.abcs.huaqiaobang.model.BaseFragmentActivity;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.sociax.android.R;
import com.astuetz.PagerSlidingTabStrip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewsTypeActivity extends BaseFragmentActivity implements View.OnClickListener {

    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.t_title_name)
    TextView tTitleName;
    @InjectView(R.id.comment_tabs)
    PagerSlidingTabStrip commentTabs;
    @InjectView(R.id.main_pager)
    ViewPager mainPager;
    CFViewPagerAdapter viewPagerAdapter;

    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> speciesList = new ArrayList<>();
    private Handler handler = new Handler();
    String countryId,cityId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_activity_news_types);
        ButterKnife.inject(this);
        nameList.clear();
        speciesList.clear();
        tTitleName.setText("新闻");

        cityId=getIntent().getStringExtra("cityId");
        countryId=getIntent().getStringExtra("countryId");
        initListener();
        initSubTitle();
    }

    private void initSubTitle() {
//        http://news.tuling.me/QhWebNewsServer/api/utc/get?platform=10&version=9
        HttpRequest.sendGet("http://europe.huaqiaobang.com/news/QhWebNewsServer/api/utc/get", "platform=10&version=9", new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject mainObj = new JSONObject(msg);
                            if (mainObj.optString("status").equals("1")) {
                                JSONObject jsonObject = mainObj.optJSONObject("joData");
                                JSONArray jsonArray=jsonObject.optJSONArray("selected");
                                if(jsonArray!=null){
                                    initSubList(jsonArray);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    private void initSubList(JSONArray msgArray) throws JSONException {
        if (msgArray.length() != 0) {
            for (int i = 0; i < msgArray.length(); i++) {
                JSONObject object = msgArray.getJSONObject(i);
                String name = object.optString("name");
                String species = object.optString("species");
                nameList.add(name);
                speciesList.add(species);
            }
            initViewPager();
        }
    }

    private void initListener() {
        relativeBack.setOnClickListener(this);
    }

    private void initViewPager() {
        viewPagerAdapter = new CFViewPagerAdapter(getSupportFragmentManager());
//        if(nameList.size()>3){
//            commentTabs.setShouldExpand(false);
//        }else {
//            commentTabs.setShouldExpand(true);
//        }
        initHireFind();
        //第三方Tab
        mainPager.setAdapter(viewPagerAdapter);
        mainPager.setOffscreenPageLimit(1);
        mainPager.setCurrentItem(0);
        commentTabs.setViewPager(mainPager);
        commentTabs.setIndicatorHeight(Util.dip2px(this, 4));
        commentTabs.setTextSize(Util.dip2px(this, 16));
        setSelectTextColor(0);
        setTextType();
        commentTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                setSelectTextColor(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int position) {

                System.out.println("Change Posiont:" + position);

                // TODO Auto-generated method stub

            }
        });
    }

    private void initHireFind() {
        for (int i = 0; i < nameList.size(); i++) {
            viewPagerAdapter.getDatas().add(NewsTypesFragment.newInstance(speciesList.get(i),nameList.get(i),countryId,cityId));
            viewPagerAdapter.getTitle().add(i, nameList.get(i));
        }
    }

    private void setTextType() {
        for (int i = 0; i < nameList.size(); i++) {
            View view = commentTabs.getChildAt(0);
            View viewText = ((LinearLayout) view).getChildAt(i);
            TextView tabTextView = (TextView) viewText;
            if (tabTextView != null) {
                Util.setFZLTHFont(tabTextView);
            }
        }
    }

    private void setSelectTextColor(int position) {
        for (int i = 0; i < nameList.size(); i++) {
            View view = commentTabs.getChildAt(0);
            View viewText = ((LinearLayout) view).getChildAt(i);
            TextView tabTextView = (TextView) viewText;
            if (tabTextView != null) {
                if (position == i) {
                    tabTextView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    tabTextView.setTextColor(getResources().getColor(R.color.hwg_text2));
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_back:
                finish();
                break;
        }
    }
}
