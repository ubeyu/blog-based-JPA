package com.why.home.back_end.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/*---------------------------------------------------------------
              ControllerExceptionHandler Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/19.
;
;  Function:
;          拦截器 异常处理
;
----------------------------------------------------------------*/


/*拦截标注为Controller的控制器*/
@ControllerAdvice
public class ControllerExceptionHandler {

    /*利用org.slf4j获取日志*/
    private final Logger logger=LoggerFactory.getLogger(this.getClass());

    /*定义返回含有错误信息页面的方法*/
    /*添加注解表明方法为“异常处理”，内部Exception.class表示拦截的信息为Exception及其子类异常*/
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
        /*通过request获取出现异常的路径URL，通过e获取异常信息*/
        logger.error("Request URL : {} , Exception : {}",request.getRequestURL(),e);

        /*判断当前异常是否标注状态码，若标注@Pointcut则不采用拦截器，而将其交给SpringBoot本身处理*/
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }

        /*准备返回页面*/
        ModelAndView mv=new ModelAndView();
        /*前端页面显示URL和异常信息*/
        /*此处url和exception变量名对应error.html中变量名，一定一致！！！*/
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        /*返回错误error页面*/
        mv.setViewName("error/error");
        return mv;
    }
}
