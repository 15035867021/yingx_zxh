package com.baizhi.service;

import com.baizhi.entity.City;
import com.baizhi.entity.Mc;
import com.baizhi.entity.User;

import java.util.List;

public interface UserService {

    //查所有 分页   page 当前页    rows 每页总条数
    public List<User> queryAll(Integer page,Integer rows);

    //总条数
    public Integer queryConut();

    //修改
    void update(User user);

    //查一个
    User queryOne(String id);

    //添加数据
    public String add(User user);

  /*  //添加  视频上传阿里云
    public void UploadAliyun(MultipartFile picImg, String id);*/

    //查所有
    public List<User> queryAlls();

     //查各个性别 分布的 地区
    public List<City> queryAllSexCity(String sex);

    //查每个月  各个性别 的注册人数
    public List<Mc> queryAllMonth(String sex,Integer month);





}
