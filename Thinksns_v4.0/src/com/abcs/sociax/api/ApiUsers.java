package com.abcs.sociax.api;

import java.io.File;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.abcs.sociax.modle.NotifyCount;
import com.thinksns.sociax.thinksnsbase.network.ApiHttpClient.HttpResponseListener;
import com.abcs.sociax.t4.exception.VerifyErrorException;
import com.abcs.sociax.t4.model.*;
import com.thinksns.sociax.thinksnsbase.bean.ListData;
import com.thinksns.sociax.thinksnsbase.bean.SociaxItem;
import com.thinksns.sociax.thinksnsbase.exception.ApiException;
import com.thinksns.sociax.thinksnsbase.exception.DataInvalidException;
import com.thinksns.sociax.thinksnsbase.exception.ExceptionIllegalParameter;
import com.thinksns.sociax.thinksnsbase.exception.ListAreEmptyException;

public interface ApiUsers {
	String MOD_NAME = "User";
	String SHOW = "show";
	String UPLOAD_FACE = "upload_face";
	String NOTIFICATION_COUNT = "notificationCount";

	String FOLLOWERS = "user_follower"; // 粉丝列表
	String FOOLOWING = "user_following"; // 关注列表
	String FOLLOWEACH = "user_friends"; // 互相关注

	String FOLLOW_CREATE = "follow_create"; // 关注
	String FOLLOW_DESTROY = "follow_destroy"; // 取消关注

	String RECENT_USER = "search_at"; // 最近@联系人
	String RECENT_TOPIC = "search_topic"; // 推荐话题

	String UNSET_NOTIFICATION_COUNT = "unsetNotificationCount";
	String NOTIFICATIONLIST = "getNotificationList";

	String GET_USER_CATEGORY = "get_user_category"; // 返回用户分类

	String SEARCH_BY_UESR_CATEGORY = "search_by_uesr_category"; // 按官方推荐分类搜索用户
	String SEARCH_BY_TAG = "search_by_tag"; // // 按标签搜索用户
	String SEARCH_BY_AREA = "search_by_area"; // 按地区搜索用户
	String SEARCH_BY_VERIFY_CATEGORY = "search_by_verify_category"; // 按认证分类搜索用户

	String GET_USER_FOLLOWER = "get_user_follower";
	String CHECKIN = "checkin";
	String NEIGHBOR = "neighbors";
	String FRIENDS = "user_friend";
	String FRIENDS_LETTER = "user_friend_by_letter";
	String FOLLOW = "follow";
	String UNFOLLOW = "unfollow";
	String FINDPEOPLE = "FindPeople";
	String NEARBY = "search_by_distinct";
	String	NEW_NEARBY = "around";
	String DISTRICT = "search_by_city";
	String SEARCH_BY_KEY = "search_user";
	String VERIFY = "search_by_verify";
	String TAG = "search_by_tag";
	String CONTRACT = "search_by_tel";
	String SEARCH = "search_user";
	String GET_USER_TAGS = "get_user_tags";
	String GET_USER_VERIFYS = "get_user_verify";
	String GET_USER_CITY = "get_user_city";
	String SAVE_USER_INFO = "save_user_info";
	String CREDIT = "Credit";
	String CREDIT_MY = "Credit_my";
	String GET_AREA_LIST = "get_area_list";
	String GET_AREA = "getArea";
	String BLACK_LIST = "user_blacklist";
	String REMOVE_BLACK = "remove_blacklist";
	String USER_PHOTO = "user_photo";
	String USER_VIDEO = "user_video";
	String GET_USER_BIND_INFO = "user_bind";
	String BIND_OTHER = "bind_other";
	String UN_BIND_OTHER ="unbind";
	String TOP_AD = "top_ad";
	String TOP_SOCRE_LIST = "rank_score";//积分排名
	String TOP_MEDAL_LIST = "rank_medal";//勋章排名
	String BIND_PHONE = "do_bind_phone";
	String SEARCH_AT = "user_friend";//搜索好友
	String CHANGE_BACKGROUND = "uploadUserCover";//更换背景

