package com.abcs.haiwaigou.local.huohang.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 项目名称：com.abcs.haiwaigou.local.huohang.bean
 * 作者：zds
 * 时间：2017/6/2 10:08
 */

public class VipInfoEntry {
    /**
     * id : 10766
     * member_id : 11170
     * member_name : 006006006
     * total_vip_money : 200.00
     * level_id : 2
     * level_name : 精英金卡
     * credit : 0.00
     * add_time : 1492844723
     * end_time : 1524380723
     */

    @SerializedName("id")
    public String id;
    @SerializedName("member_id")
    public String memberId;
    @SerializedName("member_name")
    public String memberName;
    @SerializedName("total_vip_money")
    public String totalVipMoney;
    @SerializedName("level_id")
    public String levelId;
    @SerializedName("level_name")
    public String levelName;
    @SerializedName("credit")
    public String credit;
    @SerializedName("add_time")
    public String addTime;
    @SerializedName("end_time")
    public String endTime;
}
