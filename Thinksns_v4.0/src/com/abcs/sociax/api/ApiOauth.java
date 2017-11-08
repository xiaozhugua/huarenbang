package com.abcs.sociax.api;

import com.thinksns.sociax.thinksnsbase.network.ApiHttpClient.HttpResponseListener;
import com.abcs.sociax.t4.model.ModelRegister;
import com.thinksns.sociax.thinksnsbase.exception.ApiException;

public interface ApiOauth {
	String MOD_NAME = "Oauth";
	String REQUEST_ENCRYP = "request_key";
	String AUTHORIZE = "authorize";
	String REGISTER = "Register";
	String THRID_LOGIN = "get_other_login_info";
	String BIND_LOGIN = "bind_new_user";
	String UPLPAD_FACE = "register_upload_avatar";

	String CHANGE_FACE = "upload_avatar";

	String SIGN_IN = "signIn";

	void authorize(final String uname, final String password,
				   final HttpResponseListener listener);

	Api.Status requestEncrypKey() throws ApiException;

	Object register(Object data, String... type) throws ApiException;

	int thirdRegister(Object data) throws ApiException;

	Object thridLogin(String type, String type_uid) throws ApiException;

	String getRegisterVerifyCode(String phoneNumber);

	String getFindVerifyCode(String phoneNumber);

	Object signIn(ModelRegister data) throws ApiException;
}
