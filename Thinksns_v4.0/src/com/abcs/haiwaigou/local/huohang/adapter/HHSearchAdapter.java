package com.abcs.haiwaigou.local.huohang.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.local.beans.ActivityArr2;
import com.abcs.haiwaigou.local.evenbus.Notice2;
import com.abcs.haiwaigou.local.huohang.bean.HHItemRight;
import com.abcs.haiwaigou.local.huohang.view.HHNewSearchActivity;
import com.abcs.haiwaigou.utils.NumberUtils;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.wxapi.WXEntryActivity;
import com.abcs.sociax.android.R;
import com.zhy.autolayout.utils.AutoUtils;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*货行搜索*/
public class HHSearchAdapter extends BaseAdapter{
    private Context mcontext;
    private LayoutInflater inflater;
    private List<HHItemRight.DatasBean> data=new ArrayList<>();

    public void clearDatasAndAdd(List<HHItemRight.DatasBean> beanList){
        data.clear();
        if(beanList!=null&&beanList.size()>0){
            data.addAll(beanList);
            notifyDataSetChanged();
        }
    }

    public HHSearchAdapter(Context mcontext) {
        this.mcontext = mcontext;
        inflater = LayoutInflater.from(mcontext);
    }
    public void setmTextShop(Context mcontext,TextView shopCart) {
        this.mcontext = mcontext;
        this.shopCart = shopCart;
    }

