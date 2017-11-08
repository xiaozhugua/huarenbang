package com.abcs.sociax.t4.android.gift;

import android.os.Bundle;

import com.abcs.sociax.component.CustomTitle;
import com.abcs.sociax.component.LeftAndRightTitle;
import com.abcs.sociax.listener.OnTouchListListener;
import com.abcs.sociax.t4.adapter.AdapterUserInfoGift;
import com.abcs.sociax.t4.android.ThinksnsAbscractActivity;
import com.abcs.sociax.t4.component.ListUserInfo;
import com.abcs.sociax.android.R;
import com.thinksns.sociax.thinksnsbase.bean.ListData;
import com.thinksns.sociax.thinksnsbase.bean.SociaxItem;

/**
 * 类说明： 用户礼物，需要传入intent int uid
 * 
 * @author Administrator
 * @date 2015-1-5
 * @version 1.0
 */
public class ActivityUserGift extends ThinksnsAbscractActivity {
	AdapterUserInfoGift adapter;
	ListUserInfo listView;
	ListData<SociaxItem> listGift;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		if (adapter == null) {
			listGift = new ListData<SociaxItem>();
			adapter= new AdapterUserInfoGift(this,
					listGift, 4, getIntent().getIntExtra("uid", 0));
			listView.setAdapter(adapter);
			listView.setDivierNull();
			listView.hideFooterView();
			adapter.loadInitData();
		} else {
			listView.setAdapter(adapter);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub

		listView = (ListUserInfo) findViewById(R.id.listView);
		
	}

	@Override
	public String getTitleCenter() {
		// TODO Auto-generated method stub
		return "礼物";
	}

	@Override
	protected CustomTitle setCustomTitle() {
		// TODO Auto-generated method stub
		return new LeftAndRightTitle(R.drawable.img_back, ActivityUserGift.this);
	}

	@Override
	public OnTouchListListener getListView() {
		// TODO Auto-generated method stub
		return listView;
	}

	@Override
	public void refreshHeader() {
		// TODO Auto-generated method stub
		adapter.doRefreshHeader();
	}

	@Override
	public void refreshList() {
		// TODO Auto-generated method stub
		adapter.doUpdataList();
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.activity_user_gift;
	}

}
