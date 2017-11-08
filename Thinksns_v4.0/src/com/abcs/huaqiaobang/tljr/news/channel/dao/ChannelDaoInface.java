package com.abcs.huaqiaobang.tljr.news.channel.dao;

import java.util.List;
import java.util.Map;

import com.abcs.huaqiaobang.tljr.news.channel.bean.ChannelItem;

import android.content.ContentValues;

public interface ChannelDaoInface {
	boolean addCache(ChannelItem item);

	boolean deleteCache(String whereClause, String[] whereArgs);

	boolean updateCache(ContentValues values, String whereClause,
						String[] whereArgs);

	Map<String, String> viewCache(String selection,
								  String[] selectionArgs);

	List<Map<String, String>> listCache(String selection,
										String[] selectionArgs);

	void clearFeedTable();
}
