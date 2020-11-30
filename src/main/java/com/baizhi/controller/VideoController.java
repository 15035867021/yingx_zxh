package com.baizhi.controller;

import com.baizhi.entity.Video;
import com.baizhi.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("video")
public class VideoController {

    @Resource
    private VideoService videoService;

    //分页查
    @ResponseBody
    @RequestMapping("queryByPage")
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        List<Video> videos = videoService.queryByPage(page, rows);  //每页的数据

        videos.forEach(video-> System.out.println("=============="+video));

        map.put("rows", videos);  //每页数据

        //计算总页数  查总条数
        Integer count = videoService.selectCount();  //总条数
        map.put("records", count);   //总条数

        //总页数
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        map.put("total", total);     //总页数
        map.put("page", page);   //当前页

        return map;
    }

    //添加
    @ResponseBody
    @RequestMapping("edit")
    public String edit(Video video,String oper){
        String result = null;
        if(oper.equals("add")){
            result = videoService.add(video);
        }
        if(oper.equals("edit")){
            result = videoService.update(video);
        }
        if(oper.equals("del")){
            videoService.deleteAliyun(video);
        }
        return result;
    }

    //文件上传
    @RequestMapping("upload")
    public void upload(MultipartFile videoPath, String id){
        videoService.UploadAliyun(videoPath, id);
    }

    @RequestMapping("updateVideo")
    //文件修改
    public void updateVideo(MultipartFile videoPath, String id){
        videoService.updateAliyun(videoPath, id);
    }
}
