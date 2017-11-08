package com.abcs.haiwaigou.local.huohang.bean;

import com.abcs.haiwaigou.model.ExtendOrderGoodsEntry;
import com.abcs.haiwaigou.model.StoreBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/9.
 */

public class NewOrderDetials implements Serializable {

    public String orderState; // 订单状态
    public String order_sn; // 订单号
    public String pay_sn;
    public StoreBean store; // 店铺信息
    public List<ExtendOrderGoodsEntry> extendOrderGoods;  // 商品信息
    public double atPrice;// 总价
    public long addTime; // 下单时间
    public int pay_ways; // 支付方式  0 两个可以  1 货到付款 2 在线支付

    public String getPay_sn() {
        return pay_sn;
    }

    public void setPay_sn(String pay_sn) {
        this.pay_sn = pay_sn;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public StoreBean getStore() {
        return store;
    }

    public void setStore(StoreBean store) {
        this.store = store;
    }

    public List<ExtendOrderGoodsEntry> getExtendOrderGoods() {
        return extendOrderGoods;
    }

    public void setExtendOrderGoods(List<ExtendOrderGoodsEntry> extendOrderGoods) {
        this.extendOrderGoods = extendOrderGoods;
    }

    public double getAtPrice() {
        return atPrice;
    }

    public void setAtPrice(double atPrice) {
        this.atPrice = atPrice;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public int getPay_ways() {
        return pay_ways;
    }

    public void setPay_ways(int pay_ways) {
        this.pay_ways = pay_ways;
    }
}
