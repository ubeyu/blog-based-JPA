package com.why.home.back_end.dao;

import com.why.home.back_end.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/*---------------------------------------------------------------------
         BlogRepository接口 <继承JpaRepository<Blog,Long>> Release 1.0
;    Copyright (c) 2020-2020 by why Co.
;    Written by why on 2020/8/26.
;             DAO 数据库操作层（使用SpringBoot中JPA）
;  Function:
;               第三步---数据库操作----博客数据库操作
---------------------------------------------------------------------*/


/*------@Repository表示该类是Dao层-----*/
/*------ 继承JpaRepository<Blog,Long>后可直接对数据库进行增删改查 Blog为实现类 Long为主键类型--------------*/
/*------ 继承JpaSpecificationExecutor<Blog>后可使用SpringBoot封装好的高级查询库 用于博客的组合条件查询-----*/
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {


    /*------ 简单定义接口 在Service中实现-----*/
    /*------ @Query("")自定义查询--只选择被推荐的博客文章-----*/
    @Query("select b from Blog b where b.recommendOpening=true")
    /*------Pageable根据分页对象传递参数，分页对象内有排序及大小-----*/
    List<Blog> findTopBlog(Pageable pageable);

    /*------ 简单定义接口 在Service中实现-----*/
    /*------ @Query("")自定义查询--只选择标题或正文包含字符串的文章-----*/
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1 ")
    /*------Pageable根据分页对象传递参数，分页对象内有排序及大小-----*/
    Page<Blog> findSearchBlog(String query,Pageable pageable);
}
