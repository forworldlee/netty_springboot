package com.hledu.ns.spring;

import org.springframework.web.context.WebApplicationContext;

/**
 * @program: netty_springboot
 * @description: ${description}
 * @author: Forwardlee
 * @create: 2018-06-23
 **/
public class SpringContext {
    private static WebApplicationContext context;

    public static WebApplicationContext getContext() {
        return context;
    }

    public static void setContext(WebApplicationContext context) {
        SpringContext.context = context;
    }
}
