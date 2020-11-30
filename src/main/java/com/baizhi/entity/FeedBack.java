package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "yx_feedback")
@Data     //自动生成  get/set  tostring 方法
@NoArgsConstructor     //无参构造方法
@AllArgsConstructor       //所有有参构造
public class FeedBack implements Serializable {
    @Id
    private String id;

    private String title;   //反馈标题

    private String content;  //反馈内容
    @Column(name = "user_id")
    private String userId;      //用户id
}