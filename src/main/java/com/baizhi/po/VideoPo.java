package com.baizhi.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoPo {
    private String id;     //视频id
    private String videoTitle;   //视频标题
    private String cover;       //视频封面
    private String path;       //视频路径
    private Date uploadTime;      //视频上传时间
    private String description;     //视频描述
    private Integer likeCount;        //视频点赞数
    private String cateName;       //所属二级类别
    private String categoryId;       //所属类别id
    private String userPhone;             //所属用户头像
    private String userId;         //所属用户id
    private String userName;         //所属用户名字
}