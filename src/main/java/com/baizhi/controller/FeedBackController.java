package com.baizhi.controller;

import com.baizhi.entity.FeedBack;
import com.baizhi.service.FeedBackService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("feedBack")
public class FeedBackController {
    @Resource
    private FeedBackService feedBackService;

    //分页查所有
    @ResponseBody
    @RequestMapping("queryByPage")
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();

        //每页数据
        List<FeedBack> feedBacks = feedBackService.queryByPage(page, rows);
        map.put("rows", feedBacks);

        //总条数
        Integer count = feedBackService.queryCount();
        map.put("records", count);

        //总页数
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        map.put("total", total);

        //当前页
        map.put("page", page);
        return map;
    }
}
