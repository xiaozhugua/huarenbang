package com.abcs.haiwaigou.local.beans;

/**
 * Created by Administrator on 2017/9/12.
 */

public class KeFuBean {

    public int type;
    public long time;
    public String context;

    public KeFuBean() {
    }

    public KeFuBean(int type, long time, String context) {
        this.type = type;
        this.time = time;
        this.context = context;
    }
}
