package com.abcs.haiwaigou.local.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcs.haiwaigou.local.beans.NewsBean_N;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.sociax.android.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * 更多新闻的adapter
 */
public class NewAdapter22 extends RecyclerArrayAdapter<NewsBean_N> {

    public NewAdapter22(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewViewHolder2(parent);
    }

    public class NewViewHolder2 extends BaseViewHolder<NewsBean_N> {
        private ImageView imgNew;
        private TextView tvTitle, tv_time, tv_from;

        public NewViewHolder2(ViewGroup parent) {
            super(parent, R.layout.item_new_local_news);

            imgNew = (ImageView) itemView.findViewById(R.id.img_logo);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tv_from = (TextView) itemView.findViewById(R.id.tv_from);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }

        @Override
        public void setData(NewsBean_N newsBean) {

            if (newsBean != null) {
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
                    MyApplication.imageLoader.displayImage(newsBean.purl, imgNew, MyApplication.getCircleFiveImageOptions());
                }
                if (!TextUtils.isEmpty(newsBean.title)) {
                    tvTitle.setText(newsBean.title);
                }
            }
        }
    }
}
