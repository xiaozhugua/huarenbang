package com.abcs.haiwaigou.local.huohang.bean;

import com.abcs.haiwaigou.local.beans.ActivityArr2;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9. 新货行 右边商品信息
 */

public class HHItemRight {
    /**
     * state : 1
     * datas : [{"goods_id":"102960","goods_name":"太白粉","goods_en_name":"Staercke 25Kg/Sa","goods_jingle":"","goods_serial":"001","goods_image":"11_05419002458260283.jpg","goods_image_s":"11_05419002458260283_60.jpg","goods_price":"20.90","goods_storage":"9520","tax_rate":"b","tax_include":null,"details":0,"activity_arr":[]},{"goods_id":"102961","goods_name":"亚洲面粉","goods_en_name":"Asia Mehl   10x1Kg/Pa","goods_jingle":"","goods_serial":"002","goods_image":"11_05412748840706557.jpg","goods_image_s":"11_05412748840706557_60.jpg","goods_price":"7.70","goods_storage":"9182","tax_rate":"b","tax_include":null,"details":0,"activity_arr":[]},{"goods_id":"102979","goods_name":"西米","goods_en_name":"Tapioca Pearl  400g/Pa","goods_jingle":"","goods_serial":"021","goods_image":"11_05412746565255522.jpg","goods_image_s":"11_05412746565255522_60.jpg","goods_price":"1.09","goods_storage":"9975","tax_rate":"b","tax_include":null,"details":0,"activity_arr":[]},{"goods_id":"102980","goods_name":"水磨糯米粉","goods_en_name":"Klebreismehl blau  400g/Pa","goods_jingle":"","goods_serial":"022","goods_image":"11_05435474284729256.jpg","goods_image_s":"11_05435474284729256_60.jpg","goods_price":"1.15","goods_storage":"9775","tax_rate":"b","tax_include":null,"details":0,"activity_arr":[]},{"goods_id":"102981","goods_name":"水磨粘米粉","goods_en_name":"Reismehl rot   400g/Pa","goods_jingle":"","goods_serial":"023","goods_image":"11_05435337139599928.jpg","goods_image_s":"11_05435337139599928_60.jpg","goods_price":"1.15","goods_storage":"9986","tax_rate":"b","tax_include":null,"details":0,"activity_arr":[]},{"goods_id":"102989","goods_name":"(白袋)茉莉香米","goods_en_name":"Weiss Beutel JasminReis 18.18kg","goods_jingle":"","goods_serial":"101","goods_image":"11_05419020230274502.jpg","goods_image_s":"11_05419020230274502_60.jpg","goods_price":"23.64","goods_storage":"8524","tax_rate":"b","tax_include":null,"details":0,"activity_arr":{"1":{"name":"满10送2","logo":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_0e2b85735660eb2be725298fea7e914b.png"},"2":{"name":"满15送3","logo":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_0e2b85735660eb2be725298fea7e914b.png"}}},{"goods_id":"102990","goods_name":"黄袋龙凤香米","goods_en_name":"Jasmin Reis (Gelbsack) 18.18kg","goods_jingle":"","goods_serial":"102","goods_image":"11_05419021352681602.jpg","goods_image_s":"11_05419021352681602_60.jpg","goods_price":"27.30","goods_storage":"9706","tax_rate":"b","tax_include":null,"details":0,"activity_arr":{"1":{"name":"满10送1","logo":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_0e2b85735660eb2be725298fea7e914b.png"},"2":{"name":"满15送2","logo":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_0e2b85735660eb2be725298fea7e914b.png"}}},{"goods_id":"102991","goods_name":"三塔香米","goods_en_name":"Jasmin Reis in braun beutel  20kg","goods_jingle":"","goods_serial":"103","goods_image":"11_05419021944431437.jpg","goods_image_s":"11_05419021944431437_60.jpg","goods_price":"24.90","goods_storage":"9634","tax_rate":"b","tax_include":null,"details":0,"activity_arr":{"1":{"name":"满10送1","logo":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_0e2b85735660eb2be725298fea7e914b.png"},"2":{"name":"满15送2","logo":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_0e2b85735660eb2be725298fea7e914b.png"}}},{"goods_id":"102992","goods_name":"美国丝苗长米","goods_en_name":"Langekorn Reis  20kg/sack","goods_jingle":"","goods_serial":"104","goods_image":"11_05412744375028328.jpg","goods_image_s":"11_05412744375028328_60.jpg","goods_price":"20.00","goods_storage":"9986","tax_rate":"b","tax_include":null,"details":0,"activity_arr":[]},{"goods_id":"102993","goods_name":"泰国糯米","goods_en_name":"Klebreis  kg/Pa","goods_jingle":"","goods_serial":"105","goods_image":"11_05412744290722009.jpg","goods_image_s":"11_05412744290722009_60.jpg","goods_price":"2.29","goods_storage":"9992","tax_rate":"b","tax_include":null,"details":0,"activity_arr":[]},{"goods_id":"103021","goods_name":"黄豆","goods_en_name":"Soja-Bohen   Kg/Pa","goods_jingle":"","goods_serial":"300","goods_image":"11_05412741194196708.jpg","goods_image_s":"11_05412741194196708_60.jpg","goods_price":"1.89","goods_storage":"9953","tax_rate":"b","tax_include":null,"details":0,"activity_arr":[]},{"goods_id":"103022","goods_name":"绿豆","goods_en_name":"Gruene Bohne   Kg/Pa","goods_jingle":"","goods_serial":"301","goods_image":"11_05412741082182302.jpg","goods_image_s":"11_05412741082182302_60.jpg","goods_price":"1.36","goods_storage":"9925","tax_rate":"b","tax_include":null,"details":0,"activity_arr":[]},{"goods_id":"103030","goods_name":"黑芝麻","goods_en_name":"Schwarzer Sesam   454g/Pa","goods_jingle":"","goods_serial":"309","goods_image":"11_05412737928354759.jpg","goods_image_s":"11_05412737928354759_60.jpg","goods_price":"2.69","goods_storage":"9939","tax_rate":"b","tax_include":null,"details":0,"activity_arr":[]},{"goods_id":"103031","goods_name":"白芝麻","goods_en_name":"Weiser Sesam   454g/Pa","goods_jingle":"","goods_serial":"310","goods_image":"11_05412737854142905.jpg","goods_image_s":"11_05412737854142905_60.jpg","goods_price":"2.69","goods_storage":"9781","tax_rate":"b","tax_include":null,"details":0,"activity_arr":[]},{"goods_id":"103063","goods_name":"黑豆","goods_en_name":"Schwarz Bohnen 1paX1kg","goods_jingle":"","goods_serial":"391","goods_image":"11_05412731823809339.jpg","goods_image_s":"11_05412731823809339_60.jpg","goods_price":"3.00","goods_storage":"9762","tax_rate":"b","tax_include":null,"details":0,"activity_arr":[]},{"goods_id":"103641","goods_name":"寿司米","goods_en_name":"HAYANGM I Reis 9kg/sa","goods_jingle":"","goods_serial":"108","goods_image":"11_05432827926752030.jpg","goods_image_s":"11_05432827926752030_60.jpg","goods_price":"10.50","goods_storage":"9963","tax_rate":"b","tax_include":null,"details":0,"activity_arr":[]},{"goods_id":"103956","goods_name":"水磨糯米粉（箱）","goods_en_name":"Klebreismehl blau 400g x50pa/Knt","goods_jingle":"","goods_serial":"0222","goods_image":"11_05460377872649151.jpg","goods_image_s":"11_05460377872649151_60.jpg","goods_price":"57.50","goods_storage":"9974","tax_rate":"b","tax_include":null,"details":0,"activity_arr":[]}]
     */

