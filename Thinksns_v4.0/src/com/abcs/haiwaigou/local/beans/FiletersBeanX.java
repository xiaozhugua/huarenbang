package com.abcs.haiwaigou.local.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class FiletersBeanX  implements Serializable{
    /**
     * filter_name : 所在地区
     * fileters : [{"id":1,"name":"Wien"},{"id":2,"name":"Wien周边"}]
     */

    @SerializedName("filter_name")
    public String filterName;
    @SerializedName("fileters")
    public List<FiletersBean> fileters;
}
