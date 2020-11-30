package com.baizhi.serviceimpl;

import com.baizhi.annotation.AddLog;
import com.baizhi.annotation.addCache;
import com.baizhi.annotation.deleteCache;
import com.baizhi.dao.CategoryDao;
import com.baizhi.entity.Category;
import com.baizhi.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceimpl implements CategoryService {
    @Resource
    private CategoryDao categoryDao;

    //分页查所有一级类别
    @addCache     //添加缓存
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> findByPage(Integer levels, Integer page, Integer rows) {
        Integer begin = (page - 1) * rows;
        return categoryDao.queryBuPage(levels, begin, rows);
    }

    @addCache     //添加缓存
    //查符合一级类别条件的数据条数
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer queryCount(Integer levels) {
        return categoryDao.queryCount(levels);
    }


    @addCache     //添加缓存
    //查所有二级类别
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryBuPageSecond(Integer levels,String parentId,Integer page, Integer rows) {
        Integer begin = (page - 1) * rows;
        return categoryDao.queryBuPageSecond(levels, parentId, begin, rows);
    }

    @addCache     //添加缓存
    @Transactional(propagation = Propagation.SUPPORTS)
    //查一级类别下二级类别下的数量
    @Override
    public Integer count(String parentId) {
        return categoryDao.count(parentId);
    }


    @deleteCache   //删除缓存
    //添加  一级  二级  类别
    @AddLog("添加类别")    //指定该类没调用一次 记录一次日志
    @Override
    public void add(Category category) {
        if(category.getParentId()!=null){
            category.setId(UUID.randomUUID().toString());
            category.setLevels(2);
        }else{
            category.setId(UUID.randomUUID().toString());
            category.setParentId(null);
            category.setLevels(1);
        }
        categoryDao.add(category);
    }

    @addCache     //添加缓存
    //查所有一级类别
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> selectOne(Integer levels) {
        return categoryDao.selectOne(levels);
    }


    @AddLog("修改类别")
    //修改  一级  二级  类别
    @Override
    @deleteCache   //删除缓存
    public void update(Category category) {
        categoryDao.update(category);
    }



    @AddLog("删除类别")
    //删除一级  二级类别
    @Override
    @deleteCache   //删除缓存
    public String delete(Category category) {
        String message;
        Integer count = categoryDao.count(category.getId());  //
        if(count == 0){
            categoryDao.delete(category.getId());
            message = "删除成功";
        }else{
            message = "该类别下有二级类别  无法删除！！！";
        }

        if(category.getParentId()!=null){
            categoryDao.delete(category.getId());
            message = "删除成功";
        }
        return message;
    }

    @addCache     //添加缓存
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllCategory() {
        return categoryDao.queryAllCategory();
    }
}
