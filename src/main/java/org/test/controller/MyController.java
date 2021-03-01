package org.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.test.service.HelloService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName MyController
 * @Description TODO
 * @Author zhouliansheng
 * @Date 2021/2/26 9:26
 * @Version 1.0
 **/
@org.springframework.stereotype.Controller("/hello")
public class MyController implements Controller {

    @Autowired
    HelloService helloService;
    /**
     * 这就是一个请求处理接口
     *
     * @param req  这就是前端发送来的请求
     * @param resp 这就是服务端给前端的响应
     * @return 返回值是一个 ModelAndView，Model 相当于是我们的数据模型，View 是我们的视图
     * @throws Exception
     */
    @Override
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView mv = new ModelAndView("hello");
        mv.addObject("name", "javaboy");
        return mv;
    }
}
