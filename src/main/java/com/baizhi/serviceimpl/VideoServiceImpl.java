package com.baizhi.serviceimpl;

import com.baizhi.annotation.AddLog;
import com.baizhi.annotation.addCache;
import com.baizhi.annotation.deleteCache;
import com.baizhi.dao.VideoMapper;
import com.baizhi.entity.Video;
import com.baizhi.entity.VideoExample;
import com.baizhi.po.VideoPo;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunOSSUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("videoService")
@Transactional
public class VideoServiceImpl implements VideoService {
    @Resource
    private VideoMapper videoDao;

    @addCache     //添加缓存
    @Transactional(propagation = Propagation.SUPPORTS)
    //分页
    @Override
    public List<Video> queryByPage(Integer page, Integer rows) {
        //相当于是一个条件，没有条件对所有数据进行分页
        VideoExample example = new VideoExample();
        //分页查询： 参数：忽略几条,获取几条数据
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Video> videos = videoDao.selectByExampleAndRowBounds(example, rowBounds);
        return videos;
    }

    @AddLog("添加视频/封面")
    //添加
    @Override
    @deleteCache   //删除缓存
    public String add(Video video) {
        String uuid = UUID.randomUUID().toString();
        video.setId(uuid);
        video.setUserId("1");
        video.setStatus(1);
        video.setUploadTime(new Date());
        videoDao.insert(video);
        return uuid;
    }

    @addCache     //添加缓存
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer selectCount() {
        Video video = new Video();
        int count = videoDao.selectCount(video);
        return count;
    }

    @addCache     //添加缓存
    @Override
    public Video selectOne(String id) {
        Video video = new Video();
        video.setId(id);
        return videoDao.selectOne(video);
    }

    //文件本地上传
    @Override
    public void headupload(MultipartFile videoPath, String id, HttpServletRequest request) {
        //文件上传
        //根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/img");

        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();   //创建文件夹
        }
        //获取文件名  拼接事件戳
        String filename = videoPath.getOriginalFilename();
        String newName = new Date().getTime() + "-" + filename;  //新文件名
        //文件上传
        try {
            videoPath.transferTo(new File(realPath, newName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //修改视频路径
        ///设置修改条件  通过id
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);
        Video video = new Video();
        video.setVideoPath(newName);
        videoDao.updateByExampleSelective(video, example);
    }

    //视频上传  阿里云
    @Override
    public void UploadAliyun(MultipartFile videoPath, String id) {
        //1.视频上传到阿里云
        //获取文件名
        String filename = videoPath.getOriginalFilename();
        String newName = new Date().getTime() + "-" + filename;

        //拼接视频文件
        String videoName = "video/" + newName;
        /*上传视频至阿里云
         *  videoPath  MultipartFile类型的文件
         *   bucketName 存储空间名
         *   objectName 文件名   上传的文件名
         * */
        AliyunOSSUtil.uploadFileByte(videoPath, "yingx-205", videoName);

        //2.截取文件名   截取视频第一帧  并上传封面 (通过网络文件)
        String[] split = newName.split("\\.");
        //拼接图片名
        String coverName = split[0] + ".jpg";

        /*
         * 截取视频第一帧
         * 参数:
         *   bucketName 存储空间名
         *   videoName:视频名
         *   coverName:封面名
         * */
        AliyunOSSUtil.interceptVideoCover("yingx-205", videoName, coverName);

        //5.数据修改
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);
        Video video = new Video();
        video.setVideoPath("https://yingx-205.oss-cn-beijing.aliyuncs.com/" + videoName);
        video.setCoverPath("https://yingx-205.oss-cn-beijing.aliyuncs.com/" + coverName);
        videoDao.updateByExampleSelective(video, example);
    }


