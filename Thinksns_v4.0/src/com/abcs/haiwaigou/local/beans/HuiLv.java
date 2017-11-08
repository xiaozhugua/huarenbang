package com.abcs.haiwaigou.local.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */

public class HuiLv {

    /**
     * status : 1
     * msg : [{"id":7,"sort":10,"hb_name":"人民币¥","img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E4%B8%AD%E5%9B%BD1500543757540.png","hb_enname":"CNY","hl":"1.0000"},{"id":1,"sort":20,"hb_name":"欧元\u20ac","img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E6%AC%A7%E7%9B%9F1500543755940.png","hb_enname":"EUR","hl":"7.9720"},{"id":2,"sort":30,"hb_name":"英镑£","img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/GBP1500543757822.png","hb_enname":"GBP","hl":"8.8931"},{"id":3,"sort":40,"hb_name":"澳元$","img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E6%BE%B3%E5%A4%A7%E5%88%A9%E4%BA%9A1500543759260.png","hb_enname":"AUD","hl":"5.3373"},{"id":4,"sort":50,"hb_name":"日元￥","img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E6%97%A5%E6%9C%AC1500543756195.png","hb_enname":"JPY","hl":"0.0608"},{"id":6,"sort":60,"hb_name":"泰铢฿","img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E6%B3%B0%E5%9B%BD1500543756474.png","hb_enname":"THB","hl":"0.2022"}]
     */

    @SerializedName("status")
    public int status;
    @SerializedName("msg")
    public List<MsgBean> msg;

    public static class MsgBean {
        public MsgBean() {
        }

        /**
         * id : 7
         * sort : 10
         * hb_name : 人民币¥
         * img : http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E4%B8%AD%E5%9B%BD1500543757540.png
         * hb_enname : CNY
         * hl : 1.0000
         */

        @SerializedName("id")
        public int id;
        @SerializedName("sort")
        public int sort;
        @SerializedName("hb_name")
        public String hbName;
        @SerializedName("img")
        public String img;
        @SerializedName("hb_enname")
        public String hbEnname;
        @SerializedName("hl")
        public double hl;
        @SerializedName("relect")
        public double relect;
    }
}
