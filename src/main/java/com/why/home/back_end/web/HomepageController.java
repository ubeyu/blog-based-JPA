package com.why.home.back_end.web;

import com.why.home.back_end.NotFoundException;
import com.why.home.back_end.service.BlogService;
import com.why.home.back_end.service.BlogServiceImpl;
import com.why.home.back_end.service.TagService;
import com.why.home.back_end.service.TypeService;
import com.why.home.back_end.vo.BlogQuery;
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

    /* 分类显示 需要注入TypeService */
    @Autowired
    private TypeService typeService;

    /* 标签显示 需要注入TagService */
    @Autowired
    private TagService tagService;

    /*通过Get请求路径 返回首页*/
    @GetMapping("/")
    public String homepage(@PageableDefault(size = 8 , sort = {"updateTime"} , direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        /* Model存储查询后的分页信息 从而输出给前端页面 进行数据渲染 */
        /* blogService.listBlog(pageable,blog)返回类似JSON的信息 */
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listBlogTop(8));
        model.addAttribute("page",blogService.listBlog(pageable));
        return "homepage";
    }

    /*通过Get请求路径 返回博客详情页*/
    @GetMapping("/blogs/{id}")
    public String blogs() {

        return "blogs";
    }

    /* PostMapping 返回搜索结果页*/
    /*----- @RequestParam用于从前端拿到query值 -----*/
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8 , sort = {"updateTime"} , direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String query, Model model) {
        /* "%"+query+"%"用于模糊查询 */
        model.addAttribute("page",blogService.listBlogSearch(pageable,"%"+query+"%"));
        /* 将查询字符串返回到页面上去 */
        model.addAttribute("query",query);
        return "search";
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
