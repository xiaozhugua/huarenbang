package com.abcs.sociax.t4.android.chat;
/** 
 * 类说明：   聊天消息接收对象
 * @author  ZhiYiForMac    
 * @date    2015-8-27
 * @version 1.0
 */
public interface ChatReceiverInterface {
	void addChatListener(OnChatListener listener);
	void chatDataChanged(int count);
}
