package com.abcs.haiwaigou.local.huohang.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/25.
 */

/* 货行分类*/
public class HHClassFy {

    /**
     * state : 1
     * datas : [{"sclass_id":"1","sclass_name":"服装"},{"sclass_id":"2","sclass_name":"箱包"},{"sclass_id":"3","sclass_name":"鞋靴"},{"sclass_id":"4","sclass_name":"配饰"},{"sclass_id":"5","sclass_name":"内衣"},{"sclass_id":"6","sclass_name":"小商品"},{"sclass_id":"7","sclass_name":"生活百货"},{"sclass_id":"8","sclass_name":"运动户外"},{"sclass_id":"9","sclass_name":"童装"},{"sclass_id":"10","sclass_name":"玩具"},{"sclass_id":"11","sclass_name":"母婴用品"},{"sclass_id":"12","sclass_name":"电脑办公"},{"sclass_id":"13","sclass_name":"家装家纺"},{"sclass_id":"14","sclass_name":"外卖用品"},{"sclass_id":"71","sclass_name":"32323"}]
     */

    @SerializedName("state")
    public int state;
    @SerializedName("datas")
    public List<DatasBean> datas;

    public static class DatasBean {
        /**
         * sclass_id : 1
         * sclass_name : 服装
         */

        @SerializedName("sclass_id")
        public String sclassId;
        @SerializedName("sclass_name")
        public String sclassName;
    }
}
