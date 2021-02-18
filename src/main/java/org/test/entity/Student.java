package org.test.entity;

import org.test.validation.ValidationGroup1;
import org.test.validation.ValidationGroup2;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/7 23:18
 */
public class Student {

    @NotNull(message = "${student.id.notnull}",groups = ValidationGroup1.class)
    private Integer id;
    @NotNull(message = "{student.name.notnull}",groups = {ValidationGroup1.class, ValidationGroup2.class})
    @Size(min = 2,max = 10,message = "{student.name.length}",groups = {ValidationGroup1.class, ValidationGroup2.class})
    private String name;
    @Email(message = "{student.email.error}",groups = {ValidationGroup1.class, ValidationGroup2.class})
    private String email;
    @Max(value = 150,message = "{student.age.error}",groups = {ValidationGroup2.class})
    private Integer age;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
