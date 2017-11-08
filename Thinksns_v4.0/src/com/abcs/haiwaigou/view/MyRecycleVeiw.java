package com.abcs.haiwaigou.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/7/18.
 */

public class MyRecycleVeiw extends RecyclerView {
    public MyRecycleVeiw(Context context) {
        super(context);
    }

    public MyRecycleVeiw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleVeiw(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