    /*  加上从商品页面加入购物车过来的数据*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChange(final Notice2 notice) {
        Log.d("zds", "onNoticeChange:adapter" + notice.googNum);
        Log.d("zds", "onNoticeChange:adapter" + notice.googsToalPrice);

    }

    public ImageView buyImg;
    public ViewGroup animMaskLayout;
    public static Map<String,Integer> goodsNumMap=new HashMap<>(); //用来item的购买数量
    public static Map<Integer,HHItemRight.DatasBean> datas=new HashMap<>(); //用来保存添加到购物车的对象
    public static Map<Integer,Double> goodsPri=new HashMap<>(); //用来保存价格总数
    public int buyNum;
    public TextView shopCart,goods_price;
    public LinearLayout liner;
    public Activity mActivity;
    public HHNewSearchActivity fragment;
    public Map<Integer, HHItemRight.DatasBean> getDatas() {
        return datas;
    }
    public Map<Integer, Double> getGoodsPrice() {
        return goodsPri;
    }


    /**
     * 修改购物车状态
     */
    public void changeShopCart(int buyNum) {

        try {
            Log.i("zds", "changeShopCart: buyNum"+buyNum);
            if (buyNum > 0) {
                shopCart.setVisibility(View.VISIBLE);
                if(fragment.cart_num>0){
                    shopCart.setText((buyNum +fragment.cart_num)+ "");
                }else {
                    shopCart.setText(buyNum + "");
                }
    //            fragment.img_peisong_che.setImageResource(R.drawable.bg_bottom_psong_che2);
                fragment.tvPeisongQisong.setText("选好了");
    //            fragment.rePeisongJie.setBackgroundResource(R.drawable.bg_bottom_psong_jie);

            } else {
                if(fragment.cart_num>0){
                    shopCart.setText(fragment.cart_num+ "");
    //                fragment.img_peisong_che.setImageResource(R.drawable.bg_bottom_psong_che2);
                    fragment.tvPeisongQisong.setText("选好了");
    //                fragment.rePeisongJie.setBackgroundResource(R.drawable.bg_bottom_psong_jie);

                }else {
                    shopCart.setVisibility(View.GONE);
                    fragment.tvPeisongGfw.setText("购物车为空");
    //                fragment.img_peisong_che.setImageResource(R.drawable.bg_bottom_psong_che2);
                    fragment.tvPeisongQisong.setText("选好了");
    //                fragment.rePeisongJie.setBackgroundResource(R.drawable.bg_bottom_psong_jie_no);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置动画
     *
     * @param v              要显示的内容
     * @param start_location 坐标
     */
    public void setAnim(final View v, int[] start_location) {
        animMaskLayout = null;
        animMaskLayout = createAnimLayout();
        animMaskLayout.addView(v);// 把动画小球添加到动画层
        final View view = addViewToAnimLayout(animMaskLayout, v,
                start_location);
        int[] end_location = new int[2];// 这是用来存储动画结束位置的X、Y坐标
        shopCart.getLocationInWindow(end_location);// shopCart是那个购物车的数量
        // 计算位移
        int endX = 0 - start_location[0] + end_location[0];// 动画位移的X坐标
        int endY = end_location[1] - start_location[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);
        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);
        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(500);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 初始化动画图层
     *
     * @return
     */
    public ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) mActivity.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(mActivity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }


    /**
     * 将View添加到动画图层
     *
     * @param vg
     * @param view
     * @param location
     * @return
     */
    public View addViewToAnimLayout(final ViewGroup vg, final View view,int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }



    public void setmActivity(Activity mActivity, HHNewSearchActivity fragment) {
        this.mActivity = mActivity;
        this.fragment = fragment;
    }

    public interface OnShopCartGoodsChangeListener {
        void onNumChange(int num, HHItemRight.DatasBean data);
    }

    public OnShopCartGoodsChangeListener mOnGoodsNunChangeListener = null;

    public void setOnShopCartGoodsChangeListener(OnShopCartGoodsChangeListener e){
        mOnGoodsNunChangeListener = e;
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
            convertView=inflater.inflate(R.layout.item_local_peisong3,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }

        final HHItemRight.DatasBean data = (HHItemRight.DatasBean)getItem(position);

        try {
            if(data!=null){
                holder.liner_hdong.removeAllViews();
                holder.lin_tianjai.setVisibility(View.VISIBLE);

                if(!TextUtils.isEmpty(data.goodsImageS)){
    //                http://www.huaqiaobang.com/data/upload/shop/store/goods/11/11_05375327290881075.jpg
                    String index=data.goodsImageS.substring(0,data.goodsImageS.indexOf("_"));
                    Log.i("zdsindex", "setData: "+index);
                    MyApplication.imageLoader.displayImage("http://huohang.huaqiaobang.com/data/upload/shop/store/goods/"+index+"/"+data.goodsImageS, holder.img_logo,MyApplication.getCircleImageOptions());

                }

                holder.tv_title.setText(data.goodsName+"");
                holder.tv_en_title.setText(data.goodsEnName+"");
                holder.tv_store.setText("货号:"+data.goodsSerial);
                try {
                    if(!TextUtils.isEmpty(MyApplication.getHHStoreCoin())){
                        Log.i("zzz", "getView: "+MyApplication.getHHStoreCoin());
                        holder.tv_price.setText(MyApplication.getHHStoreCoin()+data.goodsPrice+" "+data.taxRate);
                    }else {
                        holder.tv_price.setText(NumberUtils.formatPriceOuYuan(data.goodsPrice)+" "+data.taxRate);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //                    tv_sui.setText(data.taxRate+"");  // 税率

                                    /*活动信息*/
                if(data.activityArr!=null){

                    if(data.activityArr.size()>0){
                        holder.liner_hdong.setVisibility(View.VISIBLE);
                        for (int k = 0; k <data.activityArr.size() ; k++) {
                            ActivityArr2 bean_hd=data.activityArr.get(k);
                            View view=View.inflate(mActivity,R.layout.item_hd_text_h,null);
                            ViewGroup parent3 = (ViewGroup) view.getParent();
                            if (parent3 != null) {
                                parent3.removeAllViews();
                            }
                            ImageView img=(ImageView) view.findViewById(R.id.img);
                            TextView tv=(TextView) view.findViewById(R.id.tv);

                            if(bean_hd!=null){
                                if(!TextUtils.isEmpty(bean_hd.logo)){
    //                http://www.huaqiaobang.com/data/upload/shop/store/goods/11/11_05375327290881075.jpg
                                    MyApplication.imageLoader.displayImage(bean_hd.logo,img,MyApplication.options);
                                }

                                if(!TextUtils.isEmpty(bean_hd.name)){
                                    tv.setText(bean_hd.name);
                                }
                            }

                            holder.liner_hdong.addView(view);
                        }
                    }else {
                        holder.liner_hdong.setVisibility(View.GONE);
                    }
                }

    //                                判断商品是否有添加到购物车中
                if (!goodsNumMap.containsKey(data.goodsId) ||goodsNumMap.get(data.goodsId)  == 0) {
                    holder.tvGoodsSelectNum.setVisibility(View.GONE);
                    holder.ivGoodsMinus.setVisibility(View.GONE);
                } else {
                    holder.tvGoodsSelectNum.setVisibility(View.VISIBLE);
                    holder.tvGoodsSelectNum.setText(goodsNumMap.get(data.goodsId)+ "");
                    holder.ivGoodsMinus.setVisibility(View.VISIBLE);
                }


    //            if(ItemGoodsFragment.is_LocalMember==1){ // 是货行用户
    //                holder.ivGoodsAdd.setVisibility(View.GONE);
    //                if( holder.ivGoodsMinus.getVisibility()==View.VISIBLE){
    //                    holder.ivGoodsMinus.setVisibility(View.GONE);
    //                }
    //
    //                if( holder.tvGoodsSelectNum.getVisibility()==View.VISIBLE){
    //                    holder.tvGoodsSelectNum.setVisibility(View.GONE);
    //                }
    //
    //
    ////                        if(data.price_hide==1){  // 隐藏价格
    ////                            tv_price.setVisibility(View.GONE);
    ////                            tv_sui.setVisibility(View.GONE);
    ////                        }else {
    ////                            tv_price.setVisibility(View.VISIBLE);
    ////                            tv_sui.setVisibility(View.VISIBLE);
    ////                        }
    //            }else {
    //                holder.ivGoodsAdd.setVisibility(View.VISIBLE);
    //                holder.tv_price.setVisibility(View.VISIBLE);
    //                holder.tv_sui.setVisibility(View.VISIBLE);
    //            }

                holder.ivGoodsAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (MyApplication.getInstance().getMykey() == null) {
                            Intent intent = new Intent(mActivity, WXEntryActivity.class);
                            intent.putExtra("isthome", true);
                            mActivity.startActivity(intent);
                            return;
                        } else {
                            int goodsNum = 0;
                            if (goodsNumMap.containsKey(data.goodsId)){
                                goodsNum = goodsNumMap.get(data.goodsId);
                            }
                            goodsNum++;
                            goodsNumMap.put(data.goodsId,goodsNum);
    //                    // TODO:  添加购物车
                            int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                            v.getLocationInWindow(start_location);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                            buyImg = new ImageView(mActivity);
                            buyImg.setBackgroundResource(R.drawable.icon_goods_add_item);// 设置buyImg的图片
                            buyNum++;
                            setAnim(buyImg, start_location);// 开始执行动画
                            changeShopCart(buyNum);
                            data.numbers=goodsNum;

                            if (goodsNum == 0) {
                                holder.tvGoodsSelectNum.setVisibility(View.GONE);
                                holder.ivGoodsMinus.setVisibility(View.GONE);
                                datas.remove(Integer.valueOf(data.goodsId));
                                goodsPri.remove(Integer.valueOf(data.goodsId));
                            } else {
                                holder.tvGoodsSelectNum.setVisibility(View.VISIBLE);
                                holder.tvGoodsSelectNum.setText(goodsNum + "");
                                holder.ivGoodsMinus.setVisibility(View.VISIBLE);
                                datas.put(Integer.valueOf(data.goodsId),data);
                                goodsPri.put(Integer.valueOf(data.goodsId),data.goodsPrice*goodsNum);
                            }

                            mOnGoodsNunChangeListener.onNumChange(buyNum,data);
                        }
                    }
                });
                holder.ivGoodsMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int goodsNum = 0;
                        if (goodsNumMap.containsKey(data.goodsId)){
                            goodsNum = goodsNumMap.get(data.goodsId);
                        }

                        if (goodsNum > 0) {
                            goodsNum--;
                            buyNum--;
                            changeShopCart(buyNum);
                            data.numbers=goodsNum;
                            goodsNumMap.put(data.goodsId,goodsNum);
                            //  删除购物车内容

                            if (goodsNum == 0) {
                                holder.tvGoodsSelectNum.setVisibility(View.GONE);
                                holder.ivGoodsMinus.setVisibility(View.GONE);
                                datas.remove(Integer.valueOf(data.goodsId));
                                goodsPri.remove(Integer.valueOf(data.goodsId));
                            } else {
                                holder.tvGoodsSelectNum.setVisibility(View.VISIBLE);
                                holder.tvGoodsSelectNum.setText(goodsNum + "");
                                holder. ivGoodsMinus.setVisibility(View.VISIBLE);
                                datas.put(Integer.valueOf(data.goodsId),data);
                                goodsPri.put(Integer.valueOf(data.goodsId),data.goodsPrice*goodsNum);
                            }

                            mOnGoodsNunChangeListener.onNumChange(buyNum,data);
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return convertView;
    }

    public class  ViewHolder{
        ImageView img_logo;
        LinearLayout lin_tianjai;
        LinearLayout liner_hdong;
        GridView liner_tag;
        final TagFlowLayout flowlayout;  // 更换标签控件
        TextView tv_en_title;
        TextView tv_title;
        TextView tv_store;
        TextView tv_price;
        TextView tv_sui;
        final ImageView ivGoodsMinus;
        final TextView tvGoodsSelectNum;
        final ImageView ivGoodsAdd;

        public ViewHolder(View itemView) {
            img_logo=(ImageView) itemView.findViewById(R.id.img_logo);
            lin_tianjai=(LinearLayout) itemView.findViewById(R.id.lin_tianjai);
            liner_hdong=(LinearLayout) itemView.findViewById(R.id.liner_hdong);
            liner_tag=(GridView) itemView.findViewById(R.id.liner_tag);
            flowlayout=(TagFlowLayout) itemView.findViewById(R.id.id_flowlayout);  // 更换标签控件
            tv_en_title=(TextView) itemView.findViewById(R.id.tv_en_title);
            tv_title=(TextView) itemView.findViewById(R.id.tv_title);
            tv_store=(TextView) itemView.findViewById(R.id.tv_store);
            tv_price=(TextView) itemView.findViewById(R.id.tv_price);
            tv_sui=(TextView) itemView.findViewById(R.id.tv_sui);

            ivGoodsMinus = (ImageView) itemView.findViewById(R.id.ivGoodsMinus);
            tvGoodsSelectNum = (TextView) itemView.findViewById(R.id.tvGoodsSelectNum);
            ivGoodsAdd = (ImageView) itemView.findViewById(R.id.ivGoodsAdd);
        }
    }
}
