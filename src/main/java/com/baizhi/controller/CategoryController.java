package com.baizhi.controller;

import com.baizhi.entity.Category;
import com.baizhi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //分页查一级类别
    @RequestMapping("queryByPage")
    @ResponseBody
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        List<Category> byPage = categoryService.findByPage(1, page, rows);
        map.put("rows", byPage);  //每页显示的数据

//        byPage.forEach(cate-> System.out.println(cate));

        Integer count = categoryService.queryCount(1);  //总条数
        map.put("records", count);

        Integer total;
        if (count % rows == 0) {
            total = count / rows;
        } else {
            total = count / rows + 1;
        }
        map.put("total", total);
        map.put("page", page);
        return map;
    }



    //分页查一级类别
    @RequestMapping("queryByPageSencond")
    @ResponseBody
    public Map<String, Object> queryByPageSencond(String parentId,Integer page, Integer rows) {
        System.out.println("S");
        HashMap<String, Object> map = new HashMap<>();
        List<Category> byPage = categoryService.queryBuPageSecond(2,parentId, page, rows);
        map.put("rows", byPage);  //每页显示的数据

//        byPage.forEach(cate-> System.out.println(cate));

        Integer count = categoryService.count(parentId);  //总条数
        map.put("records", count);

        Integer total;
        if (count % rows == 0) {
            total = count / rows;
        } else {
            total = count / rows + 1;
        }
        map.put("total", total);
        map.put("page", page);
        return map;
    }


    //添加一级类别
    @RequestMapping("edit")
    @ResponseBody
    public Map<String, Object> edit(Category category,String oper){
        HashMap<String, Object> map = new HashMap<>();
        if(oper.equals("add")){
            categoryService.add(category);
        }
        if(oper.equals("edit")){
            categoryService.update(category);
        }
        if(oper.equals("del")){
            String message = categoryService.delete(category);
            map.put("message", message);
        }
        return map;
    }

    //查所有的一级类别对应的名字
    @RequestMapping("queryOne")
    @ResponseBody
    public void queryOne(HttpServletResponse response) throws IOException {
        //查询所有     前台需要一个html的格式  之后响应出去
        List<Category> categories = categoryService.selectOne(1);

        StringBuffer sb = new StringBuffer();
        sb.append("<select>");

        //遍历部分  所有一级类别  下拉列表的格式显示
        categories.forEach(cate->{
                  sb.append("<option value="+cate.getId()+">"+cate.getCateName()+"</option>");
                });

        sb.append("<select>");

        //设置响应格式及类型
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.getWriter().println(sb.toString());
    }

    //查所有的一级类别对应的名字
    @RequestMapping("querySecond")
    @ResponseBody
    public void querySecond(HttpServletResponse response) throws IOException {
        //查询所有     前台需要一个html的格式  之后响应出去
        List<Category> categories = categoryService.selectOne(2);

        StringBuffer sb = new StringBuffer();
        sb.append("<select>");

        //遍历部分  所有一级类别  下拉列表的格式显示
        categories.forEach(cate->{
            sb.append("<option value="+cate.getId()+">"+cate.getCateName()+"</option>");
        });

        sb.append("<select>");

        //设置响应格式及类型
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.getWriter().println(sb.toString());
    }
}
