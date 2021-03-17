package org.test.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/17 23:42
 */
public class Order implements Serializable {
    private Integer id;
    private String code;
    private Double total;
    /**
     * 订单和用户是多对一关系，一个订单只有一个用户
     */
    private TbUser tbUser;
    /**
     * 订单和商品多对对关系，一个订单有多个商品
     */
    private List<Article> articleList;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", total=" + total +
                ", tbUser=" + tbUser +
                ", articleList=" + articleList +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public TbUser getTbUser() {
        return tbUser;
    }

    public void setTbUser(TbUser tbUser) {
        this.tbUser = tbUser;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
