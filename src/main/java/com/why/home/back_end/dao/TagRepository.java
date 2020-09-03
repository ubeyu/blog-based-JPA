package com.why.home.back_end.dao;


import com.why.home.back_end.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*---------------------------------------------------------------------
         TagRepository接口 <继承JpaRepository<Tag,Long>> Release 1.0
;    Copyright (c) 2020-2020 by why Co.
;    Written by why on 2020/8/26.
;             DAO 数据库操作层（使用SpringBoot中JPA）
;  Function:
;               第三步---数据库操作----标签数据库操作
---------------------------------------------------------------------*/

/*------@Repository表示该类是Dao层-----*/
/*------ 继承JpaRepository<Tag,Long>后可直接对数据库进行增删改查 Tag为实现类 Long为主键类型----*/
public interface TagRepository extends JpaRepository<Tag,Long> {
    /*------@Repository表示该类是Dao层-----*/
    /*-----------如save/delete等已有的方法不必再写---------*/
    /*------ !!! deleteBy**这个方法，没有主键默认是新增，有主键的话则是修改 !!!----*/

    /*------Jpa TypeRepository未定义---自定义查询分类的接口---根据String类型的name查询-----*/
    Tag findByName(String name);



    /*------ 简单定义接口 在Service中实现-----*/
    /*------ @Query("")自定义查询-----*/
    @Query("select t from Tag t")
    /*------Pageable根据分页对象传递参数，分页对象内有排序及大小-----*/
    List<Tag> findTopTag(Pageable pageable);

}
