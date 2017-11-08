package com.abcs.haiwaigou.local.huohang.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 项目名称：com.abcs.haiwaigou.local.huohang.bean
 * 作者：zds
 * 时间：2017/6/2 10:09
 */

public class StoreCartListEntry {
    /**
     * goods_list : [{"cart_id":"30990","buyer_id":"11170","store_id":"11","store_name":"仟惠仁进出口贸易有限公司","goods_id":"103107","goods_name":"白醋","goods_price":"6.90","goods_num":"3","goods_image":"11_05412724228636681.jpg","bl_id":"0","g_orgin":"35","state":true,"storage_state":true,"goods_commonid":"103195","gc_id":"1533","gc_id_1":"1496","transport_id":"0","goods_freight":"0.00","goods_vat":"0","goods_storage":"9889","goods_storage_alarm":"10","is_fcode":"0","have_gift":"0","groupbuy_info":null,"xianshi_info":null,"rule_id":"0","jk_price":"6.90","hk_price":"6.90","bjk_price":"6.90","rank":"1,2,3,4","tax_rate":"b","goods_serial":"445","goods_en_name":"Essig 10L/kuebel","goods_state":"1","goods_total":"20.70","goods_image_url":"http://www.huaqiaobang.com/data/upload/shop/store/goods/11/11_05412724228636681_240.jpg"},{"cart_id":"30983","buyer_id":"11170","store_id":"11","store_name":"仟惠仁进出口贸易有限公司","goods_id":"103119","goods_name":"八角","goods_price":"8.90","goods_num":"4","goods_image":"11_05412722469156781.jpg","bl_id":"0","g_orgin":"35","state":true,"storage_state":true,"goods_commonid":"103207","gc_id":"1528","gc_id_1":"1496","transport_id":"0","goods_freight":"0.00","goods_vat":"0","goods_storage":"9969","goods_storage_alarm":"10","is_fcode":"0","have_gift":"0","groupbuy_info":null,"xianshi_info":null,"rule_id":"0","jk_price":"8.90","hk_price":"8.90","bjk_price":"8.90","rank":"1,2,3,4","tax_rate":"b","goods_serial":"458","goods_en_name":"Gewuerze 500g/pa","goods_state":"1","goods_total":"35.60","goods_image_url":"http://www.huaqiaobang.com/data/upload/shop/store/goods/11/11_05412722469156781_240.jpg"},{"cart_id":"30988","buyer_id":"11170","store_id":"11","store_name":"仟惠仁进出口贸易有限公司","goods_id":"102989","goods_name":"(白袋)茉莉香米","goods_price":"23.64","goods_num":"24","goods_image":"11_05419020230274502.jpg","bl_id":"0","g_orgin":"35","state":true,"storage_state":true,"goods_commonid":"103077","gc_id":"1483","gc_id_1":"1480","transport_id":"0","goods_freight":"0.00","goods_vat":"0","goods_storage":"8838","goods_storage_alarm":"10","is_fcode":"0","have_gift":"0","groupbuy_info":null,"xianshi_info":null,"rule_id":"0","jk_price":"23.64","hk_price":"23.64","bjk_price":"23.64","rank":"1,2,3,4","tax_rate":"b","goods_serial":"101","goods_en_name":"Weiss Beutel JasminReis 18.18kg","goods_state":"1","goods_total":"567.36","goods_image_url":"http://www.huaqiaobang.com/data/upload/shop/store/goods/11/11_05419020230274502_240.jpg"}]
     * store_id : 11
     * store_name : store_name
     * tax_arr : {"netto_a":"0.00","netto_b":"623.66","mwst_a":"0.00","mwst_b":"62.37","brutto_a":"0","brutto_b":"686.03"}
     * activity_arr : [{"img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_0e2b85735660eb2be725298fea7e914b.png","activity_name":"满20送5","activity_type":"1"},{"types":"2","activity_name":"每单赠送鱼泉榨菜","activity_type":"2","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_6c8b40686eccd6443ad5cb8a570b4c7f.png","value_1":"每单赠送鱼泉榨菜","value_2":"8","goods_id":"0"}]
     * native_rule_money : 400
     * native_rule_list : [{"rg_id":"1","goods_id":"102977","goods_num":"4","title":"地瓜粉丝4包","money":"400.00"},{"rg_id":"2","goods_id":"103626","goods_num":"4","title":"刀切馒头4包","money":"400.00"},{"rg_id":"3","goods_id":"102974","goods_num":"3","title":"江门排粉3包","money":"400.00"},{"rg_id":"4","goods_id":"102983","goods_num":"3","title":"白果干3包","money":"400.00"},{"rg_id":"5","goods_id":"103634","goods_num":"1","title":"小春卷一箱","money":"400.00"},{"rg_id":"6","goods_id":"103035","goods_num":"1","title":"松花蛋/皮蛋1盒","money":"400.00"}]
     * native_rule_message : 200+ 任选其一：
     地瓜粉丝2包、刀切馒头2包、江门排粉1包、白果干1包、松花蛋/皮蛋1盒、特价小春卷半箱。

     400+ 任选其一：
     瓜粉丝4包、刀切馒头4包、江门排粉3包、白果干3包、松花蛋/皮蛋1盒、小春卷一箱。

     700+ 任选其一：
     带鱼1包、雪菜（桶装）2.5kg、海带丝1包1kg、海带片1包1kg、松花蛋/皮蛋2盒。

     1000+ 任选其一：
     小黄鱼1包、康师傅方便面1箱、青田面1包4.5kg、小龙虾2盒、大红枣1大包。
     */

