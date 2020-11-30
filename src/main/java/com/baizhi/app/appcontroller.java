package com.baizhi.app;

import com.baizhi.common.CommonResult;
import com.baizhi.entity.Category;
import com.baizhi.po.VideoPo;
import com.baizhi.service.CategoryService;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunUtil;
import com.baizhi.util.ImageCodeUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//前台app接口
@RestController
@RequestMapping("app")
public class appcontroller {

    @Resource
    private VideoService videoService;

    @Resource
    private CategoryService categoryService;


    //发送手机验证码
    @RequestMapping("getPhoneCode")
    public Object getPhoneCode(String phone){

        //调用工具类生成随机验证码
        String securityCode = ImageCodeUtil.getSecurityCode();
        String message = null;
        try {
            //调用工具类发送验证码
            message = AliyunUtil.sendPhoneMsg(phone, securityCode);
            return new CommonResult().success(message, phone);  //发送成功
        } catch (Exception e) {
            return new CommonResult().failed(message);   //发送失败
        }
    }

    //首页展示视频
    @RequestMapping("queryByReleaseTime")
    public Object queryByReleaseTime(){
        try {
            List<VideoPo> videoPos = videoService.queryByReleaseTime();  //查询成功
            return new CommonResult().success("请求成功", videoPos);
        } catch (Exception e) {
            return new CommonResult().failed("请求失败");
        }
    }

    //前台查所有类别   一级 /  二级
    @RequestMapping("queryAllCategory")
    public Object queryAllCategory(){
        try {
            List<Category> categories = categoryService.queryAllCategory();
            return new CommonResult().success("请求成功", categories);

        } catch (Exception e) {
            return new CommonResult().failed("请求失败");
        }
    }

    //搜索
    @RequestMapping("queryByLikeVideoName")
    public Object queryByLikeVideoName(String content){
        try {
            List<VideoPo> videoPos = videoService.queryByLikeVideoName(content);
            return new CommonResult().success("请求成功", videoPos);
        } catch (Exception e){
            return new CommonResult().failed("请求失败");
        }
    }
}
