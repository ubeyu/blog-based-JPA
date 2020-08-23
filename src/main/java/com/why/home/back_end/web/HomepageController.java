package com.why.home.back_end.web;

import com.why.home.back_end.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*---------------------------------------------------------------
              HomepageController Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/19.
;
;  Function:
;          Test 404/500/homepage
----------------------------------------------------------------*/


/*@Controller表示该类是控制层 控制层定义返回首页的控制器 用于测试*/
@Controller
public class HomepageController {

    /*通过Get请求路径 返回首页*/
    @GetMapping("/")
    public String homepage() {
      //  int i = 1 / 0;

        /*博客首页为空时进行异常处理，返回404
        String Blog = null;
        if(Blog == null){
            throw new NotFoundException("博客不存在");
        }*/
        //System.out.println("---------homepage---id:{" + id + "}--name:{" + name + "}---------");
        return "homepage";
    }

    /*通过Get请求路径 返回博客详情页*/
    @GetMapping("/blogs")
    public String blogs() {
        //  int i = 1 / 0;

        /*博客首页为空时进行异常处理，返回404
        String Blog = null;
        if(Blog == null){
            throw new NotFoundException("博客不存在");
        }*/
        //System.out.println("---------homepage---id:{" + id + "}--name:{" + name + "}---------");
        return "blogs";
    }

    /*通过Get请求路径 返回分类页*/
    @GetMapping("/types")
    public String types() {
        //  int i = 1 / 0;

        /*博客首页为空时进行异常处理，返回404
        String Blog = null;
        if(Blog == null){
            throw new NotFoundException("博客不存在");
        }*/
        //System.out.println("---------homepage---id:{" + id + "}--name:{" + name + "}---------");
        return "types";
    }

    /*通过Get请求路径 返回标签页*/
    @GetMapping("/tags")
    public String tags() {
        //  int i = 1 / 0;

        /*博客首页为空时进行异常处理，返回404
        String Blog = null;
        if(Blog == null){
            throw new NotFoundException("博客不存在");
        }*/
        //System.out.println("---------homepage---id:{" + id + "}--name:{" + name + "}---------");
        return "tags";
    }

    /*通过Get请求路径 返回归档页*/
    @GetMapping("/archives")
    public String archives() {
        //  int i = 1 / 0;

        /*博客首页为空时进行异常处理，返回404
        String Blog = null;
        if(Blog == null){
            throw new NotFoundException("博客不存在");
        }*/
        //System.out.println("---------homepage---id:{" + id + "}--name:{" + name + "}---------");
        return "archives";
    }

    /*通过Get请求路径 返回关于我页*/
    @GetMapping("/aboutme")
    public String aboutme() {
        //  int i = 1 / 0;

        /*博客首页为空时进行异常处理，返回404
        String Blog = null;
        if(Blog == null){
            throw new NotFoundException("博客不存在");
        }*/
        //System.out.println("---------homepage---id:{" + id + "}--name:{" + name + "}---------");
        return "aboutme";
    }

}
