package com.abcs.haiwaigou.local.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/21.
 */

public class ZhaoGongTiaoJian  implements Serializable{

    /**
     * status : 1
     * msg : {"tags":[{"id":3,"name":"无要求"},{"id":4,"name":"有经验"},{"id":5,"name":"可报税"},{"id":6,"name":"双休"},{"id":7,"name":"加班少"},{"id":8,"name":"轻松"},{"id":9,"name":"急聘"},{"id":10,"name":"包住"},{"id":11,"name":"包吃"},{"id":12,"name":"福利好"},{"id":13,"name":"消费多"},{"id":14,"name":"环境好"},{"id":15,"name":"会寿司"},{"id":16,"name":"接电话"},{"id":17,"name":"语音好"},{"id":18,"name":"高薪"},{"id":19,"name":"半天"},{"id":20,"name":"全天"},{"id":21,"name":"钟点工"},{"id":22,"name":"有身份"},{"id":23,"name":"有责任心"}],"fileter":[{"filter_name":"所在地区","fileters":[{"id":1,"name":"Wien"},{"id":2,"name":"Wien周边"}]},{"filter_name":"供求","fileters":[{"id":85,"name":"招聘"},{"id":86,"name":"求职"}]},{"filter_name":"工种","fileters":[{"id":87,"name":"跑堂"},{"id":88,"name":"帮跑"}]},{"filter_name":"性质","fileters":[{"id":89,"name":"全工"},{"id":90,"name":"半工"}]}]}
     */

    @SerializedName("status")
    public int status;
    @SerializedName("msg")
    public MsgBean msg;

}
