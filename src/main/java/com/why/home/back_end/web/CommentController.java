package com.why.home.back_end.web;


import com.why.home.back_end.po.Comment;
import com.why.home.back_end.po.User;
import com.why.home.back_end.service.BlogService;
import com.why.home.back_end.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/*---------------------------------------------------------------
              CommentController Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/9/4.
;
;  Function:
;             评论处理
----------------------------------------------------------------*/


/*@Controller表示该类是控制层 控制层用于评论管理*/
@Controller
public class CommentController {

    /* 列表显示 需要注入BlogService */
    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;


    /* !!!!!${comment.avatar}!!!!通过yml配置文件直接注入路径 */
    @Value("${comment.avatar}")
    private String avatar;


    /*-------------------------------------------------博客页面评论显示逻辑（只刷新博客评论框）------------------------------------------------------------*/
    /* GetMapping 返回评论刷新页 id表示blog的id */
    @GetMapping("/comments/{blogId}")
    public String showBlogsComment(@PathVariable Long blogId, Model model) {
        /* 使用getBlogMTH：本可以用getBlog查询id得到对应blog对象，但此方法得到的String类型content文本属于MarkDown语法，需要转化为HTML才能在详情页完整显示，所以在BlogService中利用MarkDownUtils工具引入新的方法处理  */
        //model.addAttribute("comments",commentService.getComment(id));
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blogs::commentList";
    }
    /*-------------------------------------------------博客页面评论显示逻辑（只刷新博客评论框）------------------------------------------------------------*/

    /*-------------------------------------------------博客页面评论提交逻辑（只刷新博客评论框）------------------------------------------------------------*/
    /* PostMapping 提交评论 */
    @PostMapping("/comments")
    public String addBlogsComment(Comment comment, HttpSession session) {
        /* 从session中获取当前登录用户 */
        User user = (User) session.getAttribute("user");
        /* 获取提交评论的博客Id */
        Long blogId=comment.getBlog().getId();
        /* 将该评论设置关联博客和评论头像 */
        comment.setBlog(blogService.getBlog(blogId));
        /* 判断是否为博主，并做相应处理 */
        if(user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
           // comment.setNickname(user.getNickname());
        }else{
            comment.setAvatar(avatar);
            comment.setAdminComment(false);
        }
        /* 保存评论 */
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
    /*-------------------------------------------------博客页面评论提交逻辑（只刷新博客评论框）------------------------------------------------------------*/



}
