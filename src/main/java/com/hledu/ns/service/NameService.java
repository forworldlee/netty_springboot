package com.hledu.ns.service;

import org.springframework.stereotype.Component;

/**
 * @program: netty_springboot
 * @description: ${description}
 * @author: Forwardlee
 * @create: 2018-06-23
 **/
@Component
public class NameService {

    public Integer getAge(String name) {
        switch (name) {
            case "tom":
                return 23;
            case "John":
                return 24;
            default:
                return 25;
        }
    }

}
