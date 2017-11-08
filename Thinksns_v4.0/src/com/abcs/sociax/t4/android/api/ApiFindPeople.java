package com.abcs.sociax.t4.android.api;


import com.thinksns.sociax.thinksnsbase.exception.ApiException;

/**
 * 类说明：   附近的人
 * @author  Zoey    
 * @date    2015年10月21日
 * @version 1.0
 */
public interface ApiFindPeople {
	String MOD_NAME = "FindPeople";
	String NEARBY_UPDATE_LOCATION = "updateUserLocation";//更新位置信息
	/**
	 * 更新位置信息
	 * @return
	 * @throws ApiException
	 */
	String updateLocation(String lat, String lng) throws ApiException;
}