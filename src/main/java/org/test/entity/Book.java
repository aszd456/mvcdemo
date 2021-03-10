package org.test.entity;

import java.util.Date;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/5 1:03
 */
public class Book {
    private int id;
    private String name;
    private Author author;
    private Double price;
    private Boolean republic;
    private Date time;

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author=" + author +
                ", price=" + price +
                ", ispublic=" + republic +
                ", time=" + time +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getRepublic() {
        return republic;
    }

    public void setRepublic(Boolean republic) {
        this.republic = republic;
    }
}

