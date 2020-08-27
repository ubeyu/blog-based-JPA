package com.why.home.back_end.dao;

import com.why.home.back_end.po.User;
import org.springframework.data.jpa.repository.JpaRepository;


/*---------------------------------------------------------------------
         UseRepository接口 <继承JpaRepository<User,Long>> Release 1.0
;    Copyright (c) 2020-2020 by why Co.
;    Written by why on 2020/8/22.
;             DAO 数据库操作层（使用SpringBoot中JPA）
;  Function:
;               第三步---数据库操作----查用户数据库
---------------------------------------------------------------------*/


/*------@Repository表示该类是Dao层-----*/
/*------ 继承JpaRepository<User,Long>后可直接对数据库进行增删改查 User为实现类 Long为主键类型----*/
public interface UserRepository extends JpaRepository<User,Long> {
    /*------@Repository表示该类是Dao层-----*/
    /*------ !!! Username和Password名字一定要对应 除首字母都小写 !!!----*/
    /*-----------如save/delete等不需声明的操作不必再写---------*/
    User findByUsernameAndPassword(String name,String password);
}
