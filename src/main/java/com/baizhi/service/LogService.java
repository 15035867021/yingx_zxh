package com.baizhi.service;

import com.baizhi.entity.Log;

import java.util.List;

public interface LogService {

    //分页 查所有    page 过滤条数   rows 每页的数据
    public List<Log> queryByPage(Integer page,Integer rows);

    //数据库条数
    public Integer queryCount();

    //删除
    public void delete(Log log);
}
