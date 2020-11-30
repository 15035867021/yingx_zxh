package com.baizhi.serviceimpl;

import com.baizhi.annotation.addCache;
import com.baizhi.dao.FeedBackMapper;
import com.baizhi.entity.FeedBack;
import com.baizhi.entity.FeedBackExample;
import com.baizhi.service.FeedBackService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("feedBackService")
@Transactional
public class FeedBackServiceImpl implements FeedBackService {

    @Resource
    private FeedBackMapper feedBackMapper;

    @addCache     //添加缓存
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FeedBack> queryByPage(Integer page, Integer rows) {
        //相当于是一个条件，没有条件对所有数据进行分页
        FeedBackExample example = new FeedBackExample();
        //分页查询： 参数：忽略几条,获取几条数据
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        return feedBackMapper.selectByExampleAndRowBounds(example, rowBounds);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @addCache     //添加缓存
    @Override
    //查反馈的数条数
    public Integer queryCount() {
        FeedBack feedBack = new FeedBack();
        return feedBackMapper.selectCount(feedBack);
    }
}
