package com.why.home.back_end.web;


import com.why.home.back_end.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*---------------------------------------------------------------
              CommentController Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/9/8.
;
;  Function:
;                   归档页处理
----------------------------------------------------------------*/

/*@Controller表示该类是控制层 控制层用于归档页管理*/
@Controller
public class ArchiveController {

    @Autowired
    private BlogService blogService;

    /*通过Get请求路径 返回归档页*/
    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archivesMap", blogService.archivesBlog());
        model.addAttribute("blogsNumber",blogService.countBlogs());
        return "archives";
    }
}
