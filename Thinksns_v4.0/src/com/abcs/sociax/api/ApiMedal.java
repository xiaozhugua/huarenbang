package com.abcs.sociax.api;

import com.thinksns.sociax.thinksnsbase.exception.ApiException;

/**
 * 类说明：
 * 
 * @author Zoey
 * @date 2015年9月9日
 * @version 1.0
 */
public interface ApiMedal {
	
	String MOD_NAME = "Medal";
	String ALL_MEDALS = "getAll";// 获得全部勋章
	String MY_MEDAL = "getUser";// 获得我的勋章
	
	String getAllMedals() throws ApiException;
	String getMyMedal(int uid) throws ApiException;
}
