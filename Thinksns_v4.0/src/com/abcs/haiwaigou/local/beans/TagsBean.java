package com.abcs.haiwaigou.local.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/24.
 */

public class TagsBean implements Serializable {
    /**
     * id : 3
     * name : 无要求
     */

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
}
