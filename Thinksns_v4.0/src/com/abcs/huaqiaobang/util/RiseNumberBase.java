package com.abcs.huaqiaobang.util;

import com.abcs.huaqiaobang.view.RiseNumberTextView;

public interface RiseNumberBase {
	void start();

	RiseNumberTextView withNumber(double number);

	RiseNumberTextView withNumber(int number);

	RiseNumberTextView setDuration(long duration);

	void setOnEnd(RiseNumberTextView.EndListener callback);
}