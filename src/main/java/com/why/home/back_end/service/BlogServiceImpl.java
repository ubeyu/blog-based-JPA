package com.why.home.back_end.service;



import com.why.home.back_end.NotFoundException;
import com.why.home.back_end.dao.BlogRepository;
import com.why.home.back_end.po.Blog;
import com.why.home.back_end.po.Type;
import com.why.home.back_end.util.MarkDownUtils;
import com.why.home.back_end.util.MyBeanUtils;
import com.why.home.back_end.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
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
    /*---- 可以对类成员变量、方法及构造函数进行标注 让Spring完成bean自动装配工作 (只有定义了BlogRepository，Spring才能自动生成下面的方法) ---*/
    @Autowired
    private BlogRepository blogRepository;

    /*--------新增---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        /*--------对创建/更新日期/浏览次数设置-----*/
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        return blogRepository.save(blog);
    }

    /*--------通过ID查询---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Blog getBlog(Long id) {
        /*-------此版本SpringBoot需将findOne(id)改为findById(id).get()-----*/
        return blogRepository.findById(id).get();
    }

    /* 博客详情页：本可以用getBlog查询id得到对应blog对象，但此方法得到的String类型content文本属于MarkDown语法，需要转化为HTML才能在博客详情页完整显示，所以在BlogService中利用MarkDownUtils工具引入新的方法处理  */
    @Transactional
    @Override
    public Blog getBlogMTH(Long id) {
        Blog blog=blogRepository.findById(id).get();
        if(blog == null){
            throw new NotFoundException("该博客不存在");
        }
        /*-----直接操作可能会改变数据库或Session，利用新对象处理-----*/
        Blog blog_new=new Blog();
        BeanUtils.copyProperties(blog,blog_new);
        /*------获取content，处理content，重设content-----*/
        blog_new.setContent(MarkDownUtils.markdownToHtmlExtensions(blog_new.getContent()));

        /*------每访问一次对应id博客的views加1-----*/
        blogRepository.updateViews(id);

        return blog_new;
    }

    /*------定义返回全部Blog的接口-----返回一个Page<Blog>-----*/
    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
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
    /*-------若直接用Blog会报错java.lang.NullPointerException: null，因为若查询时Type未输入，则.getId()本身会报错。改用直接将三个查询参数封装为一个对象的方式，将type.id封装为这个对象的Long类型id----*/
    @Transactional
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

    /*------实现获取查询得到的文章List的接口----返回一个List<Blog>--用于全局搜索 搜索的字符串是否包含在标题/正文中---*/
    @Override
    public Page<Blog> listBlogSearch(Pageable pageable,String query) {
        return blogRepository.findSearchBlog(query,pageable);
    }

    /*------实现获取文章最多的标签排行List的接口----返回一个List<Blog>--用于用户页面展示 size表示显示排行前几个---*/
    @Override
    public List<Blog> listBlogTop(Integer size) {
        Sort sort= Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable= PageRequest.of(0,size,sort);
        return blogRepository.findTopBlog(pageable);
    }

    /*--------更新---@Transactional将操作放入事务中-----*/
    /*--------一直报错"Id={46"是对此处修改有问题-----*/
    /*-------错误代码：
            首先根据id查blog
                Blog blog_cur=blogRepository.findById(id).get();
                if(blog_cur == null){
                    --------未查到则抛出异常---
                    throw new NotFoundException("该博客不存在");
                }
                  BeanUtils.copyProperties(tag,tag_cur);
       第二次修改
    @Transactional
    @Override
    public Blog updateBlog(Long id,Blog blog) {
        /*--------对更新日期修改----
        blog.setUpdateTime(new Date());
        return blogRepository.save(blog);
    }
     -----------------------------------------------*/

    @Transactional
    @Override
    public Blog updateBlog(Long id,Blog blog) {
        Blog blog_cur=blogRepository.findById(id).get();
        Date createTime=blog_cur.getCreateTime();
        if(blog_cur == null){
             /*---------未查到则抛出异常-----*/
            throw new NotFoundException("该博客不存在");
        }
        /*-------- MyBeanUtils.getNullPropertyNames(blog)存储了属性值为空的字段名称的数组
              放在BeanUtils.copyProperties第三个参数可以用于过滤blog中属性值为空的数组，不复制为空的
              相当于保留了createTime和views等属性值--------------*/
        BeanUtils.copyProperties(blog,blog_cur,MyBeanUtils.getNullPropertyNames(blog));
        /*--------对更新日期修改------*/
        blog_cur.setUpdateTime(new Date());
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
