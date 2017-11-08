package com.abcs.haiwaigou.local.evenbus;

/**
 * Created by Administrator on 2017/8/28.
 */

public class changesCity {
    public  boolean haveCity=false;
    public String cityId;
    public String cityName;
    public String countryNmae;

    public changesCity(String cityId, String cityName, String countryNmae) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.countryNmae = countryNmae;
    }

    public changesCity(boolean haveCity, String cityId) {
        this.haveCity = haveCity;
        this.cityId = cityId;
    }
}
