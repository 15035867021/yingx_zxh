package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface AdminService {
    //登陆
    HashMap<String, Object> queryByUsername(Admin admin, HttpServletRequest request, String code);
}
