package com.abcs.haiwaigou.local.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

public class LocalNews {

    /**
     * status : 1
     * msg : [{"classTitle":"今日资讯","news":[{"id":"16101497","time":"1503720671000","title":"【国际】美国一架＂黑鹰＂军用直升机于也门坠毁致1人失踪","source":"网易国际新闻","species":"country_international","timeStr":"","purl":"http://cms-bucket.nosdn.127.net/900fb04a0f3c44949c153a7d09b1146f20170826121054.png"},{"id":"16101339","time":"1503713340000","title":"【国内】解放军入澳救灾：\u201c最可爱的人\u201d因何戳中泪点","source":"网易国内新闻","species":"country_home","timeStr":"","purl":"http://cms-bucket.nosdn.127.net/9153e0fdb22a4fc2bdd0afbed64a057c20170826142138.png"},{"id":"16091392","time":"1503628320000","title":"【侨乡】三明市首家数字化预防接种门诊启用","source":"三明侨乡网","species":"country_hometown","timeStr":"","purl":"http://img.smnet.com.cn/a/thumb/10001/201708/9c578b8693a5561ee48d1b41abd134ac.jpg@w400_h300.jpg"},{"id":"16091458","time":"1503674465000","title":"【侨讯】《华文教育研究合作备忘录》在马可·波罗家乡签署","source":"欧洲时报-侨界","species":"country_htinfo","timeStr":"","purl":"http://www.oushinet.com/r/cms/www/tpl_oushinet/images/bplay.png"},{"id":"16091575","time":"1503676800000","title":"【健康】女性安全期是什么时候？","source":"39健康网","species":"country_health","timeStr":"","purl":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/uploadfile/spider/newscontent/1503720373546.jpg"}],"lastId":"16012392","lastTime":"1503575411000"},{"classTitle":"生活资讯","news":[{"id":"16101646","time":"1503730849000","title":"老年人秋季养生保健常识","source":"风格教练","species":"newspaper","timeStr":"","purl":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/uploadfile/tljr/my_news/content/1503731013785_1.webp.jpg"},{"id":"16100651","time":"1503729620000","title":"暑假余额已不足，孩子作业还没写完，怎么办？","source":"华人邦","species":"newspaper","timeStr":"","purl":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/uploadfile/tljr/my_news/content/1503730051655_1.webp (1).jpg"}],"lastTime":"1501666869000","lastId":"14198609"},{"classTitle":"段子手","news":[{"id":"16102708","time":"1503732052000","title":"北京生活，处处都是段子","source":"北京生活播报","species":"jokes_hand","timeStr":"","purl":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/uploadfile/tljr/my_news/content/1503732100330_1.webp.jpg"},{"id":"16087650","time":"1503712682000","title":"这些段子到处有，知道它们的原作者之后惊呆了！","source":"当当","species":"jokes_hand","timeStr":"","purl":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/uploadfile/tljr/my_news/content/1503712681971_1.jpeg"}],"lastTime":"1492447409000","lastId":"5581476"}]
     */

    @SerializedName("status")
    public int status;
    @SerializedName("msg")
    public ArrayList<MsgBean> msg;

    public static class MsgBean {
        /**
         * classTitle : 今日资讯
         * news : [{"id":"16101497","time":"1503720671000","title":"【国际】美国一架＂黑鹰＂军用直升机于也门坠毁致1人失踪","source":"网易国际新闻","species":"country_international","timeStr":"","purl":"http://cms-bucket.nosdn.127.net/900fb04a0f3c44949c153a7d09b1146f20170826121054.png"},{"id":"16101339","time":"1503713340000","title":"【国内】解放军入澳救灾：\u201c最可爱的人\u201d因何戳中泪点","source":"网易国内新闻","species":"country_home","timeStr":"","purl":"http://cms-bucket.nosdn.127.net/9153e0fdb22a4fc2bdd0afbed64a057c20170826142138.png"},{"id":"16091392","time":"1503628320000","title":"【侨乡】三明市首家数字化预防接种门诊启用","source":"三明侨乡网","species":"country_hometown","timeStr":"","purl":"http://img.smnet.com.cn/a/thumb/10001/201708/9c578b8693a5561ee48d1b41abd134ac.jpg@w400_h300.jpg"},{"id":"16091458","time":"1503674465000","title":"【侨讯】《华文教育研究合作备忘录》在马可·波罗家乡签署","source":"欧洲时报-侨界","species":"country_htinfo","timeStr":"","purl":"http://www.oushinet.com/r/cms/www/tpl_oushinet/images/bplay.png"},{"id":"16091575","time":"1503676800000","title":"【健康】女性安全期是什么时候？","source":"39健康网","species":"country_health","timeStr":"","purl":"http://qhcdn.oss-cn-hangzhou.aliyuncs.com/uploadfile/spider/newscontent/1503720373546.jpg"}]
         * lastId : 16012392
         * lastTime : 1503575411000
         */

        @SerializedName("classTitle")
        public String classTitle;
        @SerializedName("lastId")
        public String lastId;
        @SerializedName("lastTime")
        public String lastTime;
        @SerializedName("news")
        public List<NewsBean_N> news;
    }
}
