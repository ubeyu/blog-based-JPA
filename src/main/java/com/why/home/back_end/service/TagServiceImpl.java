package com.why.home.back_end.service;



import com.why.home.back_end.NotFoundException;
import com.why.home.back_end.dao.TagRepository;
import com.why.home.back_end.entity.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/*---------------------------------------------------------------
              TagServiceImpl Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/26.
;
;  Function:
;            第二步---TagService接口实现
----------------------------------------------------------------*/

/*------@Service表示该类是业务层-----*/
@Service
public class TagServiceImpl implements TagService {


    /*---------第三步TagRepository接口构建完成后注入-------*/
    /*---- 可以对类成员变量、方法及构造函数进行标注 让Spring完成bean自动装配工作 ---*/
    @Autowired
    private TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }



    /*--------新增---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Tag saveTag(Tag type) {
        /*------ !!! save这个方法，没有主键默认是新增，有主键的话则是修改 !!!----*/
        return tagRepository.save(type);
    }

    /*--------通过ID查询---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Tag getTag(Long id) {
        /*-------此版本SpringBoot需将findOne(id)改为findById(id).get()-----*/
        return tagRepository.findById(id).get();
    }

    /*--------通过Name查询---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    /*--------分页查询---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        /*-------分页查询Page<Type>对象-----*/
        return tagRepository.findAll(pageable);
    }

    /*--------更新---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        /*--------首先根据id查tag-----*/
        Tag tag_cur=tagRepository.findById(id).get();
        if( tag_cur == null){
            /*--------未查到则抛出异常-----*/
            throw new NotFoundException("该标签不存在");
        }
        /*--------将type属性复制到type_cur-----*/
        BeanUtils.copyProperties(tag,tag_cur);
        /*--------保存-----*/
        return tagRepository.save(tag_cur);
    }


    /*--------删除---@Transactional将操作放入事务中----*/
    @Transactional
    @Override
    public void deleteTag(Long id) {
        /*-------此版本SpringBoot需将delete改为deleteById-----**/
        tagRepository.deleteById(id);
    }

}
