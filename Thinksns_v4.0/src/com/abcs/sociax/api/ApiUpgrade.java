package com.abcs.sociax.api;

import com.abcs.sociax.modle.VersionInfo;
import com.thinksns.sociax.thinksnsbase.exception.ApiException;

public interface ApiUpgrade {
	String MOD_NAME = "Upgrade";
	String GET_VERSION = "getVersion";

	/**
	 * 获取版本信息
	 * 
	 * @return
	 * @throws ApiException
	 */
	VersionInfo getVersion() throws ApiException;
}