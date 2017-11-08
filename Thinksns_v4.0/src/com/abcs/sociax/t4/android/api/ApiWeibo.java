package com.abcs.sociax.t4.android.api;

import com.thinksns.sociax.thinksnsbase.network.ApiHttpClient.HttpResponseListener;
import com.abcs.sociax.t4.exception.VerifyErrorException;
import com.abcs.sociax.t4.exception.WeiboDataInvalidException;
import com.abcs.sociax.t4.model.ModelWeibo;
import com.thinksns.sociax.thinksnsbase.bean.ListData;
import com.thinksns.sociax.thinksnsbase.bean.SociaxItem;
import com.thinksns.sociax.thinksnsbase.exception.ApiException;
import com.thinksns.sociax.thinksnsbase.exception.DataInvalidException;
import com.thinksns.sociax.thinksnsbase.exception.ExceptionIllegalParameter;
import com.thinksns.sociax.thinksnsbase.exception.ListAreEmptyException;

/**
 * 类说明： 所有跟微博相关的接口
 * 
 * @author wz
 * @date 2014-10-15
 * @version 1.0
 */
public interface ApiWeibo {
	String MOD_NAME = "Weibo";// 请求mod名称
	String SHOW = "show";//展示某条微博的详细信息
	
	String PUBLIC_TIMELINE = "public_timeline";// 公共/推荐微博
	String FRIENDS_TIMELINE = "friends_timeline";// 我的关注微博
	String CHANNEL_TIMELINE = "channels_timeline";// 频道微博
	String RECOMMEND_TIMELINE = "recommend_timeline";// 推荐微博
	
	String WEIBO_DIGG_ME = "user_diggs_to_me";// 赞我的微博
//	static final String WEIBO_AT_ME = "user_mentions";// @我的
String WEIBO_AT_ME = "user_related";// @我的
	String WEIBO_COMMENTT_ME = "user_comments_to_me";// 评论我的
	String WEIBO_MY = "user_timeline";// 我的微博列表
	String WEIBO_COLLECT = "user_collections";
	String ALL_TOPIC ="all_topic";//所有话题
	String TOPIC_WEIBO ="topic_timeline";//某个话题的微博
	String DELETE_COMMENT = "delComment";		//删除微博评论
	String WEIBO_COMMENTS = "weibo_comments";	//微博详情评论
	
	/**
	 * 公共/推荐/最新的微博
	 * 
	 * @param count
	 *            每次请求数目（必填）
	 * @param max_id
	 *            最后一条微博的id（第一次请求传0）
	 * @return
	 * @throws ApiException
	 * @throws ListAreEmptyException
	 * @throws DataInvalidException
	 * @throws VerifyErrorException
	 * @throws ExceptionIllegalParameter
	 */
	ListData<SociaxItem> publicTimeline(int count, int max_id, HttpResponseListener listener)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException, ExceptionIllegalParameter;

	/**
	 * 获取我关注的好友微博
	 * 
	 * @param count
	 *            每次请求数目（必填）
	 * @param max_id
	 *            上一次请求最后一条微博（如果有的话）
	 * @return
	 * @throws ApiException
	 * @throws ListAreEmptyException
	 * @throws DataInvalidException
	 * @throws VerifyErrorException
	 * @throws ExceptionIllegalParameter
	 */
	ListData<SociaxItem> friendsTimeline(int count, int max_id, HttpResponseListener listener)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException, ExceptionIllegalParameter;

	/**
	 * 赞我的微博列表
	 * 
	 * @param count
	 * @param max_id
	 * @return
	 * @throws ApiException
	 * @throws ListAreEmptyException
	 * @throws DataInvalidException
	 * @throws VerifyErrorException
	 * @throws ExceptionIllegalParameter
	 */
	ListData<SociaxItem> diggMeWeibo(int count, int max_id, HttpResponseListener listener)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException, ExceptionIllegalParameter;

	/**
	 * @我的微博
	 * @param count
	 * @param max_id
	 * @return
	 * @throws ApiException
	 * @throws ListAreEmptyException
	 * @throws DataInvalidException
	 * @throws VerifyErrorException
	 * @throws ExceptionIllegalParameter
	 */
	ListData<SociaxItem> atMeWeibo(int count, int max_id, HttpResponseListener listener) throws ApiException,
			ListAreEmptyException, DataInvalidException, VerifyErrorException,
			ExceptionIllegalParameter;

