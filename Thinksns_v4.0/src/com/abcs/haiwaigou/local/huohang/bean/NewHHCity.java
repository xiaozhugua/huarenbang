package com.abcs.haiwaigou.local.huohang.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */

public class NewHHCity {

    /**
     * state : 1
     * datas : [{"city_id":"41","catename_en":"Vienna","oss_url":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb/qyer_city_img1562.jpg","national_flag":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E5%A5%A5%E5%9C%B0%E5%88%A9%403x1494315408803.png","country_cn_name":"奥地利","cate_name":"维也纳"},{"city_id":"6547","catename_en":"Graz","oss_url":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb/qyer_city_img1570.jpg","national_flag":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E5%A5%A5%E5%9C%B0%E5%88%A9%403x1494315408803.png","country_cn_name":"奥地利","cate_name":"格拉茨"},{"city_id":"48","catename_en":"Budapest","oss_url":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb/qyer_city_img3859.jpg","national_flag":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hrb/img/df407191e9fe4c02a9cfa98f155c666a.jpg","country_cn_name":"匈牙利","cate_name":"布达佩斯"}]
     */

    @SerializedName("state")
    public int state;
    @SerializedName("datas")
    public List<DatasBean> datas;

    public static class DatasBean {
        /**
         * city_id : 41
         * catename_en : Vienna
         * oss_url : http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb/qyer_city_img1562.jpg
         * national_flag : http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E5%A5%A5%E5%9C%B0%E5%88%A9%403x1494315408803.png
         * country_cn_name : 奥地利
         * cate_name : 维也纳
         */

        @SerializedName("city_id")
        public String cityId;
        @SerializedName("catename_en")
        public String catenameEn;
        @SerializedName("oss_url")
        public String ossUrl;
        @SerializedName("national_flag")
        public String nationalFlag;
        @SerializedName("country_cn_name")
        public String countryCnName;
        @SerializedName("cate_name")
        public String cateName;
    }
}