	ModelUser show(ModelUser user, HttpResponseListener listener) throws ApiException, DataInvalidException,
			VerifyErrorException;

	NotifyCount notificationCount(int uid) throws ApiException,
			ListAreEmptyException, DataInvalidException, VerifyErrorException;

	boolean unsetNotificationCount(NotifyCount.Type type, int uid)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	boolean uploadFace(File file) throws ApiException;

	boolean checkint(String la, String lo) throws ApiException;

	boolean uploadFace(Bitmap bitmap, File file) throws ApiException;

	ListData<SociaxItem> getNotificationList(int uid)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	void getRecentTopic(int pagecount, int max_id, final AsyncHttpResponseHandler listener);

	ListData<SociaxItem> getRecentAt() throws ApiException;

	ListData<SociaxItem> getUserCategory(String type)
			throws ApiException;

	ListData<SociaxItem> searchByUesrCategory(String key, int page)
			throws ApiException;

	ListData<SociaxItem> searchByArea(String key, int page)
			throws ApiException;

	ListData<SociaxItem> searchByTag(String key, int page)
			throws ApiException;

	ListData<SociaxItem> searchByVerifyCategory(String key, int page)
			throws ApiException;

	ListData<SociaxItem> getUserFollower(int page) throws ApiException;

	ListData<SociaxItem> getNeighbor(String la, String lo, int page)
			throws ApiException;

	/**
	 * 获取关注的人
	 * 
	 * @param uid
	 *            用户id
	 * @param max_id
	 *            最后一个id，第一次或者刷新头部传0
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> getUserFollowingList(int uid,String name, int max_id, HttpResponseListener listener)
			throws ApiException;

	/**
	 * 获取粉丝列表
	 * 
	 * @param uid
	 *            用户id
	 * @param max_id
	 *            当前列表最后一个id 第一次或者刷新传0
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> getUserFollowList(int uid, String name,int max_id, HttpResponseListener listener)
			throws ApiException;

	/**
	 * 获取好友列表
	 * 
	 * @param uid
	 *            用户id
	 * @param max_id
	 *            当前列表最后一个id 第一次或者刷新传0
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> getUserFriendsList(int uid, int max_id, HttpResponseListener listener)
			throws ApiException;
	
	/**
	 * 按照字幕排序的互相关注好友列表
	 * @param uid
	 * @param max_id
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> getUserFriendsListByLetter(int uid, int max_id)
			throws ApiException;

	/**
	 * 修改关注状态
	 * 
	 * @param uid
	 *            关注/取消关注的uid
	 * @param type
	 *            0：取消关注，1 添加关注
	 * @return
	 * @throws ApiException
	 */
	Object changeFollowing(int uid, int type) throws ApiException;

	/**
	 * 查找附近的人
	 * 
	 * @param lat
	 *            经度
	 * @param lng
	 *            维度
	 * @param max_id
	 *            f分页，最后一个id 第一次或者刷新0
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> getNearByUser(String lat, String lng, int max_id, HttpResponseListener listener)
			throws ApiException;

	/**
	 * 获取某个地区的用户
	 * 
	 * @param city_id
	 *            城市编号
	 * @param max_id
	 *            f分页，最后一个id 第一次或者刷新0
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> getDistrictUser(int city_id, int max_id, HttpResponseListener listener)
			throws ApiException;

	/**
	 * 通过关键字找人
	 * 
	 * @param key
	 *            关键字 //传空则为感兴趣的人
	 * @param max_id
	 *            //最后一个人的id
	 * @param count
	 *            一次返回条数
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> searchUserByKey(String key, int max_id, int count, HttpResponseListener listener)
			throws ApiException;

	/**
	 * 通过官方认证找人
	 * 
	 * @param verify_id
	 *            认证id
	 * @param max_id
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> searchUserByVerifyCode(String verify_id, int max_id, HttpResponseListener listener)
			throws ApiException;

	/**
	 * 通过标签找人
	 * 
	 * @param id
	 * @param max_id
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> searchUserByTag(int id, int max_id, HttpResponseListener listener)
			throws ApiException;

	/**
	 * 通过通讯录找人
	 * 
	 * @param phone
	 *            本地联系人电话号码字符串，用逗号分隔开
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> searchUserByContract(String phone, HttpResponseListener listener) throws ApiException;

	/**
	 * 搜索感兴趣的人 每次返回的列表不一定
	 * 
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> searchUser(HttpResponseListener listener) throws ApiException;
	ListData<SociaxItem> searchUser(final HttpResponseListener listener, int count) throws ApiException;
	/**
	 * 标签用户 每次返回的列表不一定
	 * 
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> getTagList();

	/**
	 * 认证用户 每次返回的列表不一定
	 * 
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> getVerifyList();

	/**
	 * 获取城市列表
	 * 
	 * @return
	 */
	Map<String, List<ModelCityInfo>> getCityList();

