package com.abcs.haiwaigou.local.interfacer;

/**
 * Created by zjz on 2016/9/28.
 */

public interface LoadStateInterface {
    void LoadSuccess(String sucMsg);
    void LoadFailed(String faiMsg);
    void LoadEmpty(String empMsg);
    void isLoadMore(boolean isMore);
}
