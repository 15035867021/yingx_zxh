package com.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
@Configuration   //指明该类是一个配置类
@Aspect             //指明该类是一个切面类
public class CachHashAspect {
    //注入redis操作对象
    @Resource
    private RedisTemplate redisTemplate;
    //添加缓存   环绕通知
    @Around("@annotation(com.baizhi.annotation.addCache)")
    public Object addCach(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("======进入环绕通知==================");
        /*
         * redis(key   value) 形式
         *
         * key: 类名+方法名+实参
         * value  缓存数据
         * */

        //解决序列化乱码
        StringRedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);   //hash类型  大 key
        redisTemplate.setHashKeySerializer(redisSerializer);    //小key

        //获取大key   类名+方法名+实参

        //类名
        String className = proceedingJoinPoint.getTarget().getClass().getName();

        //拼接小  key   方法名+.+实参
        StringBuilder sb = new StringBuilder();
        //方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        sb.append(methodName);
        //获取方法的参数名
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {   //遍历参数名  实参
            sb.append(arg);
        }

        String key = sb.toString();   //小key

        //判断key是否存在
        HashOperations opsForHash = redisTemplate.opsForHash();
        //获取key
        Boolean aBoolean = opsForHash.hasKey(className, key);

        Object result = null;
        if(aBoolean){   //存在
            //key 存在   通过key获取缓存数据
            result = opsForHash.get(className, key);
        }else{   //key不存在  放行方法  去数据库查数据  在存入缓存中
            try {
                result = proceedingJoinPoint.proceed();   //调用方法从数据库返回的数据
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            //存入缓存中
            opsForHash.put(className, key, result);
        }
        return result;
    }

    //删除缓存  后置通知   增删改 成功后 使用删除缓存
    @After("@annotation(com.baizhi.annotation.deleteCache)")
    public void deleteCache(JoinPoint joinPoint){
        System.out.println("后置通知==============");
        //取类的类名  大key
        String className = joinPoint.getTarget().getClass().getName();
        //清空缓存
        redisTemplate.delete(className);
    }
}