    //删除   阿里云删除
    @AddLog("删除视频/封面")
    @deleteCache   //删除缓存
    @Override
    public void deleteAliyun(Video video) {
        //先查一个  获取视频路径
        //设置查询条件
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(video.getId());
        //查询数据   获取封面名 视频名
        Video video1 = videoDao.selectOneByExample(example);
        //截取 视频/封面名
        String videoPath = video1.getVideoPath();   //视频名
        String coverPath = video1.getCoverPath();   //封面名
        //通过 / 截取
//        String[] split = videoPath.split("/");
//        int length = split.length;
//        String objectName="video/"+split[length-1];  //保存的文件名
        //视频名
        String replace1 = videoPath.replace("https://yingx-205.oss-cn-beijing.aliyuncs.com/", "");
        //封面   replace 方法 参数1:  需要替换的字符 串   参数2: 需要替换为的字符串
        String replace = coverPath.replace("https://yingx-205.oss-cn-beijing.aliyuncs.com/", "");

        //删除阿里云视频
        AliyunOSSUtil.deleteFile("yingx-205", replace1);
        AliyunOSSUtil.deleteFile("yingx-205", replace);

        //删除数据库
        //添加删除条件
        videoDao.deleteByPrimaryKey(video.getId());
    }

    @AddLog("修改视频/封面")
    @deleteCache   //删除缓存
    @Override
    public String update(Video video) {
        System.out.println(video+"asdasdasdasdasdasdasdasdasdsadasdasd");
        //Video video1 = videoDao.selectOne(video);
        //查一个
        Video video1 = videoDao.selectByPrimaryKey(video);
//        System.out.println(video1);
        String id = video.getId();
        //修改数据
        Video video2 = new Video();
        video2.setTitle(video1.getTitle());
        video2.setBrief(video1.getBrief());
        VideoExample example = new VideoExample();
        //指定修改条件
        example.createCriteria().andIdEqualTo(video.getId());
        videoDao.updateByExampleSelective(video2, example);
        return id;
    }


    //修改  阿里云视频
    @Override
    public void updateAliyun(MultipartFile videoPath, String id) {

        if (videoPath.getSize() != 0) {   //判断上传的视频是否为空
            //查一个
            Video video2 = videoDao.selectByPrimaryKey(id);
            System.out.println("====================="+video2);
            String videoPaths = video2.getVideoPath();
            String coverPaths = video2.getCoverPath();


            //截取视频名  封面名
            String videoPath1 = videoPaths.replace("https://yingx-205.oss-cn-beijing.aliyuncs.com/", "");
            String coverPath1 = coverPaths.replace("https://yingx-205.oss-cn-beijing.aliyuncs.com/", "");

            //1.删除视频  删除封面
            AliyunOSSUtil.deleteFile("yingx-205", videoPath1);
            AliyunOSSUtil.deleteFile("yingx-205", coverPath1);

            //2.上传视频 截取封面  修改数据
            //获取文件名
            String filename = videoPath.getOriginalFilename();
            String newName = new Date().getTime()+"-"+filename;


            //拼接视频文件
            String videoName = "video/" + newName;
            //上传视频到阿里云
            AliyunOSSUtil.uploadFileByte(videoPath, "yingx-205", videoName);

            //截取文件名
            String[] split1 = newName.split("\\.");
            String coverName = split1[0]+".jpg";

            //截取视频第一帧  并上传图片
            AliyunOSSUtil.interceptVideoCover("yingx-205", videoName, coverName);

            //4.修改视频的信息
            Video video = new Video();
            video.setId(id);

            video.setVideoPath("http://yingx-205.oss-cn-beijing.aliyuncs.com/"+videoName);
            video.setCoverPath("http://yingx-205.oss-cn-beijing.aliyuncs.com/"+coverName);
            videoDao.updateByPrimaryKeySelective(video);
        }
    }

    @addCache     //添加缓存
    //查所有视频 及所属类别 用户头像
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<VideoPo> queryByReleaseTime() {
        List<VideoPo> videoPos = videoDao.queryByReleaseTime();

//        videoPos.forEach(video-> System.out.println(video));
        return videoPos;
    }


    //模糊查询
    @addCache     //添加缓存
    @Override
    public List<VideoPo> queryByLikeVideoName(String content) {
        List<VideoPo> videoPos = videoDao.queryByLikeVideoName(content);
//        videoPos.forEach(video-> System.out.println(video));
        return videoPos;
    }
}
