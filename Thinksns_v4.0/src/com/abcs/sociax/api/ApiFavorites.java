package com.abcs.sociax.api;

import com.abcs.sociax.t4.exception.VerifyErrorException;
import com.abcs.sociax.t4.model.ModelWeibo;
import com.thinksns.sociax.thinksnsbase.bean.ListData;
import com.thinksns.sociax.thinksnsbase.bean.SociaxItem;
import com.thinksns.sociax.thinksnsbase.exception.ApiException;
import com.thinksns.sociax.thinksnsbase.exception.DataInvalidException;
import com.thinksns.sociax.thinksnsbase.exception.ListAreEmptyException;

public interface ApiFavorites {
	String MOD_NAME = "WeiboStatuses";
	String INDEX = "favorite_feed";
	String CREATE = "favorite_create";
	String IS_FAVORITE = "isFavorite";
	String DESTROY = "favorite_destroy";

	ListData<SociaxItem> index(int count) throws ApiException,
			ListAreEmptyException, DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> indexHeader(ModelWeibo weibo, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> indexFooter(ModelWeibo weibo, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	boolean create(ModelWeibo weibo) throws ApiException, DataInvalidException,
			VerifyErrorException;

	boolean isFavorite(ModelWeibo weibo) throws ApiException, DataInvalidException,
			VerifyErrorException;

	boolean destroy(ModelWeibo weibo) throws ApiException, DataInvalidException,
			VerifyErrorException;
}
