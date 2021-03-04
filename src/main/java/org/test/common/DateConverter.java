package org.test.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 在自定义的参数类型转换器中，将一个 String 转为 Date 对象，同时，将这个转换器注册为一个 Bean。
 *
 * 接下来，在 SpringMVC 的配置文件中，配置该 Bean，使之生效
 * @Author: zhouliansheng
 * @Date: 2021/3/5 1:06
 */
@Component
public class DateConverter implements Converter<String, Date> {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date convert(String source) {
        try {
            return sdf.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
