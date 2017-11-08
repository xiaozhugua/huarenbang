package com.abcs.sociax.listener;

import android.view.View;

public interface OnTouchListListener {
	void headerShow();

	void headerHiden();

	void headerRefresh();

	long getLastRefresh();

	void setLastRefresh(long lastRefresh);

	void footerShow();

	void footerHiden();

	View hideFooterView();

	View showFooterView();
}
