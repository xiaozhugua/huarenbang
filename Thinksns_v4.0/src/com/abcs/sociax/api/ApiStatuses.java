package com.abcs.sociax.api;

import java.io.File;

import org.json.JSONException;

import android.graphics.Bitmap;

import com.abcs.sociax.modle.Comment;
import com.abcs.sociax.modle.Follow;
import com.thinksns.sociax.thinksnsbase.network.ApiHttpClient.HttpResponseListener;
import com.abcs.sociax.t4.exception.UpdateException;
import com.abcs.sociax.t4.exception.VerifyErrorException;
import com.abcs.sociax.t4.exception.WeiboDataInvalidException;
import com.abcs.sociax.t4.model.ModelBackMessage;
import com.abcs.sociax.t4.model.ModelUser;
import com.abcs.sociax.t4.model.ModelWeibo;
import com.thinksns.sociax.thinksnsbase.bean.ListData;
import com.thinksns.sociax.thinksnsbase.bean.SociaxItem;
import com.thinksns.sociax.thinksnsbase.exception.*;
import com.thinksns.sociax.thinksnsbase.utils.FormFile;

public interface ApiStatuses {
	String MOD_NAME = "Weibo";
	String SHOW = "show";
	String PUBLIC_TIMELINE = "public_timeline";
	String FRIENDS_TIMELINE = "friends_timeline";
	String CHANNEL_TIMELINE = "channels_timeline";
	String USER_TIMELINE = "user_timeline";
	String MENTION = "mentions_feed";
	String SEARCH = "weibo_search_weibo";
	String COMMENT_TIMELINE = "comments_timeline";
	String COMMENT_BY_ME = "comments_by_me";
	String COMMENT_RECEIVE_ME = "comments_to_me";
	String COMMENTS = "weibo_comments";
	String FOOLOWING = "following";
	String FOLLOWERS = "followers";
	String FOLLOWEACH = "friends";
	String SEARCH_USER = "weibo_search_user";
	String UPDATE = "update";
	String UPLOAD = "upload";

	String CREATE_TEXT_WEIBO = "post_weibo";// 发布文字微博
	String CREATE_IMAGE_WEIBO = "upload_photo";// 发布图片微博
	String CREATE_VIDEO_WEIBO = "upload_video";// 发布视频微博
	String COMMENT = "comment_weibo";// 评论微博
	String REPOST = "repost_weibo"; // 转发微博
	String REPOST_WEIBA = "comment_post"; // 转发微吧
	String DESTROY = "destroy";
	String COMMENT_DESTROY = "comment_destroy";
	String UN_READ = "unread";
	String ADD_DIGG = "digg_weibo"; // 添加赞
	String DEL_DIG = "undigg_weibo"; // 取消赞
	String FAVORITE = "favorite_weibo";// 收藏微博
	String UNFAVORITE = "unfavorite_weibo";// 取消收藏微博
	String DELETE = "del_weibo";// 删除微博
	String WEIBO_DETAIL = "weibo_detail";// 通过id获取weibo详情
	String DENOUNCE = "denounce_weibo";// 举报微博
	String DENOUNCE_POST = "denounce_weiba";// 举报帖子
	String WEIBO_PHOTO = "weibo_photo"; // 微博配图

	String WEIBO_SEARCH_TOPIC = "weibo_search_topic"; // 微博配图
	String OAUTH = "Oauth";
//	static final String REGISTER_VERIFY = "sendCodeByPhone";
String REGISTER_VERIFY = "send_register_code";
	String CHECK_REGISTER_VERIFY = "check_register_code";
//	static final String FINBACK_VERIFY = "send_findpwd_code";
String FINBACK_VERIFY = "sendCodeByPhone";
	String CHECK_FINDBACK_VERIFY = "checkCodeByPhone";
//	static final String CHECK_FINDBACK_VERIFY = "check_password_code";
String SAVA_USER_PWD = "saveUserPasswordByPhone";
//	static final String SAVA_USER_PWD = "save_user_pwd";
String PRIVACY = "User";
	String GET_PRIVACY = "user_privacy";
	String SAVE_PRIVACY = "save_user_privacy";
	String DIG_LIST = "weibo_diggs";
	String CHANNEL = "Channel";
	String ALL_CHANNEL = "get_all_channel";
	String THIDR_REG_INFO = "get_other_login_info";
	String BIND_VERIFY = "send_bind_code";

