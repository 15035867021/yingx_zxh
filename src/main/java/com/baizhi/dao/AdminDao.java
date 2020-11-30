package com.baizhi.dao;

import com.baizhi.entity.Admin;

public interface AdminDao {

    //查一个   通过用户名
    Admin selectByUsername(String username);
}
