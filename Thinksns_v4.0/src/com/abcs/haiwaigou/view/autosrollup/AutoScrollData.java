package com.abcs.haiwaigou.view.autosrollup;

/**
 * 自动滚动TextView的数据
 * 
 *
 * @param <T>
 */
public interface AutoScrollData<T> {

	/**
	 * * 获取标题
	 * 
	 * @param data
	 * @return
	 */
	String getTextTitle(T data);

	/**
	 * 获取内容
	 * 
	 * @param data
	 * @return
	 */
	String getIcon(T data);
	String getTextIcon(T data);
	String getTips(T data);
	long getTime(T data);

}
