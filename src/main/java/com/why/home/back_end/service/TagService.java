package com.why.home.back_end.service;

import com.why.home.back_end.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/*---------------------------------------------------------------
              TagsService Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/26.
;
;  Function:
;          第一步---标签业务逻辑处理层接口创建
----------------------------------------------------------------*/

public interface TagService {
    /*------定义增加标签的接口---根据Type类型新增-----*/
    Tag saveTag(Tag tag);

    /*------定义查询标签的接口---根据Long类型的id查询-----*/
    Tag getTag(Long id);

    /*------定义查询标签的接口---根据String类型的name查询-----*/
    Tag getTagByName(String name);

    /*------定义分页查询的接口---根据Pageable类型查询--返回一个Page<Type>-----*/
    Page<Tag> listTag(Pageable pageable);

    /*------定义修改标签的接口---根据Long类型id查询--然后根据Type类型修改-----*/
    Tag updateTag(Long id, Tag tag);

    /*------定义删除标签的接口---根据Long类型的id删除--使用void方法-----*/
    void deleteTag(Long id);
}
