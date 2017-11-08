package com.thinksns.tschat.listener;

import com.thinksns.tschat.bean.ModelUser;

import java.util.List;

/**
 * Created by ZhiYiForMac on 15/12/10.
 */
public interface ChatMembersInter {

    //删除群成员
    void deleteMember(ModelUser user);
    //退出群房间
    void exitGroupChat(int roomId);
    //清空聊天记录
    void clearChatHistory(int roomId);
    //添加群成员
    void addMember(List<ModelUser> users);

    //更改房间名称
    void changeRoomTitle(String name);

}
