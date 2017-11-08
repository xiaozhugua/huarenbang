package com.abcs.haiwaigou.local.huohang.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 项目名称：com.abcs.haiwaigou.local.huohang.bean
 * 作者：zds
 * 时间：2017/6/2 10:08
 */

public class AddressInfoEntry {
    /**
     * address_id : 3699
     * member_id : 11170
     * true_name : 祝福
     * area_id : 1436
     * city_id : 97
     * area_info : 内蒙古 乌海市 海勃湾区
     * address : 内蒙古乌海市海波弯区
     * tel_phone :
     * tel_phone_res :
     * mob_phone : 15218749835
     * mob_phone_res :
     * postcode :
     * is_default : 0
     * dlyp_id : 0
     * id_card :
     * address_types : 0
     */

    @SerializedName("address_id")
    public String addressId;
    @SerializedName("member_id")
    public String memberId;
    @SerializedName("true_name")
    public String trueName;
    @SerializedName("area_id")
    public String areaId;
    @SerializedName("city_id")
    public String cityId;
    @SerializedName("area_info")
    public String areaInfo;
    @SerializedName("address")
    public String address;
    @SerializedName("tel_phone")
    public String telPhone;
    @SerializedName("tel_phone_res")
    public String telPhoneRes;
    @SerializedName("mob_phone")
    public String mobPhone;
    @SerializedName("mob_phone_res")
    public String mobPhoneRes;
    @SerializedName("postcode")
    public String postcode;
    @SerializedName("is_default")
    public String isDefault;
    @SerializedName("dlyp_id")
    public String dlypId;
    @SerializedName("id_card")
    public String idCard;
    @SerializedName("address_types")
    public String addressTypes;
}
