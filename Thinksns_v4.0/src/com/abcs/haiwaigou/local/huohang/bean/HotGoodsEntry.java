package com.abcs.haiwaigou.local.huohang.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/29.
 */

public class HotGoodsEntry implements Serializable {
    /**
     * id : 1
     * goods_id : 104177
     * goods_name : 无壳大虾31/40(箱)
     * goods_price : 81.90
     * original_price : 100.00
     * descs : 介绍
     * goods_image : http://www.huaqiaobang.com/data/upload/mobile/special/s9/s8_93eef81b809095a2eb916b75209e7d60.png
     * store_id : 11
     */

    @SerializedName("id")
    public String id;
    @SerializedName("goods_id")
    public String goodsId;
    @SerializedName("goods_name")
    public String goodsName;
    @SerializedName("goods_price")
    public String goodsPrice;
    @SerializedName("original_price")
    public String originalPrice;
    @SerializedName("descs")
    public String descs;
    @SerializedName("goods_image")
    public String goodsImage;
    @SerializedName("store_id")
    public String store_id;
    @SerializedName("goods_type")
    public String goods_type;
    @SerializedName("ngc_id")
    public String ngc_id;
}
