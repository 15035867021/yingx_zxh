package com.baizhi;

import com.baizhi.entity.User;
import com.baizhi.po.VideoPo;
import com.baizhi.service.AdminService;
import com.baizhi.service.CategoryService;
import com.baizhi.service.UserService;
import com.baizhi.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class YingxZxhApplicationTests {

    @Autowired
    private CategoryService categoryDao;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Resource
    private VideoService videoService;
    @Test
    void contextLoads() {
        List<User> users = userService.queryAll(2, 3);
        users.forEach(user-> System.out.println(user));
    }
    @Test
    void s213asd() {
        categoryDao.queryBuPageSecond(2, "1", 1, 2).forEach(a -> System.out.println(a));
    }


    @Test
    void contextLoads1() {
//        Integer count = videoService.selectCount();
//        List<Video> videos = videoService.queryByPage(1, 2);
//        videos.forEach(video -> System.out.println(video));

        List<VideoPo> videoPos = videoService.queryByLikeVideoName("啊");
        videoPos.forEach(video-> System.out.println(video));
        int a = 100;
        int b = 200;
    }
}
