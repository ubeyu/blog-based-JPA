package com.why.home.back_end.service;

import com.why.home.back_end.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/*---------------------------------------------------------------
              TypeService Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/24.
;
;  Function:
;          第一步---分类业务逻辑处理层接口创建
----------------------------------------------------------------*/

public interface TypeService {
    /*------定义增加分类的接口---根据Type类型新增-----*/
    Type saveType(Type type);

    /*------定义查询分类的接口---根据Long类型的id查询-----*/
    Type getType(Long id);

    /*------定义查询分类的接口---根据String类型的name查询-----*/
    Type getTypeByName(String name);

    /*------定义分页查询的接口---根据Pageable类型查询--返回一个Page<Type>-----*/
    Page<Type> listType(Pageable pageable);

    /*------定义获取全部分类的接口----返回一个List<Type>--用于博客管理页面分类下拉框的展示和选择---*/
    List<Type> listType();

    /*------定义获取文章最多的分类排行List的接口----返回一个List<Type>--用于用户页面展示 size表示显示排行前几个---*/
    List<Type> listTypeTop(Integer size);

    /*------定义修改分类的接口---根据Long类型id查询--然后根据Type类型修改-----*/
    Type updateType(Long id,Type type);

    /*------定义删除分类的接口---根据Long类型的id删除--使用void方法-----*/
    void deleteType(Long id);
}
