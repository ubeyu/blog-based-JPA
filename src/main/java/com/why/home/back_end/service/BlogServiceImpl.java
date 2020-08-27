package com.why.home.back_end.service;



import com.why.home.back_end.NotFoundException;
import com.why.home.back_end.dao.BlogRepository;
import com.why.home.back_end.po.Blog;
import com.why.home.back_end.po.Type;
import com.why.home.back_end.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/*---------------------------------------------------------------
              BlogServiceImpl Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/26.
;
;  Function:
;            第二步---BlogService接口实现
----------------------------------------------------------------*/

/*------@Service表示该类是业务层-----*/
@Service
public class BlogServiceImpl implements BlogService {

    /*---------第三步BlogRepository接口构建完成后注入-------*/
    /*---- 可以对类成员变量、方法及构造函数进行标注 让Spring完成bean自动装配工作 ---*/
    @Autowired
    private BlogRepository blogRepository;

    /*--------新增---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    /*--------通过ID查询---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Blog getBlog(Long id) {
        /*-------此版本SpringBoot需将findOne(id)改为findById(id).get()-----*/
        return blogRepository.findById(id).get();
    }


    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, Blog blog) {
        /*--------blogRepository.findAll(Specification<Blog>,Pageable)-----用于条件查询-------*/
        return blogRepository.findAll(new Specification<Blog>() {
            /*-------传入new Specification<Blog>后自动生成--用于处理查询条件------*/
            @Override
            /*-------Root<Blog>代表要查询的对象，把Blog对象映射为一个Root；
                     CriteriaQuery<?>代表查询条件的容器，把条件放入其中；
                     CriteriaBuilder代表对查询条件表达式的设置 相等/模糊查询等------*/
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                /*------------------------------------------------------------添加查询条件(开始)---------------------------------------------------------------------*/
                /*------predicates用来封装条件-----*/
                List<Predicate> predicates=new ArrayList<>();
                /*-------博客标题查询---首先判断是否为空--若不为空则进行----*/
                if(!"".equals(blog.getTitle()) && blog.getTitle()!=null){
                    /*-------添加标题查询条件--like里面第一个参数代表属性名称 第二个参数代表属性值 "%"+blog.getTitle()+"%"前后拼接%才有效 代表将标题为该值的条件加入？------*/
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                /*-------博客分类查询---首先判断是否为空--若不为空则进行----*/
                /*-------会报错java.lang.NullPointerException: null，因为若查询时Type未输入，则.getId()本身会报错。改用直接将三个查询参数封装为一个对象的方式，将type.id封装为这个对象的Long类型id----*/
                if(blog.getType().getId()!=null){
                    /*-------添加分类查询条件--equal里面第一个参数代表属性名称 第二个参数代表属性值 代表将分类id为该值的条件加入------*/
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getType().getId()));
                }
                if(blog.isRecommendOpening()){
                    /*-------添加推荐查询条件--equal里面第一个参数代表属性名称 第二个参数代表属性值 代表将推荐的布尔值的条件加入------*/
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommendOpening"),blog.isRecommendOpening()));
                }
                /*------------------------------------------------------------添加查询条件(结束)---------------------------------------------------------------------*/

                /*------------------------------------------------使用criteriaQuery进行动态查询(开始)-----------------------------------------------------------------*/
                /*-------利用where方法---传入条件的数组形式参数----创建一个new Predicate[predicates.size()])数组 并指定大小---*/
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                /*------------------------------------------------使用criteriaQuery进行动态查询(结束)-----------------------------------------------------------------*/
                return null;
            }
        },pageable);
    }

    /*--------分页查询---@Transactional将操作放入事务中-----*/
    /*--------根据Pageable类型和Blog条件（分类/标签/是否推荐）查询--返回一个Page<Blog>-----*/
    /* Blog换为BlogQuery 构造的查询对象 */
    @Override
    public Page<Blog> listBlogQuery(Pageable pageable, BlogQuery blogQuery) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                if(!"".equals(blogQuery.getTitle()) && blogQuery.getTitle() != null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+blogQuery.getTitle()+"%"));
                }
                if(blogQuery.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blogQuery.getTypeId()));
                }
                if(blogQuery.isRecommendOpening()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommendOpening"),blogQuery.isRecommendOpening()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    /*--------更新---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        /*--------首先根据id查blog-----*/
        Blog blog_cur=blogRepository.findById(id).get();
        if(blog_cur == null){
            /*--------未查到则抛出异常-----*/
            throw new NotFoundException("该博客不存在");
        }
        /*--------将blog属性复制到blog_cur-----*/
        BeanUtils.copyProperties(blog,blog_cur);
        return blogRepository.save(blog_cur);
    }

    /*--------删除---@Transactional将操作放入事务中----*/
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        /*-------此版本SpringBoot需将delete改为deleteById-----**/
        blogRepository.deleteById(id);
    }
}
