package com.abcs.haiwaigou.local.beans;

import com.abcs.haiwaigou.local.huohang.bean.SelectStoreNew;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.  全球货行banners
 */

public class HHBanner {

    /**
     * state : 1
     * datas : [{"id":"3","goods_banner":"http://huohang.huaqiaobang.com/data/upload/mobile/special/s8/s8_facfea5e3e349af9dc80f83f1bc092cb.jpg","goods_id":"","ngc_id":"5","store_id":"11","city":"41","banner_type":"1","store_coin":"\u20ac","store_new_logo":"http://huohang.huaqiaobang.com/data/upload/shop/store_joinin/05560555170846129.png","store_name":"仟惠仁维也纳","store_goods_details":1,"store_background":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_757f34f22e3fdaec86288988ecae649b.jpg","store_address":"Althanstrasse 12/Tor15 1090 wien","store_phone":"06649155568","store_des":"本公司成立于1990年，主要经营各类餐饮食品，鸡鸭鱼肉、新鲜蔬菜、米面干货、送客礼品以及餐馆用具批发零售。","store_send_time":"9:00-23:00","activity":[{"id":"1","name":"满10送1；满20送3","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_588d42b4aa03ae1057ef41ecce32cd44.png","if_use":"1"},{"id":"2","name":"折扣商品9.5折起","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_7924d1e3ac243387355418f51a2aba2c.png","if_use":"1"},{"id":"3","name":"本周特惠","store_id":"11","img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_a507772ef4a61fa5aca66e378735b6de.png","if_use":"1"}]}]
     */

    @SerializedName("state")
    public int state;
    @SerializedName("datas")
    public List<SelectStoreNew.goodsBanners> datas;
}
