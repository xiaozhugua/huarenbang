package com.abcs.sociax.t4.android.interfaces;

import android.view.View;

import com.abcs.sociax.t4.model.ModelChannel;

/**
 * Created by hedong on 16/3/7.
 */
public interface ChannelViewInterface {
    //关注频道
    void postAddFollow(View view, ModelChannel channel);

    void responseAddFollow();

}
