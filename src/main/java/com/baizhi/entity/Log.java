package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Table(name = "yx_log")
@Data     //自动生成  get/set  tostring 方法
@NoArgsConstructor     //无参构造方法
@AllArgsConstructor       //所有有参构造
public class Log implements Serializable {
    @Id
    private String id;     //日志id

    private String name;    //当前管理员 用户
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date times;      //执行时间

    private String options;     //执行的什么操作

    private String status;        //执行的状态
}