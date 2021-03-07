package org.test.entity;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/7 22:36
 */
public class Author {

    private String name;

    private Integer age;

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
