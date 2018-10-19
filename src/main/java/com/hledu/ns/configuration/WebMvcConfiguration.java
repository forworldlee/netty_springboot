package com.hledu.ns.configuration;

import com.hledu.ns.listener.ServerStartupListener;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.EmbeddedValueResolver;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringValueResolver;

import java.util.EventListener;

/**
 * @program: netty_springboot
 * @description: ${description}
 * @author: Forwardlee
 * @create: 2018-06-23
 **/
@Configuration
public class WebMvcConfiguration {

    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean(){
        ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new ServerStartupListener());
        return registrationBean;
    }

    @Bean
    public StringValueResolver stringValueResolver(ConfigurableBeanFactory beanFactory){
        return new EmbeddedValueResolver(beanFactory);
    }
}
