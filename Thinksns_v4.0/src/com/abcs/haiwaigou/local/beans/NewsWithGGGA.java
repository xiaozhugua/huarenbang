package com.abcs.haiwaigou.local.beans;

import com.abcs.haiwaigou.model.GGAds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */

public class NewsWithGGGA {

    public NewsWithGGGA() {
    }

    NewsBean_N newsBean_n;
    GGAds ggAds;

    private List<NewsBean_N> newsBeanNs=new ArrayList<>();
    private List<GGAds> ggas=new ArrayList<>();

    public List<NewsBean_N> getNewsBeanNs() {
        return newsBeanNs;
    }

    public void setNewsBeanNs(List<NewsBean_N> newsBeanNs) {
        this.newsBeanNs = newsBeanNs;
    }

    public List<GGAds> getGgas() {
        return ggas;
    }

    public void setGgas(List<GGAds> ggas) {
        this.ggas = ggas;
    }
}
