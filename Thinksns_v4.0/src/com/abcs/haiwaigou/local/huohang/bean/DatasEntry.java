package com.abcs.haiwaigou.local.huohang.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class DatasEntry implements Serializable {

    /**
     * store_id : 11
     * store_name : 仟惠仁维也纳
     * store_img : http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_a1a57fdb54ffea73e6fd66f68a9a149d.png
     * store_coin : €
     * store_goods_details : 0
     * store_address : Althanstrasse 12/Tor15 1090 wien
     * store_phone : 068881665
     * store_des : 餐饮食品，鸡鸭鱼肉、海鲜蔬菜、米面干货、调味料、酒水及餐馆用具批发零售。
     * store_send_time : 9:00-23:00
     * store_new_logo : http://www.huaqiaobang.com/data/upload/shop/store_joinin/05374691872772467.png
     * hot_goods : [{"id":"3","goods_id":"103306","goods_name":"特价餐巾纸Servietten 40x40cm 1/4falt 2Lagig weiss 1400St/Knt","goods_price":"9.90","original_price":"10.50","descs":null,"goods_image":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_60072502334610d6888083d77660e7f1.jpg","store_id":"11"},{"id":"4","goods_id":"103408","goods_name":"半壳青口Gruenschalenmuschel 1Kg/Pa","goods_price":"6.49","original_price":"7.11","descs":null,"goods_image":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_eb8b03dd51b4e9ed0096e100abd7dd36.jpg","store_id":"11"},{"id":"5","goods_id":"104173","goods_name":"生姜片 买10+1，买15+2","goods_price":"2.20","original_price":"3.00","descs":null,"goods_image":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_b6012473370cad1fcd1ea6bf8b44aff9.jpg","store_id":"11"}]
     */

    @SerializedName("store_id")
    public String storeId;
    @SerializedName("store_name")
    public String storeName;
    @SerializedName("store_img")
    public String storeImg;
    @SerializedName("store_coin")
    public String storeCoin;
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
    @SerializedName("hot_goods")
    public List<HotGoodsEntry> hotGoods;
    @SerializedName("activity")
    public List<Acty> activity;

}
