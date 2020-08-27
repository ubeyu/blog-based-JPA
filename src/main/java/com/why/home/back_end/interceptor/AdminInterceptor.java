package com.why.home.back_end.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*----------------------------------------------------------------------------------
        AdminInterceptor <继承HandlerInterceptorAdapter> Release 1.0
;            Copyright (c) 2020-2020 by why Co.
;            Written by why on 2020/8/23.
;
;     Function:
;            拦截器-----未登录用户禁止访问博客管理和发布等页面
;       设置登录拦截器 使凡是涉及到admin下的url的页面 必须登录后才可访问
;      在AOP中，拦截器用于在某个方法或者字段被访问之前，进行拦截然后再之前或者之后加入某些操作。
-----------------------------------------------------------------------------------*/


public class AdminInterceptor extends HandlerInterceptorAdapter {

    /* 直接通过生成器生成 在请求未到达之前进行预处理用preHandle方法 */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        /* 利用request判断session内user是否为空 */
        if(request.getSession().getAttribute("user") == null){
            /* 若为空 将response重定向到登录页面 */
            response.sendRedirect("/admin");
            /* preHandle返回false 则不再往下执行 */
            return false;
        }
        /* session内有user 继续往下执行 */
        return true;
    }
}
