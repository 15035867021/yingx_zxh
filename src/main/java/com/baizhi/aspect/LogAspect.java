package com.baizhi.aspect;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

@Configuration   //指明该类是一个配置类
@Aspect     //指明是一个切面类
public class LogAspect {
    @Resource
    private LogMapper logDao;

    @Resource
    HttpServletRequest request;

    @Around("@annotation(com.baizhi.annotation.AddLog)")   //环绕通知
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint){
        //日志记录的是   谁在 什么时间 操作了什么功能  是否成功

        //先获取 当前的管理员  在session中获取
        Admin admin = (Admin) request.getSession().getAttribute("admin");

        //获取方法名    getSignature
        String methodName = proceedingJoinPoint.getSignature().getName();

        //获取切入的方法  在获取注解
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();  //f法

        //获取注解   AddLog的注解
        AddLog addLog = method.getAnnotation(AddLog.class);
        String value = addLog.value();  //获取注解对应的属性值

        //放行方法
        String message;
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();  //放行成功
            message = "success";
        } catch (Throwable throwable) {
            message = "error";
        }

        Log log = new Log();
        log.setId(UUID.randomUUID().toString());  //id
        log.setName(admin.getUsername());    //当前管理员
        log.setTimes(new Date());            //什么时间
        log.setOptions(methodName+" {"+value+"}");                                   //执行了什么操作
        log.setStatus(message);                           //状态

        System.out.println("数据入库");
        logDao.insertSelective(log);

        return result;
    }
}
