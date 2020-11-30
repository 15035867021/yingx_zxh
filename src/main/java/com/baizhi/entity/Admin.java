package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data     //自动生成  get/set  tostring 方法
@NoArgsConstructor     //无参构造方法
@AllArgsConstructor       //所有有参构造
public class Admin {
    private String id;
    private String username;
    private String password;
}
