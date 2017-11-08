package com.abcs.huaqiaobang.model;

import com.google.gson.annotations.SerializedName;

/**
 * 项目名称：com.abcs.huaqiaobang.model
 * 作者：zds
 * 时间：2017/6/17 10:49
 */

public class UserLocal {

    /**
     * id : 2161
     * store_name : Tester
     * address_id : 3685
     * district_id : 1
     * district_name : 货行
     * member_id : 11218
     * member_name : 001001001
     * add_time : 1493103267
     * text : null
     * is_default : 1
     * native_store_id : null
     * native_store_name : null
     * native_country : null
     * store_member : null
     */

    @SerializedName("id")
    public String id;
    @SerializedName("store_name")
    public String storeName;
    @SerializedName("address_id")
    public String addressId;
    @SerializedName("district_id")
    public String districtId;
    @SerializedName("district_name")
    public String districtName;
    @SerializedName("member_id")
    public String memberId;
    @SerializedName("member_name")
    public String memberName;
    @SerializedName("add_time")
    public String addTime;
    @SerializedName("text")
    public Object text;
    @SerializedName("is_default")
    public String isDefault;
    @SerializedName("native_store_id")
    public String nativeStoreId;
    @SerializedName("native_store_name")
    public String nativeStoreName;
    @SerializedName("native_country")
    public String nativeCountry;
    @SerializedName("store_member")
    public String store_member;
}
