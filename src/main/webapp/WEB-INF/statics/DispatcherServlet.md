# 1.DispatcherServlet作用
DispatcherServlet 是前端控制器设计模式的实现，提供 Spring Web MVC 的集中访问点，而且负责职责的分派，而且与 Spring IoC 容器无缝集成，从而可以获得 Spring 的所有好处。DispatcherServlet 主要用作职责调度工作，本身主要用于控制流程，主要职责如下：
* 文件上传解析，如果请求类型是 multipart 将通过 MultipartResolver 进行文件上传解析；
* 通过 HandlerMapping，将请求映射到处理器（返回一个 HandlerExecutionChain，它包括一个处理器、多个 HandlerInterceptor 拦截器）；
* 通过 HandlerAdapter 支持多种类型的处理器(HandlerExecutionChain 中的处理器)；
* 通过 ViewResolver 解析逻辑视图名到具体视图实现；
* 本地化解析；
* 渲染具体的视图等；
* 如果执行过程中遇到异常将交给 HandlerExceptionResolver 来解析

# 2.DispatcherServlet配置详解
```
<servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring-servlet.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>

```
* load-on-startup：表示启动容器时初始化该 Servlet；
* url-pattern：表示哪些请求交给 Spring Web MVC 处理， “/” 是用来定义默认 servlet 映射的。也可以如 *.html 表示拦截所有以 html 为扩展名的请求
* contextConfigLocation：表示 SpringMVC 配置文件的路径
其他的参数配置:

|  参数   | 描述  |
|  ----  | ----  |
| contextClass  | 实现WebApplicationContext接口的类，当前的servlet用它来创建上下文。如果这个参数没有指定， 默认使用XmlWebApplicationContext。 |
| contextConfigLocation  | 传给上下文实例（由contextClass指定）的字符串，用来指定上下文的位置。这个字符串可以被分成多个字符串（使用逗号作为分隔符） 来支持多个上下文（在多上下文的情况下，如果同一个bean被定义两次，后面一个优先）。 |
| namespace | WebApplicationContext命名空间。默认值是[server-name]-servlet。 |

# 3.Spring 配置
之前的案例中，只有 SpringMVC，没有 Spring，Web 项目也是可以运行的。在实际开发中，Spring 和 SpringMVC 是分开配置的，所以我们对上面的项目继续进行完善，添加 Spring 相关配置。

首先，项目添加一个 service 包，提供一个 HelloService 类，如下：
```
   @Service
   public class HelloService {
       public String hello(String name) {
           return "hello " + name;
       }
   }
```

现在，假设我需要将 HelloService 注入到 Spring 容器中并使用它，这个是属于 Spring 层的 Bean，所以我们一般将除了 Controller 之外的所有 Bean 注册到 Spring 容器中，而将 Controller 注册到 SpringMVC 容器中，现在，在 resources 目录下添加 applicationContext.xml 作为 spring 的配置：
```$xslt
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <context:component-scan base-package="org.javaboy" use-default-filters="true">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
</beans>

```

但是，这个配置文件，默认情况下，并不会被自动加载，所以，需要我们在 web.xml 中对其进行配置：
```$xslt
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
</context-param>
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>

```

首先通过 context-param 指定 Spring 配置文件的位置，这个配置文件也有一些默认规则，它的配置文件名默认就叫 applicationContext.xml ，并且，如果你将这个配置文件放在 WEB-INF 目录下，那么这里就可以不用指定配置文件位置了，只需要指定监听器就可以了。这段配置是 Spring 集成 Web 环境的通用配置；一般用于加载除 Web 层的 Bean（如DAO、Service 等），以便于与其他任何Web框架集成。
* contextConfigLocation：表示用于加载 Bean 的配置文件；
* contextClass：表示用于加载 Bean 的 ApplicationContext 实现类，默认 WebApplicationContext。

配置完成之后，还需要修改 MyController，在 MyController 中注入 HelloSerivce:
```$xslt
@org.springframework.stereotype.Controller("/hello")
public class MyController implements Controller {
    @Autowired
    HelloService helloService;
    /**
     * 这就是一个请求处理接口
     * @param req 这就是前端发送来的请求
     * @param resp 这就是服务端给前端的响应
     * @return 返回值是一个 ModelAndView，Model 相当于是我们的数据模型，View 是我们的视图
     * @throws Exception
     */
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println(helloService.hello("javaboy"));
        ModelAndView mv = new ModelAndView("hello");
        mv.addObject("name", "javaboy");
        return mv;
    }
}

```