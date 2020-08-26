package com.why.home.back_end.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    /* 通过Get请求路径 返回管理页 */
    /* 加参数 同样在全局/admin下访问 即/admin/blogManage */
    @GetMapping("/blogManage")
    public String manage() {

        return "admin/blogs-manage";

    }

    /* 通过Get请求路径 返回新增页 */
    /* 加参数 同样在全局/admin下访问 即/admin/blogPublish */
    @GetMapping("/blogManage/add")
    public String publish() {

        return "admin/blogs-publish";

    }
}
