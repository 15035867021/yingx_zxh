package com.baizhi;

import com.alibaba.fastjson.JSON;
import io.goeasy.GoEasy;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

@SpringBootTest
class GoeasyTests {

    @Test
    void queryUser() {
        for (int i = 0; i < 20; i++) {
            //获取随机数
            Random random = new Random();

            HashMap<String, Object> map = new HashMap<>();

            map.put("month", Arrays.asList("1月 ", "2月", "3月", "4月", "5月", "6月"));
            map.put("boys", Arrays.asList(random.nextInt(200), random.nextInt(200), random.nextInt(700), random.nextInt(200), random.nextInt(200), random.nextInt(200)));
            map.put("girls", Arrays.asList(random.nextInt(600), random.nextInt(800), random.nextInt(1000), random.nextInt(200), random.nextInt(300), random.nextInt(800)));
            //将map集合(对象)转为字符串类型的   json格式的字符串
            String s = JSON.toJSONString(map);

            //创建goEasy初始化对象   参数1： REST Host：机房地区   zppkey:  应用appkey
            GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-d8c82401a6b443348f0ef93d03b1e2ab");
            //发送消息     参数:  通道名称    消息的内容
            goEasy.publish("2005_channel", s);

            //睡眠
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


    @Test
    void contextLoads1() {

        //创建goEasy初始化对象   参数1： REST Host：机房地区   zppkey:  应用appkey
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-d8c82401a6b443348f0ef93d03b1e2ab");
        //发送消息     参数:  通道名称    消息的内容
        goEasy.publish("2005_channel", "Hello, GoEasy! 2005");
    }
}
