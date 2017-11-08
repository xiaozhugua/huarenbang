package com.abcs.sociax.api;

import com.thinksns.sociax.thinksnsbase.bean.ListData;
import com.thinksns.sociax.thinksnsbase.bean.SociaxItem;
import com.thinksns.sociax.thinksnsbase.exception.ApiException;

public interface ApiMobileApps {

	String MOD_NAME = "AppList";
	// 获取Apps分类列表
	String GET_CATEGORY_LIST = "getCategoryList";
	// 获取所有Apps列表
	String GET_APPS_LIST = "get_app_list";
	// 获取默认应用
	String GET_USER_APPS_LIST = "user_app_list";

	String INSTALL = "create";

	String UN_INSTALL = "destroy";

	String SEARCH_APP = "search_app";

	ListData<SociaxItem> getMobileAppsList() throws ApiException;

	ListData<SociaxItem> getUserAppsList() throws ApiException;

	ListData<SociaxItem> searchAppsList() throws ApiException;

	boolean installApp(int uid, int appid) throws ApiException;

	boolean uninstallApp(int uid, int appid) throws ApiException;

}
