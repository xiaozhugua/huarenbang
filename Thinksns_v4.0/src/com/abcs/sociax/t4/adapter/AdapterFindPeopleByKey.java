package com.abcs.sociax.t4.adapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.abcs.sociax.android.R;
import com.abcs.sociax.t4.android.ThinksnsAbscractActivity;
import com.abcs.sociax.t4.android.fragment.FragmentSociax;
import com.abcs.sociax.t4.android.function.FunctionChangeFollow;
import com.abcs.sociax.t4.component.GlideCircleTransform;
import com.abcs.sociax.t4.component.HolderSociax;
import com.abcs.sociax.t4.exception.VerifyErrorException;
import com.thinksns.sociax.thinksnsbase.bean.ListData;
import com.thinksns.sociax.thinksnsbase.bean.SociaxItem;
import com.thinksns.sociax.thinksnsbase.exception.ApiException;
import com.thinksns.sociax.thinksnsbase.exception.DataInvalidException;
import com.thinksns.sociax.thinksnsbase.exception.ListAreEmptyException;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 类说明：根据关键字搜索用户
 *
 * @author wz
 * @version 1.0
 * @date 2014-10-31
 */
public class AdapterFindPeopleByKey extends AdapterUserFollowingList {
    String key;
    boolean isShowAddButton;//是否显示添加关注按钮

    public AdapterFindPeopleByKey(FragmentSociax fragment,
                                  ListData<SociaxItem> list, int uid, String key) {
        super(fragment, list, uid);
        this.key = key;

    }

    /**
     * 从@用户中搜索用户
     *
     * @param context
     * @param list
     * @param key
     */
    public AdapterFindPeopleByKey(ThinksnsAbscractActivity context,
                                  ListData<SociaxItem> list, String key) {
        super(context, list);
        this.key = key;
    }

    @Override
    public ListData<SociaxItem> refreshNew(int count)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {

        return getApiUser().searchUserByKey(key,
                getMaxid(), count, httpListener);
    }

    @Override
    public ListData<SociaxItem> refreshHeader(SociaxItem obj) throws VerifyErrorException, ApiException, ListAreEmptyException, DataInvalidException {
        return refreshNew(PAGE_COUNT);
    }

    @Override
    public int getMaxid() {
        if (getLast() == null) {
            return 0;
        } else
            return getLast().getUid();
    }

    @Override
    public ListData<SociaxItem> refreshFooter(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return getApiUser().searchUserByKey(key,
                getMaxid(), PAGE_COUNT, httpListener);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderSociax viewHolder = null;
        int type = getItemViewType(position);
        if (convertView == null || convertView.getTag(R.id.tag_viewholder) == null) {
            if (type == 1) {
                viewHolder = new HolderSociax();
                convertView = inflater.inflate(R.layout.listitem_user, null);
                viewHolder.tv_user_photo = (ImageView) convertView
                        .findViewById(R.id.image_photo);
                viewHolder.tv_user_name = (TextView) convertView
                        .findViewById(R.id.unnames);
                viewHolder.tv_user_content = (TextView) convertView
                        .findViewById(R.id.uncontent);
                viewHolder.tv_user_add = (TextView) convertView
                        .findViewById(R.id.image_add);
                convertView.setTag(R.id.tag_viewholder, viewHolder);
            } else if (type == 0) {
                convertView = inflater.inflate(R.layout.default_nobody_bg, null);
                PullToRefreshListView listView = (PullToRefreshListView)getPullRefreshView();
                //设置加载布局的高度
                int width = listView.getWidth();
                int height = listView.getHeight() - 100;
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(width, height);
                convertView.setLayoutParams(params);

                holder = new HolderSociax();
                holder.tv_empty_content = (TextView) convertView.findViewById(R.id.tv_empty_content);
            } else if (type == 2) {
                //加载正在加载数据的界面
                convertView = inflater.inflate(R.layout.loading, null);
                PullToRefreshListView listView = (PullToRefreshListView)getPullRefreshView();
                //设置加载布局的高度
                int width = listView.getWidth();
                int height = listView.getHeight() - 100;
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(width, height);
                convertView.setLayoutParams(params);
            }
        } else {
            viewHolder = (HolderSociax) convertView.getTag(R.id.tag_viewholder);
        }
        if (type == 1) {
            convertView.setTag(R.id.tag_search_user, getItem(position));
            Glide.with(context).load(getItem(position).getUserface())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new GlideCircleTransform(context))
                    .crossFade()
                    .into(viewHolder.tv_user_photo);

            viewHolder.tv_user_name.setText(getItem(position).getUname());
            String intro = getItem(position).getIntro();
            if (intro == null || intro.isEmpty() || intro.equals("null") || intro.equals("暂无简介")) {
//                if (getItem(position).getUid() == Thinksns.getMy().getUid()) {
//                    viewHolder.tv_user_content.setText("暂无简介");
//                } else {
                    viewHolder.tv_user_content.setText("这家伙很懒，什么也没留下");
//                }
            } else {
                viewHolder.tv_user_content.setText(intro);
            }
            viewHolder.tv_user_add.setVisibility(View.VISIBLE);
            viewHolder.tv_user_add.setTag(R.id.tag_position, position);
            viewHolder.tv_user_add.setTag(R.id.tag_follow, getItem(position));
            if (getItem(position).getFollowing().equals("0")) {
                //加关注
                viewHolder.tv_user_add.setBackgroundResource(R.drawable.roundbackground_green_digg);
                viewHolder.tv_user_add.setText(R.string.fav_add_follow);
                viewHolder.tv_user_add.setTextColor(context.getResources().getColor(R.color.fav_border));
            } else {
                //取消关注
                viewHolder.tv_user_add.setBackgroundResource(R.drawable.roundbackground_fav_true);
                viewHolder.tv_user_add.setText(R.string.fav_followed);
                viewHolder.tv_user_add.setTextColor(context.getResources().getColor(R.color.fav_text_true));
            }
            viewHolder.tv_user_add.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    FunctionChangeFollow fcChangeFollow = new FunctionChangeFollow(
                            context, AdapterFindPeopleByKey.this, v);
                    fcChangeFollow.changeListFollow();
                }
            });
        } else if (type == 0) {
            holder.tv_empty_content.setText(context.getResources().getString(R.string.empty_search_result));
        }


        return convertView;
    }
}