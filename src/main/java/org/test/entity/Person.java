package org.test.entity;

import java.io.Serializable;

/**
 * @ClassName Person
 * @Description TODO
 * @Author zhouliansheng
 * @Date 2021/3/17 16:16
 * @Version 1.0
 **/
public class Person implements Serializable {
    private Integer id;
    private String name;
    private String sex;
    private Integer age;
    private Card card;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", card=" + card +
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
