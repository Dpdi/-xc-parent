package com.xuecheng.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.xuecheng.system.mappers")
//组件扫描
@ComponentScan(
        basePackages = {
                "com.xuecheng.system",
                "com.xuecheng.web"
        }//扫描哪个包的注解
)
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}