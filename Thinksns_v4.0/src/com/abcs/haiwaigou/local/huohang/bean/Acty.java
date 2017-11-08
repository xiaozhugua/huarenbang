package com.abcs.haiwaigou.local.huohang.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/17.
 */

public class Acty implements Serializable {


    /**
     * id : 1
     * name : 满10送1；满20送3
     * store_id : 11
     * img : http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_0e2b85735660eb2be725298fea7e914b.png
     * if_use : 1
     */

    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("store_id")
    public String storeId;
    @SerializedName("img")
    public String img;
    @SerializedName("if_use")
    public String ifUse;
}