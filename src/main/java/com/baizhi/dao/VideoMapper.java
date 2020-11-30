package com.baizhi.dao;

import com.baizhi.entity.Video;
import com.baizhi.po.VideoPo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoMapper extends Mapper<Video> {

    //前台查所有视频 及所在类别 用户头像
    public List<VideoPo> queryByReleaseTime();

    //前台  模糊 查视频
    public List<VideoPo> queryByLikeVideoName(String content);

}