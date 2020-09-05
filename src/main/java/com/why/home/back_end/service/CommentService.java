package com.why.home.back_end.service;


import com.why.home.back_end.po.Comment;

import java.util.List;

/*---------------------------------------------------------------
            CommentService Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/9/4.
;
;  Function:
;          第一步---评论管理业务逻辑处理层接口创建
----------------------------------------------------------------*/
public interface CommentService {


    /*------定义增加评论的接口---根据Comment类型新增-----*/
    Comment saveComment(Comment comment);

    /*------定义查询评论的接口---根据Comment的Long类型id查询-----*/
    Comment getComment(Long id);

    /*------定义获取指定博客全部评论的接口---根据Blog的Long类型id查询-----*/
    List<Comment> listCommentByBlogId(Long blogId);

    /*------定义删除评论的接口---根据Comment的Long类型id删除--使用void方法-----*/
    void deleteComment(Long id);
}
