package com.why.home.back_end.dao;


import com.why.home.back_end.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/*---------------------------------------------------------------------
         CommentRepository接口 <继承JpaRepository<Comment,Long>> Release 1.0
;    Copyright (c) 2020-2020 by why Co.
;    Written by why on 2020/9/4.
;             DAO 数据库操作层（使用SpringBoot中JPA）持久层
;  Function:
;               第三步---数据库操作----博客数据库操作
---------------------------------------------------------------------*/


/*------ @Repository表示该类是Dao层-----*/
/*------ 继承JpaRepository<Comment,Long>后可直接对数据库进行增删改查 Comment为实现类 Long为主键类型--------------*/
public interface CommentRepository extends JpaRepository<Comment,Long> {

    /*------ 自定义根据BlogID查询Comment列表的接口方法 Sort用于根据时间顺序排序----*/
    List<Comment> findByBlogId(Long blogId, Sort sort);



    /*--- 方式一 ----- 自定义根据BlogID查询父级Comment列表的接口方法 Sort用于根据时间顺序排序----*/
    @Query("select c from Comment c where c.parentComment = null ")
    List<Comment> findByBlogIdNullParent(Long blogId, Sort sort);
    /*--- 方式二 ---- 自定义根据BlogID查询父级Comment列表的接口方法 Sort用于根据时间顺序排序----*/
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);




}
