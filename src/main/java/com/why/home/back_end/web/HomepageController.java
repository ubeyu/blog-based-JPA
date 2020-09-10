package com.why.home.back_end.web;

import com.why.home.back_end.po.Blog;
import com.why.home.back_end.po.Comment;
import com.why.home.back_end.service.BlogService;
import com.why.home.back_end.service.CommentService;
import com.why.home.back_end.service.TagService;
import com.why.home.back_end.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    /* 列表显示 需要注入BlogService */
    @Autowired
    private BlogService blogService;


    /*通过Get请求路径 返回首页*/
    @GetMapping("/")
    public String homepage(Model model) {
        /* Model存储查询后的分页信息 从而输出给前端页面 进行数据渲染 */
        /* blogService.listBlog(pageable,blog)返回类似JSON的信息 */
        List<Blog> blogs=blogService.listBlogTop(3);
        model.addAttribute("blog_1",blogs.get(0));
        model.addAttribute("blog_2",blogs.get(1));
        model.addAttribute("blog_3",blogs.get(2));
        return "home";
    }

    /* 通过Get请求路径 返回关于我页 */
    @GetMapping("/aboutme")
    public String aboutme() {
        return "aboutme";
    }

    /* 通过Get请求路径 底部局部刷新 */
    @GetMapping("/footer/newBlogs")
    public String newblogs(Model model) {
        model.addAttribute("newBlogs",blogService.listBlogTop(3));
        return "_fragments :: newBlogList";
    }

}
