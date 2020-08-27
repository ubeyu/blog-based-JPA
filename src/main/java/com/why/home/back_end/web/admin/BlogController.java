package com.why.home.back_end.web.admin;

import com.why.home.back_end.po.Blog;
import com.why.home.back_end.service.BlogService;
import com.why.home.back_end.service.TypeService;
import com.why.home.back_end.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*---------------------------------------------------------------
              AdminController Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/23.
;
;  Function:
;               用于访问博客管理和发布等页面
----------------------------------------------------------------*/

@Controller
@RequestMapping("/admin")
public class BlogController {

    /* 条件查询 需要注入BlogService */
    @Autowired
    private BlogService blogService;

    /* 分类下拉框 需要注入TypeService */
    @Autowired
    private TypeService typeService;

    /* 通过Get请求路径 返回管理页 */
    /* 加参数 同样在全局/admin下访问 即/admin/blogManage */
    @GetMapping("/blogManage")
        /*----Pageable是SpringBoot构造好的分页方法 会根据前端页面构造好的参数 封装到pageable对象
          @PageableDefault可以设置分页参数:
                            size代表分页每页存放数据个数 默认10
                            sort代表排序依据 此处按照"updateTime"排序
                            direction代表排序方式 此处DESC表示倒序----------------- */
    /* Blog换为BlogQuery 构造的查询对象 */
    public String manage(@PageableDefault(size = 5 , sort = {"updateTime"} , direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model){
        /* Model存储所有分类信息List 从而输出给前端页面 进行数据渲染 */
        /* typeService.listType()返回类似JSON的信息 */
        model.addAttribute("types",typeService.listType());
        /* Model存储查询后的分页信息 从而输出给前端页面 进行数据渲染 */
        /* blogService.listBlog(pageable,blog)返回类似JSON的信息 */
        model.addAttribute("page",blogService.listBlogQuery(pageable,blogQuery));
        return "admin/blogs-manage";

    }

    /* PostMapping 返回查询页 */
    /* 加参数 同样在全局/admin下访问 即/admin/blogManage/search */
    /* Blog换为BlogQuery 构造的查询对象 */
    @PostMapping("/blogManage/search")
    public String search(@PageableDefault(size = 5 , sort = {"updateTime"} , direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model){
        model.addAttribute("page",blogService.listBlogQuery(pageable,blogQuery));
        /* :: blogList用于返回管理页下面的blogList片段，用于局部渲染，除列表显示外，其他区域不刷新，可以在点击上下页时保留查询条件，此处与HTML中th:fragment="blogList"对应 */
        return "admin/blogs-manage :: blogList";

    }



    /* 通过Get请求路径 返回新增页 */
    /* 加参数 同样在全局/admin下访问 即/admin/blogPublish */
    @GetMapping("/blogManage/add")
    public String publish() {

        return "admin/blogs-publish";

    }
}
