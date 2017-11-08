package com.abcs.haiwaigou.local.huohang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/29. 需要携带的数据
 */

public class DatasEntrySer implements Serializable {

    public String storeId;
    public String storeName;
    public String storeImg;
    public String storeCoin;
    public int storeGoodsDetails;
    public String storeAddress;
    public String storePhone;
    public String storeDes;
    public String storeSendTime;
    public String storeNewLogo;
    public String ngc_id; // 分类id
    public String goods_Id; // 商品的Id

    public String getGoods_Id() {
        return goods_Id;
    }

    public void setGoods_Id(String goods_Id) {
        this.goods_Id = goods_Id;
    }

    public String getNgc_id() {
        return ngc_id;
    }

    public void setNgc_id(String ngc_id) {
        this.ngc_id = ngc_id;
    }

    public List<Acty> activity;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreImg() {
        return storeImg;
    }

    public void setStoreImg(String storeImg) {
        this.storeImg = storeImg;
    }

    public String getStoreCoin() {
        return storeCoin;
    }

    public void setStoreCoin(String storeCoin) {
        this.storeCoin = storeCoin;
    }

    public int getStoreGoodsDetails() {
        return storeGoodsDetails;
    }

    public void setStoreGoodsDetails(int storeGoodsDetails) {
        this.storeGoodsDetails = storeGoodsDetails;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getStoreDes() {
        return storeDes;
    }

    public void setStoreDes(String storeDes) {
        this.storeDes = storeDes;
    }

    public String getStoreSendTime() {
        return storeSendTime;
    }

    public void setStoreSendTime(String storeSendTime) {
        this.storeSendTime = storeSendTime;
    }

    public String getStoreNewLogo() {
        return storeNewLogo;
    }

    public void setStoreNewLogo(String storeNewLogo) {
        this.storeNewLogo = storeNewLogo;
    }

    public List<Acty> getActivity() {
        return activity;
    }

    public void setActivity(List<Acty> activity) {
        this.activity = activity;
    }
}
