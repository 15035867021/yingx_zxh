package com.baizhi.service;

import com.baizhi.entity.FeedBack;

import java.util.List;

//反馈
public interface FeedBackService {

    //分页查所有
    public List<FeedBack> queryByPage(Integer page, Integer rows);

    //查数据库数据
    public Integer queryCount();

}
