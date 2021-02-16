package org.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.test.entity.Book;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * @ClassName JsonController
 * @Description TODO
 * @Author zhouliansheng
 * @Date 2021/2/16 11:25
 * @Version 1.0
 **/

/**
 * 在 SpringMVC 中，对 jackson 和 gson 都提供了相应的支持，就是如果使用这两个作为 JSON 转换器，
 * 只需要添加对应的依赖就可以了，返回的对象和返回的集合、Map 等都会自动转为 JSON，但是，如果使用 fastjson，
 * 除了添加相应的依赖之外，还需要自己手动配置 HttpMessageConverter 转换器。其实前两个也是使用 HttpMessageConverter 转换器，
 * 但是是 SpringMVC 自动提供的，SpringMVC 没有给 fastjson 提供相应的转换器。
 */
@Controller
@RequestMapping("json")
public class JsonController {


    @RequestMapping("/book")
    @ResponseBody
    public Book getBookById() {
        Book book = new Book();
        book.setName("三国演义");
        book.setPrice(30.5);
        book.setTime(new Date());
        return book;
    }

    /**
     * 添加了 jackson ，就能够自动返回 JSON，这个依赖于一个名为 HttpMessageConverter 的类，这本身是一个接口，从名字上就可以看出，它的作用是 Http 消息转换器，既然是消息转换器，它提供了两方面的功能：
     * <p>
     * 将返回的对象转为 JSON
     * 将前端提交上来的 JSON 转为对象
     * 但是，HttpMessageConverter 只是一个接口，由各个 JSON 工具提供相应的实现，在 jackson 中，
     * 实现的名字叫做 MappingJackson2HttpMessageConverter，而这个东西的初始化，则由 SpringMVC 来完成。
     * 除非自己有一些自定义配置的需求，否则一般来说不需要自己提供 MappingJackson2HttpMessageConverter。
     */

    /**
     * 浏览器传来的参数，可以是 key/value 形式的，也可以是一个 JSON 字符串。在 Jsp/Servlet 中，
     * 我们接收 key/value 形式的参数，一般是通过 getParameter 方法。如果客户端商户惨的是 JSON 数据，
     * 我们可以通过如下格式进行解析：
     * @param req
     * @throws IOException
     */
    @RequestMapping("/addbook2")
    @ResponseBody
    public void addBook2(HttpServletRequest req) throws IOException {
        ObjectMapper om = new ObjectMapper();
        Book book = om.readValue(req.getInputStream(), Book.class);
        System.out.println(book);
    }

    /**
     * 上面解析方式有点麻烦，在 SpringMVC 中，我们可以通过一个注解来快速的将一个 JSON 字符串转为一个对象：
     * 这样就可以直接收到前端传来的 JSON 字符串了。这也是 HttpMessageConverter 提供的第二个功能
     * @param book
     */
    @RequestMapping("/addbook3")
    @ResponseBody
    public void addBook3(@RequestBody Book book) {
        System.out.println(book);
    }

}
