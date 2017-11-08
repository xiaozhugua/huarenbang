package com.abcs.huaqiaobang.presenter;

import java.util.List;

/**
 * Created by zhou on 2016/4/19.
 */
public interface LoadDataInterFace<T> {

    void loadSuccess(List<T> mData);
    void loadFailed(String error);
}
