package com.abcs.haiwaigou.local.huohang.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/9/9.
 */

public class HHODetialsAds {

    /**
     * state : 1
     * datas : {"left_time":255678,"reciver_name":"梁锦华","phone":"13432391385","address":"奥地利 维也纳 大区五十五号"}
     */

    @SerializedName("state")
    public int state;
    @SerializedName("datas")
    public DatasBean datas;

    public static class DatasBean {
        /**
         * left_time : 255678
         * reciver_name : 梁锦华
         * phone : 13432391385
         * address : 奥地利 维也纳 大区五十五号
         */

        @SerializedName("left_time")
        public int leftTime;
        @SerializedName("reciver_name")
        public String reciverName;
        @SerializedName("phone")
        public String phone;
        @SerializedName("address")
        public String address;
    }
}
