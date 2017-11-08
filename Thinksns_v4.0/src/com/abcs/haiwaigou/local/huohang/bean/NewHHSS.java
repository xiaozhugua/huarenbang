package com.abcs.haiwaigou.local.huohang.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

public class NewHHSS {

    /**
     * state : 1
     * datas : {"store":[{"store_id":"11","store_name":"仟惠仁维也纳","store_background":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_757f34f22e3fdaec86288988ecae649b.jpg","store_coin":"\u20ac","store_new_img":"http://huohang.huaqiaobang.com/data/upload/mobile/special/s8/s8_631297163dab2796afe59787d5d45aec.jpg","store_goods_details":1,"goods_count":574,"goods_sale_count":139,"store_address":"Althanstrasse 12/Tor15 1090 wien","store_phone":"06649155568","store_des":"本公司成立于1990年，主要经营各类餐饮食品，鸡鸭鱼肉、新鲜蔬菜、米面干货、送客礼品以及餐馆用具批发零售。","store_send_time":"9:00-23:00","store_new_logo":"http://huohang.huaqiaobang.com/data/upload/shop/store_joinin/05560555170846129.png","activity":[{"id":"1","name":"满10送1；满20送3","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_588d42b4aa03ae1057ef41ecce32cd44.png","if_use":"1"},{"id":"2","name":"折扣商品9.5折起","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_7924d1e3ac243387355418f51a2aba2c.png","if_use":"1"},{"id":"3","name":"本周特惠","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_a507772ef4a61fa5aca66e378735b6de.png","if_use":"1"}]},{"store_id":"16","store_name":"仟惠仁格拉茨","store_background":"","store_coin":"\u20ac","store_new_img":"http://huohang.huaqiaobang.com/data/upload/mobile/special/s8/s8_631297163dab2796afe59787d5d45aec.jpg","store_goods_details":1,"goods_count":569,"goods_sale_count":139,"store_address":"QHRG","store_phone":"06508568816","store_des":"本公司成立于1990年，主要经营各类餐饮食品，鸡鸭鱼肉、新鲜蔬菜、米面干货、送客礼品以及餐馆用具批发零售。","store_send_time":"9:00-23:00","store_new_logo":"http://huohang.huaqiaobang.com/data/upload/shop/store_joinin/05566482750682870.png","activity":[{"id":"7","name":"满10送1；满20送3","store_id":"16","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_0e2b85735660eb2be725298fea7e914b.png","if_use":"1"},{"id":"8","name":"折扣商品9.5折起","store_id":"16","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_5927e6837f8f747879aedede9fc514ad.png","if_use":"1"},{"id":"9","name":"本周特惠","store_id":"16","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_333332de5e62e18c510981fd845a9270.png","if_use":"1"}]}],"goods":[{"goods_id":"102963","goods_name":"仟惠仁蛋面","ngc_id":"22","store_id":"11","store_name":"仟惠仁维也纳","store_new_img":"http://huohang.huaqiaobang.com/data/upload/mobile/special/s8/s8_631297163dab2796afe59787d5d45aec.jpg","store_coin":"\u20ac","store_background":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_757f34f22e3fdaec86288988ecae649b.jpg","store_goods_details":1,"store_address":"Althanstrasse 12/Tor15 1090 wien","store_phone":"06649155568","store_des":"本公司成立于1990年，主要经营各类餐饮食品，鸡鸭鱼肉、新鲜蔬菜、米面干货、送客礼品以及餐馆用具批发零售。","store_send_time":"9:00-23:00","store_new_logo":"http://huohang.huaqiaobang.com/data/upload/shop/store_joinin/05560555170846129.png","activity":[{"id":"1","name":"满10送1；满20送3","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_588d42b4aa03ae1057ef41ecce32cd44.png","if_use":"1"},{"id":"2","name":"折扣商品9.5折起","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_7924d1e3ac243387355418f51a2aba2c.png","if_use":"1"},{"id":"3","name":"本周特惠","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_a507772ef4a61fa5aca66e378735b6de.png","if_use":"1"}]}]}
     * msg : 查询成功
     */

    @SerializedName("state")
    public int state;
    @SerializedName("datas")
    public DatasBean datas;
    @SerializedName("msg")
    public String msg;

    public static class DatasBean  {
        @SerializedName("store")
        public List<StoreBean> store;
        @SerializedName("goods")
        public List<GoodsBean> goods;