    @SerializedName("state")
    public int state;
    @SerializedName("datas")
    public List<DatasBean> datas;

    public static class DatasBean {
        /**
         * goods_id : 102960
         * goods_name : 太白粉
         * goods_en_name : Staercke 25Kg/Sa
         * goods_advert :
         * goods_serial : 001
         * goods_image : 11_05419002458260283.jpg
         * goods_image_s : 11_05419002458260283_60.jpg
         * goods_price : 20.90
         * goods_storage : 9520
         * tax_rate : b
         * tax_include : null
         * details : 0
         * activity_arr : []
         */

        @SerializedName("goods_id")
        public String goodsId;
        @SerializedName("goods_name")
        public String goodsName;
        @SerializedName("goods_en_name")
        public String goodsEnName;
        @SerializedName("goods_advert")
        public String goodsJingle;
        @SerializedName("goods_serial")
        public String goodsSerial;
        @SerializedName("goods_image")
        public String goodsImage;
        @SerializedName("goods_image_s")
        public String goodsImageS;
        @SerializedName("goods_price")
        public double goodsPrice;
        @SerializedName("goods_storage")
        public String goodsStorage;
        @SerializedName("tax_rate")
        public String taxRate;
        @SerializedName("tax_include")
        public Object taxInclude;
        @SerializedName("activity_arr")
        public List<ActivityArr2> activityArr;

        //本地字段，用户recyclerview 购买数量
        public int numbers;

    }
}
