package com.abcs.haiwaigou.local.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.fragment.adapter.CFViewPagerAdapter;
import com.abcs.haiwaigou.local.beans.TitleName;
import com.abcs.haiwaigou.local.fragment.NewHireJobFragment;
import com.abcs.huaqiaobang.model.BaseFragmentActivity;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.sociax.android.R;
import com.astuetz.PagerSlidingTabStrip;
import com.thinksns.sociax.thinksnsbase.utils.TLUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.abcs.sociax.android.R.id.relative_publish;

public class HireAndFindActivity extends BaseFragmentActivity implements View.OnClickListener {

    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.t_title_name)
    TextView tTitleName;
    @InjectView(relative_publish)
    RelativeLayout relativePublish;
    @InjectView(R.id.comment_tabs)
    PagerSlidingTabStrip commentTabs;
    @InjectView(R.id.main_pager)
    ViewPager mainPager;
    @InjectView(R.id.img_openmenu)
    ImageView img_openmenu;

    Fragment currentFragment;
    int position;
    int curr_pos=0;
    int currentType;
    CFViewPagerAdapter viewPagerAdapter;
    //    String countryId;
    String cityId;
    String menuId;
    String title;
    @InjectView(R.id.linear_tab)
    LinearLayout linearTab;
    @InjectView(R.id.relative_open)
    RelativeLayout relative_open;
    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> idList = new ArrayList<>();
    private Handler handler = new Handler();
    public static HashMap<String ,Fragment>fragmentHashMap=new HashMap<String ,Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_activity_hire_and_find);
        ButterKnife.inject(this);
//        countryId=getIntent().getStringExtra("countryId");
        nameList.clear();
        title = getIntent().getStringExtra("title");
        tTitleName.setText(title);
        cityId = getIntent().getStringExtra("cityId");
        menuId = getIntent().getStringExtra("menuId");
        relativePublish.setVisibility(View.GONE);
        initListener();
        initSubTitle();
//        initViewPager();
    }

    private void initSubTitle() {
        HttpRequest.sendGet(TLUrl.getInstance().URL_local_get_subTitle, "menuId=" + menuId+"&cityId=" + cityId, new HttpRevMsg() {
            @Override
            public void revMsg(final String msg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject mainObj = new JSONObject(msg);
                            if (mainObj.optString("status").equals("1")) {
                                JSONArray msgArray = mainObj.optJSONArray("msg");
                                initSubList(msgArray);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    List<TitleName> titleNameList=new ArrayList<>();

    private void initSubList(JSONArray msgArray) throws JSONException {
        if (msgArray.length() != 0) {
            titleNameList.clear();
            for (int i = 0; i < msgArray.length(); i++) {
                JSONObject object = msgArray.getJSONObject(i);
                TitleName titleName=new TitleName();
                String name = object.optString("menuName");
                titleName.setName(object.optString("menuName"));
                String id = object.optString("menuId");
                titleName.setId(object.optString("menuId"));
                nameList.add(name);
                idList.add(id);
                titleNameList.add(titleName);
            }
            initViewPager();
        }
    }

    private boolean isFirst=true;
    private void initListener() {
        relativeBack.setOnClickListener(this);
        relativePublish.setOnClickListener(this);
        relative_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFirst){
                    isFirst=false;
                    popGoodsDetials();
                }else {
                    popupWindow.showAsDropDown(v, Gravity.NO_GRAVITY, 0, 0);
                }
            }
        });
    }

    public class TitleNameAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private Context mContext;
        private List<TitleName> mCities;
        public TitleNameAdapter(Context context,List<TitleName> mCities) {
            this.mContext = context;
            this.mCities = mCities;
            this.inflater = LayoutInflater.from(mContext);
        }
        @Override
        public int getCount() {
            return mCities.size();
        }

        @Override
        public TitleName getItem(int position) {
            return mCities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CityGridViewHolder cityGridViewHolder = null;
            if (convertView==null){
                convertView = inflater.inflate(R.layout.text_tong,parent,false);
                cityGridViewHolder = new CityGridViewHolder();
                cityGridViewHolder.tv = (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(cityGridViewHolder);
            }else
            {
                cityGridViewHolder  = (CityGridViewHolder) convertView.getTag();
            }

            TitleName citysBean=mCities.get(position);

            if(!TextUtils.isEmpty(citysBean.getName())){
                cityGridViewHolder.tv.setText(citysBean.getName());
            }

            return convertView;
        }

        public  class CityGridViewHolder{
            TextView tv;
        }
    }
    PopupWindow popupWindow;
    private  void popGoodsDetials() {
        View root_view = View.inflate(this, R.layout.pop_tongxunlu, null);
        GridView listview = (GridView) root_view.findViewById(R.id.listview);
        listview.setAdapter(new TitleNameAdapter(this,titleNameList));
        popupWindow = new PopupWindow();
        popupWindow.setWidth(Util.WIDTH);
        popupWindow.setHeight(Util.HEIGHT/2);
        popupWindow.setContentView(root_view);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 1f;
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
                WindowManager.LayoutParams params = getWindow()
                        .getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        popupWindow.setAnimationStyle(R.style.popWindowzhankai);//设置弹出和消失的动画
        //触摸点击事件
        popupWindow.setTouchable(true);
        //聚集
        popupWindow.setFocusable(true);
        //设置允许在外点击消失
        popupWindow.setOutsideTouchable(false);
        //点击返回键popupwindown消失

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        popupWindow.showAsDropDown(relative_open, Gravity.NO_GRAVITY, 0, 0);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainPager.setCurrentItem(position);
                popupWindow.dismiss();
            }
        });
    }

    private void initViewPager() {
        viewPagerAdapter = new CFViewPagerAdapter(getSupportFragmentManager());
        if(nameList.size()>3){
            commentTabs.setShouldExpand(false);
        }else {
            commentTabs.setShouldExpand(true);
        }
        initHireFind();
        //第三方Tab
        mainPager.setAdapter(viewPagerAdapter);
        mainPager.setOffscreenPageLimit(1);
        mainPager.setCurrentItem(position);
//        pager.setPageTransformer(true, new DepthPageTransformer());
        commentTabs.setViewPager(mainPager);
        commentTabs.setIndicatorHeight(Util.dip2px(this, 4));
        commentTabs.setTextSize(Util.dip2px(this, 16));
        setSelectTextColor(position);
        setTextType();
        commentTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                setSelectTextColor(position);
                currentFragment = viewPagerAdapter.getItem(position);
                currentType = position + 1;
                curr_pos=position;
//                currentgoodsFragment.initRecycler();
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
        currentFragment = viewPagerAdapter.getItem(0);
        currentType = 1;
    }

    private void initHireFind() {
        fragmentHashMap.clear();
        if (nameList.size() <2) {
            linearTab.setVisibility(View.GONE);
        }
        for (int i = 0; i < nameList.size(); i++) {
            viewPagerAdapter.getDatas().add(NewHireJobFragment.newInstance(cityId, idList.get(i),nameList.get(i),menuId,title));
//            viewPagerAdapter.getDatas().add(HireJobFragment.newInstance(cityId, idList.get(i),nameList.get(i),menuId));
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
            case relative_publish:
                go2Publish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentHashMap.clear();
    }

    public void go2Publish(){
        Intent intent = new Intent(this, PublishMessageActivity.class);
//                    intent.putExtra("countryId",countryId);
        intent.putExtra("position",curr_pos);
        intent.putExtra("cityId", cityId);
        intent.putExtra("menuId", menuId);
        intent.putExtra("title", title);
        startActivity(intent);
    }
}
