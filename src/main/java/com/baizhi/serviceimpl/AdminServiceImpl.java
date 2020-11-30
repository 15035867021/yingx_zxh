package com.baizhi.serviceimpl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String, Object> queryByUsername(Admin admin, HttpServletRequest request, String code) {
        //先判断用户输入的验证码合服务器生成的验证码是否一致
        //先在session作用域中获取服务器生成的验证码
        String serviceCode = (String) request.getSession().getAttribute("serviceCode");
        //判断
        HashMap<String, Object> map = new HashMap<>();
        if(serviceCode.equals(code)){  //验证码一致   判断用户名
            Admin admins = adminDao.selectByUsername(admin.getUsername());
            if(admins!=null){  //用户名存在  判断密码是否一致
                if(admin.getPassword().equals(admins.getPassword())){  //密码一致  登陆成功
                    //登陆标记存入session 中
                    request.getSession().setAttribute("admin", admins);
                    map.put("message", "登陆成功");
                    map.put("status", "200");
                }else{
                    map.put("message", "密码错误");
                    map.put("status", "201");
                }
            }else {
                map.put("message", "用户名不存在");
                map.put("status", "201");
            }
        }else{
            map.put("message", "验证码不一致");
            map.put("status", "201");
        }
        return map;
    }
}
