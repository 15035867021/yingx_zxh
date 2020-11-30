package com.baizhi.service;

import com.baizhi.entity.Video;
import com.baizhi.po.VideoPo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface VideoService {

    //分页查所有
    public List<Video> queryByPage(Integer page,Integer rows);

    //添加
    public String add(Video video);

    //查所有数据
    public Integer selectCount();

    //查一个
    public Video selectOne(String id);

    //文件本地上传
    public void headupload(MultipartFile videoPath,String id,HttpServletRequest request);

    //文件上传阿里云
    public void UploadAliyun(MultipartFile videoPath,String id);

    //删除
    public void deleteAliyun(Video video);

    //修改
    public String update(Video video);

    //修改  阿里云
    public void updateAliyun(MultipartFile videoPath,String id);

    //前台  查所有视频 及所属类别  所属用户头像
    public List<VideoPo> queryByReleaseTime();

    //前台  模糊 查视频
    public List<VideoPo> queryByLikeVideoName(String content);
}
