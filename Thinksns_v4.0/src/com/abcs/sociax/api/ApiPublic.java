package com.abcs.sociax.api;

import com.abcs.sociax.t4.model.ModelAds;
import com.thinksns.sociax.thinksnsbase.bean.ListData;
import com.thinksns.sociax.thinksnsbase.exception.ApiException;

/** 
 * 类说明：   
 * @author  Zoey    
 * @date    2015年8月28日
 * @version 1.0
 */
public interface ApiPublic {
	
	String MOD_NAME = "Public";
	String SHOW_ABOUT_US = "showAbout";//关于我们
	String GET_ADS = "getSlideShow";//发现页轮播图
	
	String showAboutUs() throws ApiException;
	ListData<ModelAds> getAds() throws ApiException;
}
