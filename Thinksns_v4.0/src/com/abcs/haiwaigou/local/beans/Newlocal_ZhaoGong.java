package com.abcs.haiwaigou.local.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class Newlocal_ZhaoGong {

    /**
     * status : 1
     * msg : [{"tags":"","id":75762,"createTime":1500430113,"imgUrls":"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png","title":"维也纳二区快餐店诚聘可报税大厨。","status":1,"views":6,"listTypeId":"1010","menuName":"招工"},{"tags":"","id":75797,"createTime":1500516861,"imgUrls":"","title":"维也纳地铁站口急聘炒面师傅一名。","status":1,"views":6,"listTypeId":"1010","menuName":"招工"},{"tags":"","id":75796,"createTime":1500516808,"imgUrls":"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png","title":"维也纳21区近U1诚聘大厨。","status":1,"views":5,"listTypeId":"1010","menuName":"招工"},{"tags":"","id":75795,"createTime":1500516772,"imgUrls":"","title":"14区Segafredo CaffeBar 找有经验女跑堂，待遇优。","status":1,"views":5,"listTypeId":"1010","menuName":"招工"},{"tags":"","id":75794,"createTime":1500516741,"imgUrls":"","title":"维也纳二区快餐店诚聘可报税大厨。","status":1,"views":5,"listTypeId":"1010","menuName":"招工"},{"tags":"","id":75793,"createTime":1500516665,"imgUrls":"","title":"维也纳近郊诚聘会做寿司的铁板师傅。","status":1,"views":0,"listTypeId":"1010","menuName":"招工"},{"tags":"","id":75792,"createTime":1500516636,"imgUrls":"","title":"维也纳可报税铁板和寿司师傅","status":1,"views":2,"listTypeId":"1010","menuName":"招工"},{"tags":"","id":75791,"createTime":1500516613,"imgUrls":"","title":"维也纳诚聘跑堂和半跑。","status":1,"views":1,"listTypeId":"1010","menuName":"招工"},{"tags":"","id":75790,"createTime":1500516588,"imgUrls":"","title":"维也纳诚聘有工作纸的酒台或半跑，学生或初学者亦可。","status":1,"views":1,"listTypeId":"1010","menuName":"招工"},{"tags":"","id":75789,"createTime":1500516563,"imgUrls":"","title":"维也纳急聘酒台和半跑，酒台可提供住家。","status":1,"views":0,"listTypeId":"1010","menuName":"招工"}]
     */

    @SerializedName("status")
    public int status;
    @SerializedName("msg")
    public List<MsgBean> msg;

    public static class MsgBean {

        /**
         * tags : 双休,加班少
         * createTime : 1500430113
         * status : 2
         * pubName : Wien 跑堂
         * listTypeId : 1010
         * contact :
         * contactMan :
         * id : 75762
         * content :
         * title : 维也纳二区快餐店诚聘可报税大厨。
         * imgUrls : http://qhcdn.oss-cn-hangzhou.aliyuncs.com/hqb_abc_img/A11499683420402.jpg
         * views : 44
         * filter_id : 65,5
         * ads :
         * menuName : 招工
         */

        @SerializedName("tags")
        public String tags;
        @SerializedName("createTime")
        public Long createTime;
        @SerializedName("status")
        public int status;
        @SerializedName("pubName")
        public String pubName;
        @SerializedName("listTypeId")
        public String listTypeId;
        @SerializedName("contact")
        public String contact;
        @SerializedName("contactMan")
        public String contactMan;
        @SerializedName("id")
        public int id;
        @SerializedName("content")
        public String content;
        @SerializedName("title")
        public String title;
        @SerializedName("imgUrls")
        public String imgUrls;
        @SerializedName("views")
        public int views;
        @SerializedName("filter_id")
        public String filterId;
        @SerializedName("ads")
        public String ads;
        @SerializedName("menuName")
        public String menuName;
    }
}
