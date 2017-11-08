package com.abcs.haiwaigou.local.huohang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.abcs.sociax.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 货行搜索热门
 */
public class SearchHotAdapter extends BaseAdapter {


    List<String> hots=new ArrayList<>();
    Context context;
    public SearchHotAdapter(Context context, List<String> hots) {
        this.context=context;
        this.hots = hots;
    }


    public void clearDatas(){
        if(hots!=null&&hots.size()>0){
            hots.clear();
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return hots.size();
    }

    @Override
    public Object getItem(int i) {
        return hots.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder=null;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.hwg_item_search_hot_n, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }

        try {
            String strings=hots.get(i);
            viewHolder.t_text.setText(strings+"");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    public  class ViewHolder{
        public TextView t_text;
        public ViewHolder(View itemView) {
            t_text = (TextView) itemView.findViewById(R.id.t_text);
        }
    }

}
