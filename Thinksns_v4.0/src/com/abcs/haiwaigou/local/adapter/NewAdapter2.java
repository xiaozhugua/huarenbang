package com.abcs.haiwaigou.local.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.local.activity.LocalZiXunDetialsActivity;
import com.abcs.haiwaigou.local.beans.NewsBean_N;
import com.abcs.haiwaigou.model.GGAds;
import com.abcs.haiwaigou.view.MyGridView;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.activity.GuanggaoActivity;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.sociax.android.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * 更多新闻的adapter
 */
public class NewAdapter2 extends RecyclerArrayAdapter<NewsBean_N> {

    public NewAdapter2(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewViewHolder2(parent);
    }

    public class NewViewHolder2 extends BaseViewHolder<NewsBean_N> {
        private ImageView imgNew;
        private TextView tvTitle, tv_time, tv_from;
        RelativeLayout relative_news;
        LinearLayout liner_gga;
        MyGridView gridviewGg;

        public NewViewHolder2(ViewGroup parent) {
            super(parent, R.layout.item_new_local_news);

            liner_gga = (LinearLayout) itemView.findViewById(R.id.liner_gga);
            relative_news = (RelativeLayout) itemView.findViewById(R.id.relative_news);
            gridviewGg = (MyGridView) itemView.findViewById(R.id.gridview_gg);

            imgNew = (ImageView) itemView.findViewById(R.id.img_logo);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tv_from = (TextView) itemView.findViewById(R.id.tv_from);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);

//            imgNew.setLayoutParams(new RelativeLayout.LayoutParams(MyApplication.getWidth()/3-30,(MyApplication.getWidth()/3*202/270)-50));
        }

        @Override
        public void setData(final NewsBean_N newsBean) {

            if (newsBean != null) {
                /*广告*/
                if(newsBean.adsList!=null){
                    if(newsBean.adsList.size()>0){
                        gridviewGg.setAdapter(new GridVImgsAdapter(getContext(), newsBean.adsList));
                        gridviewGg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                GGAds gg_bean = (GGAds) parent.getItemAtPosition(position);
                                try {
                                    if (gg_bean != null) {

                                        if (!TextUtils.isEmpty(gg_bean.url)) {
                                            Intent intent = new Intent(getContext(), GuanggaoActivity.class);
                                            intent.putExtra("url", gg_bean.url);
                                            getContext().startActivity(intent);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        relative_news.setVisibility(View.GONE);
                        liner_gga.setVisibility(View.VISIBLE);
                    }else {
                        gridviewGg.setAdapter(null);
                        parseNe(newsBean);
                        relative_news.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), LocalZiXunDetialsActivity.class);
                                intent.putExtra("id", newsBean.id);
                                intent.putExtra("time", newsBean.time+"");
                                intent.putExtra("title", newsBean.title);
                                intent.putExtra("species", newsBean.species);
                                getContext().startActivity(intent);
                            }
                        });
                        relative_news.setVisibility(View.VISIBLE);
                        liner_gga.setVisibility(View.GONE);
                    }
                }else {
                    parseNe(newsBean);
                    relative_news.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), LocalZiXunDetialsActivity.class);
                            intent.putExtra("id", newsBean.id);
                            intent.putExtra("time", newsBean.time+"");
                            intent.putExtra("title", newsBean.title);
                            intent.putExtra("species", newsBean.species);
                            getContext().startActivity(intent);
                        }
                    });
                    relative_news.setVisibility(View.VISIBLE);
                    liner_gga.setVisibility(View.GONE);
                }
            }
        }

        private void parseNe(NewsBean_N newsBean) {
            if (!TextUtils.isEmpty(newsBean.source)) {
                tv_from.setText("来源：" + newsBean.source);
                tv_from.setVisibility(View.VISIBLE);
            } else {
                tv_from.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(newsBean.timeStr)) {
                tv_time.setText(newsBean.timeStr);
            } else{
                if (newsBean.time < 2 * 1000000000) {
                    tv_time.setText(Util.getMonthDay(newsBean.time * 1000));
                } else {
                    tv_time.setText(Util.getMonthDay(newsBean.time));
                }
            }

            if (!TextUtils.isEmpty(newsBean.purl)) {
                try {
                    MyApplication.imageLoader.displayImage(MyApplication.encodeUrl(newsBean.purl), imgNew, MyApplication.getListOptions());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                imgNew.setVisibility(View.VISIBLE);
            }else {
                imgNew.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(newsBean.title)) {
                tvTitle.setText(newsBean.title);
            }
        }
    }
}