        public static class StoreBean {
            /**
             * store_id : 11
             * store_name : 仟惠仁维也纳
             * store_background : http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_757f34f22e3fdaec86288988ecae649b.jpg
             * store_coin : €
             * store_new_img : http://huohang.huaqiaobang.com/data/upload/mobile/special/s8/s8_631297163dab2796afe59787d5d45aec.jpg
             * store_goods_details : 1
             * goods_count : 574
             * goods_sale_count : 139
             * store_address : Althanstrasse 12/Tor15 1090 wien
             * store_phone : 06649155568
             * store_des : 本公司成立于1990年，主要经营各类餐饮食品，鸡鸭鱼肉、新鲜蔬菜、米面干货、送客礼品以及餐馆用具批发零售。
             * store_send_time : 9:00-23:00
             * store_new_logo : http://huohang.huaqiaobang.com/data/upload/shop/store_joinin/05560555170846129.png
             * activity : [{"id":"1","name":"满10送1；满20送3","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_588d42b4aa03ae1057ef41ecce32cd44.png","if_use":"1"},{"id":"2","name":"折扣商品9.5折起","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_7924d1e3ac243387355418f51a2aba2c.png","if_use":"1"},{"id":"3","name":"本周特惠","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_a507772ef4a61fa5aca66e378735b6de.png","if_use":"1"}]
             */

            @SerializedName("store_id")
            public String storeId;
            @SerializedName("store_name")
            public String storeName;
            @SerializedName("store_background")
            public String storeBackground;
            @SerializedName("store_coin")
            public String storeCoin;
            @SerializedName("store_new_img")
            public String storeNewImg;
            @SerializedName("store_goods_details")
            public int storeGoodsDetails;
            @SerializedName("goods_count")
            public int goodsCount;
            @SerializedName("goods_sale_count")
            public int goodsSaleCount;
            @SerializedName("store_address")
            public String storeAddress;
            @SerializedName("store_phone")
            public String storePhone;
            @SerializedName("store_des")
            public String storeDes;
            @SerializedName("store_send_time")
            public String storeSendTime;
            @SerializedName("store_new_logo")
            public String storeNewLogo;
            @SerializedName("activity")
            public List<Acty> activity;

        }

        public static class GoodsBean {
            /**
             * goods_id : 102963
             * goods_name : 仟惠仁蛋面
             * ngc_id : 22
             * store_id : 11
             * store_name : 仟惠仁维也纳
             * store_new_img : http://huohang.huaqiaobang.com/data/upload/mobile/special/s8/s8_631297163dab2796afe59787d5d45aec.jpg
             * store_coin : €
             * store_background : http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_757f34f22e3fdaec86288988ecae649b.jpg
             * store_goods_details : 1
             * store_address : Althanstrasse 12/Tor15 1090 wien
             * store_phone : 06649155568
             * store_des : 本公司成立于1990年，主要经营各类餐饮食品，鸡鸭鱼肉、新鲜蔬菜、米面干货、送客礼品以及餐馆用具批发零售。
             * store_send_time : 9:00-23:00
             * store_new_logo : http://huohang.huaqiaobang.com/data/upload/shop/store_joinin/05560555170846129.png
             * activity : [{"id":"1","name":"满10送1；满20送3","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_588d42b4aa03ae1057ef41ecce32cd44.png","if_use":"1"},{"id":"2","name":"折扣商品9.5折起","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_7924d1e3ac243387355418f51a2aba2c.png","if_use":"1"},{"id":"3","name":"本周特惠","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_a507772ef4a61fa5aca66e378735b6de.png","if_use":"1"}]
             */

            @SerializedName("goods_id")
            public String goodsId;
            @SerializedName("goods_name")
            public String goodsName;
            @SerializedName("ngc_id")
            public String ngcId;
            @SerializedName("store_id")
            public String storeId;
            @SerializedName("store_name")
            public String storeName;
            @SerializedName("store_new_img")
            public String storeNewImg;
            @SerializedName("store_coin")
            public String storeCoin;
            @SerializedName("store_background")
            public String storeBackground;
            @SerializedName("store_goods_details")
            public int storeGoodsDetails;
            @SerializedName("store_address")
            public String storeAddress;
            @SerializedName("store_phone")
            public String storePhone;
            @SerializedName("store_des")
            public String storeDes;
            @SerializedName("store_send_time")
            public String storeSendTime;
            @SerializedName("store_new_logo")
            public String storeNewLogo;
            @SerializedName("activity")
            public List<Acty> activity;

        }
    }
}
