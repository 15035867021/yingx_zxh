package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("admin")
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private AdminService adminService;

    @RequestMapping("creatImage")
    //生成随机验证码
    public String creatImage(HttpServletResponse response, HttpServletRequest request) throws IOException {
        //获取随机字符
        String serviceCode = ImageCodeUtil.getSecurityCode();

        log.debug("随机验证码:"+serviceCode);

        //将获取的验证码生成图片  并响应到前台页面
        BufferedImage image = ImageCodeUtil.createImage(serviceCode);
        //获取响应输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //将生成的验证码响应到前台
        ImageIO.write(image, "gif", outputStream);
        //将获取的验证码存入session中
        request.getSession().setAttribute("serviceCode", serviceCode);
        return null;
    }


    //管理员登陆
    @RequestMapping("login")
    @ResponseBody
    public HashMap<String, Object> login(Admin admin, HttpServletRequest request, String code){
        return adminService.queryByUsername(admin, request, code);
    }


    //安全退出
    @RequestMapping("loginOut")
    public String loginOut(HttpServletRequest request){
        request.getSession().removeAttribute("admin");
        return "redirect:/login/login.jsp";
    }
}
