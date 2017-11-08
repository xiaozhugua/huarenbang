package com.abcs.sociax.api;

import com.abcs.sociax.t4.exception.VerifyErrorException;
import com.abcs.sociax.t4.model.ModelUser;
import com.thinksns.sociax.thinksnsbase.exception.ApiException;
import com.thinksns.sociax.thinksnsbase.exception.DataInvalidException;

public interface ApiFriendships {
	String SHOW = "show";
	String MOD_NAME = "Friendships";
	String CREATE = "create";
	String DESTROY = "destroy";

	String ADDTOBLACKLIST = "add_blacklist";
	String DELTOBLACKLIST = "remove_blacklist";
	/** 关注话题 */
	String ISFOLLOWTOPIC = "isFollowTopic";
	/** 关注话题 */
	String FOLLOWTOPIC = "followTopic";
	/** 取消关注话题 */
	String UNFOLLOWTOPIC = "unfollowTopic";

	boolean show(ModelUser friends) throws ApiException, VerifyErrorException;

	boolean create(ModelUser user) throws ApiException, VerifyErrorException,
			DataInvalidException;

	boolean destroy(ModelUser user) throws ApiException,
			VerifyErrorException, DataInvalidException;

	boolean addBlackList(ModelUser user) throws ApiException,
			VerifyErrorException, DataInvalidException;

	boolean delBlackList(ModelUser user) throws ApiException,
			VerifyErrorException, DataInvalidException;

	/** 是否关注话题 */
	boolean isFollowTopic(ModelUser user, String topic) throws ApiException,
			VerifyErrorException, DataInvalidException;

	/** 关注话题 */
	boolean followTopic(ModelUser user, String topic) throws ApiException,
			VerifyErrorException, DataInvalidException;

	/** 取消关注话题 */
	boolean unFollowTopic(ModelUser user, String topic) throws ApiException,
			VerifyErrorException, DataInvalidException;

}
