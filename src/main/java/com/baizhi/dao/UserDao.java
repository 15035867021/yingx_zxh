package com.baizhi.dao;

import com.baizhi.entity.City;
import com.baizhi.entity.Mc;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    //查所有 分页     start 开始条数   rows 每页显示的行数
    public List<User> queryAll(@Param("start") Integer start, @Param("rows") Integer rows);

    //查询总条数
    public Integer count();

    //修改
    public void update(User user);

    //查一个
    public User queryOne(String id);

    //添加
    public void add(User user);

    //查所有
    public List<User> queryAlls();

    //查各个性别 分布的 地区
    public List<City> queryAllSexCity(String sex);

    //查每个月  各个性别 的注册人数
    public List<Mc> queryAllMonth(@Param("sex") String sex,@Param("month") Integer month);




}
