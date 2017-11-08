package com.abcs.sociax.t4.android.chat;
/** 
 * 类说明： 聊天消息监听对象
 * @author  ZhiYiForMac    
 * @date    2015-8-27
 * @version 1.0
 */
public interface OnChatListener {
	/**
	 * 消息数量,count>0新增消息，count<0删除消息
	 * @param count
	 */
	void update(int count);
}
