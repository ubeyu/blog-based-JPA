package com.why.home.back_end.web;


import com.why.home.back_end.po.Tag;
import com.why.home.back_end.po.Type;
import com.why.home.back_end.service.BlogService;
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

import java.util.List;

/*---------------------------------------------------------------
              CommentController Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/9/7.
;
;  Function:
;                   标签页处理
----------------------------------------------------------------*/

/*@Controller表示该类是控制层 控制层用于标签管理*/
@Controller
public class TagClientController {
    /* 列表显示 需要注入BlogService */
    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    /*通过Get请求路径 返回标签页 此处传入id为标签主键id */
    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 8 , sort = {"updateTime"} , direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model) {
        //可修改！！！
        List<Tag> tags=tagService.listTagTop(100);
        /* 判断id为-1 则访问以第一个标签为主题访问标签页 */
        if(id == -1){
            id = tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlogByTag(pageable,id));
        model.addAttribute("currentId",id);
        return "tags";
    }


}
