package com.abcs.sociax.t4.android.interfaces;

/**
 * 类说明：
 * 
 * @author wz
 * @date 2014-12-1
 * @version 1.0
 */
public interface OnWebSocketClientFinishListener {
	/**
	 * 添加成员是否成功
	 * @param room_id 
	 */
//	public void isAddMemberSuccess(boolean isSuccess);
	void isAddMemberSuccess(boolean isSuccess, int room_id);

	/**
	 * 删除成员是否成功
	 * 
	 * @param isSuccess
	 * @param room_id 
	 */
//	public void isDeleteMemberSuccess(int uid, boolean isSuccess,String uname);
	void isDeleteMemberSuccess(int uid, boolean isSuccess, int room_id);
//	public void isDeleteMemberSuccess(int uid, boolean isSuccess);
}
