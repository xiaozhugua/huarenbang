package com.abcs.haiwaigou.local.huohang.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 项目名称：com.abcs.haiwaigou.local.huohang.bean
 * 作者：zds
 * 时间：2017/6/2 10:30
 */

public class InvInfoEntry {
    /**
     * inv_id : 182
     * member_id : 11170
     * inv_state : 1
     * inv_title : 个人
     * inv_content : 服务费
     * inv_company :
     * inv_code :
     * inv_reg_addr :
     * inv_reg_phone :
     * inv_reg_bname :
     * inv_reg_baccount :
     * inv_rec_name :
     * inv_rec_mobphone :
     * inv_rec_province :
     * inv_goto_addr :
     * content : 普通发票 个人 服务费
     */

    @SerializedName("inv_id")
    public String invId;
    @SerializedName("member_id")
    public String memberId;
    @SerializedName("inv_state")
    public String invState;
    @SerializedName("inv_title")
    public String invTitle;
    @SerializedName("inv_content")
    public String invContent;
    @SerializedName("inv_company")
    public String invCompany;
    @SerializedName("inv_code")
    public String invCode;
    @SerializedName("inv_reg_addr")
    public String invRegAddr;
    @SerializedName("inv_reg_phone")
    public String invRegPhone;
    @SerializedName("inv_reg_bname")
    public String invRegBname;
    @SerializedName("inv_reg_baccount")
    public String invRegBaccount;
    @SerializedName("inv_rec_name")
    public String invRecName;
    @SerializedName("inv_rec_mobphone")
    public String invRecMobphone;
    @SerializedName("inv_rec_province")
    public String invRecProvince;
    @SerializedName("inv_goto_addr")
    public String invGotoAddr;
    @SerializedName("content")
    public String content;
}
