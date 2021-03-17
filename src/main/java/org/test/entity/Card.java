package org.test.entity;

import java.io.Serializable;

/**
 * @ClassName Card
 * @Description TODO
 * @Author zhouliansheng
 * @Date 2021/3/17 16:15
 * @Version 1.0
 **/

public class Card implements Serializable {

    private Integer id;
    private String code;

    public Card() {
    }

    public Card(Integer id, String code) {
        this.id = id;
        this.code = code;
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

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", code='" + code + '\'' +
                '}';
    }
}
