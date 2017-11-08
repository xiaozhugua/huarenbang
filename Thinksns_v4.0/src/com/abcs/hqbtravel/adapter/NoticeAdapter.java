package com.abcs.hqbtravel.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcs.haiwaigou.local.huohang.view.NoticeDetialsActivity;
import com.abcs.hqbtravel.entity.Notice;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.sociax.android.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 */
public class NoticeAdapter extends RecyclerArrayAdapter<Notice.DatasEntry> {
    public NoticeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeHoder(parent);
    }

    public class NoticeHoder extends BaseViewHolder<Notice.DatasEntry> {

        private ImageView img_logo;
        private TextView tv_titile,tv_message,tv_time,tv_notice;
        AutoRelativeLayout autoRelativeLayout;

        public NoticeHoder(ViewGroup parent) {
            super(parent, R.layout.item_huohang_notice);

            autoRelativeLayout=(AutoRelativeLayout) itemView.findViewById(R.id.relative_main);
            img_logo=(ImageView) itemView.findViewById(R.id.iv_logo);
            tv_titile=(TextView) itemView.findViewById(R.id.tv_titile);
            tv_message=(TextView) itemView.findViewById(R.id.tv_message);
            tv_time=(TextView) itemView.findViewById(R.id.tv_time);
            tv_notice=(TextView) itemView.findViewById(R.id.tv_notice);

        }

        @Override
        public void setData(final Notice.DatasEntry data) {

            if(!TextUtils.isEmpty(data.messageTitle)){
                tv_titile.setText(data.messageTitle);
            }
            if(!TextUtils.isEmpty(data.messageDetails)){
                tv_message.setText(data.messageDetails);
            }


            if (data.addTime < 2 * 1000000000) {
                tv_time.setText(Util.format1.format(data.addTime * 1000));
            } else {
                tv_time.setText(Util.format1.format(data.addTime));
            }


            if(data.state==1){  // 未读
                tv_notice.setVisibility(View.VISIBLE);
            }else if (data.state==0){
                tv_notice.setVisibility(View.GONE);
            }

            autoRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(getContext(), NoticeDetialsActivity.class);
                    it.putExtra("messageTitle", data.messageTitle);
                    it.putExtra("messageDetails", data.messageDetails);
                    it.putExtra("addTime", data.addTime);
                    it.putExtra("id", data.id);
                    getContext().startActivity(it);

                    tv_notice.setVisibility(View.GONE);
                }
            });

        }
    }
}
