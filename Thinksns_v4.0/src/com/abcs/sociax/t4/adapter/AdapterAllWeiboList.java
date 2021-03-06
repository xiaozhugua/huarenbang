package com.abcs.sociax.t4.adapter;

import android.util.Log;
import com.abcs.sociax.api.Api;
import com.abcs.sociax.t4.android.ThinksnsAbscractActivity;
import com.abcs.sociax.t4.android.data.AppendWeibo;
import com.abcs.sociax.t4.android.db.DbHelperManager;
import com.abcs.sociax.t4.android.fragment.FragmentSociax;
import com.abcs.sociax.t4.exception.*;

import com.abcs.sociax.t4.model.ModelWeibo;
import com.thinksns.sociax.thinksnsbase.bean.ListData;
import com.thinksns.sociax.thinksnsbase.bean.SociaxItem;
import com.thinksns.sociax.thinksnsbase.exception.ApiException;
import com.thinksns.sociax.thinksnsbase.exception.DataInvalidException;
import com.thinksns.sociax.thinksnsbase.exception.ExceptionIllegalParameter;
import com.thinksns.sociax.thinksnsbase.exception.ListAreEmptyException;
import com.thinksns.sociax.thinksnsbase.utils.UnitSociax;

/**
 * 类说明： 推荐微博list与其他weibolist的区别在需要设置背景图片，利用Append内方法区分
 * @author wz
 * @date 2014-10-16
 * @version 1.0
 */

public class AdapterAllWeiboList extends AdapterWeiboList {
	protected AppendWeibo append;		// 数据映射
	protected Api.WeiboApi apiwebo;

	public FragmentSociax getFragment() {
		return fragment;
	}

	public void setFragment(FragmentSociax fragment) {
		this.fragment = fragment;
	}

	@Override
	public ModelWeibo getFirst() {
		return super.getFirst();
	}

	@Override
	public ModelWeibo getLast() {
		return super.getLast();
	}

	/**
	 * 获取最后一条的id
	 * 
	 * @return
	 */
	public int getMaxid() {
		if (getLast() == null)
			return 0;
		else
			Log.d(TAG, "刷新尾部的最大 id  " + getLast().getWeiboId());
		return getLast().getWeiboId();
	}

	@Override
	public ModelWeibo getItem(int position) {
		return super.getItem(position);
	}

	/**
	 * 从Activity生成的微博adapter
	 * @param context
	 * @param list
	 */
	public AdapterAllWeiboList(ThinksnsAbscractActivity context,
							   ListData<SociaxItem> list, int uid) {
		super(context, list, uid);
		append = new AppendWeibo(context, this);
	}

	/**
	 * 从fragment生成的微博adapter
	 * @param fragment
	 * @param list
	 */
	public AdapterAllWeiboList(FragmentSociax fragment,
			ListData<SociaxItem> list, int uid) {
		super(fragment, list, uid);
		append = new AppendWeibo(context, this);
		this.setFragment(fragment);
	}

	@Override
	public ListData<SociaxItem> refreshNew(int count)
			throws VerifyErrorException, ApiException, ListAreEmptyException,
			DataInvalidException {
		try {
			return getApiWeibo().publicTimeline(PAGE_COUNT, 0, httpListener);
		} catch (ExceptionIllegalParameter e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public ListData<SociaxItem> refreshFooter(SociaxItem obj)
			throws VerifyErrorException, ApiException, ListAreEmptyException,
			DataInvalidException {
		try {
			Log.d(TAG, "刷新尾部的最后一条id  " + getMaxid());
			//如果网络连接错误，获取本地数据
			if (!UnitSociax.isNetWorkON(context)) {
				return DbHelperManager.getInstance(context, ListData.DataType.ALL_WEIBO).getFooterData(10, getMaxid());
			}
			return getApiWeibo().publicTimeline(PAGE_COUNT, getMaxid(), httpListener);
		} catch (ExceptionIllegalParameter e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ListData<SociaxItem> refreshHeader(SociaxItem obj)
			throws VerifyErrorException, ApiException, ListAreEmptyException,
			DataInvalidException {
		return refreshNew(PAGE_COUNT);
	}

	/**
	 * 获取api
	 * 
	 * @return
	 */
	protected Api.WeiboApi getApiWeibo() {
		return thread.getApp().getWeiboApi();
	}

}
