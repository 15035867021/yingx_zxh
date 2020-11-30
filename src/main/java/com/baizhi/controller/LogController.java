package com.baizhi.controller;

import com.baizhi.entity.Log;
import com.baizhi.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("log")
public class LogController {
    @Resource
    private LogService logService;


    //分页查所有
    @ResponseBody
    @RequestMapping("queryByPage")
    public Map<String, Object> queryByPage(Integer page, Integer rows) {

        Map<String, Object> map = new HashMap<>();

        //查数据
        List<Log> logs = logService.queryByPage(page, rows);
        map.put("rows", logs);    //每页的数据

        Integer count = logService.queryCount();  //总条数
        map.put("records", count);   //总条数

        //总条数
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        map.put("total", total);  //总条数

        //当前页
        map.put("page", page);    //当前页
        return map;
    }

    //删除日志
    @ResponseBody
    @RequestMapping("edit")
    public void edit(Log log,String oper){
        if(oper.equals("del")){
            logService.delete(log);
        }
    }


}