    @SerializedName("store_id")
    public int storeId;
    @SerializedName("store_name")
    public String store_name;
    @SerializedName("tax_arr")
    public TaxArrEntry taxArr;
    @SerializedName("native_rule_money")
    public String nativeRuleMoney;
    @SerializedName("native_rule_message")
    public String nativeRuleMessage;
    @SerializedName("goods_list")
    public List<GoodsListEntry> goodsList;
    @SerializedName("activity_arr")
    public List<ActivityArrEntry> activityArr;
    @SerializedName("native_rule_list")
    public List<NativeRuleListEntry> nativeRuleList;

    public static class TaxArrEntry {
        /**
         * netto_a : 0.00
         * netto_b : 623.66
         * mwst_a : 0.00
         * mwst_b : 62.37
         * brutto_a : 0
         * brutto_b : 686.03
         */

        @SerializedName("netto_a")
        public double nettoA;
        @SerializedName("netto_b")
        public double nettoB;
        @SerializedName("mwst_a")
        public double mwstA;
        @SerializedName("mwst_b")
        public double mwstB;
        @SerializedName("brutto_a")
        public double bruttoA;
        @SerializedName("brutto_b")
        public double bruttoB;
    }

    public static class GoodsListEntry {
        /**
         * cart_id : 30990
         * buyer_id : 11170
         * store_id : 11
         * store_name : 仟惠仁进出口贸易有限公司
         * goods_id : 103107
         * goods_name : 白醋
         * goods_price : 6.90
         * goods_num : 3
         * goods_image : 11_05412724228636681.jpg
         * bl_id : 0
         * g_orgin : 35
         * state : true
         * storage_state : true
         * goods_commonid : 103195
         * gc_id : 1533
         * gc_id_1 : 1496
         * transport_id : 0
         * goods_freight : 0.00
         * goods_vat : 0
         * goods_storage : 9889
         * goods_storage_alarm : 10
         * is_fcode : 0
         * have_gift : 0
         * groupbuy_info : null
         * xianshi_info : null
         * rule_id : 0
         * jk_price : 6.90
         * hk_price : 6.90
         * bjk_price : 6.90
         * rank : 1,2,3,4
         * tax_rate : b
         * goods_serial : 445
         * goods_en_name : Essig 10L/kuebel
         * goods_state : 1
         * goods_total : 20.70
         * goods_image_url : http://www.huaqiaobang.com/data/upload/shop/store/goods/11/11_05412724228636681_240.jpg
         */

        @SerializedName("cart_id")
        public String cartId;
        @SerializedName("buyer_id")
        public String buyerId;
        @SerializedName("store_id")
        public String storeId;
        @SerializedName("store_name")
        public String storeName;
        @SerializedName("goods_id")
        public String goodsId;
        @SerializedName("goods_name")
        public String goodsName;
        @SerializedName("goods_price")
        public double goodsPrice;
        @SerializedName("goods_num")
        public String goodsNum;
        @SerializedName("goods_image")
        public String goodsImage;
        @SerializedName("bl_id")
        public String blId;
        @SerializedName("g_orgin")
        public String gOrgin;
        @SerializedName("state")
        public boolean state;
        @SerializedName("storage_state")
        public boolean storageState;
        @SerializedName("goods_commonid")
        public String goodsCommonid;
        @SerializedName("gc_id")
        public String gcId;
        @SerializedName("gc_id_1")
        public String gcId1;
        @SerializedName("transport_id")
        public String transportId;
        @SerializedName("goods_freight")
        public String goodsFreight;
        @SerializedName("goods_vat")
        public String goodsVat;
        @SerializedName("goods_storage")
        public String goodsStorage;
        @SerializedName("goods_storage_alarm")
        public String goodsStorageAlarm;
        @SerializedName("is_fcode")
        public String isFcode;
        @SerializedName("have_gift")
        public String haveGift;
        @SerializedName("groupbuy_info")
        public Object groupbuyInfo;
        @SerializedName("xianshi_info")
        public Object xianshiInfo;
        @SerializedName("rule_id")
        public String ruleId;
        @SerializedName("jk_price")
        public String jkPrice;
        @SerializedName("hk_price")
        public String hkPrice;
        @SerializedName("bjk_price")
        public String bjkPrice;
        @SerializedName("rank")
        public String rank;
        @SerializedName("tax_rate")
        public String taxRate;
        @SerializedName("goods_serial")
        public String goodsSerial;
        @SerializedName("goods_en_name")
        public String goodsEnName;
        @SerializedName("goods_state")
        public String goodsState;
        @SerializedName("goods_total")
        public String goodsTotal;
        @SerializedName("goods_image_url")
        public String goodsImageUrl;
    }

    public static class ActivityArrEntry {
        /**
         * img : http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_0e2b85735660eb2be725298fea7e914b.png
         * activity_name : 满20送5
         * activity_type : 1
         * types : 2
         * value_1 : 每单赠送鱼泉榨菜
         * value_2 : 8
         * goods_id : 0
         */

        @SerializedName("img")
        public String img;
        @SerializedName("activity_name")
        public String activityName;
        @SerializedName("activity_type")
        public String activityType;
        @SerializedName("types")
        public String types;
        @SerializedName("value_1")
        public String value1;
        @SerializedName("value_2")
        public String value2;
        @SerializedName("goods_id")
        public String goodsId;
    }

    public static class NativeRuleListEntry {
        /**
         * rg_id : 1
         * goods_id : 102977
         * goods_num : 4
         * title : 地瓜粉丝4包
         * money : 400.00
         */

        @SerializedName("rg_id")
        public int rgId;
        @SerializedName("goods_id")
        public String goodsId;
        @SerializedName("goods_num")
        public String goodsNum;
        @SerializedName("title")
        public String title;
        @SerializedName("money")
        public String money;
    }
}
