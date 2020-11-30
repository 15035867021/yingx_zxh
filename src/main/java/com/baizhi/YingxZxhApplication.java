package com.baizhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication    //指定该类为springboot入口类
@tk.mybatis.spring.annotation.MapperScan("com.baizhi.dao")
@MapperScan("com.baizhi.dao")   //扫描dfao接口所在 的 包
public class YingxZxhApplication {
    public static void main(String[] args) {
        SpringApplication.run(YingxZxhApplication.class, args);
    }

}
