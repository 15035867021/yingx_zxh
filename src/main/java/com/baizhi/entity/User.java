package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data     //自动生成  get/set  tostring 方法
@NoArgsConstructor     //无参构造方法
@AllArgsConstructor       //所有有参构造
public class User implements Serializable {
    @Excel(name = "ID")
    private String id;
    @Excel(name = "昵称")
    private String nickName;  //昵称
    @Excel(name = "电话")
    private String phone;     //电话
    @Excel(name = "头像", type = 2)
    private String picImg;      //头像
    @Excel(name = "简介")
    private String brief;       //简介
    @Excel(name = "创建时间", exportFormat = "yyyy-MM-dd", importFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date creatDate;       //创建时间
    @Excel(name = "状态")
    private String status;    //状态
    @Excel(name = "学分")
    private Double score;      //学分
    @Excel(name = "性别")
    private String sex;       //性别
    @Excel(name = "城市")
    private String city;        //城市
}
