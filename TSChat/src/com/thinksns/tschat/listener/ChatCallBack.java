package com.thinksns.tschat.listener;

import android.view.View;

import com.thinksns.tschat.bean.ModelChatMessage;
import com.thinksns.tschat.inter.ChatCoreResponseHandler;

/**
 * Created by hedong on 16/3/24.
 */
public interface ChatCallBack {
    //全屏查看图片
    void onImageScreen(View view, String path);
    //发送消息
    void sendMessage(ModelChatMessage message, int delay);
    //重发消息
    void retrySendMessage(ModelChatMessage message);
    //查看聊天详情
    void onDetailsInfoSelected();
    //复制文本消息
    void copyTextMsg(String text);
}
