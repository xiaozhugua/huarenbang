package com.abcs.haiwaigou.local.huohang.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.local.evenbus.NoticeCityName;
import com.abcs.haiwaigou.local.evenbus.changesCity;
import com.abcs.haiwaigou.local.huohang.bean.DatasEntrySer;
import com.abcs.haiwaigou.local.huohang.bean.HHClassFy;
import com.abcs.haiwaigou.local.huohang.bean.SelectStoreNew;
import com.abcs.haiwaigou.utils.ACache;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.huaqiaobang.util.ServerUtils;
import com.abcs.huaqiaobang.ytbt.common.utils.ToastUtil;
import com.abcs.sociax.android.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.abcs.sociax.t4.android.video.ToastUtils.showToast;

/**
 * Created by zds 超市货行
 */
public class ItemPiFaFragment extends Fragment {

    Activity activity;
    View view;
    @InjectView(R.id.listview)
    ListView listview;
    @InjectView(R.id.gridview_datas)
    GridView gridviewDatas;
    @InjectView(R.id.hh_no_pifa)
    RelativeLayout hh_no_pifa;  // 空页面
    @InjectView(R.id.swipeRefresh)
    android.support.v4.widget.SwipeRefreshLayout swipeRefresh;


    LeftAdapter leftAdapter;
    RightAdapter rightAdapter;
    Handler handler=new Handler();
    ACache aCache;
     String cityId;

