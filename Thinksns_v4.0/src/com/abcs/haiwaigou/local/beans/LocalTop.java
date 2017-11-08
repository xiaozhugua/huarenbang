package com.abcs.haiwaigou.local.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

public class LocalTop {

    /**
     * status : 1
     * msg : {"countryId":198,"bannerTitle":{"sy_num":"5,239","hq_num":"23,816","hb":"EUR","hl":"792.93"},"subject":"s_hq_b_29","menu":[{"child":[{"id":1010,"menu_name":"招工"},{"id":1020,"menu_name":"个人找工作"}],"countryId":198,"img_url":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E6%8B%9B%E5%B7%A51500542106346.png","menu_id":10,"menu_name":"招工"},{"child":[{"id":2010,"menu_name":"生意转让"}],"countryId":198,"img_url":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E7%94%9F%E6%84%8F1500542105988.png","menu_id":20,"menu_name":"生意"},{"child":[{"id":3010,"menu_name":"出租求租"},{"id":3020,"menu_name":"卖房买房"}],"countryId":198,"img_url":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E6%88%BF%E5%B1%8B1500542107467.png","menu_id":30,"menu_name":"房屋"},{"child":[{"id":73,"menu_name":"吃喝"},{"id":74,"menu_name":"玩乐"},{"id":75,"menu_name":"购物"},{"id":76,"menu_name":"华人酒店"},{"id":77,"menu_name":"华人翻译"},{"id":78,"menu_name":"华人律师"}],"countryId":198,"img_url":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E7%94%9F%E6%B4%BB%403x1500889137517.png","menu_id":71,"menu_name":"生活"},{"child":[],"countryId":198,"img_url":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/txl%403x1502275795942.png","menu_id":72,"menu_name":"通讯录"}],"img":"http://files.heweather.com/cond_icon/100.png","temp":"21°","txt":"晴","banner":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/wyn1492852559824.jpg"}
     */

    @SerializedName("status")
    public int status;
    @SerializedName("msg")
    public MsgBean msg;

    public static class MsgBean {
        /**
         * countryId : 198
         * bannerTitle : {"sy_num":"5,239","hq_num":"23,816","hb":"EUR","hl":"792.93"}
         * subject : s_hq_b_29
         * menu : [{"child":[{"id":1010,"menu_name":"招工"},{"id":1020,"menu_name":"个人找工作"}],"countryId":198,"img_url":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E6%8B%9B%E5%B7%A51500542106346.png","menu_id":10,"menu_name":"招工"},{"child":[{"id":2010,"menu_name":"生意转让"}],"countryId":198,"img_url":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E7%94%9F%E6%84%8F1500542105988.png","menu_id":20,"menu_name":"生意"},{"child":[{"id":3010,"menu_name":"出租求租"},{"id":3020,"menu_name":"卖房买房"}],"countryId":198,"img_url":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E6%88%BF%E5%B1%8B1500542107467.png","menu_id":30,"menu_name":"房屋"},{"child":[{"id":73,"menu_name":"吃喝"},{"id":74,"menu_name":"玩乐"},{"id":75,"menu_name":"购物"},{"id":76,"menu_name":"华人酒店"},{"id":77,"menu_name":"华人翻译"},{"id":78,"menu_name":"华人律师"}],"countryId":198,"img_url":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E7%94%9F%E6%B4%BB%403x1500889137517.png","menu_id":71,"menu_name":"生活"},{"child":[],"countryId":198,"img_url":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/txl%403x1502275795942.png","menu_id":72,"menu_name":"通讯录"}]
         * img : http://files.heweather.com/cond_icon/100.png
         * temp : 21°
         * txt : 晴
         * banner : http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/wyn1492852559824.jpg
         */

        @SerializedName("countryId")
        public int countryId;
        @SerializedName("bannerTitle")
        public NewLocal.MsgBean.BannerTitleBean bannerTitle;
        @SerializedName("subject")
        public String subject;
        @SerializedName("img")
        public String img;
        @SerializedName("temp")
        public String temp;
        @SerializedName("txt")
        public String txt;
        @SerializedName("banner")
        public String banner;
        @SerializedName("menu")
        public List<NewLocal.MsgBean.MenuBean> menu;


    }
}
