package com.ki.springbootdubboproviderimpl;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fanxuebo
 * @description dubbo服务服务端启动类
 * @company ki
 * @createDate 2019-11-26 10:22:55 星期二
 */
@EnableDubbo
@SpringBootApplication
public class SpringBootDubboProviderImplApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDubboProviderImplApplication.class, args);
    }
}
