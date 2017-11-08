package com.abcs.sociax.api;

import com.abcs.sociax.modle.ApproveSite;
import com.abcs.sociax.t4.exception.VerifyErrorException;
import com.thinksns.sociax.thinksnsbase.bean.ListData;
import com.thinksns.sociax.thinksnsbase.bean.SociaxItem;
import com.thinksns.sociax.thinksnsbase.exception.ApiException;
import com.thinksns.sociax.thinksnsbase.exception.DataInvalidException;
import com.thinksns.sociax.thinksnsbase.exception.ListAreEmptyException;

public interface ApiSites {
	String MOD_NAME = "Sitelist";
	String GET_SITE_LIST = "getSiteList";
	String GET_SITE_STATUS = "getSiteStatus";

	ListData<SociaxItem> getSisteList() throws ApiException,
			ListAreEmptyException, DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> newSisteList(int count) throws ApiException,
			ListAreEmptyException, DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> getSisteListHeader(ApproveSite as, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> getSisteListFooter(ApproveSite as, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	boolean getSiteStatus(ApproveSite as) throws ApiException,
			ListAreEmptyException, DataInvalidException, VerifyErrorException;

	boolean isSupport() throws ApiException, ListAreEmptyException,
			DataInvalidException, VerifyErrorException;

	boolean isSupportReg() throws ApiException, ListAreEmptyException,
			DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> searchSisteList(String key, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;
}
