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
;    控制器---用于登录拦截---未登录用户禁止访问博客管理和发布等页面
;   --设置拦截器使凡是涉及到admin下的url的页面 必须登录后才可访问--
----------------------------------------------------------------*/

@Controller
@RequestMapping("/admin")
public class AdminController {

    /* 通过Get请求路径 返回管理页 */
    /* 加参数 同样在全局/admin下访问 即/admin/manage */
    @GetMapping("/manage")
    public String manage() {

        return "admin/blogs-manage";

    }

    /* 通过Get请求路径 返回发布页 */
    /* 加参数 同样在全局/admin下访问 即/admin/publish */
    @GetMapping("/publish")
    public String publish() {

        return "admin/blogs-publish";

    }
}
