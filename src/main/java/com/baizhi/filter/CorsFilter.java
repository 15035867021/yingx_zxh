package com.baizhi.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 解决Ajax的跨域问题
 * 为响应设置响应头
 * Access-Control-Allow-Origin ---> 设置访问白名单
 */
@Component   //将一个类交由工厂管理
public class CorsFilter implements Filter {
    //日志
    final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CorsFilter.class);

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        //创建响应对象
        HttpServletResponse response = (HttpServletResponse) res;

        //获取前台传过来的的域名,   参数：Origin：是前台传递域名的key    键  Origin: http://localhost:8989
        String originHeader=((HttpServletRequest) req).getHeader("Origin");

        ArrayList<String> domainList = new ArrayList<>();
        domainList.add("http://localhost:8989");   //添加允许访问的域名  ip端口

        //判断该域名是否在白名单(集合)中
        if(domainList.contains(originHeader)){
            response.setHeader("Access-Control-Allow-Origin", originHeader);  //设置头
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  //设置请求方式
            response.setHeader("Access-Control-Allow-Credentials", "true");  //设置凭证信息
        }

        chain.doFilter(req, res);
    }
    public void init(FilterConfig filterConfig) {}
    public void destroy() {}
}