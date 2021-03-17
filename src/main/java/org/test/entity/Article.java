package org.test.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/17 23:43
 */
public class Article implements Serializable {
    private Integer id;
    private String name;
    private Double prize;
    private String remark;
    /**
     * 多对多关系，一种商品可以出现在多个订单中
     */
    private List<Order> orderList;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", prize=" + prize +
                ", remark='" + remark + '\'' +
                ", orderList=" + orderList +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrize() {
        return prize;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
