package com.why.home.back_end.service;

import com.why.home.back_end.entity.User;

/*---------------------------------------------------------------
              UserService Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/21.
;
;  Function:
;          第一步---用户/管理员业务逻辑处理层接口创建
----------------------------------------------------------------*/

public interface UserService {
    /*------定义检查用户名和密码的接口-----*/
    User checkUser(String username, String password);
}
