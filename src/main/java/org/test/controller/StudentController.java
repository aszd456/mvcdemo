package org.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.test.entity.Student;
import org.test.validation.ValidationGroup2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName StudentController
 * @Description TODO
 * @Author zhouliansheng
 * @Date 2021/2/18 14:41
 * @Version 1.0
 **/
@Controller
public class StudentController {

    @RequestMapping("/student")
    public String student(){
        return "student";
    }

    /**
     * //@Validated 表示 Student 中定义的校验规则将会生效
     * BindingResult 表示出错信息，如果这个变量不为空，表示有错误，否则校验通过。
     * @param student
     * @param result
     * 分组校验
     * 在 group 中指定每一个校验规则所属的组，一个规则可以属于一个组，也可以属于多个组。
     * 最后，在接收参数的地方，指定校验组：
     */
    @RequestMapping("/addStudent")
    @ResponseBody
    public void addStudent(@Validated(ValidationGroup2.class) Student student, BindingResult result) {
        if (result != null) {
            //校验未通过，获取所有的异常信息并展示出来
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError allError : allErrors) {
                System.out.println(allError.getObjectName()+":"+allError.getDefaultMessage());
            }
        }
    }

    /**
     * @Null 被注解的元素必须为 null
     * @NotNull 被注解的元素必须不为 null
     * @AssertTrue 被注解的元素必须为 true
     * @AssertFalse 被注解的元素必须为 false
     * @Min(value) 被注解的元素必须是一个数字，其值必须大于等于指定的最小值
     * @Max(value) 被注解的元素必须是一个数字，其值必须小于等于指定的最大值
     * @DecimalMin(value) 被注解的元素必须是一个数字，其值必须大于等于指定的最小值
     * @DecimalMax(value) 被注解的元素必须是一个数字，其值必须小于等于指定的最大值
     * @Size(max=, min=) 被注解的元素的大小必须在指定的范围内
     * @Digits (integer, fraction) 被注解的元素必须是一个数字，其值必须在可接受的范围内
     * @Past 被注解的元素必须是一个过去的日期
     * @Future 被注解的元素必须是一个将来的日期
     * @Pattern(regex=,flag=) 被注解的元素必须符合指定的正则表达式
     * @NotBlank(message =) 验证字符串非 null，且长度必须大于0
     * @Email 被注解的元素必须是电子邮箱地址
     * @Length(min=,max=) 被注解的字符串的大小必须在指定的范围内
     * @NotEmpty 被注解的字符串的必须非空
     * @Range(min=,max=,message=) 被注解的元素必须在合适的范围内
     */

    /**
     *  <td><input type="text" name="id" value="{id}"></td>
     * 这种简单数据类型的回显，实际上非常麻烦，因为需要开发者在服务端一个一个手动设置。
     * 如果使用对象的话，就没有这么麻烦了，因为 SpringMVC 在页面跳转时，会自动将对象填充进返回的数据中。
     * @param id
     * @param name
     * @param email
     * @param age
     * @param model
     * @return
     */
    @RequestMapping("/addStudent2")
    public String addStudent2(Integer id, String name, String email, Integer age, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("age", age);
        return "student";
    }

    /**
     * 另一种定义回显变量别名的方式，就是使用 @ModelAttribute 注解。
     * @ModelAttribute 这个注解，主要有两方面的功能：
     * 在数据回显时，给变量定义别名
     * 定义全局数据
     *
     * 这样定义完成后，在前端再次访问回显的变量时，变量名称就不是 student 了，而是 s：
     * <td><input type="text" name="id" value="{s.id}"></td>
     */
    @RequestMapping("/addStudent3")
    public String addStudent3(@ModelAttribute("s") @Validated(ValidationGroup2.class) Student student,
                              BindingResult result) {
        if (result != null) {
            //校验未通过，获取所有的异常信息并展示出来
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError allError : allErrors) {
                System.out.println(allError.getObjectName()+":"+allError.getDefaultMessage());
            }
            return "student";
        }
        return "hello";
    }

    /**
     * 定义全局数据
     * 当用户访问当前 Controller 中的任意一个方法，在返回数据时，
     * 都会将添加了 @ModelAttribute 注解的方法的返回值，一起返回给前端。@ModelAttribute 注解中的 info 表示返回数据的 key
     * @return
     */
    @ModelAttribute("info")
    public Map<String,Object> info() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "javaboy");
        map.put("address", "www.javaboy.org");
        return map;
    }
}
