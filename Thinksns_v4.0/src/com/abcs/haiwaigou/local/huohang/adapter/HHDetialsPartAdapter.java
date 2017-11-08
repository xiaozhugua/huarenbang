package com.abcs.haiwaigou.local.huohang.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcs.haiwaigou.model.ExtendOrderGoodsEntry;
import com.abcs.haiwaigou.utils.NumberUtils;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.model.Options;
import com.abcs.sociax.android.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/*货行商品详情商品列表*/
public class HHDetialsPartAdapter extends BaseAdapter{
    private Context mcontext;
    private LayoutInflater inflater;
    private List<ExtendOrderGoodsEntry> data=new ArrayList<>();

    public void clearDatasAndAdd(List<ExtendOrderGoodsEntry> beanList){
        this.data.clear();
        if(beanList!=null&&beanList.size()>0){
            this.data.addAll(beanList);
            notifyDataSetChanged();
        }
    }
    public void DatasAndAdd(List<ExtendOrderGoodsEntry> beanList){
        if(beanList!=null&&beanList.size()>0){
            this.data.addAll(beanList);
            notifyDataSetChanged();
        }
    }

    Typeface type;
    public HHDetialsPartAdapter(Context mcontext) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_hh_order_detials_goods_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
//            MyApplication.getInstance().startAniWithPosition(position,convertView);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }

        try {
            final ExtendOrderGoodsEntry evaluation_state = (ExtendOrderGoodsEntry)getItem(position);

            if(evaluation_state!=null){
                if (!TextUtils.isEmpty(evaluation_state.goodsImage)) {
                    String index = evaluation_state.goodsImage.substring(0, evaluation_state.goodsImage.indexOf("_"));
                    MyApplication.imageLoader.displayImage("http://huohang.huaqiaobang.com/data/upload/shop/store/goods/" + index + "/" + evaluation_state.goodsImage, holder.img_goods, Options.getListOptions2());
                }

                holder.tv_title.setText(evaluation_state.goodsName);
                holder.t_num.setText("X" + evaluation_state.goodsNum);
                holder.tv_goods_price.setText(NumberUtils.formatPriceOuYuan(evaluation_state.goodsPrice));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return convertView;
    }

    public class  ViewHolder{
        ImageView img_goods ;
        TextView tv_title ;
        TextView t_num ;
        TextView tv_goods_price ;

        public ViewHolder(View itemView) {
             img_goods = (ImageView) itemView.findViewById(R.id.img_goods);
             tv_title = (TextView) itemView.findViewById(R.id.tv_goods_name);
             t_num = (TextView) itemView.findViewById(R.id.tv_goods_buy_number);
             tv_goods_price = (TextView) itemView.findViewById(R.id.tv_goods_price);

            if(type!=null){
                tv_goods_price.setTypeface(type);
            }
        }
    }
}
