package org.test.entity;

import java.util.List;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/7 23:18
 */
public class MyClass {
    private Integer id;
    private List<Student> students;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
