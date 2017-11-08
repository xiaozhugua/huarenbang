package com.abcs.haiwaigou.local.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/8/1.
 */

public class YYGBena {

    /**
     * createTime : 1501161251
     * goodsId : 1132
     * result : 咪克玛卡Micmak马卡龙8粒礼盒装 法式糕点甜品 生日礼物
     * isDeliver : 0
     * sta : 0
     * hqbOrderId : -1
     * goods : {"picture":"shopimg/20151203/88121542112181.jpg","createTime":1501161251,"price":69,"description":"咪克玛卡Micmak马卡龙8粒礼盒装 法式糕点甜品 生日礼物","name":"咪克玛卡Micmak马卡龙8粒礼盒装 法式糕点甜品 生日礼物","uuid":1132,"isEntity":1,"hqbGoodsId":-1}
     * surplusValue : 6
     * unitPrice : 1
     * is_buy : false
     * substate : -1
     * defrayPrice : 63
     * surplusTime : -1
     * isPrizeWinner : 0
     * uuid : 1212
     * tatalPrice : 69
     * accomplishTime : -1
     * periodsNum : 42
     */

    @SerializedName("createTime")
    public long createTime;
    @SerializedName("goodsId")
    public int goodsId;
    @SerializedName("result")
    public String result;
    @SerializedName("isDeliver")
    public int isDeliver;
    @SerializedName("sta")
    public String sta;
    @SerializedName("hqbOrderId")
    public int hqbOrderId;
    @SerializedName("goods")
    public GoodsBean goods;
    @SerializedName("surplusValue")
    public int surplusValue;
    @SerializedName("unitPrice")
    public double unitPrice;
    @SerializedName("is_buy")
    public boolean isBuy;
    @SerializedName("substate")
    public int substate;
    @SerializedName("defrayPrice")
    public int defrayPrice;
    @SerializedName("surplusTime")
    public int surplusTime;
    @SerializedName("isPrizeWinner")
    public int isPrizeWinner;
    @SerializedName("uuid")
    public int uuid;
    @SerializedName("tatalPrice")
    public int tatalPrice;
    @SerializedName("accomplishTime")
    public int accomplishTime;
    @SerializedName("periodsNum")
    public int periodsNum;

    public static class GoodsBean {
        /**
         * picture : shopimg/20151203/88121542112181.jpg
         * createTime : 1501161251
         * price : 69
         * description : 咪克玛卡Micmak马卡龙8粒礼盒装 法式糕点甜品 生日礼物
         * name : 咪克玛卡Micmak马卡龙8粒礼盒装 法式糕点甜品 生日礼物
         * uuid : 1132
         * isEntity : 1
         * hqbGoodsId : -1
         */

        @SerializedName("picture")
        public String picture;
        @SerializedName("createTime")
        public int createTime;
        @SerializedName("price")
        public int price;
        @SerializedName("description")
        public String description;
        @SerializedName("name")
        public String name;
        @SerializedName("uuid")
        public int uuid;
        @SerializedName("isEntity")
        public int isEntity;
        @SerializedName("hqbGoodsId")
        public int hqbGoodsId;
    }
}
