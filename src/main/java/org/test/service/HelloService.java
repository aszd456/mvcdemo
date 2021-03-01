package org.test.service;

import org.springframework.stereotype.Service;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/1 1:12
 */
@Service
public class HelloService {
    public String hello(String name) {
        return "hello " + name;
    }
}

