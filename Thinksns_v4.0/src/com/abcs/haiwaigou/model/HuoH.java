package com.abcs.haiwaigou.model;

import com.abcs.haiwaigou.local.huohang.bean.DatasEntry;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/7/24.
 */

public class HuoH {

    /**
     * id : 10
     * goods_price : 29.17
     * original_price : 30.80
     * goods_id : 104864
     * store_id : 17
     * goods_name : 圆汤盒370ml(箱) 500个
     * store_logo :
     * goods_image : http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_38b2fa9e77d5e317d2bdcc3ca4d95a10.jpg
     * store_name : 亚洲外卖盒
     * country :
     */

    @SerializedName("id")
    public String id;
    @SerializedName("goods_price")
    public String goodsPrice;
    @SerializedName("original_price")
    public String originalPrice;
    @SerializedName("goods_id")
    public String goodsId;
    @SerializedName("store_id")
    public String storeId;
    @SerializedName("goods_name")
    public String goodsName;
    @SerializedName("store_logo")
    public String storeLogo;
    @SerializedName("goods_image")
    public String goodsImage;
    @SerializedName("store_name")
    public String storeName;
    @SerializedName("country")
    public String country;
    @SerializedName("store")
    public DatasEntry store;
}
