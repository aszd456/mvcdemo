package org.test.entity;

import java.io.Serializable;

/**
 * @ClassName TbStudent
 * @Description TODO
 * @Author zhouliansheng
 * @Date 2021/3/17 16:48
 * @Version 1.0
 **/
public class TbStudent implements Serializable {
    private Integer id;
    private String name;
    private String sex;
    private Integer age;
    private Clazz clazz;

    @Override
    public String toString() {
        return "TbStudent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", clazz=" + clazz +
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

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }
}
