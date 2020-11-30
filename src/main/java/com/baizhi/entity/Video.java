package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Table(name = "yx_video")
@Data     //自动生成  get/set  tostring 方法
@NoArgsConstructor     //无参构造方法
@AllArgsConstructor       //所有有参构造
public class Video implements Serializable {
    @Id
    private String id;

    private String title;  //视频名字  标题

    private String brief;      //视频简介
    @Column(name = "cover_path")
    private String coverPath;     //视频封面

    @Column(name = "video_path")
    private String videoPath;    //视频

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "upload_time")
    private Date uploadTime;    //上传时间

    @Column(name = "like_count")
    private Integer likeCount;   //点赞数

    @Column(name = "play_count")
    private Integer playCount;    //播放数

    @Column(name = "category_id")
    private String categoryId;   //类别id

    @Column(name = "user_id")
    private String userId;      //用户id

    @Column(name = "group_id")
    private String groupId;        //分组id

    private Integer status;
}