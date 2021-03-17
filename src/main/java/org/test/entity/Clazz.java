package org.test.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName Clazz
 * @Description TODO
 * @Author zhouliansheng
 * @Date 2021/3/17 16:47
 * @Version 1.0
 **/
public class Clazz implements Serializable {
    private Integer id;
    private String code;
    private String name;
    private List<TbStudent> students;

    @Override
    public String toString() {
        return "Clazz{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", students=" + students +
                '}';
    }

    public List<TbStudent> getStudents() {
        return students;
    }

    public void setStudents(List<TbStudent> students) {
        this.students = students;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
