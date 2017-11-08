package com.abcs.haiwaigou.local.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/24.
 */

public class FiletersBean implements Serializable {
    /**
     * id : 1
     * name : Wien
     */

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
}