	/**
	 * 获取地区列表
	 * 
	 * @return
	 */
	Map<String, List<ModelCityInfo>> getAreaList();

	/**
	 * 获取黑名单
	 *            当前用户id
	 * @param max_id
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> getUserBlackList(int count, int max_id, final HttpResponseListener listener);

	/**
	 * 移除黑名单
	 * 
	 * @param uid
	 * @return
	 * @throws ApiException
	 */
	Object removeBlackList(int uid) throws ApiException;

	/**
	 * 获取二维码文本信息
	 * 
	 * @param uid
	 * @return
	 * @throws ApiException
	 */
	String getEwmText(String uid) throws ApiException;

	/**
	 * 获取用户照片列表
	 * 
	 * @param uid
	 * @param max_id
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> getUserPhoto(int uid, int max_id, int count, final HttpResponseListener listener) throws ApiException;

	/**
	 * 获取用户视频列表
	 * 
	 * @param uid
	 * @param max_id
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> getUserVedio(int uid, int max_id, int count, final HttpResponseListener listener)
			throws ApiException;
	/**
	 * 获取用户账号的绑定信息
	 * @return
	 */
	ListData<SociaxItem> getUserBindInfo()throws ApiException;
	/**
	 * 第三方绑定
	 * @param type qzone /sina/weixin/phone
	 * @param openId
	 * @param token
	 * @return
	 */
	Object bindOther(String type, String openId, String token);
	/**
	 * 解除绑定
	 * @param type
	 * @return
	 */
	Object unbindOther(String type);
	/**
	 * 获取广告
	 * @return  null 或者list
	 * @throws ApiException
	 */
	ListData<SociaxItem> readAds() throws ApiException;
	/**
	 * 搜索联系人
	 * @param key 关键字，传空为全部联系人
	 * @param max_id 最后一个人的uid，第一次传0
	 * @param count 一次请求数量，默认20
	 * @return
	 * @throws ApiException
	 */
	ListData<SociaxItem> searchAtUser(String key, int max_id, int count, final HttpResponseListener listener);
	/**
	 * 获取用户勋章
	 * @param uid
	 * @param max_id
	 * @return
	 * @throws ApiException
	 * @throws ListAreEmptyException
	 * @throws DataInvalidException
	 * @throws VerifyErrorException
	 * @throws ExceptionIllegalParameter
	 */
	ListData<SociaxItem> getUserHonner(int uid, int max_id)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException, ExceptionIllegalParameter;
	/**
	 * 获取反馈类型
	 * @param count
	 * @param max_id
	 * @return
	 * @throws ApiException
	 * @throws ListAreEmptyException
	 * @throws DataInvalidException
	 * @throws VerifyErrorException
	 * @throws ExceptionIllegalParameter
	 */
	ListData<SociaxItem> getFeedbackType(int count, int max_id)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException, ExceptionIllegalParameter;

	/**
	 * 提交用户意见
	 * @param string
	 * @return
	 */
	String addFeedBack(String string);
	/**
	 * 通过uname获取user信息
	 * @param user
	 * @return
	 * @throws ApiException
	 * @throws DataInvalidException
	 * @throws VerifyErrorException
	 */
	ModelUser showByUname(ModelUser user) throws ApiException,
			DataInvalidException, VerifyErrorException;

	Map<String, List<ModelAreaInfo>> getArea();
	Map<String, List<ModelAreaInfo>> getArea(String id);

}
