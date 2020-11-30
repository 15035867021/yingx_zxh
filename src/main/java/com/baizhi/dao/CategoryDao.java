package com.baizhi.dao;

import com.baizhi.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryDao {

    //查所有一级类别
    public List<Category> queryBuPage(@Param("levels") Integer levels,@Param("begin") Integer begin,@Param("rows") Integer rows);

    //查符合条件的数据条数
    public Integer queryCount(Integer levels);


    //查所有二级类别
    public List<Category> queryBuPageSecond(@Param("levels") Integer levels,@Param("id") String id,@Param("begin") Integer begin,@Param("rows") Integer rows);

    //查一级类别下二级类别下的数量
    public Integer count(String id);

    //添加一级类别
    public void add(Category category);

    /*//添加二级类别
    public void addSecond(Category category);*/

    //查所有的一级类别对应的名字
    public List<Category> selectOne(Integer levels);

    //修改一级  二级类别
    public void update(Category category);

    //删除 一级 二级类别
    public void delete(String id);

    //查所有类别   一级 二级 类别
    public List<Category> queryAllCategory();

}
