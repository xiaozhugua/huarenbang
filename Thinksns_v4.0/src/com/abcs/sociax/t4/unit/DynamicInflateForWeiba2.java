package com.abcs.sociax.t4.unit;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.huaqiaobang.MyApplication;
import com.abcs.sociax.android.R;
import com.abcs.sociax.constant.AppConstant;
import com.abcs.sociax.t4.adapter.AdapterSociaxList;
import com.abcs.sociax.t4.adapter.AdapterWeiBoImageGridView;
import com.abcs.sociax.t4.android.Listener.ListenerSociax;
import com.abcs.sociax.t4.android.function.FunctionChangeWeibaFollow;
import com.abcs.sociax.t4.android.img.ActivityViewPager;
import com.abcs.sociax.t4.android.img.UIImageLoader;
import com.abcs.sociax.t4.android.weiba.ActivitySearchWeiba;
import com.abcs.sociax.t4.android.weiba.ActivityWeiba;
import com.abcs.sociax.t4.android.weibo.ActivityCreatePost;
import com.abcs.sociax.t4.component.GridViewNoScroll;
import com.abcs.sociax.t4.model.ModelPhoto;
import com.abcs.sociax.t4.model.ModelWeiba;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class DynamicInflateForWeiba2 {
    private DynamicInflateForWeiba2() {
    }

    /**
     * 添加分类
     *
     * @param viewStub
     * @param name
     */
    public static void addCategory(ViewStub viewStub, String name) {
        TextView category = null;
        try {
            category = (TextView) viewStub.inflate();
            viewStub.setTag(R.id.tv_part_name, category);
        } catch (Exception e) {
            viewStub.setVisibility(View.VISIBLE);
            category = (TextView) viewStub.getTag(R.id.tv_part_name);
        } finally {
            if (category != null) {
                category.setText(name);
            }
        }
    }

    /**
     * 添加图片组
     *
     * @param context
     * @param viewStub
     * @param imgUrl
     */
    public static void addImageTable(final Context context, ViewStub viewStub,
                                     ArrayList<ModelPhoto> imgUrl, int tableWidth) {
        GridViewNoScroll imageGroup = null;
        try {
            imageGroup = (GridViewNoScroll) viewStub.inflate();
            viewStub.setTag(R.id.tl_imgs, imageGroup);
        } catch (Exception e) {
            viewStub.setVisibility(View.VISIBLE);
            imageGroup = (GridViewNoScroll) viewStub.getTag(R.id.tl_imgs);
        } finally {
            if (imageGroup != null) {
                setAboutImageTable(context, imgUrl, imageGroup, tableWidth);
            }
        }
    }

    /**
     * 添加Part Name
     *
     * @param viewStub
     */
    public static void addPartName(ViewStub viewStub, String name) {
        TextView partName = null;
        try {
            partName = (TextView) viewStub.inflate();
            viewStub.setTag(R.id.tv_part_name, partName);
        } catch (Exception e) {
            partName = (TextView) viewStub.getTag(R.id.tv_part_name);
            viewStub.setVisibility(View.VISIBLE);
        } finally {
            if (partName != null) {
                partName.setText(name);
            }
        }
    }

    /**
     * 添加微吧列表
     *
     * @param context
     * @param viewStub
     * @param weiba
     */
    public static void addWeibaInfo(final Context context, ViewStub viewStub, ModelWeiba weiba, AdapterSociaxList adapter) {
        RelativeLayout weibaInfo = null;
        try {
            weibaInfo = (RelativeLayout) viewStub.inflate();
            viewStub.setTag(R.id.ll_weiba_info2, weibaInfo);
        } catch (Exception e) {
            weibaInfo = (RelativeLayout) viewStub.getTag(R.id.ll_weiba_info2);
            viewStub.setVisibility(View.VISIBLE);
        } finally {
            if (weibaInfo != null) {
                setAboutWeibaInfo(context, weibaInfo, weiba, adapter);
            }
        }
    }

    /**
     * 添加关注
     *
     * @param context
     * @param stub_weiba_follow
     * @param isFollow
     * @param weiba_id
     * @param adapter
     */
    public static void addFollow(final Context context, ViewStub stub_weiba_follow, final boolean isFollow, final int weiba_id,
                                 final AdapterSociaxList adapter) {
        FrameLayout fl_follow = null;
        try {
            fl_follow = (FrameLayout) stub_weiba_follow.inflate();
            stub_weiba_follow.setTag(R.id.tv_follow);
        } catch (Exception e) {
            stub_weiba_follow.setVisibility(View.VISIBLE);
            fl_follow = (FrameLayout) stub_weiba_follow.getTag(R.id.tv_follow);
        } finally {
            if (fl_follow != null) {
                final TextView tv_follow = (TextView) fl_follow.findViewById(R.id.tv_follow);
                tv_follow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ButtonUtils.isFastDoubleClick()) {
                            return;
                        }
                        FunctionChangeWeibaFollow fc = new FunctionChangeWeibaFollow(context, isFollow, weiba_id, adapter);
                        fc.changeFollow();
                    }
                });
            }
        }
    }

    /**
     * 添加新建
     *
     * @param context
     * @param stub_weiba_follow
     * @param weiba_id
     */
    public static void addNew(final Context context, ViewStub stub_weiba_follow, final int weiba_id) {
        ImageButton ib_new = null;
        try {
            ib_new = (ImageButton) stub_weiba_follow.inflate();
            stub_weiba_follow.setTag(R.id.tv_follow);
        } catch (Exception e) {
            stub_weiba_follow.setVisibility(View.VISIBLE);
            ib_new = (ImageButton) stub_weiba_follow.getTag(R.id.tv_follow);
        } finally {
            if (ib_new != null) {
                ib_new.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ActivityCreatePost.class);
                        intent.putExtra("weiba_id", weiba_id);
                        intent.putExtra("type", AppConstant.CREATE_WEIBA_POST);
                        context.startActivity(intent);
                    }
                });
            }
        }
    }

    /**
     * 设置图片组
     *
     * @param context
     * @param imgUrl
     * @param view
     */
    private static void setAboutImageTable(final Context context, final ArrayList<ModelPhoto> imgUrl,
                                           GridViewNoScroll view, int gridWidth) {
        int imgNum = imgUrl.size();
        // 设置列数
        view.setNumColumns(/*imgNum == 2 ? 2 : */3);
        //设置点击透明
        view.setSelector(new ColorDrawable(0));
        view.setAdapter(new AdapterWeiBoImageGridView(context, imgUrl, gridWidth));
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                Intent i = new Intent(context, ActivityViewPager.class);
                i.putExtra("index", position);
                i.putParcelableArrayListExtra("photolist", imgUrl);
                ActivityViewPager.imageSize = new ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                context.startActivity(i);
            }
        });

        //设置表格快速滚动不加载图片
        view.setOnScrollListener(new PauseOnScrollListener(UIImageLoader.getInstance(context).getImageLoader(),
                true, true));
    }

    /**
     * 设置微吧列表
     *
     * @param context
     * @param weibaInfo
     * @param weiba
     */
    private static void setAboutWeibaInfo(final Context context, RelativeLayout weibaInfo, final ModelWeiba weiba, final AdapterSociaxList adapter) {
        // init something
        ImageView img_weiba_icon = (ImageView) weibaInfo.findViewById(R.id.img_weiba_icon);
        TextView tv_weiba_name = (TextView) weibaInfo.findViewById(R.id.tv_weiba_name);
        TextView tv_weiba_name_tag = (TextView) weibaInfo.findViewById(R.id.tv_weiba_name_tag);
        TextView tv_weiba_des = (TextView) weibaInfo.findViewById(R.id.tv_weiba_des);
        final TextView btn_attention = (TextView) weibaInfo.findViewById(R.id.btn_attention);
        // do something
        tv_weiba_name.setText(weiba.getWeiba_name());

        if(!TextUtils.isEmpty(weiba.getWeiba_name_tag())){
            tv_weiba_name_tag.setText(weiba.getWeiba_name_tag());
            tv_weiba_name_tag.setVisibility(View.VISIBLE);
        }else {
            tv_weiba_name_tag.setVisibility(View.GONE);
        }

        tv_weiba_des.setText(weiba.getIntro());
        MyApplication.imageLoader.displayImage(weiba.getAvatar_big(),img_weiba_icon,MyApplication.getCircleImageOptions());
       /* Glide.with(context).load(weiba.getAvatar_big())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideRoundTransform(context, 10))
                .crossFade()
                .into(img_weiba_icon);*/
        // 推荐微吧
        if ((context instanceof ActivityWeiba|| context instanceof ActivitySearchWeiba) && weiba.isFollow()) {
            btn_attention.setVisibility(View.GONE);
        } else {
//            btn_attention.setVisibility(View.VISIBLE);  // 隐藏关注按钮
            setAttentionButton(weiba.isFollow(), btn_attention, context);
        }

        btn_attention.setTag(weiba.isFollow());
        // 点击关注微吧
        btn_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ButtonUtils.isFastDoubleClick())
                    return;
                Boolean isFollow = (Boolean) btn_attention.getTag();
                FunctionChangeWeibaFollow fc = new FunctionChangeWeibaFollow(
                        context, isFollow, weiba.getWeiba_id(), adapter);
                fc.setListenerSociax(new ListenerSociax() {
                    @Override
                    public void onTaskSuccess() {
                        adapter.notifyDataSetChanged();
                        //通知界面刷新
                        EventBus.getDefault().post(weiba);
                    }

                    @Override
                    public void onTaskError() {
                        weiba.setFollow(!weiba.isFollow());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onTaskCancle() {

                    }
                });

                fc.changeFollow();
                isFollow = !isFollow;
                weiba.setFollow(isFollow);
                setAttentionButton(isFollow, (TextView) v, context);
                btn_attention.setTag(isFollow);
            }
        });

    }

    /**
     * 更改按钮样式
     *
     * @param isFav
     * @param btn
     * @param context
     */
    private static void setAttentionButton(boolean isFav, TextView btn, Context context) {
        if (isFav) {
            btn.setBackgroundResource(R.drawable.roundbackground_fav_true);
            btn.setText(R.string.fav_followed);
            btn.setTextColor(context.getResources().getColor(R.color.fav_text_true));
        } else {
            btn.setBackgroundResource(R.drawable.roundbackground_green_digg);
            btn.setText(R.string.fav_add_follow);
            btn.setTextColor(context.getResources().getColor(R.color.themeColor));
        }
    }
}
