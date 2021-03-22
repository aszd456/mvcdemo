package org.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.test.entity.Book;
import org.test.entity.MyClass;

import java.util.Arrays;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/5 0:49
 */
@Controller
public class BookController {

    @RequestMapping("/book")
    public String addBook() {
        return "book";
    }

    /**
     * 2 简单数据类型
     *
     * @param name
     * @param author
     * @param price
     * @param ispublic
     */
    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public void doAdd(String name, String author, Double price, Boolean ispublic) {
        System.out.println(name);
        System.out.println(author);
        System.out.println(price);
        System.out.println(ispublic);
    }

    /**
     * 默认支持的参数类型，就是可以直接写在 @RequestMapping 所注解的方法中的参数类型，一共有四类：
     *
     * HttpServletRequest
     * HttpServletResponse
     * HttpSession
     * Model/ModelMap
     */

    /**
     * @RequestParam
     * 这个注解的的功能主要有三方面：
     *
     * 给变量取别名
     * 设置变量是否必填
     * 给变量设置默认值
     */

    /**
     * 注解中的 “name” 表示给 bookname 这个变量取的别名，也就是说，bookname 将接收前端传来的 name 这个变量的值
     *
     * @param bookname
     * @param author
     * @param price
     * @param ispublic
     */
    @RequestMapping(value = "/doAdd2", method = RequestMethod.POST)
    @ResponseBody
    public void doAdd2(@RequestParam("name") String bookname, String author, Double price, Boolean ispublic) {
        System.out.println(bookname);
        System.out.println(author);
        System.out.println(price);
        System.out.println(ispublic);
    }

    /**
     * required 属性默认为 true，即只要添加了 @RequestParam 注解，这个参数默认就是必填的，如果不填，请求无法提交，
     * 会报 400 错误，如果这个参数不是必填项，可以手动把 required 属性设置为 false。但是，如果同时设置了 defaultValue，
     * 这个时候，前端不传该参数到后端，即使 required 属性为 true，它也不会报错。
     *
     * @param bookname
     * @param author
     * @param price
     * @param ispublic
     */
    @RequestMapping(value = "/doAdd3", method = RequestMethod.POST)
    @ResponseBody
    public void doAdd3(@RequestParam(value = "name", required = true, defaultValue = "三国演义") String bookname, String author, Double price, Boolean ispublic) {
        System.out.println(bookname);
        System.out.println(author);
        System.out.println(price);
        System.out.println(ispublic);
    }

    /**
     * 前端页面传值的时候和上面的一样，只需要写属性名就可以了，不需要写 book 对象名
     * book 对象中，有一个 Author 属性，前端传值参考book.jsp
     *
     * @param book
     */
    @RequestMapping(value = "/doAdd4", method = RequestMethod.POST)
    @ResponseBody
    public void doAdd4(Book book, String[] favorites) {
        System.out.println(book);
        System.out.println(String.join(",", favorites));
    }

    @RequestMapping("/addClass")
    @ResponseBody
    public void addClass(MyClass myClass) {
        System.out.println(myClass);
    }


}
