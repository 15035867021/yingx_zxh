package com.baizhi.serviceimpl;

import com.baizhi.annotation.addCache;
import com.baizhi.annotation.deleteCache;
import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Log;
import com.baizhi.entity.LogExample;
import com.baizhi.service.LogService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("logService")
@Transactional
public class LogServiceImpl implements LogService {
    @Resource
    private LogMapper logDao;


    @addCache     //添加缓存
    //分页查所有
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Log> queryByPage(Integer page, Integer rows) {
        //相当于是一个条件，没有条件对所有数据进行分页
        LogExample example = new LogExample();
        //分页查询： 参数：忽略几条,获取几条数据
        RowBounds rowBounds = new RowBounds((page - 1) * rows,rows);
        List<Log> logs = logDao.selectByExampleAndRowBounds(example, rowBounds);
        return logs;
    }

    @addCache     //添加缓存
    //查所有条数
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer queryCount() {
        Log log = new Log();
        return logDao.selectCount(log);
    }


    @deleteCache   //删除缓存
    //删除
    public void delete(Log log){
        logDao.delete(log);
    }


}
