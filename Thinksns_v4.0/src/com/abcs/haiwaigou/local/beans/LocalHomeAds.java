package com.abcs.haiwaigou.local.beans;

import com.abcs.haiwaigou.model.GGAds;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

public class LocalHomeAds {

    /**
     * status : 1
     * msg : [[{"img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/A11499683420402.jpg","is_jump":0,"url":"https://japi.tuling.me/hrq/admin/getHtmlById?id=1293fdd5b95240a8bf325276ef87ca04"},{"img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E5%85%A5%E5%8F%A3%E8%82%89%E5%88%B6%E5%93%811502162022695.jpg","is_jump":0,"url":"https://japi.tuling.me/hrq/admin/getHtmlById?id=c5c8c77f3c1644e8831d86ae9dd14d00"},{"img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E5%85%A5%E5%8F%A3CWKB1502162453742.jpg","is_jump":0,"url":"https://japi.tuling.me/hrq/admin/getHtmlById?id=eddb434caaeb48d1991a948643785cda"},{"img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_c634828ba96fecc67fbebcf72c410373.jpg","is_jump":0,"url":"https://japi.tuling.me/hrq/admin/getHtmlById?id=7e4057bed7f34335ae788ff1fa3371bd"}],[{"img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/A31499737833611.jpg","is_jump":0,"url":"https://japi.tuling.me/hrq/admin/getHtmlById?id=694f1c1eea25415c87a04234c55610f9"},{"img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E7%BE%8E%E5%8D%8E1503487174501.jpg","is_jump":0,"url":"https://japi.tuling.me/hrq/admin/getHtmlById?id=96ad457640d44250ab1ce0c0a218de27"},{"img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/A41499738381794.jpg","is_jump":0,"url":"https://japi.tuling.me/hrq/admin/getHtmlById?id=f7e6d2a246834dd592398d46d28a1fd2"},{"img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/A51499738599916.jpg","is_jump":0,"url":"https://japi.tuling.me/hrq/admin/getHtmlById?id=5d19e51694be4ade9355dc896d10945d"}],[{"img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/A61499738921513.jpg","is_jump":0,"url":"https://japi.tuling.me/hrq/admin/getHtmlById?id=a89984817b604bee8fa2fcd1fbe61335"},{"img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/%E4%BF%AE%E7%94%B5%E8%84%911495598123238.jpg","is_jump":0,"url":"https://japi.tuling.me/hrq/admin/getHtmlById?id=16b1383390db4602aad185c5b79a2384"},{"img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/A81499740688772.jpg","is_jump":0,"url":"https://japi.tuling.me/hrq/admin/getHtmlById?id=67cbb3510ee549fbac43d5383ab2d5ca"},{"img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_84d098dc800e741b61df1d57425907ca.jpg","is_jump":0,"url":"https://japi.tuling.me/hrq/admin/getHtmlById?id=1d08911820954e69bc705a976d7ac3ba"}]]
     */

    @SerializedName("status")
    public int status;
    @SerializedName("msg")
    public List<List<GGAds>> msg;

}
