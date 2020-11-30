package com.baizhi;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.Mc;
import com.baizhi.entity.User;
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
class QueryTests {

    @Autowired
    private CategoryService categoryDao;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;


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
    void s13asd() {
        userService.queryAllMonth("男",11 ).forEach(a-> System.out.println(a));
    }


    @Test
    void contextLoads1() {

        //查各个地区  各个性别  注册的人数
//        userDao.queryAllSexCity("男").forEach(a -> System.out.println(a));

/*        //查各个月  各个性别 的注册人数
        List<Mc> mcList = userDao.queryAllMonth("女");
        mcList.forEach(mcc-> System.out.println(mcc));*/
       userDao.queryAllMonth("男",1).forEach(a -> System.out.println(a));
    }

}
