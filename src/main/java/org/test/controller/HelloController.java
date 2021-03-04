package org.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/3 22:25
 */
@Controller
@RequestMapping("/user")
public class HelloController {

    /**
     * 前后端不分的开发，大部分情况下，我们返回 ModelAndView，即数据模型+视图
     *
     * @return
     */
    @RequestMapping(value = "/hello", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ModelAndView hello() {
        return new ModelAndView("hello");
    }

    @RequestMapping({"/hello1", "/hello2"})
    public ModelAndView hello2() {
        return new ModelAndView("hello2");
    }

    /**
     * 通过 HttpServletRequest 做服务端跳转
     */
    @RequestMapping("/hello2")
    public void hello2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/hello.jsp").forward(req, resp);
    }


    /**
     * 通过 HttpServletResponse 做重定向
     */
    @RequestMapping("/hello3")
    public void hello3(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/hello.jsp");
    }

    /**
     * 也可以自己手动指定响应头去实现重定向：
     */
    @RequestMapping("/hello4")
    public void hello4(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(302);
        resp.addHeader("Location", "/jsp/hello.jsp");
    }

    /**
     * 通过 HttpServletResponse 给出响应
     */
    public void hello5(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write("hello javaboy!");
        out.flush();
        out.close();
    }

    /**
     * 前面的 ModelAndView 可以拆分为两部分，Model 和 View，在 SpringMVC 中，
     * Model 我们可以直接在参数中指定，然后返回值是逻辑视图名：
     */
    public String hello6(Model model) {
        model.addAttribute("username", "javaboy");//这是数据模型
        return "hello";//表示去查找一个名为 hello 的视图
    }

    /**
     * 服务端跳转
     */
    @RequestMapping("/hello7")
    public String hello7() {
        return "forward:/jsp/hello.jsp";
    }

    /**
     * 客户端跳转 浏览器重定向
     */
    @RequestMapping("/hello8")
    public String hello8() {
        return "redirect:/user/hello";
    }

    /**
     * 如果单纯的返回一个中文字符串，是会乱码的，可以在 @RequestMapping 中添加 produces 属性来解决：
     * @return
     */
    @RequestMapping(value = "/hello9",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String hello9() {
        return "Java 语言程序设计";
    }





}
