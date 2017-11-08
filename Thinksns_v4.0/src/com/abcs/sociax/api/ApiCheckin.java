package com.abcs.sociax.api;


import com.thinksns.sociax.thinksnsbase.exception.ApiException;

/**
 * 类说明：
 * 
 * @author povol
 * @date 2013-2-6
 * @version 1.0
 */
public interface ApiCheckin {

	String MOD_NAME = "Checkin";

	String CHECKIN = "checkin";

	String GET_CHECK_INFO = "get_check_info";

	String RANK = "rank";

	Object checkIn() throws ApiException;

	Object getCheckInfo() throws ApiException;
	
	/**
	 * 获取签到排行榜
	 * @return
	 * @throws ApiException
	 */
	Object getCheckRankList() throws ApiException;

	void setLocationInfo(double latitude, double longitude) throws ApiException;

}
