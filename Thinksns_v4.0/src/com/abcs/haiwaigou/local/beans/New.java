package com.abcs.haiwaigou.local.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuke on 2017/1/16.
 */
public class New implements Parcelable {

    /**
     * id : 2076542
     * time : 1484487838000
     * title : 泰国回应人权报告缺乏公正∣移交77国集团主席职位
     * purl : http://qhcdn.oss-cn-hangzhou.aliyuncs.com/icon/tuling/wxh/hqb/505_1484489717536.png
     */

    private String id;
    private long time;
    private String title;
    private String timeStr;
    private String purl;
    private int ids;


    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public static final Creator<New> CREATOR = new Creator<New>() {
        @Override
        public New createFromParcel(Parcel in) {
            return new New(in);
        }

        @Override
        public New[] newArray(int size) {
            return new New[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }



    public int getIds() {
        return ids;
    }
    public void setIds(int ids) {
        this.ids = ids;
    }

    public New() {
    }

    protected New(Parcel in) {
        purl = in.readString();
        title = in.readString();
        timeStr = in.readString();
        id = in.readString();
        time = in.readLong();
        ids = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(purl);
        dest.writeString(title);
        dest.writeString(timeStr);
        dest.writeLong(time);
        dest.writeInt(ids);
    }
}
