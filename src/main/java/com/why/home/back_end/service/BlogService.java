package com.why.home.back_end.service;

import com.why.home.back_end.po.Blog;
import com.why.home.back_end.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/*---------------------------------------------------------------
              BlogService Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/26.
;
;  Function:
;          第一步---博客管理业务逻辑处理层接口创建
----------------------------------------------------------------*/

public interface BlogService {

    /*------定义增加博客的接口---根据Blog类型新增-----*/
    Blog saveBlog(Blog blog);

    /*------定义查询博客的接口---根据Long类型的id查询-----*/
    Blog getBlog(Long id);

    /*------定义分页查询的接口---根据Pageable类型和Blog条件（分类/标签/是否推荐）查询--返回一个Page<Blog>-----*/
    Page<Blog> listBlog(Pageable pageable,Blog blog);

    /*------定义分页查询的接口---根据Pageable类型和BlogQuery条件（分类/标签/是否推荐）查询--返回一个Page<Blog>-----*/
    Page<Blog> listBlogQuery(Pageable pageable, BlogQuery blogQuery);

    /*------定义修改博客的接口---直接保存更新时间（根据Long类型id查询--然后根据Blog类型修改）-----*/
    Blog updateBlog(Blog blog);

    /*------定义删除博客的接口---根据Long类型的id删除--使用void方法-----*/
    void deleteBlog(Long id);

}
