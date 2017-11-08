package com.abcs.haiwaigou.local.huohang.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.abcs.haiwaigou.local.beans.KeFuBean;
import com.abcs.sociax.android.R;

import java.util.ArrayList;
import java.util.List;

/*货行客服聊天*/
public class HHKeFuChatAdapter extends BaseAdapter{
    private Context mcontext;
    private LayoutInflater inflater;
    private List<KeFuBean> data=new ArrayList<>();

    public static final int TYPE_FROM = 0;
    public static final int TYPE_TO = 1;

    public void clearDatasAndAdd(List<KeFuBean> beanList){
        this.data.clear();
        if(beanList!=null&&beanList.size()>0){
            this.data.addAll(beanList);
            notifyDataSetChanged();
        }
    }
    public void DatasAndAdd(List<KeFuBean> beanList){
        if(beanList!=null&&beanList.size()>0){
            this.data.addAll(beanList);
            notifyDataSetChanged();
        }
    }
    public void AddItemBean(KeFuBean bean){
        if(bean!=null){
            this.data.add(bean);
            notifyDataSetChanged();
        }
    }

    Typeface type;
    public HHKeFuChatAdapter(Context mcontext) {
        this.mcontext = mcontext;
        inflater = LayoutInflater.from(mcontext);

        try {
            type = Typeface.createFromAsset(mcontext.getAssets(), "font/ltheijian.TTF");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        int Mytype=data.get(position).type;
        return Mytype;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderFrom holderFrom;
        ViewHolderTo viewHolderTo;
        final KeFuBean keFuBean = (KeFuBean)getItem(position);
        int type=getItemViewType(position);
        if(convertView==null){
            switch (type){
                case TYPE_FROM:
                    convertView=inflater.inflate(R.layout.item_hh_kefu_from,parent,false);
                    holderFrom=new ViewHolderFrom(convertView);
                    convertView.setTag(holderFrom);
                    break;
                case TYPE_TO:
                    convertView=inflater.inflate(R.layout.item_hh_kefu_to,parent,false);
                    viewHolderTo=new ViewHolderTo(convertView);
                    convertView.setTag(viewHolderTo);
                    break;
                default:
                    break;
            }
        }else{
            switch (type){
                case TYPE_FROM:
                    holderFrom=(ViewHolderFrom) convertView.getTag();
                    if(keFuBean!=null){
                        holderFrom.tv_time.setText(keFuBean.time+"");
                        holderFrom.tv_content.setText(keFuBean.context);
                    }
                    break;
                case TYPE_TO:
                    viewHolderTo=(ViewHolderTo) convertView.getTag();
                    if(keFuBean!=null){
                        viewHolderTo.tv_time.setText(keFuBean.time+"");
                        viewHolderTo.tv_content.setText(keFuBean.context);
                    }
                    break;
                default:
                    break;
            }
        }

        return convertView;
    }

    public class  ViewHolderFrom{
        TextView tv_time ;
        TextView tv_content ;

        public ViewHolderFrom(View itemView) {
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
    public class  ViewHolderTo{
        TextView tv_time ;
        TextView tv_content ;

        public ViewHolderTo(View itemView) {
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
