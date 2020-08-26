package com.why.home.back_end.service;

import com.why.home.back_end.NotFoundException;
import com.why.home.back_end.dao.TypeRepository;
import com.why.home.back_end.entity.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/*---------------------------------------------------------------
              TypeServiceImpl Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/24.
;
;  Function:
;            第二步---TypeService接口实现
----------------------------------------------------------------*/

/*------@Service表示该类是业务层-----*/
@Service
public class TypeServiceImpl implements TypeService {


    /*---------第三步TypeRepository接口构建完成后注入-------*/
    /*---- 可以对类成员变量、方法及构造函数进行标注 让Spring完成bean自动装配工作 ---*/
    @Autowired
    private TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    /*--------新增-----*/
    /*--------将操作放入事务中-----*/
    @Transactional
    @Override
    public Type saveType(Type type) {
        /*------ !!! save这个方法，没有主键默认是新增，有主键的话则是修改 !!!----*/
        return typeRepository.save(type);
    }

    /*--------通过ID查询-----*/
    /*--------将操作放入事务中-----*/
    @Transactional
    @Override
    public Type getType(Long id) {
        /*-------此版本SpringBoot需将findOne改为findById(id).get()-----*/
        return typeRepository.findById(id).get();
    }

    /*--------通过Name查询-----*/
    /*--------将操作放入事务中-----*/
    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    /*--------分页查询-----*/
    /*--------将操作放入事务中-----*/
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        /*-------分页查询Page<Type>对象-----*/
        return typeRepository.findAll(pageable);
    }

    /*--------更新-----*/
    /*--------将操作放入事务中-----*/
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        /*--------首先根据id查type-----*/
        Type type_cur=typeRepository.findById(id).get();
        /*--------未查到则抛出异常-----*/
        if (type_cur == null){
            throw new NotFoundException("该分类不存在");
        }
        /*--------将修改type属性复制到查到的type-----*/
        BeanUtils.copyProperties(type,type_cur);
        /*--------保存-----*/
        return typeRepository.save(type_cur);
    }

    /*--------删除-----*/
    /*--------将操作放入事务中-----*/
    @Transactional
    @Override
    public void deleteType(Long id) {
        /*-------此版本SpringBoot需将delete改为deleteById-----*/
        typeRepository.deleteById(id);
    }

}
