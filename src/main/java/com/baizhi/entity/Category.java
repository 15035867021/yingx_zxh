package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data    //提供get / set 方法
@NoArgsConstructor    //无参方法
@AllArgsConstructor   //所有有参方法
public class Category implements Serializable {
    private String id;
    private String cateName;
    private Integer levels;
    private String parentId;

    //一级类别对应的二级类别
    private List<Category> categoryList;
}
