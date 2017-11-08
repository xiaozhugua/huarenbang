package com.abcs.haiwaigou.local.huohang.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9.  新货行 左边分类
 */

public class HHItemLeft {

    /**
     * state : 1
     * datas : [{"class_id":"1","class_name":"新鲜水果","store_id":"11","class_type":"1"},{"class_id":"2","class_name":"蔬菜蛋类","store_id":"11","class_type":"1"},{"class_id":"3","class_name":"五谷杂粮","store_id":"11","class_type":"1"},{"class_id":"4","class_name":"精选肉类","store_id":"11","class_type":"1"},{"class_id":"5","class_name":"海鲜水产","store_id":"11","class_type":"1"},{"class_id":"6","class_name":"冷饮冻食","store_id":"11","class_type":"1"},{"class_id":"7","class_name":"休闲食品","store_id":"11","class_type":"1"},{"class_id":"30","class_name":"精美礼品","store_id":"11","class_type":"1"},{"class_id":"29","class_name":"生活日用","store_id":"11","class_type":"1"},{"class_id":"28","class_name":"外卖用品","store_id":"11","class_type":"1"},{"class_id":"27","class_name":"厨房烹饪","store_id":"11","class_type":"1"},{"class_id":"26","class_name":"茗茶饮品","store_id":"11","class_type":"1"},{"class_id":"25","class_name":"脱水食品","store_id":"11","class_type":"1"},{"class_id":"24","class_name":"中外名酒","store_id":"11","class_type":"1"},{"class_id":"23","class_name":"寿司食材","store_id":"11","class_type":"1"},{"class_id":"22","class_name":"粮油调味","store_id":"11","class_type":"1"},{"class_id":"21","class_name":"罐头腌制","store_id":"11","class_type":"1"},{"class_id":"31","class_name":"其他","store_id":"11","class_type":"1"}]
     */

    @SerializedName("state")
    public int state;
    @SerializedName("msg")
    public String msg;
    @SerializedName("datas")
    public List<DatasBean> datas;

    public static class DatasBean {

        /**
         * class_id : 2
         * class_name : 蔬菜蛋类
         * store_id : 11
         */

        @SerializedName("class_id")
        public String classId;
        @SerializedName("class_name")
        public String className;
        @SerializedName("store_id")
        public String storeId;

        public int item_num;
    }
}