	ModelWeibo show(int id) throws ApiException, WeiboDataInvalidException,
			VerifyErrorException;

	ListData<ModelWeibo> publicTimeline(int count) throws ApiException,
			ListAreEmptyException, DataInvalidException, VerifyErrorException;

	ListData<ModelWeibo> publicHeaderTimeline(ModelWeibo item, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<ModelWeibo> publicFooterTimeline(ModelWeibo item, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> userTimeline(ModelUser user, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> userHeaderTimeline(ModelUser user, ModelWeibo item,
											int count) throws ApiException, ListAreEmptyException,
			DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> userFooterTimeline(ModelUser user, ModelWeibo item,
											int count) throws ApiException, ListAreEmptyException,
			DataInvalidException, VerifyErrorException;

	// 获取好友微博（第一页）
	ListData<SociaxItem> friendsTimeline(int count) throws ApiException,
			ListAreEmptyException, DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> friendsHeaderTimeline(ModelWeibo item, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	// 获取好友微博（加载更多）
	ListData<SociaxItem> friendsFooterTimeline(ModelWeibo item, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	// 获取频道微博（第一页）
	ListData<SociaxItem> channelTimeline(int count) throws ApiException,
			ListAreEmptyException, DataInvalidException, VerifyErrorException;

	// 获取频道微博（加载更多）
	ListData<SociaxItem> channelFooterTimeline(ModelWeibo item, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> mentions(int count) throws ApiException,
			ListAreEmptyException, DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> mentionsHeader(ModelWeibo item, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> mentionsFooter(ModelWeibo item, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> search(String key, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> searchHeader(String key, ModelWeibo item, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> searchFooter(String key, ModelWeibo item, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<Comment> commentTimeline(int count) throws ApiException,
			ListAreEmptyException, DataInvalidException, VerifyErrorException;

	ListData<Comment> commentHeaderTimeline(Comment item, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<Comment> commentFooterTimeline(Comment item, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> commentMyTimeline(int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> commentMyHeaderTimeline(Comment item, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> commentMyFooterTimeline(Comment item, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> commentReceiveMyTimeline(int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> commentReceiveMyHeaderTimeline(Comment item,
														int count) throws ApiException, ListAreEmptyException,
			DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> commentReceiveMyFooterTimeline(Comment item,
														int count) throws ApiException, ListAreEmptyException,
			DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> commentForWeiboTimeline(ModelWeibo item, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> commentForWeiboHeaderTimeline(ModelWeibo item,
													   Comment comment, int count) throws ApiException,
			ListAreEmptyException, DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> commentForWeiboFooterTimeline(ModelWeibo item,
													   Comment comment, int count) throws ApiException,
			ListAreEmptyException, DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> following(ModelUser user, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> followingHeader(ModelUser user, Follow firstUser,
										 int count) throws ApiException, ListAreEmptyException,
			DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> followingFooter(ModelUser user, Follow lastUser,
										 int count) throws ApiException, ListAreEmptyException,
			DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> followers(ModelUser user, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> followersHeader(ModelUser user, Follow firstUser,
										 int count) throws ApiException, ListAreEmptyException,
			DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> followersFooter(ModelUser user, Follow lastUser,
										 int count) throws ApiException, ListAreEmptyException,
			DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> followEach(ModelUser user, int count)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> followEachHeader(ModelUser user, Follow firstUser,
										  int count) throws ApiException, ListAreEmptyException,
			DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> followEachFooter(ModelUser user, Follow lastUser,
										  int count) throws ApiException, ListAreEmptyException,
			DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> searchUser(String user, int count, int page)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException;

	ListData<SociaxItem> searchHeaderUser(String user, ModelUser firstUser,
										  int count, int page) throws ApiException, ListAreEmptyException,
			DataInvalidException, VerifyErrorException;

	ListData<SociaxItem> searchFooterUser(String user, ModelUser lastUser,
										  int count, int page) throws ApiException, ListAreEmptyException,
			DataInvalidException, VerifyErrorException;

	int update(ModelWeibo weibo) throws ApiException, VerifyErrorException,
			UpdateException;

	boolean upload(ModelWeibo weibo, File file) throws ApiException,
			VerifyErrorException, UpdateException;

	boolean repost(ModelWeibo weibo, boolean comment) throws ApiException,
			VerifyErrorException, UpdateException, DataInvalidException;

	// 发布文字微博
	ModelBackMessage createNewTextWeibo(ModelWeibo weibo) throws ApiException,
			VerifyErrorException, UpdateException;

	// 发布图片微博
	ModelBackMessage createNewImageWeibo(ModelWeibo weibo, FormFile[] filelist)
			throws ApiException, VerifyErrorException, UpdateException;

	// 发送包含位置的文字微博
	ModelBackMessage createNewTextWeibo(ModelWeibo weibo, double longitude, double latitude, String address)
			throws ApiException, VerifyErrorException, UpdateException;
	/**
	 * 发布视频微博
	 * @param weibo
	 * @param file1 预览的图片
	 * @param file2 图片地址
	 * @return
	 * @throws ApiException
	 * @throws VerifyErrorException
	 * @throws UpdateException
	 */
	ModelBackMessage createNewVideoWeibo(ModelWeibo weibo, Bitmap file1, File file2)
			throws ApiException, VerifyErrorException, UpdateException;

	// 评论一条微博
	int comment(Comment comment) throws ApiException,
			VerifyErrorException, UpdateException, DataInvalidException,
			JSONException;

	// 转发一条微博
	ModelBackMessage transpond(Comment comment) throws ApiException,
			VerifyErrorException, UpdateException, DataInvalidException,
			JSONException;

	boolean destroyComment(Comment coment) throws ApiException,
			VerifyErrorException, DataInvalidException;

	boolean destroyWeibo(ModelWeibo weibo) throws ApiException,
			VerifyErrorException, DataInvalidException;

	int unRead() throws ApiException, VerifyErrorException,
			DataInvalidException;

	// 添加赞
	int addDig(int feedId) throws ApiException, JSONException;

	// 取消赞
	int delDigg(int feedId) throws ApiException, JSONException;

	ListData<SociaxItem> getWeiboPhoto(int uid, int count, int page)
			throws ApiException;

	ListData<SociaxItem> getTopicWeiboList(String key, int page)
			throws ApiException;

	// 收藏微博
	ModelBackMessage favWeibo(ModelWeibo weibo, HttpResponseListener listener) throws ApiException, JSONException;

	// 取消收藏微博
	ModelBackMessage unFavWeibo(ModelWeibo weibo, HttpResponseListener listener) throws ApiException,
			JSONException;

	// 删除微博
	ModelBackMessage deleteWeibo(ModelWeibo weibo) throws ApiException,
			JSONException;

	// 获取微博
	ModelWeibo getWeiboById(int id) throws ApiException, JSONException,
			WeiboDataInvalidException;

	// 举报微博
	ModelBackMessage denounceWeibo(int id, String reason)
			throws ApiException, JSONException;

	ListData<SociaxItem> userTimeline(int count) throws ApiException,
			ListAreEmptyException, DataInvalidException, VerifyErrorException;

	Object getDiggList(int feedId, int max_id) throws ApiException,
			JSONException;

	/**
	 * 赞和取消赞微博
	 * @param feedId
	 * @param prestatus
	 * @return
	 * @throws ApiException
	 * @throws JSONException
	 */
	ModelBackMessage changeWeiboDigg(int feedId, int prestatus)
			throws ApiException, JSONException;
}