	/**
	 * 评论我的微博
	 * 
	 * @param count
	 * @param max_id
	 * @param type 在哪里评论我的微博，例如weiba_post表示在微吧里面评论我的
	 * @return
	 * @throws ApiException
	 * @throws ListAreEmptyException
	 * @throws DataInvalidException
	 * @throws VerifyErrorException
	 * @throws ExceptionIllegalParameter
	 */
	ListData<SociaxItem> commentMeWeibo(int count, int max_id,String type, HttpResponseListener listener)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException, ExceptionIllegalParameter;

	/**
	 * 用户的微博
	 * 
	 * @param uid
	 *            用户id
	 * @param count
	 * @param max_id
	 * @return
	 * @throws ApiException
	 * @throws ListAreEmptyException
	 * @throws DataInvalidException
	 * @throws VerifyErrorException
	 * @throws ExceptionIllegalParameter
	 */
	ListData<SociaxItem> myWeibo(int uid, int count, int max_id, HttpResponseListener listener)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException, ExceptionIllegalParameter;
	/**
	 * 用户收藏的微博
	 * @param uid  用户id
	 * @param count 读取条数
	 * @param max_id 最后一条微博id
	 * @return
	 * @throws ApiException
	 * @throws ListAreEmptyException
	 * @throws DataInvalidException
	 * @throws VerifyErrorException
	 * @throws ExceptionIllegalParameter
	 */
	ListData<SociaxItem> collectWeibo(int uid, int count, int max_id, HttpResponseListener listener)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException, ExceptionIllegalParameter;
	/**
	 * 展示某条微博的微博详情
	 * @param id 微博id
	 * @return
	 * @throws ApiException
	 * @throws WeiboDataInvalidException
	 * @throws VerifyErrorException
	 */
	ModelWeibo show(int id) throws ApiException, WeiboDataInvalidException,
			VerifyErrorException;
	/**
	 * 推荐微博
	 * @param count 每次请求数目
	 * @param max_id 上一条微博id 没有传0
	 * @return
	 * @throws ApiException
	 * @throws ListAreEmptyException
	 * @throws DataInvalidException
	 * @throws VerifyErrorException
	 * @throws ExceptionIllegalParameter
	 */
	ListData<SociaxItem> recommendTimeline(int count, int max_id, HttpResponseListener listener)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException, ExceptionIllegalParameter;
	/**
	 * 获取所有话题
	 * @param count
	 * @param max_id 最后一条topic id
	 * @return
	 * @throws ApiException
	 * @throws ListAreEmptyException
	 * @throws DataInvalidException
	 * @throws VerifyErrorException
	 * @throws ExceptionIllegalParameter
	 */
	ListData<SociaxItem> getAllTopic(int count, int max_id, HttpResponseListener listener)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException, ExceptionIllegalParameter;
	/**
	 * 获取某个频道的微博
	 * @param topic_name  频道名称（必填）
	 * @param count  获取数目（默认20）
	 * @param max_id 当前列表最后一条微博的id（第一次为0）
	 * @return
	 * @throws ApiException
	 * @throws ListAreEmptyException
	 * @throws DataInvalidException
	 * @throws VerifyErrorException
	 * @throws ExceptionIllegalParameter
	 */
	Object getTopicWeibo(String topic_name,int count, int max_id, HttpResponseListener listener)
			throws ApiException, ListAreEmptyException, DataInvalidException,
			VerifyErrorException, ExceptionIllegalParameter;
	
	Object deleteWeiboComment(int commentId) throws ApiException;

	/**
	 * 频道微博
	 * @param count
	 * @param max_id
	 * @param listener
	 * @return
	 * @throws ApiException
	 * @throws ListAreEmptyException
	 * @throws DataInvalidException
	 * @throws VerifyErrorException
     * @throws ExceptionIllegalParameter
     */
	ListData<SociaxItem> channelsTimeline(int count, int max_id, final HttpResponseListener listener)
			throws ApiException, ListAreEmptyException,
			DataInvalidException, VerifyErrorException,
			ExceptionIllegalParameter;
}
