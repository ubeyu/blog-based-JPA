package com.why.home.back_end.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*----------------------------------------------------------------------------------
              WebConfig <实现WebMvcConfigurer> Release 1.0
;        Copyright (c) 2020-2020 by why Co.
;        Written by why on 2020/8/23.
;
;     Function:
;          拦截器配置类-----WebConfig
;            用于配置需要拦截的页面url
-----------------------------------------------------------------------------------*/

/*---SpringBoot配置类注解---*/
@Configuration
/*------WebMvcConfigurerAdapter is deprecated已过时 不再继承 改用实现WebMvcConfigurer接口 -----*/
public class WebConfig implements WebMvcConfigurer {

    /* 直接通过生成器生成 重写addInterceptors方法用于添加过滤url */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /* addInterceptor用来将定义的过滤器通过new加进来 */
        registry.addInterceptor(new AdminInterceptor())
                /* addPathPatterns用来将需要过滤的url路径加进去 此处将admin下所有路径都添加进去 */
                .addPathPatterns("/admin/**")
                /* excludePathPatterns用来将不需要过滤的url路径排除 admin为了防止循环 admin/login为了防止form表单无法提交 */
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
