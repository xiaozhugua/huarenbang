package com.abcs.haiwaigou.local.beans;

import com.abcs.haiwaigou.model.GGAds;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

public class NewsBean_N {


    /**
     * id : 16320881
     * summary :
     * adsList : [{"img":"http://www.huaqiaobang.com/data/upload/mobile/special/s8/s8_d35ef786fb8a9d6f2309d1da0294e068.jpg","is_jump":0,"url":"https://japi.tuling.me/hrq/admin/getHtmlById?id=1573666bb113470c8dee31ad665de1ae"},{"img":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/A111499741601438.jpg","is_jump":0,"url":"https://japi.tuling.me/hrq/admin/getHtmlById?id=a6db2e8d4367454c964e4170d60d8ddf"}]
     * title : 巴育:未和英拉就出境达成任何协议研究吊销其护照
     * time : 1503960240000
     * source : 网易国际新闻
     * species : country_international
     * purl : http://qhcdn.oss-cn-hangzhou.aliyuncs.com/uploadfile/spider/newscontent/1503968494310.png
     * timeStr :
     * type : 163_international_news
     */

    @SerializedName("id")
    public String id;
    @SerializedName("summary")
    public String summary;
    @SerializedName("title")
    public String title;
    @SerializedName("time")
    public long time;
    @SerializedName("source")
    public String source;
    @SerializedName("species")
    public String species;
    @SerializedName("purl")
    public String purl;
    @SerializedName("timeStr")
    public String timeStr;
    @SerializedName("type")
    public String type;
    @SerializedName("adsList")
    public List<GGAds> adsList;

}
