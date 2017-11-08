package com.abcs.sociax.api;

import java.io.File;

import com.abcs.sociax.modle.Comment;
import com.abcs.sociax.modle.ReceiveComment;
import com.abcs.sociax.t4.model.ModelUser;
import com.abcs.sociax.t4.model.ModelWeibo;
import com.thinksns.sociax.thinksnsbase.bean.ListData;
import com.thinksns.sociax.thinksnsbase.bean.SociaxItem;
import com.thinksns.sociax.thinksnsbase.exception.ApiException;

public interface ApiGroup {
	String MOD_NAME = "Group";
	String SHOW_STATUSES_TYPE = "showStatusType"; //
	String SHOW_STATUSES = "showStatuses";
	String SHOW_ATME_STATUSES = "showAtmeStatuses";
	String SHOW_STATUS_COMMENTS = "showStatusComments"; // 群组内评论我的
	String GROUP_MEMBERS = "groupMembers"; //

	String WEIBO_DETAI = "weiboDetai";
	String WEIBO_COMMENTS = "WeiboComments";

	String UPDATE_STATUS = "updateStatus";
	String UPLOAD_STATUS = "uploadStatus";
	String REPOST_STATUSES = "repostStatuses";

	String COMMENT_STATUSES = "commentStatuses";

	ListData<SociaxItem> showStatuesType() throws ApiException;

	ListData<SociaxItem> showStatuses(int count, int type)
			throws ApiException;

	ListData<SociaxItem> showStatusesHeader(ModelWeibo item, int count,
											int type) throws ApiException;

	ListData<SociaxItem> showStatusesFooter(ModelWeibo item, int count,
											int type) throws ApiException;

	ListData<SociaxItem> showAtmeStatuses(int count) throws ApiException;

	ListData<SociaxItem> showAtmeStatusesHeader(ModelWeibo item, int count)
			throws ApiException;

	ListData<SociaxItem> showAtmeStatusesFooter(ModelWeibo item, int count)
			throws ApiException;

	ListData<SociaxItem> showStatusComments(int count)
			throws ApiException;

	ListData<SociaxItem> showStatusCommentsHeader(ReceiveComment item,
												  int count) throws ApiException;

	ListData<SociaxItem> showStatusCommentsFooter(ReceiveComment item,
												  int count) throws ApiException;

	ListData<SociaxItem> groupMembers(int count) throws ApiException;

	ListData<SociaxItem> groupMembersHeader(ModelUser user, int count)
			throws ApiException;

	ListData<SociaxItem> groupMembersFooter(ModelUser user, int count)
			throws ApiException;

	ListData<SociaxItem> weiboComments(ModelWeibo item, Comment comment,
									   int count) throws ApiException;

	ListData<SociaxItem> weiboCommentsHeader(ModelWeibo item,
											 Comment comment, int count) throws ApiException;

	ListData<SociaxItem> weiboCommentsFooter(ModelWeibo item,
											 Comment comment, int count) throws ApiException;

	boolean updateStatus(ModelWeibo weibo) throws ApiException;

	boolean uploadStatus(ModelWeibo weibo, File file) throws ApiException;

	boolean repostStatuses(ModelWeibo weibo, boolean isComment)
			throws ApiException;

	boolean commentStatuses(Comment comment) throws ApiException;
}
