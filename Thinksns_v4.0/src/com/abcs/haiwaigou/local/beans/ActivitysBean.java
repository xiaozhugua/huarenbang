package com.abcs.haiwaigou.local.beans;

import com.google.gson.annotations.SerializedName;

/**
 * 项目名称：com.abcs.haiwaigou.local.beans
 * 作者：zds
 * 时间：2017/3/21 12:01
 */

public class ActivitysBean {

    /**
     * jbf :
     * id : 1
     * title : 市政厅广场上的音乐电影节
     * tag : 免费,美食,歌剧,舞蹈,聚会
     * img : http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E6%B4%BB%E5%8A%A81(2)1495073066557.jpg
     * activity_time : 2017/6/30 - 2017/9/3
     * is_click : 0
     * date : 1504368000
     * ads : 市政厅
     * url :
     */

    @SerializedName("jbf")
    public String jbf;
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("tag")
    public String tag;
    @SerializedName("img")
    public String img;
    @SerializedName("activity_time")
    public String activityTime;
    @SerializedName("is_click")
    public int isClick;
    @SerializedName("date")
    public long date;
    @SerializedName("ads")
    public String ads;
    @SerializedName("url")
    public String url;
}
