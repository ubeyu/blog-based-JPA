package com.why.home.back_end.service;

import com.why.home.back_end.po.Blog;
import com.why.home.back_end.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    /* 博客详情页：本可以用getBlog查询id得到对应blog对象，但此方法得到的String类型content文本属于MarkDown语法，需要转化为HTML才能在博客详情页完整显示，所以在BlogService中利用MarkDownUtils工具引入新的方法处理  */
    Blog getBlogMTH(Long id);

    /*------定义返回全部Blog的接口-----返回一个Page<Blog>-----*/
    Page<Blog> listBlog(Pageable pageable);

    /*------（不再使用，变为listBlogQuery）定义分页查询的接口---根据Pageable类型和Blog条件（分类/标签/是否推荐）查询--返回一个Page<Blog>-----*/
    Page<Blog> listBlog(Pageable pageable,Blog blog);

    /*------定义分页查询的接口---根据Pageable类型和BlogQuery条件（分类/标签/是否推荐）查询--返回一个Page<Blog>-----*/
    Page<Blog> listBlogQuery(Pageable pageable, BlogQuery blogQuery);

    /*------定义获取查询得到的文章List的接口----返回一个List<Blog>--用于全局搜索 搜索的字符串是否包含在标题/正文中---*/
    Page<Blog> listBlogSearch(Pageable pageable,String query);

    /*------定义获取文章最多的标签排行List的接口----返回一个List<Blog>--用于用户页面展示 size表示显示排行前几个---*/
    List<Blog> listBlogTop(Integer size);

    /*------定义修改博客的接口---跟据Long类型id查询--然后根据Blog类型修改-----*/
    Blog updateBlog(Long id,Blog blog);

    /*------定义删除博客的接口---根据Long类型的id删除--使用void方法-----*/
    void deleteBlog(Long id);

}
