package com.abcs.haiwaigou.local.huohang.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 项目名称：com.abcs.haiwaigou.local.huohang.bean
 * 作者：zds
 * 时间：2017/6/2 10:09
 */

public class PayGoodsListEntry {
    @SerializedName("offline")
    public List<OfflineEntry> offline;

    public static class OfflineEntry {
        /**
         * goods_num : 2
         * goods_id : 104196
         * store_id : 11
         * gc_id : 1664
         * goods_name : 白菇
         * goods_price : 0.10
         * goods_en_name : Champignon 500g
         * tax_rate : b
         * goods_storage : 9999
         * store_name : 仟惠仁进出口贸易有限公司
         * goods_image : 11_05495608029467846.jpg
         * transport_id : 0
         * goods_freight : 0.00
         * goods_vat : 0
         * is_fcode : 0
         * bl_id : 0
         * g_orgin : 9
         * rule_id : 0
         */

        @SerializedName("goods_num")
        public String goodsNum;
        @SerializedName("goods_id")
        public String goodsId;
        @SerializedName("store_id")
        public String storeId;
        @SerializedName("gc_id")
        public String gcId;
        @SerializedName("goods_name")
        public String goodsName;
        @SerializedName("goods_price")
        public String goodsPrice;
        @SerializedName("goods_en_name")
        public String goodsEnName;
        @SerializedName("tax_rate")
        public String taxRate;
        @SerializedName("goods_storage")
        public String goodsStorage;
        @SerializedName("store_name")
        public String storeName;
        @SerializedName("goods_image")
        public String goodsImage;
        @SerializedName("transport_id")
        public String transportId;
        @SerializedName("goods_freight")
        public String goodsFreight;
        @SerializedName("goods_vat")
        public String goodsVat;
        @SerializedName("is_fcode")
        public String isFcode;
        @SerializedName("bl_id")
        public int blId;
        @SerializedName("g_orgin")
        public String gOrgin;
        @SerializedName("rule_id")
        public String ruleId;
    }

}
