package com.abcs.haiwaigou.local.huohang.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 项目名称：com.abcs.haiwaigou.local.huohang.bean
 * 作者：zds
 * 时间：2017/6/16 10:24
 */

public class SelectStore {

    /**
     * state : 1
     * datas : [{"id":"1","store_id":"11","store_name":"仟惠仁","store_img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_5b273d5d4b05da5985bf804df1750921.png","country":"[中国]","hot_goods":[{"id":"1","goods_id":"104177","goods_name":"无壳大虾31/40(箱)","goods_price":"81.90","original_price":"100.00","descs":"介绍","goods_image":"http://www.huaqiaobang.com/data/upload/mobile/special/s9/s8_93eef81b809095a2eb916b75209e7d60.png","store_id":"11"}]},{"id":"2","store_id":"13","store_name":"蔬菜行","store_img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_5b273d5d4b05da5985bf804df1750921.png","country":"","hot_goods":[{"id":"2","goods_id":"104177","goods_name":"无壳大虾31/40(箱)","goods_price":"81.90","original_price":"1111.00","descs":"介绍2","goods_image":"http://www.huaqiaobang.com/data/upload/mobile/special/s9/s8_93eef81b809095a2eb916b75209e7d60.png","store_id":"13"}]}]
     * adv : {"adv_img":"","adv_param":"","adv_type":""}
     */

    @SerializedName("state")
    public int state;
    @SerializedName("adv")
    public AdvEntry adv;
    @SerializedName("datas")
    public List<DatasEntry> datas;

    public static class AdvEntry {
        /**
         * adv_img :
         * adv_param :
         * adv_type :
         */

        @SerializedName("adv_img")
        public String advImg;
        @SerializedName("adv_param")
        public String advParam;
        @SerializedName("adv_type")
        public String advType;
    }

}
