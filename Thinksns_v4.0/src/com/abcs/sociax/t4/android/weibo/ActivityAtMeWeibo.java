package com.abcs.sociax.t4.android.weibo;

import android.os.Bundle;

import com.abcs.sociax.component.CustomTitle;
import com.abcs.sociax.component.LeftAndRightTitle;
import com.abcs.sociax.t4.android.ThinksnsAbscractActivity;
import com.abcs.sociax.t4.android.fragment.FragmentAtMeWeibo;
import com.abcs.sociax.android.R;

/**
 * 类说明：@我的
 * 
 * @author wz
 * @date 2014-10-17
 * @version 1.0
 */
public class ActivityAtMeWeibo extends ThinksnsAbscractActivity {
	FragmentAtMeWeibo fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragment = new FragmentAtMeWeibo();
		fragmentTransaction.add(R.id.ll_content, fragment);
		fragmentTransaction.commit();
	}

	@Override
	public String getTitleCenter() {
		return "提到我的";
	}

	@Override
	protected CustomTitle setCustomTitle() {
		return new LeftAndRightTitle(R.drawable.img_back, this);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_common;
	}


}
