package com.baizhi.service;

import com.baizhi.entity.Category;

import java.util.List;

public interface CategoryService {
    //分页查所有一级类别
    public List<Category> findByPage(Integer levels, Integer page, Integer rows);

    //查符合条件的数据条数
    public Integer queryCount(Integer levels);

    //查所有二级类别
    public List<Category> queryBuPageSecond(Integer levels,String parentId,Integer begin,Integer rows);

    //查一级类别下二级类别下的数量
    public Integer count(String parentId);

    //添加一级类别
    public void add(Category category);

    //查所有的一级类别
    public List<Category> selectOne(Integer levels);

    //修改一级  二级类别
    public void update(Category category);

    //删除  一级 二级类别
    public String delete(Category category);

    //查所有类别
    public List<Category> queryAllCategory();


}
