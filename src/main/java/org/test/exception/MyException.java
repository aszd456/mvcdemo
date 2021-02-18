package org.test.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

/**
 * SpringMVC 中，针对全局异常也提供了相应的解决方案，主要是通过 @ControllerAdvice 和 @ExceptionHandler 两个注解来处理的
 * @ClassName MyException
 * @Description TODO
 * @Author zhouliansheng
 * @Date 2021/2/18 14:34
 * @Version 1.0
 * @ControllerAdvice 表示这是一个增强版的 Controller，主要用来做全局数据处理
 * @ExceptionHandler 表示这是一个异常处理方法，这个注解的参数，表示需要拦截的异常，
 * 参数为 Exception 表示拦截所有异常，这里也可以具体到某一个异常，如果具体到某一个异常，那么发生了其他异常则不会被拦截到。
 * 异常方法的定义，和 Controller 中方法的定义一样，可以返回 ModelAndview，也可以返回 String 或者 void
 * //@ExceptionHandler(MaxUploadSizeExceededException.class) 拦截文件上传异常
 **/
@ControllerAdvice //表示这是一个增强版的 Controller，主要用来做全局数据处理
public class MyException {
    @ExceptionHandler(Exception.class)
    public ModelAndView fileUploadException(Exception e) {
        ModelAndView error = new ModelAndView("error");
        error.addObject("error", e.getMessage());
        return error;
    }
}
