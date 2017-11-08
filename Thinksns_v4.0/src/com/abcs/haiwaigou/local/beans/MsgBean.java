package com.abcs.haiwaigou.local.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class MsgBean implements Serializable {

    @Override
    public String toString() {
        return "MsgBean{" +
                "tags=" + tags +
                ", fileter=" + fileter +
                '}';
    }

    @SerializedName("tags")
    public List<TagsBean> tags;
    @SerializedName("fileter")
    public List<FiletersBeanX> fileter;

}