package com.abcs.haiwaigou.local.huohang.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */

public class SelectStoreNew implements Serializable {


    @SerializedName("state")
    public int state;
    @SerializedName("datas")
    public List<DatasBean> datas;
    @SerializedName("goods_banner")
    public List<goodsBanners> goodsBanner;
    public static class goodsBanners implements Serializable {

        /**
         * id : 1
         * goods_banner : http://huohang.huaqiaobang.com/data/upload/mobile/special/s8/s8_facfea5e3e349af9dc80f83f1bc092cb.jpg
         * goods_id : 104173
         * ngc_id : 5
         * store_id : 11
         * city : 41
         * store_coin : €
         * store_new_logo : http://huohang.huaqiaobang.com/data/upload/shop/store_joinin/05560555170846129.png
         * store_name : 仟惠仁维也纳
         * store_goods_details : 1
         * store_background : http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_757f34f22e3fdaec86288988ecae649b.jpg
         * store_address : Althanstrasse 12/Tor15 1090 wien
         * store_phone : 068881665
         * store_des : 餐饮食品，鸡鸭鱼肉、海鲜蔬菜、米面干货、调味料、酒水及餐馆用具批发零售。
         * store_send_time : 9:00-23:00
         * activity : [{"id":"1","name":"满10送1；满20送3","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_588d42b4aa03ae1057ef41ecce32cd44.png","if_use":"1"},{"id":"2","name":"折扣商品9.5折起","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_7924d1e3ac243387355418f51a2aba2c.png","if_use":"1"},{"id":"3","name":"本周特惠","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_a507772ef4a61fa5aca66e378735b6de.png","if_use":"1"}]
         */

        @SerializedName("id")
        public String id;
        @SerializedName("goods_banner")
        public String goodsBanner;
        @SerializedName("goods_id")
        public String goodsId;
        @SerializedName("ngc_id")
        public String ngcId;
        @SerializedName("store_id")
        public String storeId;
        @SerializedName("city")
        public String city;
        @SerializedName("store_coin")
        public String storeCoin;
        @SerializedName("store_new_logo")
        public String storeNewLogo;
        @SerializedName("store_name")
        public String storeName;
        @SerializedName("store_goods_details")
        public int storeGoodsDetails;
        @SerializedName("store_background")
        public String storeBackground;
        @SerializedName("store_address")
        public String storeAddress;
        @SerializedName("store_phone")
        public String storePhone;
        @SerializedName("store_des")
        public String storeDes;
        @SerializedName("store_send_time")
        public String storeSendTime;
        @SerializedName("activity")
        public List<Acty> activity;

    }
    public static class DatasBean implements Serializable {

        /**
         * store_id : 11
         * store_name : 仟惠仁维也纳
         * store_img : http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_a1a57fdb54ffea73e6fd66f68a9a149d.png
         * store_coin : €
         * store_background : http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_757f34f22e3fdaec86288988ecae649b.jpg
         * store_new_img : http://huohang.huaqiaobang.com/data/upload/mobile/special/s8/s8_631297163dab2796afe59787d5d45aec.jpg
         * store_goods_details : 1
         * goods_count : 574
         * goods_sale_count : 2087
         * store_address : Althanstrasse 12/Tor15 1090 wien
         * store_phone : 068881665
         * store_des : 餐饮食品，鸡鸭鱼肉、海鲜蔬菜、米面干货、调味料、酒水及餐馆用具批发零售。
         * store_send_time : 9:00-23:00
         * store_new_logo : http://huohang.huaqiaobang.com/data/upload/shop/store_joinin/05560555170846129.png
         * hot_goods : [{"id":"3","goods_id":"103306","goods_name":"特价餐巾纸Servietten 40x40cm 1/4falt 2Lagig weiss 1400St/Knt","goods_price":"9.90","original_price":"10.50","descs":"","goods_image":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_60072502334610d6888083d77660e7f1.jpg","store_id":"11","goods_type":"1","ngc_id":"29"},{"id":"4","goods_id":"103408","goods_name":"半壳青口Gruenschalenmuschel 1Kg/Pa","goods_price":"6.49","original_price":"7.11","descs":"","goods_image":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_eb8b03dd51b4e9ed0096e100abd7dd36.jpg","store_id":"11","goods_type":"1","ngc_id":"5"},{"id":"5","goods_id":"104173","goods_name":"生姜片 买10+1，买15+2","goods_price":"2.20","original_price":"3.00","descs":"","goods_image":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_b6012473370cad1fcd1ea6bf8b44aff9.jpg","store_id":"11","goods_type":"3","ngc_id":"5"}]
         */

        @SerializedName("store_id")
        public String storeId;
        @SerializedName("store_name")
        public String storeName;
        @SerializedName("store_img")
        public String storeImg;
        @SerializedName("store_coin")
        public String storeCoin;
        @SerializedName("store_background")
        public String storeBackground;
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
        @SerializedName("hot_goods")
        public List<HotGoodsEntry> hotGoods;
    }

}
