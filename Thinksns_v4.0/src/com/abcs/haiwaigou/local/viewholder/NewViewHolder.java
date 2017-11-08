package com.abcs.haiwaigou.local.viewholder;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcs.haiwaigou.local.beans.New;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.sociax.android.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

public class NewViewHolder extends BaseViewHolder<New> {
    private ImageView imgNew;
    private TextView tvTitle,tv_time;

    public NewViewHolder(ViewGroup parent) {
        super(parent, R.layout.local_item_new);

        imgNew = (ImageView) itemView.findViewById(R.id.img_new);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_new);
        tv_time = (TextView) itemView.findViewById(R.id.tv_time);
    }

    @Override
    public void setData(New data) {

        MyApplication.imageLoader.displayImage(data.getPurl(), imgNew, MyApplication.getCircleFiveImageOptions());

        if (!TextUtils.isEmpty(data.getTitle())) {
            tvTitle.setText(data.getTitle());
        }

        if (!TextUtils.isEmpty(data.getTimeStr())) {
            tv_time.setText(data.getTimeStr());
        } else {
            if (data.getTime() < 2 * 1000000000) {
                tv_time.setText(Util.getMonthDay(data.getTime() * 1000));
            } else {
                tv_time.setText(Util.getMonthDay(data.getTime()));
            }
        }

    }
}
