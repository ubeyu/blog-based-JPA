package com.why.home.back_end.service;

import com.why.home.back_end.NotFoundException;
import com.why.home.back_end.dao.TypeRepository;
import com.why.home.back_end.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    /*---- 可以对类成员变量、方法及构造函数进行标注 让Spring完成bean自动装配工作 (只有定义了TypeRepository，Spring才能自动生成下面的方法) ---*/
    @Autowired
    private TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    /*--------新增---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Type saveType(Type type) {
        /*------ !!! save这个方法，没有主键默认是新增，有主键的话则是修改 !!!----*/
        return typeRepository.save(type);
    }

    /*--------通过ID查询---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Type getType(Long id) {
        /*-------此版本SpringBoot需将findOne(id)改为findById(id).get()-----*/
        return typeRepository.findById(id).get();
    }

    /*--------通过Name查询---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    /*--------分页查询---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        /*-------分页查询Page<Type>对象-----*/
        return typeRepository.findAll(pageable);
    }

    /*------获取全部分类-----用于博客管理页面分类下拉框的展示和选择---*/
    @Transactional
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    /*-----(报错，未使用！！！)实现以文章数量由大到小的顺序获取全部分类的接口----返回一个List<Type>--用于用户分类页---*/
    @Transactional
    @Override
    public List<Type> listTypeByOrder() {
        Sort sort= Sort.by(Sort.Direction.DESC,"blogs.size");
        return typeRepository.findAll(sort);
    }

    /*------获取文章最多的分类排行----返回一个List<Type>--用于用户页面展示 size表示显示排行前几个---*/
    /*------typeRepository内没有的方法，需要去DAO层自定义---*/
    @Transactional
    @Override
    public List<Type> listTypeTop(Integer size) {
        /*------指定排序规则------------本版本Spring需要将new Sort(Sort.Direction.DESC,"blogs.size")改为这样的！！！利用的是Type实体类中的blogs集合取size------------------------*/
        Sort sort= Sort.by(Sort.Direction.DESC,"blogs.size");
        /*------取分页对象中第一页，指定size--------本版本Spring需要将new PageRequest(0,size,sort)改为这样的---------------------------*/
        Pageable pageable= PageRequest.of(0,size,sort);
        /*------取分页对象中第一页，指定size--------本版本Spring需要将pageable改为(java.awt.print.Pageable) pageable------------------*/
        return typeRepository.findTopType(pageable);
    }

    /*--------更新---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        /*--------首先根据id查type-----*/
        Type type_cur=typeRepository.findById(id).get();
        /*--------未查到则抛出异常-----*/
        if (type_cur == null){
            throw new NotFoundException("该分类不存在");
        }
        /*--------将type属性复制到type_cur-----*/
        BeanUtils.copyProperties(type,type_cur);
        /*--------保存-----*/
        return typeRepository.save(type_cur);
    }

    /*--------删除---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public void deleteType(Long id) {
        /*-------此版本SpringBoot需将delete改为deleteById-----*/
        typeRepository.deleteById(id);
    }

}
