package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.baizhi.entity.City;
import com.baizhi.entity.Mc;
import com.baizhi.entity.Sc;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.AliyunUtil;
import com.baizhi.util.ImageCodeUtil;
import io.goeasy.GoEasy;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    //查每个月  各个性别 的注册人数
    @ResponseBody
    @RequestMapping("queryMonthSex")
    public HashMap<String,Object> queryMonthSex(){
        HashMap<String, Object> map = new HashMap<>();

        ArrayList<Object> monthList = new ArrayList<>();
        ArrayList<Object> boyList = new ArrayList<>();
        ArrayList<Object> girlList = new ArrayList<>();

        for (int i = 1 ; i <= 12; i++) {
            monthList.add(i+"月");     //所有月份
            //所有男生的  各个月份的注册人数
            Integer counts = 0;
            List<Mc> man = userService.queryAllMonth("男", i);
            for (Mc mc : man) {
                counts = mc.getCounts();
            }
            boyList.add(counts);

            //所有女生的  各个月份的注册人数
            List<Mc> girl = userService.queryAllMonth("女", i);
            Integer counts1 = 0;
            for (Mc mc : girl) {
                counts1 = mc.getCounts();
            }
            girlList.add(counts1);
        }
        map.put("month", monthList);
        map.put("boys", boyList);
        map.put("girls",girlList);

        String jsonString = JSON.toJSONString(map);

        //创建goEasy初始化对象   参数1： REST Host：机房地区   zppkey:  应用appkey
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-d8c82401a6b443348f0ef93d03b1e2ab");
        //发送消息     参数:  通道名称    消息的内容
        goEasy.publish("2005_channel", jsonString);

        return map;
    }


    //查各个性别 各个地区的注册人数
    @ResponseBody
    @RequestMapping("querySexCity")
    public ArrayList<Sc> querySexCity(){

        ArrayList<Sc> sc = new ArrayList<>();
        //所有的各个地区的男生的注册人数
        List<City> cities = userService.queryAllSexCity("男");
        sc.add(new Sc("小男孩",cities));

        //所有的各个地区的女生的注册人数
        List<City> citie = userService.queryAllSexCity("女");
        sc.add(new Sc("小姑娘",citie));

        return sc;
    }




    //发送手机验证码
    @RequestMapping("sendyzm")
    @ResponseBody
    public HashMap<String, Object> sendyzm(String phoneNumbers){
        String message = null;
        HashMap<String, Object> map = new HashMap<>();
        try {
            //先调用工具类获取随机数字
            String securityCode = ImageCodeUtil.getSecurityCode();
            //发送验证码
            message = AliyunUtil.sendPhoneMsg(phoneNumbers, securityCode);
            map.put("message", message);
            map.put("status", "200");
            System.out.println(map.get("message"));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", message);
            map.put("status", "201");
        }
        return map;
    }



    @RequestMapping("queryAll")
    @ResponseBody
    public HashMap<String, Object> queryAll(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //每页的数据   rows
        List<User> users = userService.queryAll(page, rows);
        //计算总条数  计算总页数
        Integer totalcount = userService.queryConut();
        //计算总页数
        Integer totalPage;
        if (totalcount % rows == 0) {
            totalPage = totalcount / rows;
        }else{
            totalPage = totalcount / rows+1;
        }

        map.put("page",page); //当前页
        map.put("records", totalcount);    //总条数
        map.put("rows", users);    //每页的数据
        map.put("total", totalPage);    //总页数
        return map;
    }

    /*@ResponseBody
    @RequestMapping("updateStatus")
    public String updateStatus(User user){
        userService.update(user);
        return "";
    }*/

    //增删改
    @ResponseBody
    @RequestMapping("edit")
    public String edit(String oper,User user){
        String result = null;
        if(oper.equals("add")){
            result = userService.add(user);
        }
        if(oper.equals("edit")){
            userService.update(user);
        }
        if(oper.equals("del")){

        }
        return result;
    }

    /*@RequestMapping("UploadAliyun")
    public void UploadAliyun(MultipartFile picImg, String id) {

    }*/

    //查所有  导出
    @RequestMapping("queryAlls")
    @ResponseBody
    public void queryAlls() {
        //查所有
        List<User> users = userService.queryAlls();
        //参数：标题，表名，                 实体类类对象，             导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息", "学生"), User.class, users);

        try {
            workbook.write(new FileOutputStream(new File("E:/easypoi.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //导入
    @RequestMapping("InputStream")
    @ResponseBody
    public void InputStream(){

        //导入设置的参数
        ImportParams params = new ImportParams();
        params.setTitleRows(1);  //标题所占行
        params.setHeadRows(1);   //表头所占行

        //导入
        List<User> users = ExcelImportUtil.importExcel(new File("E:/easypoi.xls"), User.class, params);

        users.forEach(teacher -> System.out.println(users));

    }

}
