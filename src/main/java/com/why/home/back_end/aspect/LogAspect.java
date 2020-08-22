package com.why.home.back_end.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/*---------------------------------------------------------------
              LogAspect Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/19.
;
;  基于 AOP (Aspect Oriented Programming) 面向切面编程知识。
;
;  Function:
;          日志处理：
;              请求url、访问者ip、调用方法classMethod、参数args、返回内容
;
----------------------------------------------------------------*/

/*标注为切面，用来进行切面操作*/
@Aspect
/*开启组件扫描，SpringBoot可以扫描到该切面*/
@Component
public class LogAspect {

    /*利用org.slf4j获取日志*/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*注解声明该方法为一个切面*/
    /*execution()内参数表示需要拦截的类，此处表示拦截web下所有的控制器*/
    @Pointcut("execution(* com.why.home.back_end.web.*.*(..))")
    public void log(){}

    /*注解在切面操作log()执行前的方法(开始)*/
    @Before("log()")
    /*此处传递一个JoinPiont切面对象用于获取类名+方法名和请求对象信息*/
    public void doBefore(JoinPoint joinPoint){
        /*获取当前Request实例*/
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        /*根据当前Request实例获取url和ip信息*/
        String url=request.getRequestURL().toString();
        String ip=request.getRemoteAddr();
        /*根据切面获取类名+方法名和请求对象信息*/
        String classMethod=joinPoint.getSignature().getDeclaringTypeName()+joinPoint.getSignature().getName();
        Object[] args=joinPoint.getArgs();
        /*构建RequestMessageLog对象用于信息输出*/
        RequestMessageLog requestmessagelog=new RequestMessageLog(url,ip,classMethod,args);
        /*日志信息输出*/
        logger.info("---doBefore---Request:"+requestmessagelog);
    }

    /*注解在切面操作log()执行后的方法(最后？)*/
    @After("log()")
    public void doAfter(){
        //logger.info("---------doAfter----------");
    }

    /*注解在切面操作log()返回后的方法(中间？)*/
    @AfterReturning(returning = "res",pointcut = "log()")
    public void doAfterReturning(Object res){
        logger.info("---doAfterReturn---Result : {" + res + "}");
    }

    /*将请求url、访问者ip、调用方法classMethod、参数args、返回内容封装为一个类*/
    private class RequestMessageLog{
        private String url;
        private String ip;
        private String classMethod;
        /*请求对象数组，可能是比较复杂的数据类型，所以用Object[]*/
        private Object[] args;

        /*生成构造方法*/
        public RequestMessageLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        /*生成toString方法，主要用于日志输出*/
        @Override
        public String toString() {
            return "RequestMessageLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