    public static ItemPiFaFragment newInstance(String cityId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("cityId", cityId);
        ItemPiFaFragment fragment = new ItemPiFaFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private int listview_first_item=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        try {
            view = inflater.inflate(R.layout.item_pifa_for_hh, null);
            activity = (Activity) getActivity();
            ButterKnife.inject(this, view);
            aCache=ACache.get(activity);
            EventBus.getDefault().register(this);
            swipeRefresh.setEnabled(false);
            leftAdapter=new LeftAdapter(activity);
            listview.setAdapter(leftAdapter);
            rightAdapter=new RightAdapter(activity);
            gridviewDatas.setAdapter(rightAdapter);
            Bundle bundle = getArguments();
            if (bundle != null) {
                  cityId = (String) bundle.getSerializable("cityId");
                if (ServerUtils.isConnect(activity)) {
                    if (!TextUtils.isEmpty(cityId)) {
                        hh_no_pifa.setVisibility(View.GONE);
                        getDatas(cityId);
                    }else {
                        hh_no_pifa.setVisibility(View.VISIBLE);
                    }

                }else {
                    ToastUtil.showMessage("请检查您的网络");
                }
            }

//        hh_no_pifa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, SelectedHHStoreActivity.class);
//                startActivityForResult(intent, 1);
//            }
//        });

            listview.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (firstVisibleItem == 0)
                        listview_first_item=0;
                    else
                        listview_first_item=1;
                }
            });
            gridviewDatas.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (firstVisibleItem == 0&&listview_first_item==0)
                        swipeRefresh.setEnabled(true);
                    else
                        swipeRefresh.setEnabled(false);
                }
            });

            swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
            swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (!ServerUtils.isConnect(activity)) {
                        showToast("请检查您的网络");
                    }else {
                        if (!TextUtils.isEmpty(cityId)) {
                            hh_no_pifa.setVisibility(View.GONE);
                            getDatas(cityId);
                        }else {
                            hh_no_pifa.setVisibility(View.VISIBLE);
                        }
                    }

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefresh.setRefreshing(false);
                        }
                    }, 1000);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

            return view;

    }


    private boolean isFirst=true;
    private void getDatas(final String cityId){
        try {
            if(isFirst){
                ProgressDlgUtil.showProgressDlg("",activity);
            }

//      http://huohang.huaqiaobang.com/mobile/index.php?act=native_index&op=get_native_store_class&city_id=41
            HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=native_index&op=get_native_store_class&city_id="+cityId, new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    // TODO Auto-generated method stub
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (TextUtils.isEmpty(msg)) {
                                return;
                            } else {

                                try {
                                    HHClassFy hhClassFy=new Gson().fromJson(msg,HHClassFy.class);
                                    if(hhClassFy!=null){

                                        if(hhClassFy.state==1){
                                            if(hhClassFy.datas!=null){
                                                if(hhClassFy.datas.size()>0){
                                                    leftAdapter.addAllDatas(hhClassFy.datas);
                                                    try {
                                                        HHClassFy.DatasBean dda = (HHClassFy.DatasBean) leftAdapter.getItem(0);
                                                        if (dda != null) {
                                                            initRight(cityId,dda.sclassId);
                                                            leftAdapter.setSelectedPosition(0);
                                                        }
                                                    } catch (NullPointerException e) {
                                                        e.printStackTrace();
                                                    }

                                                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                        @Override
                                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                            HHClassFy.DatasBean ssXaun = (HHClassFy.DatasBean) parent.getItemAtPosition(position);
                                                            leftAdapter.setSelectedPosition(position);
                                                            if (ssXaun!=null&&!TextUtils.isEmpty(ssXaun.sclassId)) {
                                                                initRight(cityId,ssXaun.sclassId);
                                                            }
                                                        }
                                                    });
                                                }else {
                                                    leftAdapter.clearAll();
                                                }
                                            }
                                            hh_no_pifa.setVisibility(View.GONE);
                                        }else {
                                            Log.i("zszs",""+MyApplication.current_position);
                                            leftAdapter.clearAll();
                                            rightAdapter.clearAll();
        //                                    if(MyApplication.current_position==1){
        //                                        ToastUtil.showMessage("暂无数据,换个城市看看");
        //                                    }
                                            hh_no_pifa.setVisibility(View.VISIBLE);
                                        }
                                    }

                                    if(isFirst){
                                        isFirst=false;
                                        ProgressDlgUtil.stopProgressDlg();
                                    }
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRight(String cityId,String sclass_id){
        try {
            if(!TextUtils.isEmpty(aCache.getAsString("clasd"+sclass_id))){
                Log.i("zds", "initPopRight: acache"+aCache.getAsString("clasd"+sclass_id));
                parseDatas(aCache.getAsString("clasd"+sclass_id));
            }else {
                getDatasFromNet(cityId,sclass_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDatasFromNet(final String cityId,final String sclass_id) {
        try {
//        ProgressDlgUtil.showProgressDlg("",activity);
//       http://huohang.huaqiaobang.com/mobile/index.php?act=native_index&op=get_native_class_store_list&city_id=41&sclass_id=6
            HttpRequest.sendGet("http://huohang.huaqiaobang.com/mobile/index.php", "act=native_index&op=get_native_class_store_list&city_id="+cityId+"&sclass_id="+sclass_id, new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    // TODO Auto-generated method stub
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (TextUtils.isEmpty(msg)) {
                                return;
                            } else {

                                parseDatas(msg);
                                aCache.put("clasd"+sclass_id,msg,1*60*60*24);

    //                            ProgressDlgUtil.stopProgressDlg();
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseDatas(String msg) {
        try {
            SelectStoreNew hhClassFy=new Gson().fromJson(msg,SelectStoreNew.class);
            if(hhClassFy!=null){

                if(hhClassFy.state==1){

                    if(hhClassFy.datas!=null){
                        if(hhClassFy.datas.size()>0){
                            rightAdapter.addAllData(hhClassFy.datas);
                        }else {
                            rightAdapter.clearAll();
                        }
                        gridviewDatas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                SelectStoreNew.DatasBean citysBean = (SelectStoreNew.DatasBean) parent.getItemAtPosition(position);
                                Log.i("ccc", "onItemClick: ");
                                if (citysBean != null) {
                                    DatasEntrySer datasEntrySer = new DatasEntrySer();
                                    datasEntrySer.setActivity(citysBean.activity);
                                    datasEntrySer.setStoreAddress(citysBean.storeAddress);
                                    datasEntrySer.setStoreCoin(citysBean.storeCoin);
                                    datasEntrySer.setStoreDes(citysBean.storeDes);
                                    datasEntrySer.setStoreGoodsDetails(citysBean.storeGoodsDetails);
                                    datasEntrySer.setStoreId(citysBean.storeId);
                                    datasEntrySer.setStoreNewLogo(citysBean.storeNewLogo);
                                    datasEntrySer.setStoreImg(citysBean.storeImg);
                                    datasEntrySer.setStoreName(citysBean.storeName);
                                    datasEntrySer.setStorePhone(citysBean.storePhone);
                                    datasEntrySer.setStoreSendTime(citysBean.storeSendTime);
                                    datasEntrySer.setNgc_id("");
                                    datasEntrySer.setGoods_Id("");
                                    checkIsSuccess(datasEntrySer, "");
                                    MyApplication.saveHHStoreCoin(citysBean.storeCoin);
                                }
                            }
                        });
                    }

                }else {
                    rightAdapter.clearAll();
                    ToastUtil.showMessage("暂无数据");
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotices(changesCity changes) {   // 通知切换城市，重新刷新数据
        if(changes!=null&&!TextUtils.isEmpty(changes.cityId)){
            cityId=changes.cityId;
            getDatas(cityId);
        }
    }

    /**
     */
    private void checkIsSuccess(final DatasEntrySer bean, final String goods_Id) {
        Intent intent = new Intent(activity, NewHuoHangActivity.class);
        intent.putExtra("datas", bean);
        intent.putExtra("goods_Id", goods_Id);
        startActivity(intent);
    }
    public class LeftAdapter extends BaseAdapter {

        Context context;
        List<HHClassFy.DatasBean> list=new ArrayList<>();

        public LeftAdapter(Context context) {
            this.context = context;
        }

        private void addAllDatas(List<HHClassFy.DatasBean> datasBeanList){
            if(datasBeanList!=null&&datasBeanList.size()>0){
                list.clear();
                list.addAll(datasBeanList);
                notifyDataSetChanged();
            }
        }
        private void clearAll(){
            list.clear();
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

          VieeHolder vieeHolder=null;
            if(view==null){
                view= LayoutInflater.from(context).inflate(R.layout.item_huohang_classfy,viewGroup,false);
                vieeHolder=new LeftAdapter.VieeHolder(view);
                view.setTag(vieeHolder);
                AutoUtils.autoSize(view);
            }else {
                vieeHolder=(VieeHolder) view.getTag();
            }

            HHClassFy.DatasBean bean= (HHClassFy.DatasBean) getItem(i);

            if(bean!=null){
                if(!TextUtils.isEmpty(bean.sclassName)){
                    vieeHolder.tv_title.setText(bean.sclassName);
                }
            }

            if (selectedPosition == i) {
                vieeHolder.line_bg.setVisibility(View.VISIBLE);
                vieeHolder.tv_title.setTextColor(getResources().getColor(R.color.colorPrimaryDark2));
                vieeHolder.rela_bg.setSelected(true);
                vieeHolder.rela_bg.setPressed(true);
                vieeHolder.rela_bg.setBackgroundColor(context.getResources().getColor(R.color.white));
            } else {
                vieeHolder.line_bg.setVisibility(View.GONE);
                vieeHolder.tv_title.setTextColor(getResources().getColor(R.color.text_unselected));
                vieeHolder.rela_bg.setSelected(false);
                vieeHolder.rela_bg.setPressed(false);
                vieeHolder.rela_bg.setBackgroundColor(context.getResources().getColor(R.color.default_left_selected));
            }

            return view;
        }
        public class VieeHolder{
            TextView tv_title,line_bg;
            RelativeLayout rela_bg;

            public VieeHolder(View view) {
                line_bg=(TextView) view.findViewById(R.id.line_bg);
                tv_title=(TextView) view.findViewById(R.id.tv_title);
                rela_bg=(RelativeLayout) view.findViewById(R.id.rela_bg);
            }
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
            notifyDataSetChanged();
        }

        private int selectedPosition = -1;// 选中的位置

    }


    public class RightAdapter extends BaseAdapter {

        Context context;
        public  List<SelectStoreNew.DatasBean> list=new ArrayList<>();
        public RightAdapter(Context context) {
            this.context = context;
        }

        public List<SelectStoreNew.DatasBean> getList() {
            return list;
        }

        public void addAllData(List<SelectStoreNew.DatasBean> date){
            if(date!=null){
                if(date.size()>0){
                    list.clear();
                    list.addAll(date);
                    notifyDataSetChanged();
                }
            }
        }

        public void clearAll(){
            list.clear();
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public SelectStoreNew.DatasBean getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            RightAdapter.VieeHolder vieeHolder=null;
            if(view==null){
                view= LayoutInflater.from(context).inflate(R.layout.item_huohang_two,viewGroup,false);
                vieeHolder=new RightAdapter.VieeHolder(view);
                view.setTag(vieeHolder);
            }else {
                vieeHolder=(RightAdapter.VieeHolder) view.getTag();
            }

            final SelectStoreNew.DatasBean item=getItem(i);

            try {
                if(item!=null){
                    if (!TextUtils.isEmpty(item.storeName)) {
                        vieeHolder.title.setText(item.storeName);
                    }

                    if (!TextUtils.isEmpty(item.storeNewImg)) {
                        MyApplication.imageLoader.displayImage(item.storeNewImg,vieeHolder.iv_logo,MyApplication.getCircleFiveImageOptions());
                    }else {
                        MyApplication.imageLoader.displayImage("",vieeHolder.iv_logo,MyApplication.getCircleFiveImageOptions());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return view;
        }

        public class VieeHolder{
            TextView title;
            ImageView iv_logo;

            public VieeHolder(View view) {
                title = (TextView) view.findViewById(R.id.tv);
                iv_logo = (ImageView) view.findViewById(R.id.iv_logo);
//                iv_logo.setScaleType(ImageView.ScaleType.FIT_XY);
                iv_logo.setLayoutParams(new RelativeLayout.LayoutParams((MyApplication.getWidth()-MyApplication.getWidth()/4-30)/2,((MyApplication.getWidth()-MyApplication.getWidth()/4-30)/2)*189/252));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotices(NoticeCityName changes) {   // 两个字fragment 通知切换城市，重新刷新数据
        if(changes!=null){
            if(!TextUtils.isEmpty(changes.cityId)){
                cityId=changes.cityId;
                getDatas(cityId);
            }else {
                if(MyApplication.current_position==1){
                    ToastUtil.showMessage("暂无数据,换个城市看看");
                }
            }
        }
    }

/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1 && data != null) {
            cityId = data.getStringExtra("cityId");
            String cityName = data.getStringExtra("cityName");
            String countryName = data.getStringExtra("countryName");
            Log.i("zds1", "onActivityResult: " + cityId);
            if(!TextUtils.isEmpty(cityId)){
                hh_emtry.setVisibility(View.GONE);
                getDatas(cityId);
            }else {
                hh_emtry.setVisibility(View.VISIBLE);
            }

            EventBus.getDefault().post(new chanCity(cityId,cityName,countryName));


        }
    }*/

/*    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotices(chanCity changes) {   // 两个字fragment 通知切换城市，重新刷新数据
        if(changes!=null){
            if(!TextUtils.isEmpty(changes.cityId)){
                cityId=changes.cityId;
                hh_emtry.setVisibility(View.GONE);
                getDatas(cityId);
            }else {
                hh_emtry.setVisibility(View.VISIBLE);
            }
        }
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }
}
