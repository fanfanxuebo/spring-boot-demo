package com.ki.springbootdubboconsumerone;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fanxuebo
 * @description dubbo服务客户端启动类
 * @company ki
 * @createDate 2019-11-26 10:38:07 星期二
 */
@EnableDubbo
@SpringBootApplication
public class SpringBootDubboConsumerOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDubboConsumerOneApplication.class, args);
    }
}
