package com.why.home.back_end.web;


import com.why.home.back_end.po.Type;
import com.why.home.back_end.service.BlogService;
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
;                   分类页处理
----------------------------------------------------------------*/

/*@Controller表示该类是控制层 控制层用于分类管理*/
@Controller
public class TypeClientController {
    /* 列表显示 需要注入BlogService */
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    /*通过Get请求路径 返回分类页 此处传入id为分类主键id */
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8 , sort = {"updateTime"} , direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model) {
        //可修改！！！
        List<Type> types=typeService.listTypeTop(100);
        /* 判断id为-1 则访问以第一个分类为主题访问分类页 */
        if(types.size()>0 && id == -1){
            id = types.get(0).getId();
        }
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlogByType(pageable,id));
        model.addAttribute("currentId",id);
        return "types";
    }


}
